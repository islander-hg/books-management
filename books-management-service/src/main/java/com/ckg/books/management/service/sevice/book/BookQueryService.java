package com.ckg.books.management.service.sevice.book;

import com.ckg.books.management.api.book.req.PageBookReq;
import com.ckg.books.management.api.book.resp.GetBookResp;
import com.ckg.books.management.api.book.resp.PageBookItem;
import com.ckg.books.management.common.domain.resp.PageResult;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

/**
 * 图书查询操作 Service 接口
 *
 * @author chenkaigui
 * @date 2024/11/8
 */
@Validated
public interface BookQueryService {

    /**
     * 获取图书详情信息
     *
     * @param id 图书ID
     * @return 图书详情信息
     */
    GetBookResp get(@NotNull Long id);

    /**
     * 分页查询图书信息
     *
     * @param pageReq 分页请求信息
     * @return 分页结果
     */
    PageResult<PageBookItem> searchPage(@Valid PageBookReq pageReq);
}
