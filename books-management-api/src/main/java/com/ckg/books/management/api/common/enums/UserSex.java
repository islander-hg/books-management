package com.ckg.books.management.api.common.enums;

import lombok.Getter;

/**
 * 用户性别
 *
 * @author chenkaigui
 * @date 2024/11/10
 */
@Getter
public enum UserSex {
    WOMAN(0, "女"),
    MAN(1, "男"),
    UNKNOWN(2, "未知");

    private final Integer code;
    private final String desc;

    UserSex(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
