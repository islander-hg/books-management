package com.ckg.books.management.server.security.impl;

import cn.hutool.core.util.StrUtil;
import com.ckg.books.management.common.domain.resp.CommonResp;
import com.ckg.books.management.common.utils.json.JsonUtils;
import com.ckg.books.management.common.utils.servlet.ServletUtils;
import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 * 认证失败处理类 返回未授权
 *
 * @author chenkaigui
 * @date 2024/11/8
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint, Serializable {

    private static final long serialVersionUID = 1;

    @Override
    public void commence(
            HttpServletRequest request, HttpServletResponse response, AuthenticationException e) {
        String msg = StrUtil.format("请求访问：{}，认证失败，无法访问系统资源", request.getRequestURI());
        ServletUtils.renderString(response,
                JsonUtils.toJsonString(CommonResp.error(HttpStatus.UNAUTHORIZED.value(), msg)));
    }

}
