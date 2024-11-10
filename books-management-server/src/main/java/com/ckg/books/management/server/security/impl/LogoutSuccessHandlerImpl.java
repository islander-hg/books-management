package com.ckg.books.management.server.security.impl;

import com.alibaba.druid.support.json.JSONUtils;
import com.ckg.books.management.common.domain.resp.CommonResp;
import com.ckg.books.management.common.domain.user.LoginUser;
import com.ckg.books.management.common.utils.servlet.ServletUtils;
import com.ckg.books.management.service.sevice.auth.UserTokenService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

/**
 * 自定义退出处理类 返回成功
 *
 * @author chenkaigui
 * @date 2024/11/8
 */
@Configuration
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {

    @Autowired
    private UserTokenService tokenService;

    /**
     * 退出处理
     *
     * @return
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) {
        LoginUser loginUser = tokenService.getLoginUser(request);
        if (null != loginUser) {
            // 删除用户缓存记录
            tokenService.deleteLoginUser(loginUser.getTokenId());
        }
        ServletUtils.renderString(response, JSONUtils.toJSONString(CommonResp.success("退出成功")));
    }
}
