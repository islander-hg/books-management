package com.ckg.books.management.service.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ckg.books.management.common.constants.TableNameConstant;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 用户角色关联信息
 *
 * @author chenkaigui
 * @date 2024/11/9
 */
@Data
@Accessors(chain = true)
@TableName(TableNameConstant.USER_ROLE)
public class UserRoleEntity {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 角色ID
     */
    private Long roleId;
}
