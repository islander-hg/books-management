package com.ckg.books.management.server.web;

import com.ckg.books.management.api.common.resp.CommonResp;
import com.ckg.books.management.api.common.resp.PageResult;
import com.ckg.books.management.api.role.req.CreateRoleReq;
import com.ckg.books.management.api.role.req.PageRoleReq;
import com.ckg.books.management.api.role.req.UpdateRoleReq;
import com.ckg.books.management.api.role.resp.GetRoleResp;
import com.ckg.books.management.api.role.resp.PageRoleItem;
import com.ckg.books.management.service.sevice.role.RoleOperateService;
import com.ckg.books.management.service.sevice.role.RoleQueryService;
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
 * 角色管理
 *
 * @author chenkaigui
 * @date 2024/11/8
 */
@Tag(name = "角色管理")
@RestController
@RequestMapping("/role")
public class RoleController {

    @Resource
    private RoleOperateService roleOperateService;

    @Resource
    private RoleQueryService roleQueryService;

    /**
     * 创建角色
     *
     * @param createReq 创建请求信息
     * @return 请求响应
     */
    @Operation(summary = "创建角色")
    @PreAuthorize("@ss.hasPermi('role;create')")
    @PostMapping()
    public CommonResp create(@RequestBody CreateRoleReq createReq) {
        roleOperateService.create(createReq);
        return CommonResp.success();
    }

    /**
     * 修改角色
     *
     * @param updateReq 更新请求信息
     * @return 请求响应
     */
    @Operation(summary = "修改角色",
            parameters = {@Parameter(name = "id", description = "角色ID")})
    @PreAuthorize("@ss.hasPermi('role;update')")
    @PutMapping("/{id}")
    public CommonResp update(
            @PathVariable("id") Long id, @RequestBody UpdateRoleReq updateReq) {
        roleOperateService.update(id, updateReq);
        return CommonResp.success();
    }

    /**
     * 删除角色
     *
     * @param id 角色ID
     * @return 请求响应
     */
    @Operation(summary = "删除角色",
            parameters = {@Parameter(name = "id", description = "角色ID")})
    @PreAuthorize("@ss.hasPermi('role;delete')")
    @DeleteMapping("/{id}")
    public CommonResp delete(@PathVariable("id") Long id) {
        roleOperateService.delete(id);
        return CommonResp.success();
    }

    /**
     * 获取角色详情信息
     *
     * @param id 角色ID
     * @return 请求响应
     */
    @Operation(summary = "获取角色详情信息",
            parameters = {@Parameter(name = "id", description = "角色ID")})
    @PreAuthorize("@ss.hasPermi('role;get')")
    @GetMapping("/{id}")
    public CommonResp<GetRoleResp> get(@PathVariable("id") Long id) {
        return CommonResp.success(roleQueryService.get(id));
    }

    /**
     * 分页查询角色信息
     *
     * @param pageReq 分页请求信息
     * @return 请求响应
     */
    @Operation(summary = "分页查询角色信息")
    @PreAuthorize("@ss.hasPermi('role;page-search')")
    @PostMapping("/page-search")
    public CommonResp<PageResult<PageRoleItem>> searchPage(@RequestBody PageRoleReq pageReq) {
        return CommonResp.success(roleQueryService.searchPage(pageReq));
    }

}
