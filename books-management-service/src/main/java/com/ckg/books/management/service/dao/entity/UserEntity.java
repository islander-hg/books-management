package com.ckg.books.management.service.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * 用户
 *
 * @author chenkaigui
 * @date 2024/11/7
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@TableName(TableNameConstant.USER)
public class UserEntity extends BaseEntity {

    /**
     * 用户ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户名（账号）
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 性别，0-女 1-男 2-未知
     */
    private Integer sex;

    /**
     * 状态，0-正常, 1-停用
     */
    private Integer status;

    /**
     * 删除标识；0-未删除，值为ID时-已删除
     */
    @TableLogic(delval = EntityFieldConstant.ID)
    private Long deleted;
}
