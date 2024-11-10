package com.ckg.books.management.server.web;

import com.ckg.books.management.api.common.resp.CommonResp;
import com.ckg.books.management.api.menu.req.CreateMenuReq;
import com.ckg.books.management.api.menu.req.UpdateMenuReq;
import com.ckg.books.management.api.menu.resp.GetMenuResp;
import com.ckg.books.management.common.domain.tree.TreeNode;
import com.ckg.books.management.service.sevice.menu.MenuOperateService;
import com.ckg.books.management.service.sevice.menu.MenuQueryService;
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
 * 菜单管理
 *
 * @author chenkaigui
 * @date 2024/11/9
 */
@Tag(name = "菜单管理")
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Resource
    private MenuOperateService menuOperateService;

    @Resource
    private MenuQueryService menuQueryService;

    /**
     * 创建菜单
     *
     * @param createReq 创建请求信息
     * @return 请求响应
     */
    @Operation(summary = "创建菜单")
    @PreAuthorize("@ss.hasPermi('menu:create')")
    @PostMapping()
    public CommonResp create(@RequestBody CreateMenuReq createReq) {
        menuOperateService.create(createReq);
        return CommonResp.success();
    }

    /**
     * 修改菜单
     *
     * @param updateReq 更新请求信息
     * @return 请求响应
     */
    @Operation(summary = "修改菜单",
            parameters = {@Parameter(name = "id", description = "菜单ID")})
    @PreAuthorize("@ss.hasPermi('menu:update')")
    @PutMapping("/{id}")
    public CommonResp update(
            @PathVariable("id") Long id, @RequestBody UpdateMenuReq updateReq) {
        menuOperateService.update(id, updateReq);
        return CommonResp.success();
    }

    /**
     * 删除菜单
     *
     * @param id 菜单ID
     * @return 请求响应
     */
    @Operation(summary = "删除菜单",
            parameters = {@Parameter(name = "id", description = "菜单ID")})
    @PreAuthorize("@ss.hasPermi('menu:delete')")
    @DeleteMapping("/{id}")
    public CommonResp delete(@PathVariable("id") Long id) {
        menuOperateService.delete(id);
        return CommonResp.success();
    }

    /**
     * 获取菜单详情信息
     *
     * @param id 菜单ID
     * @return 请求响应
     */
    @Operation(summary = "获取菜单详情信息",
            parameters = {@Parameter(name = "id", description = "菜单ID")})
    @PreAuthorize("@ss.hasPermi('menu:get')")
    @GetMapping("/{id}")
    public CommonResp<GetMenuResp> get(@PathVariable("id") Long id) {
        return CommonResp.success(menuQueryService.get(id));
    }

    /**
     * 获取用户菜单树（用户权限范围内的可见的菜单）
     *
     * @param userId 用户ID
     * @return 请求响应
     */
    @Operation(summary = "获取用户菜单树（用户权限范围内的可见的菜单）",
            parameters = {@Parameter(name = "id", description = "用户ID")})
    @PreAuthorize("@ss.hasPermi('menu:user-menu-tree')")
    @GetMapping("/user-menu-tree/{userId}")
    public CommonResp<List<TreeNode>> getUserMenuTree(@PathVariable("userId") Long userId) {
        return CommonResp.success(menuQueryService.getUserMenuTree(userId));
    }

    /**
     * 获取角色菜单树（角色权限范围内的可见的菜单）
     *
     * @param roleId 角色ID
     * @return 请求响应
     */
    @Operation(summary = "获取角色菜单树（角色权限范围内的可见的菜单）",
            parameters = {@Parameter(name = "id", description = "角色ID")})
    @PreAuthorize("@ss.hasPermi('menu:role-menu-tree')")
    @GetMapping("/role-menu-tree/{roleId}")
    public CommonResp<List<TreeNode>> getRoleMenuTree(@PathVariable("roleId") Long roleId) {
        return CommonResp.success(menuQueryService.getRoleMenuTree(roleId));
    }

}
