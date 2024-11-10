package com.ckg.books.management.server.web;

import com.ckg.books.management.api.auth.req.LoginReq;
import com.ckg.books.management.api.auth.resp.LoginResp;
import com.ckg.books.management.api.common.resp.CommonResp;
import com.ckg.books.management.api.user.resp.GetUserResp;
import com.ckg.books.management.common.domain.user.LoginUser;
import com.ckg.books.management.common.utils.security.SecurityUtils;
import com.ckg.books.management.service.sevice.auth.LoginService;
import com.ckg.books.management.service.sevice.user.UserQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录验证
 *
 * @author chenkaigui
 * @date 2024/11/9
 */
@Tag(name = "登录")
@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private UserQueryService userQueryService;

    /**
     * 账号密码登录
     *
     * @param loginReq 登录信息
     * @return 登录响应信息
     */
    @Operation(summary = "账号密码登录")
    @PostMapping("/login")
    public CommonResp<LoginResp> login(@RequestBody LoginReq loginReq) {
        return CommonResp.success(loginService.login(loginReq));
    }

    /**
     * 获取当前登录用户详情信息
     *
     * @return 当前登录用户详情信息
     */
    @Operation(summary = "获取当前登录用户详情信息")
    @GetMapping("/cur_login_user")
    public CommonResp<GetUserResp> getCurLoginUser() {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        return CommonResp.success(userQueryService.get(loginUser.getId()));
    }
}
