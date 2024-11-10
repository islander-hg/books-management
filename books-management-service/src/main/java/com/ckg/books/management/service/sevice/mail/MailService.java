package com.ckg.books.management.service.sevice.mail;

import com.alibaba.nacos.shaded.org.checkerframework.checker.nullness.qual.Nullable;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

/**
 * 邮箱服务 接口
 *
 * @author chenkaigui
 * @date 2024/11/10
 */
@Validated
public interface MailService {

    /**
     * @param message        邮件信息（注：不需要设置发送人邮箱）
     * @param senderNickname 发送人昵称
     */
    void sendMessage(@NotNull MimeMessage message, @Nullable String senderNickname)
            throws MessagingException;


    MimeMessage createMimeMessage();
}
