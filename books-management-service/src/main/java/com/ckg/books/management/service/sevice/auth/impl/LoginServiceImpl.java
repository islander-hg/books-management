package com.ckg.books.management.service.sevice.auth.impl;

import cn.hutool.core.collection.CollUtil;
import com.ckg.books.management.api.auth.req.LoginReq;
import com.ckg.books.management.api.auth.resp.LoginResp;
import com.ckg.books.management.common.constants.RedisConstant;
import com.ckg.books.management.common.domain.user.LoginFailInfo;
import com.ckg.books.management.common.domain.user.LoginUser;
import com.ckg.books.management.common.exception.BizErrorCodes;
import com.ckg.books.management.common.exception.ExceptionHelper;
import com.ckg.books.management.service.config.properties.AuthProperties;
import com.ckg.books.management.service.sevice.auth.LoginService;
import com.ckg.books.management.service.sevice.auth.UserTokenService;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

/**
 * 用户登录 接口实现
 *
 * @author chenkaigui
 * @date 2024/11/9
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    private UserTokenService userTokenService;

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private AuthProperties authProperties;

    @Override
    public LoginResp login(LoginReq loginReq) {
        //1. 判断是否允许登录
        if (!isAbleToLogin(loginReq.getUsername())) {
            throw ExceptionHelper
                    .create(BizErrorCodes.LOGIN_RESTRICTION, "您操作过于频繁，{}分钟后再来操作，或联系系统管理员解决",
                            authProperties.getLogin().getLimitLoginDuration());
        }

        //2. 用户验证
        Authentication authentication = null;
        try {
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            authentication =
                    authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    loginReq.getUsername(), loginReq.getPassword()));
        } catch (AuthenticationException e) {
            // 记录登录失败信息
            recordLoginFailureInfo(loginReq.getUsername());
            throw ExceptionHelper.create(BizErrorCodes.LOGIN_FAILED, "登录失败:{}", e.getMessage(), e);
        }

        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        loginUser.setLoginTime(System.currentTimeMillis());

        //3. 登录成功，清空错误计数
        clearLoginFailureInfo(loginReq.getUsername());
        return new LoginResp().setToken(userTokenService.createToken(loginUser));
    }

    /**
     * 是否允许登录
     *
     * @param username 用户名（账号）
     * @return true-是， false-否
     */
    private boolean isAbleToLogin(String username) {
        String limitKey = RedisConstant.LIMIT_LOGIN_CACHE_KEY_PREFIX + username;
        if (null != redisTemplate.opsForValue().get(limitKey)) {
            return false;
        }
        int failCount = getLoginFailInfoWithinTimeWindow(username).size();
        return failCount < authProperties.getLogin().getMaxFailureCount();
    }

    /**
     * 记录登录失败缓存信息
     *
     * @param username （账号）
     */
    private void recordLoginFailureInfo(String username) {
        List<LoginFailInfo> loginFailInfos =
                getLoginFailInfoWithinTimeWindow(username);

        loginFailInfos.add(new LoginFailInfo().setUsername(username)
                .setLoginTime(System.currentTimeMillis()));

        // 如果失败次数超过限制，则根据配置限制登录
        if (loginFailInfos.size() >= authProperties.getLogin().getMaxFailureCount()) {
            String limitKey = RedisConstant.LIMIT_LOGIN_CACHE_KEY_PREFIX + username;
            redisTemplate.opsForValue()
                    .set(limitKey, "1",
                            authProperties.getLogin().getLimitLoginDuration(), TimeUnit.MINUTES);
        }

        // 记录登录失败信息
        ValueOperations<String, List<LoginFailInfo>> operation = redisTemplate.opsForValue();
        String failKey = RedisConstant.LOGIN_FAIL_INFO_CACHE_KEY_PREFIX + username;
        operation.set(failKey,
                loginFailInfos,
                authProperties.getLogin().getFailureTimeWindow(), TimeUnit.MINUTES);
    }

    /**
     * 删除登录失败缓存信息
     *
     * @param username （账号）
     */
    private void clearLoginFailureInfo(String username) {
        String cacheKey = RedisConstant.LOGIN_FAIL_INFO_CACHE_KEY_PREFIX + username;
        redisTemplate.delete(cacheKey);
    }

    /**
     * 获取时间窗口内的登录失败信息
     *
     * @param username 用户名（账号）
     * @return 时间窗口内的登录失败信息
     */
    private List<LoginFailInfo> getLoginFailInfoWithinTimeWindow(String username) {
        String cacheKey = RedisConstant.LOGIN_FAIL_INFO_CACHE_KEY_PREFIX + username;
        ValueOperations<String, List<LoginFailInfo>> operation = redisTemplate.opsForValue();
        List<LoginFailInfo> loginFailInfos = operation.get(cacheKey);
        if (CollUtil.isEmpty(loginFailInfos)) {
            return new ArrayList<>();
        }

        long now = System.currentTimeMillis();
        long timeWindow =
                TimeUnit.MINUTES.toMillis(authProperties.getLogin().getFailureTimeWindow());
        return loginFailInfos.stream()
                .filter(item -> item.getLoginTime() + timeWindow > now)
                .collect(Collectors.toList());
    }
}
