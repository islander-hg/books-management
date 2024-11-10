package com.ckg.books.management.service.sevice.book.impl;

import cn.hutool.core.util.ArrayUtil;
import com.ckg.books.management.api.book.req.BaseOperateBookBorrowReq;
import com.ckg.books.management.api.book.req.CreateBookBorrowReq;
import com.ckg.books.management.api.book.req.ReturnBookBorrowReq;
import com.ckg.books.management.api.book.req.UpdateBookBorrowReq;
import com.ckg.books.management.common.exception.BizErrorCodes;
import com.ckg.books.management.common.exception.ExceptionHelper;
import com.ckg.books.management.common.utils.spring.BeanHelper;
import com.ckg.books.management.service.dao.entity.BookBorrowEntity;
import com.ckg.books.management.service.dao.entity.BookEntity;
import com.ckg.books.management.service.dao.repository.BookBorrowRespository;
import com.ckg.books.management.service.dao.repository.BookRespository;
import com.ckg.books.management.service.dao.repository.UserRespository;
import com.ckg.books.management.service.dao.utils.EntityHelper;
import com.ckg.books.management.service.sevice.book.BookBorrowOperateService;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * 图书借阅记录新增、变更操作 Service 接口
 *
 * @author chenkaigui
 * @date 2024/11/8
 */
@Slf4j
@Service
public class BookBorrowOperateServiceImpl implements BookBorrowOperateService {

    @Resource
    private BookRespository bookRespository;

    @Resource
    private BookBorrowRespository bookBorrowRespository;

    @Resource
    private UserRespository userRespository;

    @Resource
    private TransactionTemplate transactionTemplate;

