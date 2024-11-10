package com.ckg.books.management.service.sevice.role;

import com.ckg.books.management.api.role.req.CreateRoleReq;
import com.ckg.books.management.api.role.req.UpdateRoleReq;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

/**
 * 角色新增、变更操作 Service 接口
 *
 * @author chenkaigui
 * @date 2024/11/8
 */
@Validated
public interface RoleOperateService {

    /**
     * 创建角色
     *
     * @param createReq 创建请求信息
     */
    void create(@Valid CreateRoleReq createReq);

    /**
     * 修改角色
     *
     * @param id        角色ID
     * @param updateReq 修改请求信息
     */
    void update(@NotNull Long id, @Valid UpdateRoleReq updateReq);

    /**
     * 删除角色
     *
     * @param id 角色ID
     */
    void delete(@NotNull Long id);

}
