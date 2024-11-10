package com.ckg.books.management.service.sevice.auth;

import com.ckg.books.management.common.domain.user.LoginUser;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

/**
 * 用户Token 接口
 *
 * @author chenkaigui
 * @date 2024/11/9
 */
@Validated
public interface UserTokenService {

    /**
     * 创建令牌
     *
     * @param loginUser 用户信息
     * @return 令牌
     */
    String createToken(@NotNull LoginUser loginUser);

    /**
     * 删除登录用户信息
     *
     * @param token 用户token
     */
    void deleteLoginUser(@NotBlank String token);

    /**
     * 获取登录用户信息
     *
     * @param request http 请求信息
     * @return 用户信息
     */
    LoginUser getLoginUser(@NotNull HttpServletRequest request);

    /**
     * 根据配置刷新 token 过期时间
     *
     * @param loginUser 登录用户信息
     */
    void refreshTokenExpireTime(@NotNull LoginUser loginUser);

}
