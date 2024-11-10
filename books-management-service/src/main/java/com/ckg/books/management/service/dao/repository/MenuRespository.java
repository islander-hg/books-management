package com.ckg.books.management.service.dao.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ckg.books.management.service.dao.entity.MenuEntity;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * 菜单Respository 接口实现
 *
 * @author chenkaigui
 * @date 2024/11/8
 */
public interface MenuRespository extends IService<MenuEntity> {

    /**
     * 根据菜单ID获取菜单信息
     *
     * @param id                 菜单ID
     * @param throwNotFoundError 菜单不存在时是否抛异常; true-是 false-否
     * @return 菜单信息
     */
    MenuEntity getById(Long id, boolean throwNotFoundError);

    /**
     * 通过父菜单ID和菜单名模糊查询获取菜单信息
     *
     * @param parentId 父菜单ID
     * @param name     菜单名称
     * @return 菜单信息
     */
    MenuEntity getByParentIdAndName(Long parentId, String name);

    /**
     * 通过菜单ID列表查询获取菜单信息列表
     *
     * @param ids 菜单ID列表
     * @return 菜单信息列表
     */
    List<MenuEntity> findByIdIn(Collection<Long> ids);

    /**
     * 通过菜单ID列表查询获取菜单权限标识列表
     *
     * @param ids 菜单ID列表
     * @return 菜单权限标识列表
     */
    Set<String> findPermsByIdIn(Collection<Long> ids);

}
