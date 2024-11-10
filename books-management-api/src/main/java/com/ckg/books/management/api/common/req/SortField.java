package com.ckg.books.management.api.common.req;

import lombok.Data;

/**
 * 排序字段 DTO
 *
 * @author chenkaigui
 * @date 2024/11/8
 */
@Data
public class SortField {

    /**
     * 顺序 - 升序
     */
    public static final String ORDER_ASC = "asc";
    /**
     * 顺序 - 降序
     */
    public static final String ORDER_DESC = "desc";

    /**
     * 字段
     */
    private String field;

    /**
     * 顺序
     */
    private String order;

    public SortField() {
    }

    public SortField(String field, String order) {
        this.field = field;
        this.order = order;
    }
}
