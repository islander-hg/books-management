package com.ckg.books.management.common.enums;

import lombok.Getter;

/**
 * 菜单显示类型
 *
 * @author chenkaigui
 * @date 2024/11/10
 */
@Getter
public enum MenuVisible {

    DISPLAY(0, "显示"),
    HIDE(1, "隐藏");

    private final Integer code;
    private final String desc;

    MenuVisible(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
