package com.ckg.books.management.service.dao.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ckg.books.management.api.common.resp.PageResult;
import com.ckg.books.management.api.user.req.PageUserReq;
import com.ckg.books.management.service.dao.entity.UserEntity;
import java.util.List;

/**
 * 用户 Respository 接口实现
 *
 * @author chenkaigui
 * @date 2024/11/8
 */
public interface UserRespository extends IService<UserEntity> {

    /**
     * 根据用户ID获取用户信息
     *
     * @param id                 用户ID
     * @param throwNotFoundError 用户不存在时是否抛异常; true-是 false-否
     * @return 用户信息
     */
    UserEntity getById(Long id, boolean throwNotFoundError);

    /**
     * 通过用户名（账号）或邮箱获取用户信息
     *
     * @param username           用户名（账号）
     * @param throwNotFoundError 用户不存在时是否抛异常; true-是 false-否
     * @return 用户信息
     */
    UserEntity getByUsername(String username, boolean throwNotFoundError);

    /**
     * 通过用户名（账号）或邮箱获取用户信息
     *
     * @param username 用户名（账号）
     * @param email    邮箱
     * @return 用户信息列表
     */
    List<UserEntity> findByUsernameOrEmail(String username, String email);

    /**
     * 分页查询用户信息
     *
     * @param pageReq 分页请求信息
     * @return 用户信息分页结果
     */
    PageResult<UserEntity> searchPage(PageUserReq pageReq);

    /**
     * 更新用户密码
     *
     * @param username 用户名（账号）
     * @param password 新密码
     * @return true-成功 false-失败
     */
    boolean updatePassword(String username, String password);

}
