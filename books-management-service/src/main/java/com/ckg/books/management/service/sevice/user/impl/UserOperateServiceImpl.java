package com.ckg.books.management.service.sevice.user.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.ckg.books.management.api.user.req.BaseOperateUserReq;
import com.ckg.books.management.api.user.req.CreateUserReq;
import com.ckg.books.management.api.user.req.UpdateUserReq;
import com.ckg.books.management.common.exception.BizErrorCodes;
import com.ckg.books.management.common.exception.ExceptionHelper;
import com.ckg.books.management.common.utils.security.SecurityUtils;
import com.ckg.books.management.common.utils.spring.BeanHelper;
import com.ckg.books.management.service.dao.entity.UserEntity;
import com.ckg.books.management.service.dao.repository.UserRespository;
import com.ckg.books.management.service.dao.repository.UserRoleRespository;
import com.ckg.books.management.service.sevice.user.UserOperateService;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * 用户新增、变更操作 Service 接口
 *
 * @author chenkaigui
 * @date 2024/11/8
 */
@Slf4j
@Service
public class UserOperateServiceImpl implements UserOperateService {

    @Resource
    private UserRespository userRespository;

    @Resource
    private UserRoleRespository userRoleRespository;

    @Resource
    private TransactionTemplate transactionTemplate;

    @Override
    public void create(CreateUserReq createReq) {
        //1. 参数预处理
        preprocessParams(createReq);

        //2. 校验冲突
        verifyDataUniqueness(createReq, createReq.getUsername(), null);
        // todo 校验角色ID是否在表中存在

        //3. 创建
        UserEntity toBeCreatedEntity = BeanHelper.copyProperties(createReq, UserEntity.class);
        toBeCreatedEntity
                .setPassword(SecurityUtils.encryptPassword(toBeCreatedEntity.getPassword()));
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                try {
                    boolean created = userRespository.save(toBeCreatedEntity);
                    if (!created) {
                        throw ExceptionHelper
                                .create(BizErrorCodes.UNABLE_CREATE_TABLE_RECORD_BECAUSE_UNKNOWN,
                                        "未知异常导致无法新增用户：{}", createReq.getUsername());
                    }
                    // 关联角色
                    userRoleRespository
                            .insertUserRole(toBeCreatedEntity.getId(), createReq.getRoleIds());
                } catch (DuplicateKeyException ex) {
                    verifyDataUniqueness(createReq, createReq.getUsername(), null);
                    throw ExceptionHelper
                            .create(BizErrorCodes.UNABLE_CREATE_TABLE_RECORD_BECAUSE_UNKNOWN,
                                    "未知异常导致无法新增用户：{}", createReq.getUsername());
                }
            }
        });

    }

    @Override
    public void update(Long id, UpdateUserReq updateReq) {
        //1. 参数预处理
        preprocessParams(updateReq);

        //2. 校验存在性和冲突
        UserEntity user = userRespository.getById(id);
        verifyDataUniqueness(updateReq, null, id);
        // todo 校验角色ID是否在表中存在

        //3. 修改
        UserEntity toBeUpdatedEntity = BeanHelper.copyProperties(updateReq, UserEntity.class);
        toBeUpdatedEntity.setId(id);

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                try {
                    boolean updated = userRespository.updateById(toBeUpdatedEntity);
                    if (!updated) {
                        throw ExceptionHelper
                                .create(BizErrorCodes.UNABLE_UPDATE_TABLE_RECORD_BECAUSE_UNKNOWN,
                                        "未知异常导致无法修改用户：{}", user.getUsername());
                    }

                    // 关联角色
                    userRoleRespository.deleteByUserId(id);
                    userRoleRespository.insertUserRole(id, updateReq.getRoleIds());
                } catch (DuplicateKeyException ex) {
                    verifyDataUniqueness(updateReq, null, id);
                    throw ExceptionHelper
                            .create(BizErrorCodes.UNABLE_UPDATE_TABLE_RECORD_BECAUSE_UNKNOWN,
                                    "未知异常导致无法修改用户：{}", user.getUsername());
                }
            }
        });

    }

    @Override
    public void delete(Long id) {
        //1. 校验存在性 并 执行删除
        userRespository.getById(id, true);
        boolean deleted = userRespository.removeById(id);
        if (deleted) {
            return;
        }

        //2. 校验删除失败原因
        userRespository.getById(id, true);
        throw ExceptionHelper
                .create(BizErrorCodes.UNABLE_DELETE_TABLE_RECORD_BECAUSE_UNKNOWN,
                        "未知异常导致无法删除用户：{}", id);
    }

    /**
     * 对参数预处理
     *
     * @param operateReq 操作用户请求信息
     */
    private void preprocessParams(BaseOperateUserReq operateReq) {
        if (null == operateReq.getRoleIds()) {
            return;
        }
        operateReq.setRoleIds(
                operateReq.getRoleIds().stream().filter(roleId -> null != roleId)
                        .distinct()
                        .collect(Collectors.toList()));
    }

    /**
     * 校验数据唯一性
     *
     * @param username   用户名（账号）
     * @param operateReq 操作用户请求信息
     * @param excludeId  不在校验范围内的用户ID
     */
    private void verifyDataUniqueness(
            BaseOperateUserReq operateReq,
            @Nullable String username,
            @Nullable Long excludeId) {
        List<UserEntity> userEntities =
                userRespository.findByUsernameOrEmail(username, operateReq.getEmail());
        if (CollUtil.isEmpty(userEntities)
                || (userEntities.size() == 1 && userEntities.get(0).getId().equals(excludeId))) {
            return;
        }

        if (StrUtil.isNotBlank(username)
                && userEntities.stream()
                .filter(userEntity -> StrUtil.equals(userEntity.getUsername(), username))
                .findFirst().isPresent()) {
            throw ExceptionHelper
                    .create(BizErrorCodes.TABLE_RECORD_DUPLICATE, "用户名（账号）：{} 已存在", username);
        }
        if (userEntities.stream()
                .filter(userEntity ->
                        StrUtil.equals(userEntity.getEmail(), operateReq.getEmail()))
                .findFirst().isPresent()) {
            throw ExceptionHelper
                    .create(BizErrorCodes.TABLE_RECORD_DUPLICATE,
                            "用户邮箱：{} 已存在", operateReq.getEmail());
        }
    }

}
