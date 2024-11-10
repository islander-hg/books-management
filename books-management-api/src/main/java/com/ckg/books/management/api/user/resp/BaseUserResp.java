package com.ckg.books.management.api.user.resp;

import com.ckg.books.management.common.domain.resp.BaseEntityResp;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 用户基础的响应信息
 *
 * @author chenkaigui
 * @date 2024/11/9
 */
@Data
@Accessors(chain = true)
public class BaseUserResp extends BaseEntityResp {

    /**
     * 用户ID
     */
    @Schema(description = "用户ID")
    private Long id;

    /**
     * 用户名（账号）
     */
    @Schema(description = "用户名（账号）")
    private String username;

    /**
     * 用户昵称
     */
    @Schema(description = "用户昵称")
    private String nickname;

    /**
     * 邮箱
     */
    @Schema(description = "邮箱")
    private String email;

    /**
     * 性别，0-女 1-男 2-未知
     */
    @Schema(description = "性别，0-女 1-男 2-未知")
    private Integer sex;
}
