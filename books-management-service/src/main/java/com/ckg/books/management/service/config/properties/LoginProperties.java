package com.ckg.books.management.service.config.properties;

import lombok.Data;

/**
 * login 配置属性
 *
 * @author chenkaigui
 * @date 2024/11/10
 */
@Data
public class LoginProperties {

    /**
     * 允许登录失败的最大次数
     */
    private Integer maxFailureCount;

    /**
     * 登录失败次数限制的时间窗口（单位：分钟）
     */
    private Integer failureTimeWindow;

    /**
     * 时间窗口内登录失败次数超过限制后禁止登录的时长（单位：分钟）
     */
    private Integer limitLoginDuration;
}
