package com.ckg.books.management.service.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ckg.books.management.common.constants.EntityFieldConstant;
import com.ckg.books.management.common.constants.TableNameConstant;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 菜单
 *
 * @author chenkaigui
 * @date 2024/11/9
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@TableName(TableNameConstant.MENU)
public class MenuEntity extends BaseEntity {

    /**
     * 菜单ID
     */
    @TableId(type = IdType.AUTO)
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
     * 层级
     */
    private Integer level;

    /**
     * 显示顺序
     */
    @TableField("`order`")
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
    private Integer type;

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

    /**
     * 删除标识；0-未删除，值为ID时-已删除
     */
    @TableLogic(delval = EntityFieldConstant.ID)
    private Long deleted;
}
