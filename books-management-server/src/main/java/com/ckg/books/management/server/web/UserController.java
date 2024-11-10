package com.ckg.books.management.server.web;

import com.ckg.books.management.common.domain.resp.CommonResp;
import com.ckg.books.management.common.domain.resp.PageResult;
import com.ckg.books.management.api.user.req.CreateUserReq;
import com.ckg.books.management.api.user.req.PageUserReq;
import com.ckg.books.management.api.user.req.UpdateUserReq;
import com.ckg.books.management.api.user.resp.GetUserResp;
import com.ckg.books.management.api.user.resp.PageUserItem;
import com.ckg.books.management.service.sevice.user.UserOperateService;
import com.ckg.books.management.service.sevice.user.UserQueryService;
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
 * 用户管理
 *
 * @author chenkaigui
 * @date 2024/11/8
 */
@Tag(name = "用户管理")
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserOperateService userOperateService;

    @Resource
    private UserQueryService userQueryService;

    /**
     * 创建用户
     *
     * @param createReq 创建请求信息
     * @return 请求响应
     */
    @Operation(summary = "创建用户")
    @PreAuthorize("@ss.hasPermi('user:create')")
    @PostMapping()
    public CommonResp create(@RequestBody CreateUserReq createReq) {
        userOperateService.create(createReq);
        return CommonResp.success();
    }

    /**
     * 修改用户
     *
     * @param updateReq 更新请求信息
     * @return 请求响应
     */
    @Operation(summary = "修改用户",
            parameters = {@Parameter(name = "id", description = "用户ID")})
    @PreAuthorize("@ss.hasPermi('user:update')")
    @PutMapping("/{id}")
    public CommonResp update(
            @PathVariable("id") Long id, @RequestBody UpdateUserReq updateReq) {
        userOperateService.update(id, updateReq);
        return CommonResp.success();
    }

    /**
     * 删除用户
     *
     * @param id 用户ID
     * @return 请求响应
     */
    @Operation(summary = "删除用户",
            parameters = {@Parameter(name = "id", description = "用户ID")})
    @PreAuthorize("@ss.hasPermi('user:delete')")
    @DeleteMapping("/{id}")
    public CommonResp delete(@PathVariable("id") Long id) {
        userOperateService.delete(id);
        return CommonResp.success();
    }

    /**
     * 获取用户详情信息
     *
     * @param id 用户ID
     * @return 请求响应
     */
    @Operation(summary = "获取用户详情信息",
            parameters = {@Parameter(name = "id", description = "用户ID")})
    @PreAuthorize("@ss.hasPermi('user:get')")
    @GetMapping("/{id}")
    public CommonResp<GetUserResp> get(@PathVariable("id") Long id) {
        return CommonResp.success(userQueryService.get(id));
    }

    /**
     * 分页查询用户信息
     *
     * @param pageReq 分页请求信息
     * @return 请求响应
     */
    @Operation(summary = "分页查询用户信息")
    @PreAuthorize("@ss.hasPermi('user:page-search')")
    @PostMapping("/page-search")
    public CommonResp<PageResult<PageUserItem>> searchPage(@RequestBody PageUserReq pageReq) {
        return CommonResp.success(userQueryService.searchPage(pageReq));
    }

}
