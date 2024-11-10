package com.ckg.books.management.service.dao.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ckg.books.management.api.book.req.PageBookBorrowReq;
import com.ckg.books.management.common.domain.resp.PageResult;
import com.ckg.books.management.service.dao.entity.BookBorrowEntity;

/**
 * 图书借阅记录 Respository 接口实现
 *
 * @author chenkaigui
 * @date 2024/11/8
 */
public interface BookBorrowRespository extends IService<BookBorrowEntity> {

    /**
     * 根据图书借阅记录ID获取图书信息
     *
     * @param id                 图书借阅记录ID
     * @param throwNotFoundError 图书借阅记录信息不存在时是否抛异常; true-是 false-否
     * @return 图书信息
     */
    BookBorrowEntity getById(Long id, boolean throwNotFoundError);

    /**
     * 分页查询图书借阅记录信息
     *
     * @param pageReq 分页请求信息
     * @return 图书借阅记录信息分页结果
     */
    PageResult<BookBorrowEntity> searchPage(PageBookBorrowReq pageReq);

    /**
     * 增加退还数量
     *
     * @param id             图书借阅记录ID
     * @param returnQuantity 归还数量
     * @return true-增加成功 false-增加失败
     */
    boolean increaseReturnQuantity(Long id, Integer returnQuantity);

}
