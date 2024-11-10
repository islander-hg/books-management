package com.ckg.books.management.service.sevice.menu.impl;

import cn.hutool.core.util.ObjectUtil;
import com.ckg.books.management.api.menu.req.CreateMenuReq;
import com.ckg.books.management.api.menu.req.UpdateMenuReq;
import com.ckg.books.management.common.exception.BizErrorCodes;
import com.ckg.books.management.common.exception.ExceptionHelper;
import com.ckg.books.management.common.utils.spring.BeanHelper;
import com.ckg.books.management.service.dao.entity.MenuEntity;
import com.ckg.books.management.service.dao.repository.MenuRespository;
import com.ckg.books.management.service.dao.repository.RoleMenuRespository;
import com.ckg.books.management.service.dao.utils.EntityHelper;
import com.ckg.books.management.service.sevice.menu.MenuOperateService;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Resource
    private RoleMenuRespository roleMenuRespository;

    @Override
    public void create(CreateMenuReq createReq) {
        //1. 参数预处理默认值
        createReq.setParentId(ObjectUtil.defaultIfNull(createReq.getParentId(), 0l));

        //2. 校验冲突
        verifyDataUniqueness(createReq.getParentId(), createReq.getName(), null);

        //3. 创建
        int level = createReq.getParentId().equals(0l) ? 1
                : menuRespository.getById(createReq.getParentId(), true).getLevel() + 1;
        MenuEntity toBeCreatedEntity = BeanHelper.copyProperties(createReq, MenuEntity.class);
        toBeCreatedEntity.setLevel(level);
        EntityHelper.fillBaseFieldValue(toBeCreatedEntity, true);
        try {
            boolean created = menuRespository.save(toBeCreatedEntity);
            if (!created) {
                throw ExceptionHelper
                        .create(BizErrorCodes.UNABLE_CREATE_TABLE_RECORD_BECAUSE_UNKNOWN,
                                "未知异常导致无法新增菜单：{}", createReq.getName());
            }

        } catch (DuplicateKeyException ex) {
            verifyDataUniqueness(createReq.getParentId(), createReq.getName(), null);
            throw ExceptionHelper.create(BizErrorCodes.UNABLE_CREATE_TABLE_RECORD_BECAUSE_UNKNOWN,
                    "未知异常导致无法新增菜单：{}", createReq.getName());
        }
    }

    @Override
    public void update(Long id, UpdateMenuReq updateReq) {
        //2. 校验存在性和冲突
        MenuEntity menu = menuRespository.getById(id);
        verifyDataUniqueness(menu.getParentId(), updateReq.getName(), id);

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
            verifyDataUniqueness(menu.getParentId(), updateReq.getName(), id);
            throw ExceptionHelper.create(BizErrorCodes.UNABLE_UPDATE_TABLE_RECORD_BECAUSE_UNKNOWN,
                    "未知异常导致无法修改菜单：{}", menu.getName());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(Long id) {
        //1. 校验
        verifyDeletable(id);

        //2. 删除
        boolean deleted = menuRespository.removeById(id);
        if (!deleted) {
            verifyDeletable(id);
            throw ExceptionHelper
                    .create(BizErrorCodes.UNABLE_DELETE_TABLE_RECORD_BECAUSE_UNKNOWN,
                            "未知异常导致无法删除菜单：{}", id);
        }

        //3. 删除关联关系
        roleMenuRespository.deleteByMenuId(id);
    }

    /**
     * 校验数据唯一性
     *
     * @param parentId  父节点ID
     * @param name      节点名称
     * @param excludeId 不在校验范围内的菜单ID
     */
    private void verifyDataUniqueness(Long parentId, String name, @Nullable Long excludeId) {
        MenuEntity menuEntity =
                menuRespository.getByParentIdAndName(parentId, name);
        if (null == menuEntity || menuEntity.getId().equals(excludeId)) {
            return;
        }
        throw ExceptionHelper
                .create(BizErrorCodes.TABLE_RECORD_DUPLICATE, "菜单：{} 已存在", name);
    }

    /**
     * 验证是否可删除
     *
     * @param id 菜单ID
     */
    private void verifyDeletable(Long id) {
        //1. 节点存在
        menuRespository.getById(id, true);
        //2. 无子节点
        if (menuRespository.hasChildren(id)) {
            throw ExceptionHelper
                    .create(BizErrorCodes.DELETE_NODE_WITH_CHILD_NODES_IS_NOT_ALLOWED,
                            "该菜单下有子菜单，请先删除子菜单");
        }
    }

}
