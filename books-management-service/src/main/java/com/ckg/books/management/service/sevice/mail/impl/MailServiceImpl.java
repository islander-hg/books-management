package com.ckg.books.management.service.sevice.mail.impl;

import cn.hutool.core.util.StrUtil;
import com.ckg.books.management.service.config.properties.MailProperties;
import com.ckg.books.management.service.sevice.mail.MailService;
import java.text.MessageFormat;
import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * 邮箱服务 接口实现
 *
 * @author chenkaigui
 * @date 2024/11/10
 */
@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender mailSender;

    @Resource
    private MailProperties mailProperties;

    @Override
    public void sendMessage(MimeMessage message, String senderNickname) throws MessagingException {
        String from = mailProperties.getUsername();
        if (StrUtil.isNotBlank(senderNickname)) {
            from = MessageFormat.format("{0}<{1}>", senderNickname, from);
        }
        message.setFrom(from);
        mailSender.send(message);
    }

    public MimeMessage createMimeMessage() {
        return mailSender.createMimeMessage();
    }
}
