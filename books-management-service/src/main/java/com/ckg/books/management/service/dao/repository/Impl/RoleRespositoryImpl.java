package com.ckg.books.management.service.dao.repository.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ckg.books.management.api.common.resp.PageResult;
import com.ckg.books.management.api.role.req.PageRoleReq;
import com.ckg.books.management.common.exception.BizErrorCodes;
import com.ckg.books.management.common.exception.ExceptionHelper;
import com.ckg.books.management.common.mapper.LambdaQueryWrapperX;
import com.ckg.books.management.service.dao.entity.RoleEntity;
import com.ckg.books.management.service.dao.mapper.RoleMapper;
import com.ckg.books.management.service.dao.repository.RoleRespository;
import org.springframework.stereotype.Component;

/**
 * 角色 Respository 接口实现
 *
 * @author chenkaigui
 * @date 2024/11/9
 */
@Component
public class RoleRespositoryImpl
        extends ServiceImpl<RoleMapper, RoleEntity> implements RoleRespository {

    @Override
    public RoleEntity getById(Long id, boolean throwNotFoundError) {
        RoleEntity user = getById(id);
        if (null == user && throwNotFoundError) {
            throw ExceptionHelper
                    .create(BizErrorCodes.TABLE_RECORD_NOT_EXIST, "角色ID：{} 不存在", id);
        }
        return user;
    }

    @Override
    public RoleEntity getByName(String name) {
        return getOne(new LambdaQueryWrapperX<RoleEntity>().eq(RoleEntity::getName, name));
    }

    @Override
    public PageResult<RoleEntity> searchPage(PageRoleReq pageReq) {
        return getBaseMapper().selectPage(pageReq,
                new LambdaQueryWrapperX<RoleEntity>()
                        .likeIfPresent(RoleEntity::getName, pageReq.getName())
                        .eqIfPresent(RoleEntity::getStatus, pageReq.getStatus())
                        .likeIfPresent(RoleEntity::getRemark, pageReq.getRemark())
                        .orderByAsc(RoleEntity::getOrder)
                        .orderByDesc(RoleEntity::getUpdateTime));
    }
}
