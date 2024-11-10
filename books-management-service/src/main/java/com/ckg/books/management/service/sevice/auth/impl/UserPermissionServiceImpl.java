package com.ckg.books.management.service.sevice.auth.impl;

import cn.hutool.core.util.StrUtil;
import com.ckg.books.management.common.constants.AuthConstant;
import com.ckg.books.management.common.domain.user.LoginUser;
import com.ckg.books.management.common.utils.security.SecurityUtils;
import com.ckg.books.management.service.config.properties.AuthProperties;
import com.ckg.books.management.service.sevice.auth.UserPermissionService;
import com.ckg.books.management.service.sevice.menu.MenuQueryService;
import com.google.common.collect.Sets;
import java.util.Set;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * 用户权限 接口实现
 *
 * @author chenkaigui
 * @date 2024/11/9
 */
@Service("ss")
public class UserPermissionServiceImpl implements UserPermissionService {

    @Lazy
    @Resource
    private MenuQueryService menuQueryService;

    @Resource
    private AuthProperties authProperties;

    @Override
    public boolean isSuperuser(Long userId) {
        return authProperties.getSuperuserId().equals(userId);
    }

    @Override
    public boolean hasPermi(String permission) {
        if (StrUtil.isBlank(permission)) {
            return false;
        }
        LoginUser loginUser = SecurityUtils.getLoginUser();
        if (CollectionUtils.isEmpty(loginUser.getPermissions())) {
            return false;
        }
        return hasPermissions(loginUser.getPermissions(), permission);
    }

    @Override
    public Set<String> getUserMenuPermission(Long userId) {
        if (isSuperuser(userId)) {
            return Sets.newHashSet(AuthConstant.ALL_PERMISSION);
        }
        return menuQueryService.findPermsByUserId(userId);
    }

    /**
     * 判断是否包含权限
     *
     * @param permissions 权限列表
     * @param permission  权限字符串
     * @return 用户是否具备某权限
     */
    private boolean hasPermissions(Set<String> permissions, String permission) {
        return permissions.contains(AuthConstant.ALL_PERMISSION)
                || permissions.contains(StringUtils.trim(permission));
    }
}
