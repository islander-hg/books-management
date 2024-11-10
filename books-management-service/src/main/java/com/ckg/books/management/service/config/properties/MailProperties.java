package com.ckg.books.management.service.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Mail 配置属性
 *
 * @author chenkaigui
 * @date 2024/11/9
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "spring.mail")
public class MailProperties {

    /**
     * 发送地址（SMTP）
     */
    private String host;

    /**
     * 发送邮件的账号
     */
    private String username;

    /**
     * 账号秘钥（开启SMTP服务时生成）
     */
    private String password;

    /**
     * 默认编码
     */
    private String defaultEncoding;

    /**
     * 验证码过期时间（单位：分钟）
     */
    private int captchaAvailableDuration;

}
