package com.ckg.books.management.common.enums;

import lombok.Getter;

/**
 * 图书状态
 *
 * @author chenkaigui
 * @date 2024/11/10
 */
@Getter
public enum BookStatus {
    OK(0, "正常"),
    INVALID(1, "无效"),
    NOT_BORROW(2, "不允许借阅");

    private final Integer code;
    private final String desc;

    BookStatus(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
