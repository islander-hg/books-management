package com.ckg.books.management.service.sevice.book;

import com.ckg.books.management.api.book.req.CreateBookReq;
import com.ckg.books.management.api.book.req.UpdateBookReq;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

/**
 * 图书新增、变更操作 Service 接口
 *
 * @author chenkaigui
 * @date 2024/11/8
 */
@Validated
public interface BookOperateService {

    /**
     * 创建图书
     *
     * @param createReq 创建请求信息
     */
    void create(@Valid CreateBookReq createReq);

    /**
     * 修改图书
     *
     * @param id        图书ID
     * @param updateReq 修改请求信息
     */
    void update(@NotNull Long id, @Valid UpdateBookReq updateReq);

    /**
     * 删除图书
     *
     * @param id 图书ID
     */
    void delete(@NotNull Long id);

}
