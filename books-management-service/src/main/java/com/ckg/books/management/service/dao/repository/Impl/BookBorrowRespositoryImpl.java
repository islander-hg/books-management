package com.ckg.books.management.service.dao.repository.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ckg.books.management.api.book.req.PageBookBorrowReq;
import com.ckg.books.management.common.domain.resp.PageResult;
import com.ckg.books.management.common.exception.BizErrorCodes;
import com.ckg.books.management.common.exception.ExceptionHelper;
import com.ckg.books.management.common.mapper.LambdaQueryWrapperX;
import com.ckg.books.management.service.dao.entity.BookBorrowEntity;
import com.ckg.books.management.service.dao.mapper.BookBorrowMapper;
import com.ckg.books.management.service.dao.repository.BookBorrowRespository;
import org.springframework.stereotype.Component;

/**
 * 图书借阅记录 Respository 接口实现
 *
 * @author chenkaigui
 * @date 2024/11/9
 */
@Component
public class BookBorrowRespositoryImpl
        extends ServiceImpl<BookBorrowMapper, BookBorrowEntity>
        implements BookBorrowRespository {

    @Override
    public BookBorrowEntity getById(Long id, boolean throwNotFoundError) {
        BookBorrowEntity bookBorrow = getById(id);
        if (null == bookBorrow && throwNotFoundError) {
            throw ExceptionHelper
                    .create(BizErrorCodes.TABLE_RECORD_NOT_EXIST, "图书借阅记录不存在");
        }
        return bookBorrow;
    }

    @Override
    public PageResult<BookBorrowEntity> searchPage(PageBookBorrowReq pageReq) {
        return getBaseMapper().selectPage(pageReq,
                new LambdaQueryWrapperX<BookBorrowEntity>()
                        .eqIfPresent(BookBorrowEntity::getBookId, pageReq.getBookId())
                        .eqIfPresent(BookBorrowEntity::getUserId, pageReq.getUserId())
                        .gtIfPresent(BookBorrowEntity::getBorrowQuantity,
                                pageReq.getMinBorrowQuantity())
                        .ltIfPresent(BookBorrowEntity::getBorrowQuantity,
                                pageReq.getMaxBorrowQuantity())
                        .gtIfPresent(BookBorrowEntity::getBorrowTime,
                                pageReq.getFromBorrowTime())
                        .ltIfPresent(BookBorrowEntity::getBorrowTime,
                                pageReq.getToBorrowTime())
                        .gtIfPresent(BookBorrowEntity::getPlanReturnTime,
                                pageReq.getFromPlanReturnTime())
                        .ltIfPresent(BookBorrowEntity::getPlanReturnTime,
                                pageReq.getToPlanReturnTime())
                        .gtIfPresent(BookBorrowEntity::getFinalReturnTime,
                                pageReq.getFromPlanReturnTime())
                        .ltIfPresent(BookBorrowEntity::getFinalReturnTime,
                                pageReq.getToPlanReturnTime())
                        .eqIfPresent(BookBorrowEntity::getOperator, pageReq.getOperator())
                        .orderByDesc(BookBorrowEntity::getUpdateTime));
    }

    @Override
    public boolean increaseReturnQuantity(Long id, Integer returnQuantity) {
        return getBaseMapper().increaseReturnQuantity(id, returnQuantity) > 0;
    }
}
