package com.ckg.books.management.service.sevice.book;

import com.ckg.books.management.api.book.req.CreateBookBorrowReq;
import com.ckg.books.management.api.book.req.ReturnBookBorrowReq;
import com.ckg.books.management.api.book.req.UpdateBookBorrowReq;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

/**
 * 图书借阅记录新增、变更操作 Service 接口
 *
 * @author chenkaigui
 * @date 2024/11/8
 */
@Validated
public interface BookBorrowOperateService {

    /**
     * 创建图书借阅记录
     *
     * @param createReq 创建请求信息
     */
    void create(@Valid CreateBookBorrowReq createReq);

    /**
     * 修改图书借阅记录
     *
     * @param id        图书借阅记录ID
     * @param updateReq 修改请求信息
     */
    void update(@NotNull Long id, @Valid UpdateBookBorrowReq updateReq);

    /**
     * 删除图书借阅记录
     *
     * @param id 图书借阅记录ID
     */
    void delete(@NotNull Long id);

    /**
     * 归还图书
     *
     * @param id        图书借阅记录ID
     * @param returnReq 归还请求信息
     */
    void giveBack(@NotNull Long id, @Valid ReturnBookBorrowReq returnReq);

}
