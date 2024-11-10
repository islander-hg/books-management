package com.ckg.books.management.service.sevice.role.impl;

import com.ckg.books.management.api.common.resp.PageResult;
import com.ckg.books.management.api.role.req.PageRoleReq;
import com.ckg.books.management.api.role.resp.GetRoleResp;
import com.ckg.books.management.api.role.resp.PageRoleItem;
import com.ckg.books.management.common.utils.spring.BeanHelper;
import com.ckg.books.management.service.dao.entity.RoleEntity;
import com.ckg.books.management.service.dao.repository.RoleRespository;
import com.ckg.books.management.service.sevice.role.RoleQueryService;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 角色查询操作 Service 接口实现
 *
 * @author chenkaigui
 * @date 2024/11/8
 */
@Slf4j
@Service
public class RoleQueryServiceImpl implements RoleQueryService {

    @Resource
    private RoleRespository roleRespository;

    @Override
    public GetRoleResp get(Long id) {
        RoleEntity roleEntity = roleRespository.getById(id, true);
        return BeanHelper.copyProperties(roleEntity, GetRoleResp.class);
    }

    @Override
    public PageResult<PageRoleItem> searchPage(PageRoleReq pageReq) {
        PageResult<RoleEntity> pageResult = roleRespository.searchPage(pageReq);
        return new PageResult(
                BeanHelper.copyWithCollection(pageResult.getItems(), PageRoleItem.class),
                pageResult.getTotal());
    }

}
