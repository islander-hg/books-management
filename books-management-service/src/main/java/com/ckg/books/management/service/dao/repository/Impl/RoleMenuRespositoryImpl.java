package com.ckg.books.management.service.dao.repository.Impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ckg.books.management.common.exception.BizErrorCodes;
import com.ckg.books.management.common.exception.ExceptionHelper;
import com.ckg.books.management.common.mapper.LambdaQueryWrapperX;
import com.ckg.books.management.service.dao.entity.RoleMenuEntity;
import com.ckg.books.management.service.dao.mapper.RoleMenuMapper;
import com.ckg.books.management.service.dao.repository.RoleMenuRespository;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 角色菜单关联 Respository 接口实现
 *
 * @author chenkaigui
 * @date 2024/11/9
 */
@Component
public class RoleMenuRespositoryImpl
        extends ServiceImpl<RoleMenuMapper, RoleMenuEntity>
        implements RoleMenuRespository {

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void insertRoleMenu(Long roleId, Collection<Long> menuIds) {
        if (CollUtil.isEmpty(menuIds)) {
            return;
        }
        List<RoleMenuEntity> toBeCreatedEntities =
                menuIds.stream()
                        .map(menuId -> new RoleMenuEntity().setRoleId(roleId).setMenuId(menuId))
                        .collect(Collectors.toList());
        saveBatch(toBeCreatedEntities);
    }

    @Override
    public void deleteByRoleId(Long roleId) {
        remove(new LambdaQueryWrapper<RoleMenuEntity>().eq(RoleMenuEntity::getRoleId, roleId));
    }

    @Override
    public RoleMenuEntity getByUserIdAndRoleId(
            Long roleId, Long menuId, boolean throwNotFoundError) {
        RoleMenuEntity roleMenu =
                getOne(new LambdaQueryWrapperX<RoleMenuEntity>()
                        .eq(RoleMenuEntity::getRoleId, roleId)
                        .eq(RoleMenuEntity::getMenuId, menuId));
        if (null == roleMenu && throwNotFoundError) {
            throw ExceptionHelper
                    .create(BizErrorCodes.TABLE_RECORD_NOT_EXIST,
                            "角色ID：{} 与菜单ID：{} 不存在关联信息", roleId, menuId);
        }
        return roleMenu;
    }

    @Override
    public List<RoleMenuEntity> findByRoleIdIn(Collection<Long> roleIds) {
        if (CollUtil.isEmpty(roleIds)) {
            return Collections.emptyList();
        }
        return list(
                new LambdaQueryWrapperX<RoleMenuEntity>().in(RoleMenuEntity::getRoleId, roleIds));
    }

}
