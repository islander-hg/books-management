package com.ckg.books.management.service.sevice.auth.impl;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import com.ckg.books.management.common.constants.AuthConstant;
import com.ckg.books.management.common.domain.user.LoginUser;
import com.ckg.books.management.common.utils.time.TimeUtils;
import com.ckg.books.management.service.config.properties.AuthProperties;
import com.ckg.books.management.service.sevice.auth.UserTokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

/**
 * 用户Token 接口实现
 *
 * @author chenkaigui
 * @date 2024/11/9
 */
@Service
public class UserTokenServiceImpl implements UserTokenService {

    @Resource
    private AuthProperties authProperties;

    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public String createToken(LoginUser loginUser) {
        String tokenId = UUID.fastUUID().toString();
        loginUser.setTokenId(tokenId);
        doRefreshTokenExpireTime(loginUser);

        Map<String, Object> claims = new HashMap<>();
        claims.put(AuthConstant.JWT_LOGIN_USER_KEY, tokenId);
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, authProperties.getToken().getSecret())
                .compact();
    }

    @Override
    public void deleteLoginUser(String token) {
        redisTemplate.delete(getTokenKey(token));
    }

    @Override
    public LoginUser getLoginUser(HttpServletRequest request) {
        // 获取请求携带的令牌
        String token = getToken(request);
        if (StrUtil.isBlank(token)) {
            return null;
        }
        Claims claims = parseToken(token);
        // 解析对应的权限以及用户信息
        String uuid = (String) claims.get(AuthConstant.JWT_LOGIN_USER_KEY);
        String tokenKey = getTokenKey(uuid);
        ValueOperations<String, LoginUser> operation = redisTemplate.opsForValue();
        return operation.get(tokenKey);
    }

    @Override
    public void refreshTokenExpireTime(LoginUser loginUser) {
        long remainTime = loginUser.getExpireTime() - System.currentTimeMillis();
        if (remainTime <= TimeUtils
                .minuteToMillisecond(authProperties.getToken().getRefreshThreshold())) {
            doRefreshTokenExpireTime(loginUser);
        }
    }

    /**
     * 获取请求token
     *
     * @param request http 请求信息
     * @return token
     */
    private String getToken(HttpServletRequest request) {
        String token = request.getHeader(authProperties.getToken().getHeader());
        if (StrUtil.isNotBlank(token) && token.startsWith(AuthConstant.TOKEN_PREFIX)) {
            token = token.substring(AuthConstant.TOKEN_PREFIX.length());
        }
        return token;
    }

    /**
     * 刷新令牌有效期
     *
     * @param loginUser 登录信息
     */
    private void doRefreshTokenExpireTime(LoginUser loginUser) {
        loginUser.setExpireTime(System.currentTimeMillis()
                + TimeUtils.minuteToMillisecond(authProperties.getToken().getAvailableDuration()));
        // 根据uuid将loginUser缓存
        String tokenKey = getTokenKey(loginUser.getTokenId());
        redisTemplate.opsForValue()
                .set(tokenKey, loginUser,
                        authProperties.getToken().getAvailableDuration(), TimeUnit.MINUTES);
    }

    /**
     * 从令牌中获取数据声明
     *
     * @param token 令牌
     * @return 数据声明
     */
    private Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(authProperties.getToken().getSecret())
                .parseClaimsJws(token)
                .getBody();
    }

    private String getTokenKey(String uuid) {
        return AuthConstant.JWT_LOGIN_USER_KEY + ":" + uuid;
    }
}
