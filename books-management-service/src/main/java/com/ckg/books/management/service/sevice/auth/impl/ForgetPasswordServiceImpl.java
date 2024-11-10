package com.ckg.books.management.service.sevice.auth.impl;

import cn.hutool.core.util.StrUtil;
import com.ckg.books.management.api.auth.req.FpwdChangePasswordReq;
import com.ckg.books.management.api.auth.req.FpwdSendCaptchaReq;
import com.ckg.books.management.common.constants.RedisConstant;
import com.ckg.books.management.common.exception.BizErrorCodes;
import com.ckg.books.management.common.exception.ExceptionHelper;
import com.ckg.books.management.common.utils.security.SecurityUtils;
import com.ckg.books.management.service.config.properties.MailProperties;
import com.ckg.books.management.service.dao.entity.UserEntity;
import com.ckg.books.management.service.dao.repository.UserRespository;
import com.ckg.books.management.service.sevice.auth.ForgetPasswordService;
import com.ckg.books.management.service.sevice.mail.MailService;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import javax.validation.Valid;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/**
 * 忘记密码操作 接口实现
 *
 * @author chenkaigui
 * @date 2024/11/10
 */
@Service
public class ForgetPasswordServiceImpl implements ForgetPasswordService {


    private static final String CAPTCHA_EMAIL_CONTENT_TPL = "<table align=\"center\" width=\"100%\"><tbody><tr><td>&nbsp;</td><td valign=\"top\" width=\"640\" style=\"width:640px\"><table cellspacing=\"0\" cellpadding=\"0\" width=\"640\" style=\"background-color:#FFFFFF;border-radius:8px;min-width:640px;width:640px;max-width:640px\"><tbody><tr><td style=\"line-height:0px\"><table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\" style=\"border-radius:8px\" class=\"\"><tbody><tr><td width=\"32px\"></td><td border=\"0\" bgcolor=\"#ffffff\" style=\"position:relative;border-radius:8px;background-color:#ffffff;padding-top:16px;padding-bottom:16px\"><span style=\"font-size:14px;color:#141933;line-height:24px;word-break:break-word;overflow-wrap:break-word;word-wrap:break-word\">您的验证码为{captcha}，有效期{timeout}分钟，如非本人操作，请忽略本邮件</span></td><td width=\"32px\"></td></tr></tbody></table></td></tr><tr><td style=\"line-height:0px\"><table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\" style=\"border-radius:8px\" class=\"\"><tbody><tr><td width=\"32px\"></td><td border=\"0\" bgcolor=\"#ffffff\" style=\"position:relative;border-radius:8px;background-color:#ffffff;padding-top:16px;padding-bottom:16px\"><span style=\"font-size:14px;color:#141933;line-height:24px;word-break:break-word;overflow-wrap:break-word;word-wrap:break-word\">为了确保您的帐号安全，请不要将此邮件转发给任何人</span></td><td width=\"32px\"></td></tr></tbody></table></td></tr></tbody></table></td><td>&nbsp;</td></tr></tbody></table>";

    @Resource
    private UserRespository userRespository;

    @Resource
    private MailService mailService;

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private MailProperties mailProperties;

    @Override
    public void sendCaptchaToUserEmail(@Valid FpwdSendCaptchaReq sendCaptchaReq) {
        String username = sendCaptchaReq.getUsername();
        String email = sendCaptchaReq.getEmail();

        //1. 校验用户名和邮箱
        validateUsernameEmailMatch(username, email);

        //2. 构建并发送邮箱
        String captcha = createRandomCaptcha();
        MimeMessage mimeMessage =
                buildCaptchaEmail(
                        email, captcha, mailProperties.getCaptchaAvailableDuration());

        try {
            mailService.sendMessage(mimeMessage, "图书管理中心");
        } catch (Exception ex) {
            throw ExceptionHelper
                    .create(BizErrorCodes.EMAIL_SEND_FAILED, "向用户：{} 的邮箱：{} 发送验证码失败",
                            username, email, ex);
        }

        //3. 缓存验证码
        String captchaCacheKey = buildCaptchaCacheKey(username);
        redisTemplate.opsForValue().set(
                captchaCacheKey,
                captcha,
                mailProperties.getCaptchaAvailableDuration(),
                TimeUnit.MINUTES);
    }

