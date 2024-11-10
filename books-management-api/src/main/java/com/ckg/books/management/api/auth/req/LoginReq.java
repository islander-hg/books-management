package com.ckg.books.management.api.auth.req;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 登录请求信息
 *
 * @author chenkaigui
 * @date 2024/11/9
 */
@Data
@Accessors(chain = true)
public class LoginReq {

    /**
     * 用户名
     */
    @Schema(description = "用户名（账号）")
    @NotBlank(message = "用户名（账号）不能为空")
    private String username;

    /**
     * 用户密码
     */
    @Schema(description = "用户密码")
    @NotBlank(message = "用户密码不能为空")
    private String password;

}
