package com.ckg.books.management.service.sevice.book.impl;

import cn.hutool.core.util.ObjectUtil;
import com.ckg.books.management.api.book.req.CreateBookCategoryReq;
import com.ckg.books.management.api.book.req.UpdateBookCategoryReq;
import com.ckg.books.management.common.exception.BizErrorCodes;
import com.ckg.books.management.common.exception.BizException;
import com.ckg.books.management.common.exception.ExceptionHelper;
import com.ckg.books.management.common.utils.spring.BeanHelper;
import com.ckg.books.management.service.dao.entity.BookCategoryEntity;
import com.ckg.books.management.service.dao.repository.BookCategoryRelationRespository;
import com.ckg.books.management.service.dao.repository.BookCategoryRespository;
import com.ckg.books.management.service.dao.utils.EntityHelper;
import com.ckg.books.management.service.sevice.book.BookCategoryOperateService;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

/**
 * 图书分类新增、变更操作 Service 接口
 *
 * @author chenkaigui
 * @date 2024/11/8
 */
@Slf4j
@Service
public class BookCategoryOperateServiceImpl implements BookCategoryOperateService {

    @Resource
    private BookCategoryRespository bookCategoryRespository;

    @Resource
    private BookCategoryRelationRespository bookCategoryRelationRespository;

    @Override
    public void create(CreateBookCategoryReq createReq) {
        //1. 参数预处理默认值
        createReq.setParentId(ObjectUtil.defaultIfNull(createReq.getParentId(), 0l));

        //2. 校验冲突
        verifyDataUniqueness(createReq.getParentId(),createReq.getName(), null);

        //3. 创建
        int level = createReq.getParentId().equals(0L) ? 1
                : bookCategoryRespository.getById(createReq.getParentId(), true).getLevel() + 1;
        BookCategoryEntity toBeCreatedEntity =
                BeanHelper.copyProperties(createReq, BookCategoryEntity.class);
        toBeCreatedEntity.setLevel(level);
        EntityHelper.fillBaseFieldValue(toBeCreatedEntity, true);
        try {
            boolean created = bookCategoryRespository.save(toBeCreatedEntity);
            if (!created) {
                throw ExceptionHelper
                        .create(BizErrorCodes.UNABLE_CREATE_TABLE_RECORD_BECAUSE_UNKNOWN,
                                "未知异常导致无法新增图书分类：{}", createReq.getName());
            }

        } catch (BizException ex) {
            throw ex;
        } catch (Exception ex) {
            if (ex instanceof DuplicateKeyException) {
                verifyDataUniqueness(createReq.getParentId(), createReq.getName(), null);
            }
            throw ExceptionHelper.create(BizErrorCodes.UNABLE_CREATE_TABLE_RECORD_BECAUSE_UNKNOWN,
                    "未知异常导致无法新增图书分类：{}", createReq.getName());
        }
    }

    @Override
    public void update(Long id, UpdateBookCategoryReq updateReq) {
        //1. 填充默认值
        updateReq.setParentId(ObjectUtil.defaultIfNull(updateReq.getParentId(), 0l));

        //2. 校验存在性和冲突
        BookCategoryEntity menu = bookCategoryRespository.getById(id);
        verifyDataUniqueness(menu.getParentId(),updateReq.getName(), id);

        //3. 修改
        BookCategoryEntity toBeUpdatedEntity =
                BeanHelper.copyProperties(updateReq, BookCategoryEntity.class);
        toBeUpdatedEntity.setId(id);
        EntityHelper.fillBaseFieldValue(toBeUpdatedEntity, false);

        try {
            boolean updated = bookCategoryRespository.updateById(toBeUpdatedEntity);
            if (!updated) {
                throw ExceptionHelper
                        .create(BizErrorCodes.UNABLE_UPDATE_TABLE_RECORD_BECAUSE_UNKNOWN,
                                "未知异常导致无法修改图书分类：{}", menu.getName());
            }
        } catch (BizException ex) {
            throw ex;
        } catch (Exception ex) {
            if (ex instanceof DuplicateKeyException) {
                verifyDataUniqueness(menu.getParentId(), updateReq.getName(), id);
            }
            throw ExceptionHelper.create(BizErrorCodes.UNABLE_UPDATE_TABLE_RECORD_BECAUSE_UNKNOWN,
                    "未知异常导致无法修改图书分类：{}", menu.getName());
        }
    }

    @Override
    public void delete(Long id) {
        //1. 校验是否可删除
        verifyDeletable(id);

        //2. 删除
        boolean deleted = bookCategoryRespository.removeById(id);
        if (!deleted) {
            verifyDeletable(id);
            throw ExceptionHelper
                    .create(BizErrorCodes.UNABLE_DELETE_TABLE_RECORD_BECAUSE_UNKNOWN,
                            "未知异常导致无法删除图书分类");
        }

        //3. 删除关联关系
        bookCategoryRelationRespository.deleteByCategoryId(id);
    }

    /**
     * 校验数据唯一性
     *
     * @param parentId  父节点ID
     * @param name      节点名称
     * @param excludeId 不在校验范围内的图书分类ID
     */
    private void verifyDataUniqueness(Long parentId, String name, @Nullable Long excludeId) {
        BookCategoryEntity menuEntity =
                bookCategoryRespository.getByParentIdAndName(parentId, name);
        if (null == menuEntity || menuEntity.getId().equals(excludeId)) {
            return;
        }
        throw ExceptionHelper
                .create(BizErrorCodes.TABLE_RECORD_DUPLICATE, "图书分类：{} 已存在", name);
    }

    /**
     * 验证是否可删除
     *
     * @param id 图书分类ID
     */
    private void verifyDeletable(Long id) {
        //1. 节点存在
        bookCategoryRespository.getById(id, true);
        //2. 无子节点
        if (bookCategoryRespository.hasChildren(id)) {
            throw ExceptionHelper
                    .create(BizErrorCodes.DELETE_NODE_WITH_CHILD_NODES_IS_NOT_ALLOWED,
                            "该图书分类下有子分类，请先删除子分类");
        }
    }

}
