package com.ckg.books.management.service.sevice.role;

import com.ckg.books.management.api.common.resp.PageResult;
import com.ckg.books.management.api.role.req.PageRoleReq;
import com.ckg.books.management.api.role.resp.GetRoleResp;
import com.ckg.books.management.api.role.resp.PageRoleItem;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

/**
 * 角色查询操作 Service 接口
 *
 * @author chenkaigui
 * @date 2024/11/8
 */
@Validated
public interface RoleQueryService {

    /**
     * 获取角色详情信息
     *
     * @param id 角色ID
     * @return 角色详情信息
     */
    GetRoleResp get(@NotNull Long id);

    /**
     * 分页查询角色信息
     *
     * @param pageReq 分页请求信息
     * @return 分页结果
     */
    PageResult<PageRoleItem> searchPage(@Valid PageRoleReq pageReq);
}
