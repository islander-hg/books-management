package com.ckg.books.management.service.dao.repository.Impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ckg.books.management.common.exception.BizErrorCodes;
import com.ckg.books.management.common.exception.ExceptionHelper;
import com.ckg.books.management.common.mapper.LambdaQueryWrapperX;
import com.ckg.books.management.service.dao.entity.BookCategoryEntity;
import com.ckg.books.management.service.dao.mapper.BookCategoryMapper;
import com.ckg.books.management.service.dao.repository.BookCategoryRespository;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 * 图书分类 Respository 接口实现
 *
 * @author chenkaigui
 * @date 2024/11/8
 */
@Component
public class BookCategoryRespositoryImpl
        extends ServiceImpl<BookCategoryMapper, BookCategoryEntity>
        implements BookCategoryRespository {

    @Override
    public BookCategoryEntity getById(Long id, boolean throwNotFoundError) {
        BookCategoryEntity bookCategory = getById(id);
        if (null == bookCategory && throwNotFoundError) {
            throw ExceptionHelper
                    .create(BizErrorCodes.TABLE_RECORD_NOT_EXIST, "图书分类不存在");
        }
        return bookCategory;
    }

    @Override
    public BookCategoryEntity getByParentIdAndName(Long parentId, String name) {
        return getOne(new LambdaQueryWrapperX<BookCategoryEntity>()
                .eq(BookCategoryEntity::getParentId, parentId)
                .eq(BookCategoryEntity::getName, name));
    }

    @Override
    public List<BookCategoryEntity> findByIdIn(Collection<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return Collections.emptyList();
        }
        return list(
                new LambdaQueryWrapperX<BookCategoryEntity>().in(BookCategoryEntity::getId, ids));
    }

    @Override
    public boolean hasChildren(Long id) {
        return CollUtil.isNotEmpty(
                list(new LambdaQueryWrapperX<BookCategoryEntity>()
                        .select(BookCategoryEntity::getId)
                        .eq(BookCategoryEntity::getParentId, id)));
    }

}
