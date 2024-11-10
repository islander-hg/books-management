package com.ckg.books.management.api.auth.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 登录请求响应信息
 *
 * @author chenkaigui
 * @date 2024/11/9
 */
@Data
@Accessors(chain = true)
public class LoginResp {

    /**
     * 用户token
     */
    @Schema(description = "用户token")
    private String token;
}
