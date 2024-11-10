package com.ckg.books.management.service.sevice.menu;

import com.ckg.books.management.api.menu.resp.GetMenuResp;
import com.ckg.books.management.common.domain.tree.TreeNode;
import java.util.List;
import java.util.Set;
import javax.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

/**
 * 菜单查询操作 Service 接口
 *
 * @author chenkaigui
 * @date 2024/11/8
 */
@Validated
public interface MenuQueryService {

    /**
     * 获取菜单详情信息
     *
     * @param id 菜单ID
     * @return 菜单详情信息
     */
    GetMenuResp get(@NotNull Long id);

    /**
     * 根据用户ID查询权限标识列表
     *
     * @param userId 用户ID
     * @return 权限标识列表
     */
    Set<String> findPermsByUserId(@NotNull Long userId);

    /**
     * 获取用户菜单树（用户权限范围内的可见的菜单）
     *
     * @param userId 用户ID
     * @return 菜单树
     */
    List<TreeNode> getUserMenuTree(@NotNull Long userId);

    /**
     * 获取角色菜单树（角色权限范围内的可见的菜单）
     *
     * @param roleId 角色ID
     * @return 菜单树
     */
   List<TreeNode> getRoleMenuTree(@NotNull Long roleId);

}
