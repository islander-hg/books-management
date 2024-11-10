package com.ckg.books.management.service.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * 认证配置属性
 *
 * @author chenkaigui
 * @date 2024/11/10
 */
@Data
@RefreshScope
@Configuration
@ConfigurationProperties(prefix = "auth")
public class AuthProperties {

    private Long superuserId;

    private LoginProperties login;

    private TokenProperties token;
}
