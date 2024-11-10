package com.ckg.books.management.api.menu.resp;

import com.ckg.books.management.common.domain.resp.BaseEntityResp;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 菜单基础的响应信息
 *
 * @author chenkaigui
 * @date 2024/11/9
 */
@Data
@Accessors(chain = true)
public class BaseMenuResp extends BaseEntityResp {

    /**
     * 菜单ID
     */
    @Schema(description = "菜单ID")
    private Long id;

    /**
     * 菜单名称
     */
    @Schema(description = "菜单名称")
    private String name;

    /**
     * 父菜单ID
     */
    @Schema(description = "父菜单ID")
    private Long parentId;

    /**
     * 层级
     */
    @Schema(description = "层级")
    private Integer level;

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
    private Integer frameable;

    /**
     * 菜单类型（0-目录 1-菜单 2-按钮）
     */
    @Schema(description = "菜单类型（0-目录 1-菜单 2-按钮）")
    private Integer type;

    /**
     * 菜单状态（0-显示 1-隐藏）
     */
    @Schema(description = "菜单状态（0-显示 1-隐藏）")
    private Integer visible;

    /**
     * 菜单状态（0-正常 1-停用）
     */
    @Schema(description = "菜单状态（0-正常 1-停用）")
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
