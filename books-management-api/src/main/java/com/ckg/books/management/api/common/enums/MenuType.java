package com.ckg.books.management.api.common.enums;

import lombok.Getter;

/**
 * 菜单类型
 *
 * @author chenkaigui
 * @date 2024/11/10
 */
@Getter
public enum MenuType {
    DIR(0, "目录"),
    MENU(1, "菜单"),
    BUTTON(2,"按钮");

    private final Integer code;
    private final String desc;

    MenuType(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
