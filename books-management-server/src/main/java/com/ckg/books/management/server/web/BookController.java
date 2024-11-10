package com.ckg.books.management.server.web;

import com.ckg.books.management.api.book.req.CreateBookReq;
import com.ckg.books.management.api.book.req.PageBookReq;
import com.ckg.books.management.api.book.req.UpdateBookReq;
import com.ckg.books.management.api.book.resp.GetBookResp;
import com.ckg.books.management.api.book.resp.PageBookItem;
import com.ckg.books.management.api.common.resp.CommonResp;
import com.ckg.books.management.api.common.resp.PageResult;
import com.ckg.books.management.service.sevice.book.BookOperateService;
import com.ckg.books.management.service.sevice.book.BookQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.annotation.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 图书管理
 *
 * @author chenkaigui
 * @date 2024/11/9
 */
@Tag(name = "图书管理")
@RestController
@RequestMapping("/book")
public class BookController {

    @Resource
    private BookOperateService bookOperateService;

    @Resource
    private BookQueryService bookQueryService;

    /**
     * 创建图书
     *
     * @param createReq 创建请求信息
     * @return 请求响应
     */
    @Operation(summary = "创建图书")
    @PreAuthorize("@ss.hasPermi('book:create')")
    @PostMapping()
    public CommonResp create(@RequestBody CreateBookReq createReq) {
        bookOperateService.create(createReq);
        return CommonResp.success();
    }

    /**
     * 修改图书
     *
     * @param updateReq 更新请求信息
     * @return 请求响应
     */
    @Operation(summary = "修改图书",
            parameters = {@Parameter(name = "id", description = "图书ID")})
    @PreAuthorize("@ss.hasPermi('book:update')")
    @PutMapping("/{id}")
    public CommonResp update(
            @PathVariable("id") Long id, @RequestBody UpdateBookReq updateReq) {
        bookOperateService.update(id, updateReq);
        return CommonResp.success();
    }

    /**
     * 删除图书
     *
     * @param id 图书ID
     * @return 请求响应
     */
    @Operation(summary = "删除图书",
            parameters = {@Parameter(name = "id", description = "图书ID")})
    @PreAuthorize("@ss.hasPermi('book:delete')")
    @DeleteMapping("/{id}")
    public CommonResp delete(@PathVariable("id") Long id) {
        bookOperateService.delete(id);
        return CommonResp.success();
    }

    /**
     * 获取图书详情信息
     *
     * @param id 图书ID
     * @return 请求响应
     */
    @Operation(summary = "获取图书详情信息",
            parameters = {@Parameter(name = "id", description = "图书ID")})
    @PreAuthorize("@ss.hasPermi('book:get')")
    @GetMapping("/{id}")
    public CommonResp<GetBookResp> get(@PathVariable("id") Long id) {
        return CommonResp.success(bookQueryService.get(id));
    }

    /**
     * 分页查询图书信息
     *
     * @param pageReq 分页请求信息
     * @return 请求响应
     */
    @Operation(summary = "分页查询图书信息")
    @PreAuthorize("@ss.hasPermi('book:page-search')")
    @PostMapping("/page-search")
    public CommonResp<PageResult<PageBookItem>> searchPage(@RequestBody PageBookReq pageReq) {
        return CommonResp.success(bookQueryService.searchPage(pageReq));
    }

}
