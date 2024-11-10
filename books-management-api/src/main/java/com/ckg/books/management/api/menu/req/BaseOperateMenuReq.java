package com.ckg.books.management.api.menu.req;

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
    @NotBlank(message = "菜单名不能为空")
    @Size(max = 50, message = "菜单名长度不允许超过50位字符")
    private String name;

    /**
     * 父菜单ID
     */
    private Long parentId;

    /**
     * 显示顺序
     */
    private Integer order;

    /**
     * 路由地址
     */
    private String path;

    /**
     * 组件路径
     */
    private String component;

    /**
     * 路由参数
     */
    private String routeParams;

    /**
     * 是否为外链（0-否 1-是）
     */
    private Integer frameable;

    /**
     * 菜单类型（0-目录 1-菜单 2-按钮）
     */
    private Integer menuType;

    /**
     * 菜单状态（0-显示 1-隐藏）
     */
    private Integer visible;

    /**
     * 菜单状态（0-正常 1-停用）
     */
    private Integer status;

    /**
     * 权限标识
     */
    private String perms;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 备注
     */
    private String remark;

}
