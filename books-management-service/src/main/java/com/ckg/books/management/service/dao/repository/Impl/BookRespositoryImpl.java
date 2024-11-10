package com.ckg.books.management.service.dao.repository.Impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ckg.books.management.api.book.req.PageBookReq;
import com.ckg.books.management.api.common.resp.PageResult;
import com.ckg.books.management.common.exception.BizErrorCodes;
import com.ckg.books.management.common.exception.ExceptionHelper;
import com.ckg.books.management.common.mapper.LambdaQueryWrapperX;
import com.ckg.books.management.service.dao.entity.BookEntity;
import com.ckg.books.management.service.dao.mapper.BookMapper;
import com.ckg.books.management.service.dao.repository.BookRespository;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 * 图书 Respository 接口实现
 *
 * @author chenkaigui
 * @date 2024/11/8
 */
@Component
public class BookRespositoryImpl
        extends ServiceImpl<BookMapper, BookEntity> implements BookRespository {

    @Override
    public BookEntity getById(Long id, boolean throwNotFoundError) {
        BookEntity book = getById(id);
        if (null == book && throwNotFoundError) {
            throw ExceptionHelper
                    .create(BizErrorCodes.TABLE_RECORD_NOT_EXIST, "图书ID：{} 不存在", id);
        }
        return book;
    }

    @Override
    public List<BookEntity> findByNameLike(String name) {
        if (StrUtil.isBlank(name)) {
            return Collections.emptyList();
        }
        return list(new LambdaQueryWrapperX<BookEntity>().like(BookEntity::getName, name));
    }

    @Override
    public PageResult<BookEntity> searchPage(PageBookReq pageReq) {
        return getBaseMapper().selectPage(pageReq,
                new LambdaQueryWrapperX<BookEntity>()
                        .likeIfPresent(BookEntity::getName, pageReq.getName())
                        .likeIfPresent(BookEntity::getAuthor, pageReq.getAuthor())
                        .gtIfPresent(BookEntity::getAvailableQuantity,
                                pageReq.getMinAvailableQuantity())
                        .ltIfPresent(BookEntity::getAvailableQuantity,
                                pageReq.getMaxAvailableQuantity())
                        .gtIfPresent(BookEntity::getBorrowingQuantity,
                                pageReq.getMinBorrowingQuantity())
                        .ltIfPresent(BookEntity::getBorrowingQuantity,
                                pageReq.getMaxBorrowingQuantity())
                        .gtIfPresent(BookEntity::getPrice, pageReq.getMinPrice())
                        .ltIfPresent(BookEntity::getPrice, pageReq.getMaxPrice())
                        .likeIfPresent(BookEntity::getPublishingHouse, pageReq.getPublishingHouse())
                        .gtIfPresent(BookEntity::getPublishingTime,
                                pageReq.getFromPublicationTime())
                        .ltIfPresent(BookEntity::getPublishingTime,
                                pageReq.getToPublicationTime())
                        .eqIfPresent(BookEntity::getStatus, pageReq.getStatus())
                        .orderByDesc(BookEntity::getUpdateTime));
    }

    @Override
    public boolean increaseBorrowingQuantity(Long id, Integer borrowQuantity) {
        return getBaseMapper().increaseBorrowingQuantity(id, borrowQuantity) > 0;
    }

    @Override
    public boolean decreaseBorrowingQuantity(Long id, Integer returnQuantity) {
        return getBaseMapper().decreaseBorrowingQuantity(id, returnQuantity) > 0;
    }
}
