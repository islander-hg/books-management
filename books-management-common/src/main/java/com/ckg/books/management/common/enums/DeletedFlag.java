package com.ckg.books.management.common.enums;

import lombok.Getter;

/**
 * 删除标识
 *
 * @author chenkaigui
 * @date 2024/11/9
 */
@Getter
public enum DeletedFlag {

    UNDELETED(0),

    DELETED(1);

    private Integer code;

    DeletedFlag(Integer code) {
        this.code = code;
    }
}
