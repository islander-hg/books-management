package com.ckg.books.management.service.sevice.book;

import com.ckg.books.management.api.book.req.PageBookBorrowReq;
import com.ckg.books.management.api.book.resp.GetBookBorrowResp;
import com.ckg.books.management.api.book.resp.PageBookBorrowItem;
import com.ckg.books.management.common.domain.resp.PageResult;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

/**
 * 图书借阅记录查询操作 Service 接口
 *
 * @author chenkaigui
 * @date 2024/11/8
 */
@Validated
public interface BookBorrowQueryService {

    /**
     * 获取图书借阅记录详情信息
     *
     * @param id 图书借阅记录ID
     * @return 图书借阅记录详情信息
     */
    GetBookBorrowResp get(@NotNull Long id);

    /**
     * 分页查询图书借阅记录信息
     *
     * @param pageReq 分页请求信息
     * @return 分页结果
     */
    PageResult<PageBookBorrowItem> searchPage(@Valid PageBookBorrowReq pageReq);
}
