package com.ckg.books.management.service.config.properties;

import lombok.Data;

/**
 * token 配置属性
 *
 * @author chenkaigui
 * @date 2024/11/9
 */
@Data
public class TokenProperties {

    /**
     * 自定义标识（HTTP 请求头）
     */
    private String header;

    /**
     * 秘钥
     */
    private String secret;

    /**
     * 有效时长（单位：分钟）
     */
    private Integer availableDuration;

    /**
     * 令牌有效期小于该时长时才会执行有效期刷新（单位：分钟）
     */
    private Integer refreshThreshold;

}
