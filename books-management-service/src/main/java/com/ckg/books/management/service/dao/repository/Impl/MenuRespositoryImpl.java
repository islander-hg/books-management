package com.ckg.books.management.service.dao.repository.Impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ckg.books.management.common.exception.BizErrorCodes;
import com.ckg.books.management.common.exception.ExceptionHelper;
import com.ckg.books.management.common.mapper.LambdaQueryWrapperX;
import com.ckg.books.management.service.dao.entity.BookCategoryEntity;
import com.ckg.books.management.service.dao.entity.MenuEntity;
import com.ckg.books.management.service.dao.mapper.MenuMapper;
import com.ckg.books.management.service.dao.repository.MenuRespository;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

/**
 * 菜单 Respository 接口实现
 *
 * @author chenkaigui
 * @date 2024/11/8
 */
@Component
public class MenuRespositoryImpl
        extends ServiceImpl<MenuMapper, MenuEntity> implements MenuRespository {

    @Override
    public MenuEntity getById(Long id, boolean throwNotFoundError) {
        MenuEntity menu = getById(id);
        if (null == menu && throwNotFoundError) {
            throw ExceptionHelper
                    .create(BizErrorCodes.TABLE_RECORD_NOT_EXIST, "菜单ID：{} 不存在", id);
        }
        return menu;
    }

    @Override
    public MenuEntity getByParentIdAndName(Long parentId, String name) {
        return getOne(new LambdaQueryWrapperX<MenuEntity>()
                .eq(MenuEntity::getParentId, parentId)
                .eq(MenuEntity::getName, name));
    }

    @Override
    public List<MenuEntity> findByIdIn(Collection<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return Collections.emptyList();
        }
        return list(new LambdaQueryWrapperX<MenuEntity>().in(MenuEntity::getId, ids));
    }

    @Override
    public Set<String> findPermsByIdIn(Collection<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return Collections.emptySet();
        }
        return list(new LambdaQueryWrapperX<MenuEntity>()
                .select(MenuEntity::getPerms).in(MenuEntity::getId, ids))
                .stream().map(MenuEntity::getPerms).collect(Collectors.toSet());
    }

    @Override
    public boolean hasChildren(Long id) {
        return CollUtil.isNotEmpty(
                list(new LambdaQueryWrapperX<MenuEntity>()
                        .select(MenuEntity::getId).eq(MenuEntity::getParentId, id)));
    }

}
