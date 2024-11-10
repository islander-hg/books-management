package com.ckg.books.management.service.sevice.user.impl;

import com.ckg.books.management.common.domain.resp.PageResult;
import com.ckg.books.management.api.user.req.PageUserReq;
import com.ckg.books.management.api.user.resp.GetUserResp;
import com.ckg.books.management.api.user.resp.PageUserItem;
import com.ckg.books.management.common.domain.user.LoginUser;
import com.ckg.books.management.common.enums.UserStatus;
import com.ckg.books.management.common.exception.BizErrorCodes;
import com.ckg.books.management.common.exception.ExceptionHelper;
import com.ckg.books.management.common.utils.spring.BeanHelper;
import com.ckg.books.management.service.dao.entity.UserEntity;
import com.ckg.books.management.service.dao.repository.UserRespository;
import com.ckg.books.management.service.sevice.auth.UserPermissionService;
import com.ckg.books.management.service.sevice.role.RoleQueryService;
import com.ckg.books.management.service.sevice.user.UserQueryService;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 用户查询操作 Service 接口实现
 *
 * @author chenkaigui
 * @date 2024/11/8
 */
@Slf4j
@Service
public class UserQueryServiceImpl implements UserQueryService, UserDetailsService {

    @Resource
    private UserRespository userRespository;

    @Resource
    private UserPermissionService userPermissionService;

    @Resource
    private RoleQueryService roleQueryService;

    @Override
    public GetUserResp get(Long id) {
        UserEntity userEntity = userRespository.getById(id, true);
        GetUserResp resp = BeanHelper.copyProperties(userEntity, GetUserResp.class);
        resp.setRoleList(roleQueryService.getUserRoles(id));
        return resp;
    }

    @Override
    public PageResult<PageUserItem> searchPage(PageUserReq pageReq) {
        PageResult<UserEntity> pageResult = userRespository.searchPage(pageReq);
        return new PageResult(
                BeanHelper.copyWithCollection(pageResult.getItems(), PageUserItem.class),
                pageResult.getTotal());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRespository.getByUsername(username, true);
        if (UserStatus.DISABLE.getCode().equals(userEntity.getStatus())) {
            throw ExceptionHelper
                    .create(BizErrorCodes.ACCOUNT_ABNORMALITY, "对不起，您的账号：{} 已停用", username);
        }
        return createLoginUser(userEntity);
    }

    private LoginUser createLoginUser(UserEntity userEntity) {
        LoginUser loginUser = BeanHelper.copyProperties(userEntity, LoginUser.class);
        return loginUser
                .setPermissions(userPermissionService.getUserMenuPermission(userEntity.getId()));
    }

}
