package com.ckg.books.management.service.sevice.book;

import com.ckg.books.management.api.book.req.CreateBookCategoryReq;
import com.ckg.books.management.api.book.req.UpdateBookCategoryReq;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

/**
 * 图书分类新增、变更操作 Service 接口
 *
 * @author chenkaigui
 * @date 2024/11/8
 */
@Validated
public interface BookCategoryOperateService {

    /**
     * 创建图书分类
     *
     * @param createReq 创建请求信息
     */
    void create(@Valid CreateBookCategoryReq createReq);

    /**
     * 修改图书分类
     *
     * @param id        图书分类ID
     * @param updateReq 修改请求信息
     */
    void update(@NotNull Long id, @Valid UpdateBookCategoryReq updateReq);

    /**
     * 删除图书分类
     *
     * @param id 图书分类ID
     */
    void delete(@NotNull Long id);

}
