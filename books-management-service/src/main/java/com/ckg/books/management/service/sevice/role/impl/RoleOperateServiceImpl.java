package com.ckg.books.management.service.sevice.role.impl;

import com.ckg.books.management.api.role.req.BaseOperateRoleReq;
import com.ckg.books.management.api.role.req.CreateRoleReq;
import com.ckg.books.management.api.role.req.UpdateRoleReq;
import com.ckg.books.management.common.exception.BizErrorCodes;
import com.ckg.books.management.common.exception.ExceptionHelper;
import com.ckg.books.management.common.utils.spring.BeanHelper;
import com.ckg.books.management.service.dao.entity.RoleEntity;
import com.ckg.books.management.service.dao.repository.RoleMenuRespository;
import com.ckg.books.management.service.dao.repository.RoleRespository;
import com.ckg.books.management.service.dao.utils.EntityHelper;
import com.ckg.books.management.service.sevice.role.RoleOperateService;
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
 * 角色新增、变更操作 Service 接口
 *
 * @author chenkaigui
 * @date 2024/11/8
 */
@Slf4j
@Service
public class RoleOperateServiceImpl implements RoleOperateService {

    @Resource
    private RoleRespository roleRespository;

    @Resource
    private RoleMenuRespository roleMenuRespository;

    @Resource
    private TransactionTemplate transactionTemplate;


    @Override
    public void create(CreateRoleReq createReq) {
        //1. 参数预处理
        preprocessParams(createReq);

        //2. 校验冲突
        verifyDataUniqueness(createReq, null);
        // todo 校验菜单ID是否在表中存在

        //3. 创建
        RoleEntity toBeCreatedEntity = BeanHelper.copyProperties(createReq, RoleEntity.class);
        EntityHelper.fillBaseFieldValue(toBeCreatedEntity, true);

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                try {
                    boolean created = roleRespository.save(toBeCreatedEntity);
                    if (!created) {
                        throw ExceptionHelper
                                .create(BizErrorCodes.UNABLE_CREATE_TABLE_RECORD_BECAUSE_UNKNOWN,
                                        "未知异常导致无法新增角色：{}", createReq.getName());
                    }
                    // 关联菜单
                    roleMenuRespository
                            .insertRoleMenu(toBeCreatedEntity.getId(), createReq.getMenuIds());
                } catch (DuplicateKeyException ex) {
                    verifyDataUniqueness(createReq, null);
                    throw ExceptionHelper
                            .create(BizErrorCodes.UNABLE_CREATE_TABLE_RECORD_BECAUSE_UNKNOWN,
                                    "未知异常导致无法新增角色：{}", createReq.getName());
                }
            }
        });


    }

    @Override
    public void update(Long id, UpdateRoleReq updateReq) {
        //1. 参数预处理
        preprocessParams(updateReq);

        //2. 校验存在性和冲突
        RoleEntity role = roleRespository.getById(id);
        verifyDataUniqueness(updateReq, id);
        // todo 校验菜单ID是否在表中存在

        //3. 修改
        RoleEntity toBeUpdatedEntity = BeanHelper.copyProperties(updateReq, RoleEntity.class);
        toBeUpdatedEntity.setId(id);
        EntityHelper.fillBaseFieldValue(toBeUpdatedEntity, false);

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                try {
                    boolean updated = roleRespository.updateById(toBeUpdatedEntity);
                    if (!updated) {
                        throw ExceptionHelper
                                .create(BizErrorCodes.UNABLE_UPDATE_TABLE_RECORD_BECAUSE_UNKNOWN,
                                        "未知异常导致无法修改角色：{}", role.getName());
                    }
                    // 关联菜单
                    roleMenuRespository.deleteByRoleId(id);
                    roleMenuRespository.insertRoleMenu(id, updateReq.getMenuIds());
                } catch (DuplicateKeyException ex) {
                    verifyDataUniqueness(updateReq, id);
                    throw ExceptionHelper
                            .create(BizErrorCodes.UNABLE_UPDATE_TABLE_RECORD_BECAUSE_UNKNOWN,
                                    "未知异常导致无法修改角色：{}", role.getName());
                }
            }
        });

    }

    @Override
    public void delete(Long id) {
        //1. 校验存在性 并 执行删除
        roleRespository.getById(id, true);
        boolean deleted = roleRespository.removeById(id);
        if (deleted) {
            return;
        }

        //2. 校验删除失败原因
        roleRespository.getById(id, true);
        throw ExceptionHelper
                .create(BizErrorCodes.UNABLE_DELETE_TABLE_RECORD_BECAUSE_UNKNOWN,
                        "未知异常导致无法删除角色：{}", id);
    }

    /**
     * 对参数预处理
     *
     * @param operateReq 操作角色请求信息
     */
    private void preprocessParams(BaseOperateRoleReq operateReq) {
        if (null == operateReq.getMenuIds()) {
            return;
        }
        operateReq.setMenuIds(
                operateReq.getMenuIds().stream().filter(menuId -> null != menuId)
                        .distinct()
                        .collect(Collectors.toList()));
    }

    /**
     * 校验数据唯一性
     *
     * @param operateReq 操作角色请求信息
     * @param excludeId  不在校验范围内的角色ID
     */
    private void verifyDataUniqueness(
            BaseOperateRoleReq operateReq, @Nullable Long excludeId) {
        RoleEntity roleEntity = roleRespository.getByName(operateReq.getName());
        if (null == roleEntity || roleEntity.getId().equals(excludeId)) {
            return;
        }
        throw ExceptionHelper
                .create(BizErrorCodes.TABLE_RECORD_DUPLICATE, "角色：{} 已存在", operateReq.getName());
    }

}
