package com.ckg.books.management.api.common.enums;

import lombok.Getter;

/**
 * 菜单外链类型
 *
 * @author chenkaigui
 * @date 2024/11/10
 */
@Getter
public enum MenuFrameable {

    NO(0, "否"),
    YES(1, "是");

    private final Integer code;
    private final String desc;

    MenuFrameable(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
