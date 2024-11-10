package com.ckg.books.management.api.common.enums;

import lombok.Getter;

/**
 * 角色状态
 *
 * @author chenkaigui
 * @date 2024/11/10
 */
@Getter
public enum RoleStatus {
    OK(0, "正常"),
    DISABLE(1, "停用");

    private final Integer code;
    private final String desc;

    RoleStatus(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}