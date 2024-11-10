package com.ckg.books.management.service.dao.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ckg.books.management.service.dao.entity.UserRoleEntity;
import java.util.Collection;
import java.util.List;

/**
 * 用户角色关联 Respository 接口实现
 *
 * @author chenkaigui
 * @date 2024/11/9
 */
public interface UserRoleRespository extends IService<UserRoleEntity> {

    /**
     * 插入用户角色关联信息
     *
     * @param userId  用户ID
     * @param roleIds 角色ID列表
     */
    void insertUserRole(Long userId, Collection<Long> roleIds);

    /**
     * 通过用户ID删除用户角色关联关系
     *
     * @param userId 用户ID
     */
    void deleteByUserId(Long userId);

    /**
     * 通过角色ID删除用户角色关联关系
     *
     * @param roleId 角色ID
     */
    void deleteByRoleId(Long roleId);

    /**
     * 根据用户ID和角色ID获取用户角色关联信息
     *
     * @param userId             用户ID
     * @param roleId             角色ID
     * @param throwNotFoundError 关联信息不存在时是否抛异常; true-是 false-否
     * @return 用户角色关联信息
     */
    UserRoleEntity getByUserIdAndRoleId(Long userId, Long roleId, boolean throwNotFoundError);

    /**
     * 根据用户ID获取用户角色关联信息列表
     *
     * @param userId 用户ID
     * @return 用户角色关联信息列表
     */
    List<UserRoleEntity> findByUserId(Long userId);

    /**
     * 根据角色ID列表获取用户角色关联信息列表
     *
     * @param roleIds 角色ID列表
     * @return 用户角色关联信息列表
     */
    List<UserRoleEntity> findByRoleIdsIn(Collection<Long> roleIds);
}
