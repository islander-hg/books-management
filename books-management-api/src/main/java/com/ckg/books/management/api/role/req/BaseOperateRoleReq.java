package com.ckg.books.management.api.role.req;

import com.ckg.books.management.api.annotation.EnumValue;
import com.ckg.books.management.api.common.enums.MenuStatus;
import com.ckg.books.management.api.common.enums.RoleStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 * 通用的操作角色请求信息
 *
 * @author chenkaigui
 * @date 2024/11/8
 */
@Data
public class BaseOperateRoleReq {

    /**
     * 角色名
     */
    @Schema(description = "角色名")
    @NotBlank(message = "角色名不能为空")
    @Size(max = 50, message = "角色名长度不允许超过50位字符")
    private String name;

    /**
     * 显示顺序
     */
    @Schema(description = "显示顺序")
    private Integer order;

    /**
     * 状态,0-正常, 1-停用
     */
    @Schema(description = "状态,0-正常, 1-停用")
    @EnumValue(enumClass = RoleStatus.class)
    private Integer status;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;

    /**
     * 授权关联的菜单ID列表
     */
    @Schema(description = "授权关联的菜单ID列表")
    private List<Long> menuIds;
}
