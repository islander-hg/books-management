package com.ckg.books.management.service.dao.repository.Impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ckg.books.management.common.domain.resp.PageResult;
import com.ckg.books.management.api.user.req.PageUserReq;
import com.ckg.books.management.common.exception.BizErrorCodes;
import com.ckg.books.management.common.exception.ExceptionHelper;
import com.ckg.books.management.common.mapper.LambdaQueryWrapperX;
import com.ckg.books.management.service.dao.entity.UserEntity;
import com.ckg.books.management.service.dao.entity.UserRoleEntity;
import com.ckg.books.management.service.dao.mapper.UserMapper;
import com.ckg.books.management.service.dao.repository.UserRespository;
import com.ckg.books.management.service.dao.repository.UserRoleRespository;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * 用户 Respository 接口实现
 *
 * @author chenkaigui
 * @date 2024/11/8
 */
@Component
public class UserRespositoryImpl
        extends ServiceImpl<UserMapper, UserEntity> implements UserRespository {

    @Lazy
    @Resource
    private UserRoleRespository userRoleRespository;

    @Override
    public UserEntity getById(Long id, boolean throwNotFoundError) {
        UserEntity user = getById(id);
        if (null == user && throwNotFoundError) {
            throw ExceptionHelper
                    .create(BizErrorCodes.TABLE_RECORD_NOT_EXIST, "用户不存在");
        }
        return user;
    }

    @Override
    public UserEntity getByUsername(String username, boolean throwNotFoundError) {
        UserEntity user = getOne(
                new LambdaQueryWrapperX<UserEntity>().eq(UserEntity::getUsername, username));
        if (null == user && throwNotFoundError) {
            throw ExceptionHelper
                    .create(BizErrorCodes.TABLE_RECORD_NOT_EXIST, "用户：{} 不存在", username);
        }
        return user;
    }

    @Override
    public List<UserEntity> findByUsernameOrEmail(String username, String email) {
        if (StrUtil.isAllBlank(username, email)) {
            return Collections.emptyList();
        }
        LambdaQueryWrapperX<UserEntity> lambdaQueryWrapper = new LambdaQueryWrapperX<>();
        if (StrUtil.isNotBlank(username)) {
            lambdaQueryWrapper.eq(UserEntity::getUsername, username);
        }
        if (StrUtil.isNotBlank(email)) {
            lambdaQueryWrapper.or().eq(UserEntity::getEmail, email);
        }
        return list(lambdaQueryWrapper);
    }

    @Override
    public PageResult<UserEntity> searchPage(PageUserReq pageReq) {
        List<Long> userIds = null;
        if (CollUtil.isNotEmpty(pageReq.getRoleIds())) {
            userIds = userRoleRespository.findByRoleIdsIn(pageReq.getRoleIds())
                    .stream().map(UserRoleEntity::getUserId).collect(Collectors.toList());
            if (CollUtil.isEmpty(userIds)) {
                return PageResult.empty();
            }
        }
        return getBaseMapper().selectPage(pageReq,
                new LambdaQueryWrapperX<UserEntity>()
                        .inIfPresent(UserEntity::getId, userIds)
                        .likeIfPresent(UserEntity::getUsername, pageReq.getUsername())
                        .likeIfPresent(UserEntity::getNickname, pageReq.getNickname())
                        .eqIfPresent(UserEntity::getEmail, pageReq.getEmail())
                        .eqIfPresent(UserEntity::getSex, pageReq.getSex())
                        .orderByDesc(UserEntity::getUpdateTime));
    }

    @Override
    public boolean updatePassword(String username, String password) {
        LambdaUpdateWrapper<UserEntity> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper
                .set(UserEntity::getPassword, password).eq(UserEntity::getUsername, username);
        return update(lambdaUpdateWrapper);
    }
}
