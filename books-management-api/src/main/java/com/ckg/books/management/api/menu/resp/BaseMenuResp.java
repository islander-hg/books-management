package com.ckg.books.management.api.menu.resp;

import com.ckg.books.management.api.common.resp.BaseEntityResp;
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
    private Long id;

    /**
     * 菜单名称
     */
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
