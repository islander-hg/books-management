package com.ckg.books.management.service.sevice.auth;

import java.util.Set;
import javax.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

/**
 * 用户权限 接口
 *
 * @author chenkaigui
 * @date 2024/11/9
 */
@Validated
public interface UserPermissionService {

    /**
     * 验证用户是否为超管
     *
     * @param userId 用户ID
     * @return true-是超管 false-不是超管
     */
    boolean isSuperuser(@NotNull Long userId);

    /**
     * 验证用户是否具备某权限
     *
     * @param permission 权限字符串
     * @return true-有权限 false-无权限
     */
    boolean hasPermi(@NotNull String permission);

    /**
     * 获取用户菜单数据权限列表
     *
     * @param userId 用户ID
     * @return 菜单权限信息列表
     */
    Set<String> getUserMenuPermission(@NotNull Long userId);

}
