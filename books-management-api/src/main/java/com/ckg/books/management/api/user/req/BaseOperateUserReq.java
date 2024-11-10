package com.ckg.books.management.api.user.req;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 * 通用的操作用户请求信息
 *
 * @author chenkaigui
 * @date 2024/11/8
 */
@Data
public class BaseOperateUserReq {

    /**
     * 用户昵称
     */
    @Schema(description = "用户昵称")
    @NotBlank(message = "用户昵称不能为空")
    @Size(max = 50, message = "用户昵称长度不允许超过50位字符")
    private String nickname;

    /**
     * 邮箱
     */
    @Schema(description = "邮箱")
    @NotBlank(message = "用户邮箱不能为空")
    @Size(max = 50, message = "用户邮箱长度不允许超过50位字符")
    private String email;

    /**
     * 性别，0-女 1-男 2-未知
     */
    @Schema(description = "性别，0-女 1-男 2-未知")
    @NotNull(message = "用户性别不能为空")
    private Integer sex;

    /**
     * 状态，0-正常, 1-停用
     */
    @Schema(description = "状态，0-正常, 1-停用")
    @NotNull(message = "用户状态不能为空")
    private Integer status;

    /**
     * 授权关联的角色ID列表
     */
    @Schema(description = "授权关联的角色ID列表")
    private List<Long> roleIds;
}
