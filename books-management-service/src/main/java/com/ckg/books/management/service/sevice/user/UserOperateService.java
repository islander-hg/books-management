package com.ckg.books.management.service.sevice.user;

import com.ckg.books.management.api.user.req.CreateUserReq;
import com.ckg.books.management.api.user.req.UpdateUserReq;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

/**
 * 用户新增、变更操作 Service 接口
 *
 * @author chenkaigui
 * @date 2024/11/8
 */
@Validated
public interface UserOperateService {

    /**
     * 创建用户
     *
     * @param createReq 创建请求信息
     */
    void create(@Valid CreateUserReq createReq);

    /**
     * 修改用户
     *
     * @param id        用户ID
     * @param updateReq 修改请求信息
     */
    void update(@NotNull Long id, @Valid UpdateUserReq updateReq);

    /**
     * 删除用户
     *
     * @param id 用户ID
     */
    void delete(@NotNull Long id);

}
