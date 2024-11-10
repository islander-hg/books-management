package com.ckg.books.management.service.dao.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ckg.books.management.service.dao.entity.RoleMenuEntity;
import java.util.Collection;
import java.util.List;

/**
 * 角色菜单关联 Respository 接口
 *
 * @author chenkaigui
 * @date 2024/11/9
 */
public interface RoleMenuRespository extends IService<RoleMenuEntity> {

    /**
     * 插入角色菜单关联信息
     *
     * @param roleId  角色ID
     * @param menuIds 菜单ID列表
     */
    void insertRoleMenu(Long roleId, Collection<Long> menuIds);

    /**
     * 通过角色ID删除角色菜单关联关系
     *
     * @param roleId 角色ID
     */
    void deleteByRoleId(Long roleId);

    /**
     * 通过菜单ID删除角色菜单关联关系
     *
     * @param menuID 菜单ID
     */
    void deleteByMenuId(Long menuID);

    /**
     * 根据角色ID和菜单ID获取角色菜单关联信息
     *
     * @param roleId             角色ID
     * @param menuId             菜单ID
     * @param throwNotFoundError 关联信息不存在时是否抛异常; true-是 false-否
     * @return 角色菜单关联信息
     */
    RoleMenuEntity getByUserIdAndRoleId(Long roleId, Long menuId, boolean throwNotFoundError);

    /**
     * 根据角色ID列表获取角色菜单关联信息列表
     *
     * @param roleIds 角色ID列表
     * @return 角色菜单关联信息列表
     */
    List<RoleMenuEntity> findByRoleIdIn(Collection<Long> roleIds);

}
