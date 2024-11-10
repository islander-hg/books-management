package com.ckg.books.management.service.sevice.book;

import com.ckg.books.management.api.book.resp.GetBookCategoryResp;
import com.ckg.books.management.common.domain.tree.TreeNode;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

/**
 * 图书分类查询操作 Service 接口
 *
 * @author chenkaigui
 * @date 2024/11/8
 */
@Validated
public interface BookCategoryQueryService {

    /**
     * 获取图书分类详情信息
     *
     * @param id 图书分类ID
     * @return 图书分类详情信息
     */
    GetBookCategoryResp get(@NotNull Long id);

    /**
     * 获取图书分类树
     *
     * @return 图书分类树
     */
    List<TreeNode> getTree();

}
