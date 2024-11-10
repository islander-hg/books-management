package com.ckg.books.management.service.sevice.auth;

import com.ckg.books.management.api.auth.req.LoginReq;
import com.ckg.books.management.api.auth.resp.LoginResp;
import javax.validation.Valid;
import org.springframework.validation.annotation.Validated;

/**
 * 用户登录 接口
 *
 * @author chenkaigui
 * @date 2024/11/9
 */
@Validated
public interface LoginService {

    /**
     * 账号密码登录
     *
     * @param loginReq 登录信息
     * @return 登录响应信息
     */
    LoginResp login(@Valid LoginReq loginReq);
}
