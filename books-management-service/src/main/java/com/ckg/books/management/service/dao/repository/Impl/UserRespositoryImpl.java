package com.ckg.books.management.service.dao.repository.Impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ckg.books.management.api.common.resp.PageResult;
import com.ckg.books.management.api.user.req.PageUserReq;
import com.ckg.books.management.common.exception.BizErrorCodes;
import com.ckg.books.management.common.exception.ExceptionHelper;
import com.ckg.books.management.common.mapper.LambdaQueryWrapperX;
import com.ckg.books.management.service.dao.entity.UserEntity;
import com.ckg.books.management.service.dao.mapper.UserMapper;
import com.ckg.books.management.service.dao.repository.UserRespository;
import java.util.Collections;
import java.util.List;
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

    @Override
    public UserEntity getById(Long id, boolean throwNotFoundError) {
        UserEntity user = getById(id);
        if (null == user && throwNotFoundError) {
            throw ExceptionHelper
                    .create(BizErrorCodes.TABLE_RECORD_NOT_EXIST, "用户ID：{} 不存在", id);
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
            lambdaQueryWrapper.eq(UserEntity::getEmail, email);
        }
        return list(lambdaQueryWrapper);
    }

    @Override
    public PageResult<UserEntity> searchPage(PageUserReq pageReq) {
        return getBaseMapper().selectPage(pageReq,
                new LambdaQueryWrapperX<UserEntity>()
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
