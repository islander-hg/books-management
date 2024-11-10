package com.ckg.books.management.service.sevice.menu.impl;

import cn.hutool.core.util.ObjectUtil;
import com.ckg.books.management.api.menu.req.BaseOperateMenuReq;
import com.ckg.books.management.api.menu.req.CreateMenuReq;
import com.ckg.books.management.api.menu.req.UpdateMenuReq;
import com.ckg.books.management.common.exception.BizErrorCodes;
import com.ckg.books.management.common.exception.ExceptionHelper;
import com.ckg.books.management.common.utils.spring.BeanHelper;
import com.ckg.books.management.service.dao.entity.MenuEntity;
import com.ckg.books.management.service.dao.repository.MenuRespository;
import com.ckg.books.management.service.dao.utils.EntityHelper;
import com.ckg.books.management.service.sevice.menu.MenuOperateService;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

/**
 * 菜单新增、变更操作 Service 接口
 *
 * @author chenkaigui
 * @date 2024/11/8
 */
@Slf4j
@Service
public class MenuOperateServiceImpl implements MenuOperateService {

    @Resource
    private MenuRespository menuRespository;

    @Override
    public void create(CreateMenuReq createReq) {
        //1. 参数预处理默认值
        createReq.setParentId(ObjectUtil.defaultIfNull(createReq.getParentId(), 0l));

        //2. 校验冲突
        verifyDataUniqueness(createReq, null);

        //3. 创建
        MenuEntity toBeCreatedEntity = BeanHelper.copyProperties(createReq, MenuEntity.class);
        EntityHelper.fillBaseFieldValue(toBeCreatedEntity, true);
        try {
            boolean created = menuRespository.save(toBeCreatedEntity);
            if (!created) {
                throw ExceptionHelper
                        .create(BizErrorCodes.UNABLE_CREATE_TABLE_RECORD_BECAUSE_UNKNOWN,
                                "未知异常导致无法新增菜单：{}", createReq.getName());
            }

        } catch (DuplicateKeyException ex) {
            verifyDataUniqueness(createReq, null);
            throw ExceptionHelper.create(BizErrorCodes.UNABLE_CREATE_TABLE_RECORD_BECAUSE_UNKNOWN,
                    "未知异常导致无法新增菜单：{}", createReq.getName());
        }
    }

    @Override
    public void update(Long id, UpdateMenuReq updateReq) {
        //1. 填充默认值
        updateReq.setParentId(ObjectUtil.defaultIfNull(updateReq.getParentId(), 0l));

        //2. 校验存在性和冲突
        MenuEntity menu = menuRespository.getById(id);
        verifyDataUniqueness(updateReq, id);

        //3. 修改
        MenuEntity toBeUpdatedEntity = BeanHelper.copyProperties(updateReq, MenuEntity.class);
        toBeUpdatedEntity.setId(id);
        EntityHelper.fillBaseFieldValue(toBeUpdatedEntity, false);

        try {
            boolean updated = menuRespository.updateById(toBeUpdatedEntity);
            if (!updated) {
                throw ExceptionHelper
                        .create(BizErrorCodes.UNABLE_UPDATE_TABLE_RECORD_BECAUSE_UNKNOWN,
                                "未知异常导致无法修改菜单：{}", menu.getName());
            }
        } catch (DuplicateKeyException ex) {
            verifyDataUniqueness(updateReq, id);
            throw ExceptionHelper.create(BizErrorCodes.UNABLE_UPDATE_TABLE_RECORD_BECAUSE_UNKNOWN,
                    "未知异常导致无法修改菜单：{}", menu.getName());
        }
    }

    @Override
    public void delete(Long id) {
        //1. 校验存在性 并 执行删除
        menuRespository.getById(id, true);
        boolean deleted = menuRespository.removeById(id);
        if (deleted) {
            return;
        }

        //2. 校验删除失败原因
        menuRespository.getById(id, true);
        throw ExceptionHelper
                .create(BizErrorCodes.UNABLE_DELETE_TABLE_RECORD_BECAUSE_UNKNOWN,
                        "未知异常导致无法删除菜单：{}", id);
    }

    /**
     * 校验数据唯一性
     *
     * @param operateReq 操作菜单请求信息
     * @param excludeId  不在校验范围内的菜单ID
     */
    private void verifyDataUniqueness(
            BaseOperateMenuReq operateReq, @Nullable Long excludeId) {
        MenuEntity menuEntity =
                menuRespository
                        .getByParentIdAndName(operateReq.getParentId(), operateReq.getName());
        if (null == menuEntity || menuEntity.getId().equals(excludeId)) {
            return;
        }
        throw ExceptionHelper
                .create(BizErrorCodes.TABLE_RECORD_DUPLICATE, "菜单：{} 已存在", operateReq.getName());
    }

}
