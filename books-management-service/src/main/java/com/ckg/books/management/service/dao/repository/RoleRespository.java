package com.ckg.books.management.service.dao.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ckg.books.management.common.domain.resp.PageResult;
import com.ckg.books.management.api.role.req.PageRoleReq;
import com.ckg.books.management.service.dao.entity.RoleEntity;
import java.util.Collection;
import java.util.List;

/**
 * 角色 Respository 接口实现
 *
 * @author chenkaigui
 * @date 2024/11/9
 */
public interface RoleRespository extends IService<RoleEntity> {

    /**
     * 根据角色ID获取角色信息
     *
     * @param id                 角色ID
     * @param throwNotFoundError 角色不存在时是否抛异常; true-是 false-否
     * @return 角色信息
     */
    RoleEntity getById(Long id, boolean throwNotFoundError);

    /**
     * 通过角色名或邮箱获取角色信息
     *
     * @param name 角色名
     * @return 角色信息
     */
    RoleEntity getByName(String name);

    /**
     * 分页查询角色信息
     *
     * @param pageReq 分页请求信息
     * @return 角色信息分页结果
     */
    PageResult<RoleEntity> searchPage(PageRoleReq pageReq);

    /**
     * 根据角色ID列表获取角色信息
     *
     * @param ids 角色ID列表
     * @return 角色信息列表
     */
    List<RoleEntity> findByIds(Collection<Long> ids);
}
