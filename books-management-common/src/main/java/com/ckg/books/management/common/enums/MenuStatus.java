package com.ckg.books.management.common.enums;

import lombok.Getter;

/**
 * 菜单状态
 *
 * @author chenkaigui
 * @date 2024/11/10
 */
@Getter
public enum MenuStatus {
    NORMAL(0, "正常"),
    DISABLE(1, "停用");

    private final Integer code;
    private final String desc;

    MenuStatus(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
