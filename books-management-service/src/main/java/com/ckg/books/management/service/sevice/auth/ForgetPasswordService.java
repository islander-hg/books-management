package com.ckg.books.management.service.sevice.auth;

import com.ckg.books.management.api.auth.req.FpwdChangePasswordReq;
import com.ckg.books.management.api.auth.req.FpwdSendCaptchaReq;
import javax.validation.Valid;
import org.springframework.validation.annotation.Validated;

/**
 * 忘记密码操作 接口
 *
 * @author chenkaigui
 * @date 2024/11/10
 */
@Validated
public interface ForgetPasswordService {

    /**
     * 向用户邮箱发送验证码
     *
     * @param sendCaptchaReq 发送验证码请求信息
     */
    void sendCaptchaToUserEmail(@Valid FpwdSendCaptchaReq sendCaptchaReq);

    /**
     * 更改密码
     *
     * @param changeReq 变更密码请求信息
     */
    void changePwd(@Valid FpwdChangePasswordReq changeReq);
}