    @Override
    public void changePwd(FpwdChangePasswordReq changeReq) {
        String username = changeReq.getUsername();
        validateCaptcha(username, changeReq.getCaptcha());
        validateNewPassword(username, changeReq.getNewPassword());

        if (userRespository.updatePassword(
                username, SecurityUtils.encryptPassword(changeReq.getNewPassword()))) {
            redisTemplate.delete(buildCaptchaCacheKey(username));
            return;
        }
        throw ExceptionHelper
                .create(BizErrorCodes.UNABLE_UPDATE_TABLE_RECORD_BECAUSE_UNKNOWN,
                        "未知错误导致无法修改密码，请联系管理员");
    }

    /**
     * 检测用户名、邮箱信息是否匹配
     *
     * @param username 用户名
     * @param email    邮箱
     */
    private void validateUsernameEmailMatch(String username, String email) {
        UserEntity user = userRespository.getByUsername(username, true);
        if (!StrUtil.equals(user.getEmail(), email)) {
            throw ExceptionHelper
                    .create(BizErrorCodes.TABLE_RECORD_NOT_EXIST, "邮箱地址:{} 与用户预留邮箱地址不匹配", email);
        }
    }

    /**
     * 生成随机验证码(6位数)
     *
     * @return 随机验证码(6位数)
     */
    private String createRandomCaptcha() {
        return String.valueOf(new Random().nextInt(900000) + 100000);
    }

    /**
     * 构建验证码邮件信息
     *
     * @param targetEmail 发送目标邮箱
     * @param captcha     验证码
     * @param timeout     过期时间（单位：分钟）
     * @return 验证码邮件信息
     */
    private MimeMessage buildCaptchaEmail(
            String targetEmail, String captcha, int timeout) {
        MimeMessage mailMessage = mailService.createMimeMessage();

        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mailMessage, true);
            mimeMessageHelper.setTo(targetEmail);
            mimeMessageHelper.setSubject("图书管理系统重置密码验证");

            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("captcha", captcha);
            paramMap.put("timeout", timeout);

            mimeMessageHelper.setText(
                    StrUtil.format(CAPTCHA_EMAIL_CONTENT_TPL, paramMap), true);
        } catch (Exception ex) {
            throw ExceptionHelper.create(BizErrorCodes.EMAIL_BUILD_FAILED, "验证码邮件构建失败", ex);
        }
        return mailMessage;
    }

    /**
     * 检查验证码是否和缓存中的一致
     *
     * @param username 用户名
     * @param captcha  验证码
     */
    private void validateCaptcha(String username, String captcha) {
        String captchaCacheKey = buildCaptchaCacheKey(username);
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        String cacheCaptcha = operations.get(captchaCacheKey);

        if (StrUtil.isBlank(cacheCaptcha)) {
            throw ExceptionHelper.create(BizErrorCodes.EMAIL_CAPTCHA_EXPIRED, "邮箱验证码已过期");
        }
        if (!StrUtil.equalsIgnoreCase(cacheCaptcha, captcha)) {
            throw ExceptionHelper.create(BizErrorCodes.EMAIL_CAPTCHA_INVALID, "邮箱验证码不正确");
        }
    }

    /**
     * 验证新密码是否有效（不能和旧密码相同, 密码强度必须符合要求）
     *
     * @param username    用户名
     * @param newPassword 新密码
     */
    private void validateNewPassword(String username, String newPassword) {
        UserEntity user = userRespository.getByUsername(username, true);
        if (SecurityUtils.matchesPassword(newPassword, user.getPassword())) {
            throw ExceptionHelper.create(BizErrorCodes.PASSWORD_UNCHANGED, "新密码不能与旧密码相同");
        }
    }

    /**
     * 构建验证码缓存key
     *
     * @param username 用户名（密码）
     * @return 验证码缓存key
     */
    private String buildCaptchaCacheKey(String username) {
        return RedisConstant.FPWD_CAPTCHA_KEY_PREFIX + username;
    }
}
