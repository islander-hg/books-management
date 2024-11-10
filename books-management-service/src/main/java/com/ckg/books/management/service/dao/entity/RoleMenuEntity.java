package com.ckg.books.management.service.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ckg.books.management.common.constants.TableNameConstant;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 角色菜单关联信息
 *
 * @author chenkaigui
 * @date 2024/11/9
 */
@Data
@Accessors(chain = true)
@TableName(TableNameConstant.ROLE_MENU)
public class RoleMenuEntity {

    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 菜单ID
     */
    private Long menuId;
}
