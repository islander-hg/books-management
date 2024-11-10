package com.ckg.books.management.server.web;

import com.ckg.books.management.api.book.req.CreateBookBorrowReq;
import com.ckg.books.management.api.book.req.PageBookBorrowReq;
import com.ckg.books.management.api.book.req.ReturnBookBorrowReq;
import com.ckg.books.management.api.book.req.UpdateBookBorrowReq;
import com.ckg.books.management.api.book.resp.GetBookBorrowResp;
import com.ckg.books.management.api.book.resp.PageBookBorrowItem;
import com.ckg.books.management.api.common.resp.CommonResp;
import com.ckg.books.management.api.common.resp.PageResult;
import com.ckg.books.management.service.sevice.book.BookBorrowOperateService;
import com.ckg.books.management.service.sevice.book.BookBorrowQueryService;
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
 * 图书借阅记录管理
 *
 * @author chenkaigui
 * @date 2024/11/8
 */
@Tag(name = "图书借阅管理")
@RestController
@RequestMapping("/book-borrow")
public class BookBorrowController {

    @Resource
    private BookBorrowOperateService bookBorrowOperateService;

    @Resource
    private BookBorrowQueryService bookBorrowQueryService;

    /**
     * 创建图书借阅记录
     *
     * @param createReq 创建请求信息
     * @return 请求响应
     */
    @Operation(summary = "创建图书借阅记录")
    @PreAuthorize("@ss.hasPermi('book-rorrow;create')")
    @PostMapping()
    public CommonResp create(@RequestBody CreateBookBorrowReq createReq) {
        bookBorrowOperateService.create(createReq);
        return CommonResp.success();
    }

    /**
     * 修改图书借阅记录
     *
     * @param updateReq 更新请求信息
     * @return 请求响应
     */
    @Operation(summary = "修改图书借阅记录",
            parameters = {@Parameter(name = "id", description = "图书借阅记录ID")})
    @PreAuthorize("@ss.hasPermi('book-borrow;update')")
    @PutMapping("/{id}")
    public CommonResp update(
            @PathVariable("id") Long id, @RequestBody UpdateBookBorrowReq updateReq) {
        bookBorrowOperateService.update(id, updateReq);
        return CommonResp.success();
    }

    /**
     * 删除图书借阅记录
     *
     * @param id 图书借阅记录ID
     * @return 请求响应
     */
    @Operation(summary = "删除图书借阅记录",
            parameters = {@Parameter(name = "id", description = "图书借阅记录ID")})
    @PreAuthorize("@ss.hasPermi('book-borrow;delete')")
    @DeleteMapping("/{id}")
    public CommonResp delete(@PathVariable("id") Long id) {
        bookBorrowOperateService.delete(id);
        return CommonResp.success();
    }

    /**
     * 归还图书
     *
     * @param id 图书借阅记录ID
     * @return 请求响应
     */
    @Operation(summary = "归还图书",
            parameters = {@Parameter(name = "id", description = "图书借阅记录ID")})
    @PreAuthorize("@ss.hasPermi('book-borrow;give-back')")
    @PutMapping("/{id}/give-back")
    public CommonResp giveBack(
            @PathVariable("id") Long id, @RequestBody ReturnBookBorrowReq returnReq) {
        bookBorrowOperateService.giveBack(id, returnReq);
        return CommonResp.success();
    }

    /**
     * 获取图书借阅记录详情信息
     *
     * @param id 图书借阅记录ID
     * @return 请求响应
     */
    @Operation(summary = "获取图书借阅记录详情信息",
            parameters = {@Parameter(name = "id", description = "图书借阅记录ID")})
    @PreAuthorize("@ss.hasPermi('book-borrow;get')")
    @GetMapping("/{id}")
    public CommonResp<GetBookBorrowResp> get(@PathVariable("id") Long id) {
        return CommonResp.success(bookBorrowQueryService.get(id));
    }

    /**
     * 分页查询图书借阅记录信息
     *
     * @param pageReq 分页请求信息
     * @return 请求响应
     */
    @Operation(summary = "分页查询图书借阅记录信息")
    @PreAuthorize("@ss.hasPermi('book-borrow;page-search')")
    @PostMapping("/page-search")
    public CommonResp<PageResult<PageBookBorrowItem>> searchPage(
            @RequestBody PageBookBorrowReq pageReq) {
        return CommonResp.success(bookBorrowQueryService.searchPage(pageReq));
    }

}
