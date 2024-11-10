package com.ckg.books.management.common.exception;

import lombok.Getter;

/**
 * 业务错误类型枚举
 *
 * @author chenkaigui
 * @date 2024/11/10
 */
@Getter
public enum BizErrorType {

    /**
     * 用户
     */
    USER(1),

    /**
     * 角色
     */
    ROLE(2),

    /**
     * 菜单
     */
    MENU(3),

    /**
     * 图书
     */
    BOOK(4),

    /**
     * 图书借阅
     */
    BOOK_BORROW(5),

    /**
     * 工具
     */
    UTIL(6),

    /**
     * 通用
     */
    COMMON(9);

    private int code;

    BizErrorType(int code) {
        this.code = code;
    }


}
