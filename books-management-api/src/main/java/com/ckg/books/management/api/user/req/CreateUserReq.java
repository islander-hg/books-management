package com.ckg.books.management.api.user.req;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 创建用户请求信息
 *
 * @author chenkaigui
 * @date 2024/11/8
 */
@Data
@Accessors(chain = true)
public class CreateUserReq extends BaseOperateUserReq {

    /**
     * 用户名（账号）
     */
    @Schema(description = "用户名（账号）")
    @NotBlank(message = "用户名（账号）不能为空")
    @Size(max = 30, message = "用户名（账号）长度不允许超过30位字符")
    private String username;

    /**
     * 用户密码
     */
    @Schema(description = "用户密码")
    @NotBlank(message = "用户密码不能为空")
    @Size(max = 100, message = "用户密码长度不允许超过100位字符")
    private String password;

}
