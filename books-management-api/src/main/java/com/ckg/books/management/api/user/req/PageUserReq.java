package com.ckg.books.management.api.user.req;

import com.ckg.books.management.api.common.req.PageReq;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 分页查询用户请求信息
 *
 * @author chenkaigui
 * @date 2024/11/8
 */
@Data
@Accessors(chain = true)
public class PageUserReq extends PageReq {

    /**
     * 用户名（账号，模糊查询）
     */
    private String username;

    /**
     * 用户昵称（模糊查询）
     */
    private String nickname;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 性别，0-女 1-男 2-未知
     */
    private Integer sex;
}
