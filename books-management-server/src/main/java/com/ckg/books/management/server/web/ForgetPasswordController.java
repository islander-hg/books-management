package com.ckg.books.management.server.web;

import com.ckg.books.management.api.auth.req.FpwdChangePasswordReq;
import com.ckg.books.management.api.auth.req.FpwdSendCaptchaReq;
import com.ckg.books.management.api.common.resp.CommonResp;
import com.ckg.books.management.service.sevice.auth.ForgetPasswordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 忘记密码处理
 *
 * @author chenkaigui
 * @date 2024/11/9
 */
@Tag(name = "忘记密码处理")
@RestController
@RequestMapping("/fpwd")
public class ForgetPasswordController {

    @Autowired
    private ForgetPasswordService forgetPasswordService;

    /**
     * 发送验证码
     *
     * @param sendCaptchaReq 发送验证码请求信息
     */
    @Operation(summary = "发送验证码")
    @PostMapping("/captcha")
    public CommonResp sendCaptcha(@RequestBody FpwdSendCaptchaReq sendCaptchaReq) {
        forgetPasswordService.sendCaptchaToUserEmail(sendCaptchaReq);
        return CommonResp.success();
    }

    /**
     * 更改密码
     *
     * @param changePwdReq 更改密码请求信息
     */
    @Operation(summary = "更改密码")
    @PutMapping("/password")
    public CommonResp changePwd(@RequestBody FpwdChangePasswordReq changePwdReq) {
        forgetPasswordService.changePwd(changePwdReq);
        return CommonResp.success();
    }

}
