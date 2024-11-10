package com.ckg.books.management.api.auth.req;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 * 忘记密码 - 发送验证码对象
 *
 * @author chenkaigui
 * @date 2024/11/9
 */
@Data
public class FpwdSendCaptchaReq {

    /**
     * 用户名（账号）
     */
    @NotBlank(message = "用户名（账号）不能为空")
    @Size(max = 30, message = "用户名（账号）长度不允许超过30位字符")
    private String username;

    /**
     * 邮箱
     */
    @NotBlank(message = "用户邮箱不能为空")
    @Size(max = 50, message = "用户邮箱长度不允许超过50位字符")
    private String email;
}
