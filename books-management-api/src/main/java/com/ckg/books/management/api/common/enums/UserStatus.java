package com.ckg.books.management.api.common.enums;

import lombok.Getter;

/**
 * 用户状态
 *
 * @author chenkaigui
 * @date 2024/11/7
 */
@Getter
public enum UserStatus {
    OK(0, "正常"),
    DISABLE(1, "停用");

    private final Integer code;
    private final String desc;

    UserStatus(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