    @Override
    public void create(CreateBookBorrowReq createReq) {
        //1. 借阅
        verifyTime(createReq);
        verifyBorrowQuantity(createReq);
        verifyUser(createReq.getUserId(), createReq.getOperator());

        //2. 创建
        BookBorrowEntity toBeCreatedEntity =
                BeanHelper.copyProperties(createReq, BookBorrowEntity.class);
        EntityHelper.fillBaseFieldValue(toBeCreatedEntity, true);
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                boolean increased =
                        bookRespository.increaseBorrowingQuantity(
                                createReq.getBookId(), createReq.getBorrowQuantity());
                if (!increased) {
                    verifyBorrowQuantity(createReq);
                    throw ExceptionHelper
                            .create(BizErrorCodes.UNABLE_CREATE_TABLE_RECORD_BECAUSE_UNKNOWN,
                                    "未知异常导致无法新增图书借阅数量，图书ID{}", createReq.getBookId());
                }
                boolean created = bookBorrowRespository.save(toBeCreatedEntity);
                if (!created) {
                    throw ExceptionHelper
                            .create(BizErrorCodes.UNABLE_CREATE_TABLE_RECORD_BECAUSE_UNKNOWN,
                                    "未知异常导致无法新增图书借阅记录，图书ID{}", createReq.getBookId());
                }
            }
        });
    }

    @Override
    public void update(Long id, UpdateBookBorrowReq updateReq) {
        //1. 校验
        verifyTime(updateReq);
        bookBorrowRespository.getById(id, true);
        verifyUser(updateReq.getOperator());

        //2. 修改
        BookBorrowEntity toBeUpdatedEntity =
                BeanHelper.copyProperties(updateReq, BookBorrowEntity.class);
        toBeUpdatedEntity.setId(id);
        EntityHelper.fillBaseFieldValue(toBeUpdatedEntity, false);

        boolean updated = bookBorrowRespository.updateById(toBeUpdatedEntity);
        if (!updated) {
            throw ExceptionHelper.create(BizErrorCodes.UNABLE_UPDATE_TABLE_RECORD_BECAUSE_UNKNOWN,
                    "未知异常导致无法修改图书借阅记录，借阅记录ID：{}", id);
        }
    }

    @Override
    public void delete(Long id) {
        throw ExceptionHelper.create(BizErrorCodes.CURRENTLY_UNSUPPORTED, "暂不支持删除图书解决记录");
//        //1. 校验存在性 并 执行删除
//        bookBorrowRespository.getById(id, true);
//        boolean deleted = bookBorrowRespository.removeById(id);
//        if (deleted) {
//            return;
//        }
//
//        //2. 校验删除失败原因
//        bookBorrowRespository.getById(id, true);
//        throw new ServiceException("未知异常导致无法删除图书借阅记录：{}", id);
    }

    @Override
    public void giveBack(Long id, ReturnBookBorrowReq returnReq) {
        //1. 校验
        BookBorrowEntity bookBorrow = verifyReturnQuantityAndGetRecord(id, returnReq);

        //2. 更新
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                boolean increased =
                        bookBorrowRespository
                                .increaseReturnQuantity(id, returnReq.getReturnQuantity());
                if (!increased) {
                    verifyReturnQuantityAndGetRecord(id, returnReq);
                    throw ExceptionHelper
                            .create(BizErrorCodes.UNABLE_UPDATE_TABLE_RECORD_BECAUSE_UNKNOWN,
                                    "未知异常导致无法新增图书归还数量，图书ID：{}", bookBorrow.getBookId());
                }

                boolean decreased =
                        bookRespository.decreaseBorrowingQuantity(
                                bookBorrow.getBookId(), returnReq.getReturnQuantity());
                if (!decreased) {
                    throw ExceptionHelper
                            .create(BizErrorCodes.UNABLE_UPDATE_TABLE_RECORD_BECAUSE_UNKNOWN,
                                    "未知异常导致无法新增图书可借阅数量，图书ID：{}", bookBorrow.getBookId());
                }
            }
        });
    }

    /**
     * 校验时间大小是否合规
     *
     * @param operateReq 操作图书借阅记录请求信息
     */
    private void verifyTime(BaseOperateBookBorrowReq operateReq) {
        int compareResult = operateReq.getPlanReturnTime().compareTo(operateReq.getBorrowTime());
        if (compareResult < 0) {
            throw ExceptionHelper
                    .create(BizErrorCodes.BORROW_VERIFY_FAILED, "计划归还时间不能早于借阅时间");
        }
        compareResult = operateReq.getFinalReturnTime().compareTo(operateReq.getBorrowTime());
        if (compareResult < 0) {
            throw ExceptionHelper
                    .create(BizErrorCodes.BORROW_VERIFY_FAILED, "实际归还时间不能早于借阅时间");
        }
    }

    /**
     * 校验借阅数量
     *
     * @param createReq 创建请求信息
     */
    private void verifyBorrowQuantity(CreateBookBorrowReq createReq) {
        BookEntity book = bookRespository.getById(createReq.getBookId(), true);
        if (book.getAvailableQuantity() < createReq.getBorrowQuantity()) {
            throw ExceptionHelper
                    .create(BizErrorCodes.INSUFFICIENT_QUANTITY,
                            "图书：{} 剩余可借阅数量不足，剩余可借阅数：{}",
                            book.getName(), book.getAvailableQuantity());
        }
    }

    /**
     * 校验归还数量
     *
     * @param id        图书借阅记录ID
     * @param returnReq 归还请求信息
     * @return 图书借阅记录
     */
    private BookBorrowEntity verifyReturnQuantityAndGetRecord(
            Long id, ReturnBookBorrowReq returnReq) {
        BookBorrowEntity bookBorrow = bookBorrowRespository.getById(id, true);
        Integer refundableQuantity =
                bookBorrow.getBorrowQuantity() - bookBorrow.getReturnQuantity();
        if (refundableQuantity == 0) {
            throw ExceptionHelper
                    .create(BizErrorCodes.RETURN_VERIFY_FAILED, "本次借阅图书已经全部归还");
        }
        if (refundableQuantity < returnReq.getReturnQuantity()) {
            throw ExceptionHelper
                    .create(BizErrorCodes.RETURN_VERIFY_FAILED, "本次图书归还数量大于剩余应归还数量");
        }

        return bookBorrow;
    }

    /**
     * 校验用户
     *
     * @param userIds 用户ID列表
     */
    private void verifyUser(Long... userIds) {
        if (ArrayUtil.isEmpty(userIds)) {
            return;
        }
        // 校验用户存在性
        for (Long userId : userIds) {
            if (null == userId) {
                continue;
            }
            userRespository.getById(userId, true);
        }
    }

}
