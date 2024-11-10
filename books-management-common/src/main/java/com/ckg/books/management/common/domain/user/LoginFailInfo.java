package com.ckg.books.management.common.domain.user;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 登录失败信息
 *
 * @author chenkaigui
 * @date 2024/11/10
 */
@Data
@Accessors(chain = true)
public class LoginFailInfo {

    /**
     * 用户名（账号 ）
     */
    private String username;

    /**
     * 登录时间（毫秒时间戳）
     */
    private long loginTime;
}
