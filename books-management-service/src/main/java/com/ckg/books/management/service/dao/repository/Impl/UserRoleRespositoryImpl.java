package com.ckg.books.management.service.dao.repository.Impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ckg.books.management.common.exception.BizErrorCodes;
import com.ckg.books.management.common.exception.ExceptionHelper;
import com.ckg.books.management.common.mapper.LambdaQueryWrapperX;
import com.ckg.books.management.service.dao.entity.UserRoleEntity;
import com.ckg.books.management.service.dao.mapper.UserRoleMapper;
import com.ckg.books.management.service.dao.repository.UserRoleRespository;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

/**
 * 用户角色关联 Respository 接口实现
 *
 * @author chenkaigui
 * @date 2024/11/9
 */
@Component
public class UserRoleRespositoryImpl
        extends ServiceImpl<UserRoleMapper, UserRoleEntity>
        implements UserRoleRespository {

    @Override
    public void insertUserRole(Long userId, Collection<Long> roleIds) {
        if (CollUtil.isEmpty(roleIds)) {
            return;
        }
        List<UserRoleEntity> toBeCreatedEntities =
                roleIds.stream()
                        .map(roleId -> new UserRoleEntity().setUserId(userId).setRoleId(roleId))
                        .collect(Collectors.toList());
        saveBatch(toBeCreatedEntities);
    }

    @Override
    public void deleteByUserId(Long userId) {
        remove(new LambdaQueryWrapper<UserRoleEntity>().eq(UserRoleEntity::getUserId, userId));
    }

    @Override
    public void deleteByRoleId(Long roleId) {
        remove(new LambdaQueryWrapper<UserRoleEntity>().eq(UserRoleEntity::getRoleId, roleId));
    }

    @Override
    public UserRoleEntity getByUserIdAndRoleId(
            Long userId, Long roleId, boolean throwNotFoundError) {
        UserRoleEntity userRole =
                getOne(new LambdaQueryWrapperX<UserRoleEntity>()
                        .eq(UserRoleEntity::getUserId, userId)
                        .eq(UserRoleEntity::getRoleId, roleId));
        if (null == userRole && throwNotFoundError) {
            throw ExceptionHelper
                    .create(BizErrorCodes.TABLE_RECORD_NOT_EXIST, "用户角色不存在关联信息");
        }
        return userRole;
    }

    @Override
    public List<UserRoleEntity> findByUserId(Long userId) {
        return list(
                new LambdaQueryWrapperX<UserRoleEntity>().eq(UserRoleEntity::getUserId, userId));
    }

    @Override
    public List<UserRoleEntity> findByRoleIdsIn(Collection<Long> roleIds) {
        if (CollUtil.isEmpty(roleIds)) {
            return Collections.emptyList();
        }
        return list(
                new LambdaQueryWrapperX<UserRoleEntity>().in(UserRoleEntity::getRoleId, roleIds));
    }
}
