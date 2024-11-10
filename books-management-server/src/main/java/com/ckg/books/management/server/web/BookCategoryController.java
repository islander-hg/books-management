package com.ckg.books.management.server.web;

import com.ckg.books.management.api.book.req.CreateBookCategoryReq;
import com.ckg.books.management.api.book.req.UpdateBookCategoryReq;
import com.ckg.books.management.api.book.resp.GetBookCategoryResp;
import com.ckg.books.management.api.common.resp.CommonResp;
import com.ckg.books.management.common.domain.tree.TreeNode;
import com.ckg.books.management.service.sevice.book.BookCategoryOperateService;
import com.ckg.books.management.service.sevice.book.BookCategoryQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
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
 * 图书分类管理
 *
 * @author chenkaigui
 * @date 2024/11/9
 */
@Tag(name = "图书分类管理")
@RestController
@RequestMapping("/book-category")
public class BookCategoryController {

    @Resource
    private BookCategoryOperateService bookCategoryOperateService;

    @Resource
    private BookCategoryQueryService bookCategoryQueryService;

    /**
     * 创建图书分类
     *
     * @param createReq 创建请求信息
     * @return 请求响应
     */
    @Operation(summary = "创建图书分类")
    @PreAuthorize("@ss.hasPermi('book-category:create')")
    @PostMapping()
    public CommonResp create(@RequestBody CreateBookCategoryReq createReq) {
        bookCategoryOperateService.create(createReq);
        return CommonResp.success();
    }

    /**
     * 修改图书分类
     *
     * @param updateReq 更新请求信息
     * @return 请求响应
     */
    @Operation(summary = "修改图书分类",
            parameters = {@Parameter(name = "id", description = "图书分类ID")})
    @PreAuthorize("@ss.hasPermi('book-category:update')")
    @PutMapping("/{id}")
    public CommonResp update(
            @PathVariable("id") Long id, @RequestBody UpdateBookCategoryReq updateReq) {
        bookCategoryOperateService.update(id, updateReq);
        return CommonResp.success();
    }

    /**
     * 删除图书分类
     *
     * @param id 图书分类ID
     * @return 请求响应
     */
    @Operation(summary = "删除图书分类",
            parameters = {@Parameter(name = "id", description = "图书分类ID")})
    @PreAuthorize("@ss.hasPermi('book-category:delete')")
    @DeleteMapping("/{id}")
    public CommonResp delete(@PathVariable("id") Long id) {
        bookCategoryOperateService.delete(id);
        return CommonResp.success();
    }

    /**
     * 获取图书分类详情信息
     *
     * @param id 图书分类ID
     * @return 请求响应
     */
    @Operation(summary = "获取图书分类详情信息",
            parameters = {@Parameter(name = "id", description = "图书分类ID")})
    @PreAuthorize("@ss.hasPermi('book-category:get')")
    @GetMapping("/{id}")
    public CommonResp<GetBookCategoryResp> get(@PathVariable("id") Long id) {
        return CommonResp.success(bookCategoryQueryService.get(id));
    }

    /**
     * 获取图书分类树
     *
     * @return 请求响应
     */
    @Operation(summary = "获取图书分类树")
    @PreAuthorize("@ss.hasPermi('book-category:tree')")
    @GetMapping("/tree")
    public CommonResp<List<TreeNode>> getTree() {
        return CommonResp.success(bookCategoryQueryService.getTree());
    }

}
