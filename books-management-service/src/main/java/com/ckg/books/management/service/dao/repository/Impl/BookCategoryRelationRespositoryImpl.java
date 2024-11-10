package com.ckg.books.management.service.dao.repository.Impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ckg.books.management.common.exception.BizErrorCodes;
import com.ckg.books.management.common.exception.ExceptionHelper;
import com.ckg.books.management.common.mapper.LambdaQueryWrapperX;
import com.ckg.books.management.service.dao.entity.BookCategoryRelationEntity;
import com.ckg.books.management.service.dao.mapper.BookCategoryRelationMapper;
import com.ckg.books.management.service.dao.repository.BookCategoryRelationRespository;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 图书和图书分类关联 Respository 接口实现
 *
 * @author chenkaigui
 * @date 2024/11/10
 */
@Component
public class BookCategoryRelationRespositoryImpl
        extends ServiceImpl<BookCategoryRelationMapper, BookCategoryRelationEntity>
        implements BookCategoryRelationRespository {

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void insertRelation(Long bookId, Collection<Long> categoryIds) {
        if (CollUtil.isEmpty(categoryIds)) {
            return;
        }
        List<BookCategoryRelationEntity> toBeCreatedEntities =
                categoryIds.stream()
                        .map(categoryId -> new BookCategoryRelationEntity()
                                .setBookId(bookId).setCategoryId(categoryId))
                        .collect(Collectors.toList());
        saveBatch(toBeCreatedEntities);
    }

    @Override
    public void deleteByBookId(Long bookId) {
        remove(new LambdaQueryWrapper<BookCategoryRelationEntity>()
                .eq(BookCategoryRelationEntity::getBookId, bookId));
    }

    @Override
    public void deleteByCategoryId(Long categoryId) {
        remove(new LambdaQueryWrapper<BookCategoryRelationEntity>()
                .eq(BookCategoryRelationEntity::getCategoryId, categoryId));
    }

    @Override
    public BookCategoryRelationEntity getByBookIdAndCategoryId(
            Long bookId, Long categoryId, boolean throwNotFoundError) {
        BookCategoryRelationEntity relation =
                getOne(new LambdaQueryWrapperX<BookCategoryRelationEntity>()
                        .eq(BookCategoryRelationEntity::getBookId, bookId)
                        .eq(BookCategoryRelationEntity::getCategoryId, categoryId));
        if (null == relation && throwNotFoundError) {
            throw ExceptionHelper
                    .create(BizErrorCodes.TABLE_RECORD_NOT_EXIST,
                            "图书ID：{} 与图书分类ID：{} 不存在关联信息", bookId, categoryId);
        }
        return relation;
    }

    @Override
    public List<BookCategoryRelationEntity> findByCategoryIdIn(Collection<Long> categoryIds) {
        if (CollUtil.isEmpty(categoryIds)) {
            return Collections.emptyList();
        }
        return list(
                new LambdaQueryWrapperX<BookCategoryRelationEntity>()
                        .in(BookCategoryRelationEntity::getCategoryId, categoryIds));
    }

    @Override
    public List<BookCategoryRelationEntity> findByBookIdIn(Collection<Long> bookIds) {
        if (CollUtil.isEmpty(bookIds)) {
            return Collections.emptyList();
        }
        return list(
                new LambdaQueryWrapperX<BookCategoryRelationEntity>()
                        .in(BookCategoryRelationEntity::getBookId, bookIds));
    }

}
