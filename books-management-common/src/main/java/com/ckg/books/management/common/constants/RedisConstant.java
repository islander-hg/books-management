package com.ckg.books.management.common.constants;

/**
 * redis 常量
 *
 * @author chenkaigui
 * @date 2024/11/10
 */
public class RedisConstant {

    /**
     * 登录失败信息缓存KEY 前缀
     */
    public static final String LOGIN_FAIL_INFO_CACHE_KEY_PREFIX = "user_login_fail_info:";

    /**
     * 限制用户登录的缓存KEY前缀
     */
    public static final String LIMIT_LOGIN_CACHE_KEY_PREFIX = "limit_user_login:";

    /**
     * 忘记密码使用的验证码缓存KEY前缀
     */
    public static final String FPWD_CAPTCHA_KEY_PREFIX = "fpwd_captcha:";
}
