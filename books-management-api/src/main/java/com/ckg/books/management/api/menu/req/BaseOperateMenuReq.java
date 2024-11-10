package com.ckg.books.management.api.menu.req;

import com.ckg.books.management.api.annotation.EnumValue;
import com.ckg.books.management.api.common.enums.BookStatus;
import com.ckg.books.management.api.common.enums.MenuFrameable;
import com.ckg.books.management.api.common.enums.MenuStatus;
import com.ckg.books.management.api.common.enums.MenuType;
import com.ckg.books.management.api.common.enums.MenuVisible;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 * 通用的操作菜单请求信息
 *
 * @author chenkaigui
 * @date 2024/11/8
 */
@Data
public class BaseOperateMenuReq {

    /**
     * 菜单名称
     */
    @Schema(description = "菜单名称")
    @NotBlank(message = "菜单名不能为空")
    @Size(max = 50, message = "菜单名长度不允许超过50位字符")
    private String name;

    /**
     * 父菜单ID
     */
    @Schema(description = "父菜单ID")
    private Long parentId;

    /**
     * 显示顺序
     */
    @Schema(description = "显示顺序")
    private Integer order;

    /**
     * 路由地址
     */
    @Schema(description = "路由地址")
    private String path;

    /**
     * 组件路径
     */
    @Schema(description = "组件路径")
    private String component;

    /**
     * 路由参数
     */
    @Schema(description = "路由参数")
    private String routeParams;

    /**
     * 是否为外链（0-否 1-是）
     */
    @Schema(description = "是否为外链（0-否 1-是）")
    @EnumValue(enumClass = MenuFrameable.class)
    private Integer frameable;

    /**
     * 菜单类型（0-目录 1-菜单 2-按钮）
     */
    @Schema(description = "菜单类型（0-目录 1-菜单 2-按钮）")
    @EnumValue(enumClass = MenuType.class)
    private Integer type;

    /**
     * 菜单状态（0-显示 1-隐藏）
     */
    @Schema(description = "菜单状态（0-显示 1-隐藏）")
    @EnumValue(enumClass = MenuVisible.class)
    private Integer visible;

    /**
     * 菜单状态（0-正常 1-停用）
     */
    @Schema(description = "菜单状态（0-正常 1-停用）")
    @EnumValue(enumClass = MenuStatus.class)
    private Integer status;

    /**
     * 权限标识
     */
    @Schema(description = "权限标识")
    private String perms;

    /**
     * 菜单图标
     */
    @Schema(description = "菜单图标")
    private String icon;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;

}
