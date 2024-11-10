package com.ckg.books.management.service.sevice.book.impl;

import cn.hutool.core.collection.CollUtil;
import com.ckg.books.management.api.book.resp.GetBookCategoryResp;
import com.ckg.books.management.common.domain.tree.TreeNode;
import com.ckg.books.management.common.utils.spring.BeanHelper;
import com.ckg.books.management.service.dao.entity.BookCategoryEntity;
import com.ckg.books.management.service.dao.repository.BookCategoryRespository;
import com.ckg.books.management.service.sevice.book.BookCategoryQueryService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 图书分类查询操作 Service 接口实现
 *
 * @author chenkaigui
 * @date 2024/11/8
 */
@Slf4j
@Service
public class BookCategoryQueryServiceImpl implements BookCategoryQueryService {

    @Resource
    private BookCategoryRespository bookCategoryRespository;

    @Override
    public GetBookCategoryResp get(Long id) {
        BookCategoryEntity bookCategoryEntity = bookCategoryRespository.getById(id, true);
        return BeanHelper.copyProperties(bookCategoryEntity, GetBookCategoryResp.class);
    }

    @Override
    public List<TreeNode> getTree() {
        return buildCategoryTree(bookCategoryRespository.list());
    }

    /**
     * 构建图书分类树
     *
     * @param categoryEntities 图书分类信息列表
     * @return 图书分类树
     */
    private List<TreeNode> buildCategoryTree(List<BookCategoryEntity> categoryEntities) {
        if (CollUtil.isEmpty(categoryEntities)) {
            return Collections.emptyList();
        }

        List<TreeNode> treeNodes = new ArrayList<>();
        List<Long> menuIds =
                categoryEntities.stream().map(BookCategoryEntity::getId)
                        .collect(Collectors.toList());

        for (Iterator<BookCategoryEntity> iterator = categoryEntities.iterator();
                iterator.hasNext(); ) {
            BookCategoryEntity menuEntity = iterator.next();
            // 遍历顶层父节点
            if (!menuIds.contains(menuEntity.getParentId())) {
                TreeNode treeNode = new TreeNode().setId(menuEntity.getId())
                        .setName(menuEntity.getName());
                buildCategoryTree(categoryEntities, treeNode);
                treeNodes.add(treeNode);
            }
        }
        return treeNodes;
    }

    /**
     * 构建图书分类树
     *
     * @param categoryEntities 图书分类信息列表
     * @param parentNode       当前父节点信息
     */
    private void buildCategoryTree(List<BookCategoryEntity> categoryEntities, TreeNode parentNode) {
        List<TreeNode> childNodeList = getChildList(categoryEntities, parentNode);
        parentNode.setChildren(childNodeList);
        for (TreeNode childNode : childNodeList) {
            buildCategoryTree(categoryEntities, childNode);
        }
    }

    /**
     * 获取子节点信息列表
     *
     * @param categoryEntities 图书分类信息列表
     * @param parentNode       父节点信息
     * @return 子节点信息列表
     */
    private List<TreeNode> getChildList(
            List<BookCategoryEntity> categoryEntities, TreeNode parentNode) {
        List<TreeNode> treeNodes = new ArrayList<>();
        for (BookCategoryEntity menuEntity : categoryEntities) {
            if (parentNode.getId().equals(menuEntity.getParentId())) {
                treeNodes.add(new TreeNode()
                        .setId(menuEntity.getId()).setName(menuEntity.getName()));
            }
        }
        return treeNodes;
    }

}
