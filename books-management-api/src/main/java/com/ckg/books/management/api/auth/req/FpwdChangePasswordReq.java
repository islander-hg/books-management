package com.ckg.books.management.api.auth.req;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 * 忘记密码 - 更改密码对象
 *
 * @author chenkaigui
 * @date 2024/11/9
 */
@Data
public class FpwdChangePasswordReq {

    /**
     * 用户名（账号）
     */
    @Schema(description = "用户名（账号）")
    @NotBlank(message = "用户名（账号）不能为空")
    @Size(max = 30, message = "用户名（账号）长度不允许超过30位字符")
    private String username;

    /**
     * 新密码
     */
    @Schema(description = "新密码")
    @NotBlank(message = "用户新密码不能为空")
    @Size(max = 100, message = "用户新密码长度不允许超过100位字符")
    private String newPassword;

    /**
     * 验证码
     */
    @Schema(description = "验证码")
    @NotBlank(message = "验证码不能为空")
    @Size(max = 6, message = "验证码长度不允许超过6位字符")
    private String captcha;
}
