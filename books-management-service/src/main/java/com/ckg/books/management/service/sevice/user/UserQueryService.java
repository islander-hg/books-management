package com.ckg.books.management.service.sevice.user;

import com.ckg.books.management.common.domain.resp.PageResult;
import com.ckg.books.management.api.user.req.PageUserReq;
import com.ckg.books.management.api.user.resp.GetUserResp;
import com.ckg.books.management.api.user.resp.PageUserItem;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

/**
 * 用户查询操作 Service 接口
 *
 * @author chenkaigui
 * @date 2024/11/8
 */
@Validated
public interface UserQueryService {

    /**
     * 获取用户详情信息
     *
     * @param id 用户ID
     * @return 用户详情信息
     */
    GetUserResp get(@NotNull Long id);

    /**
     * 分页查询用户信息
     *
     * @param pageReq 分页请求信息
     * @return 分页结果
     */
    PageResult<PageUserItem> searchPage(@Valid PageUserReq pageReq);
}
