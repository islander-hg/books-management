package com.ckg.books.management.api.common.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

/**
 * 分页结果
 *
 * @author chenkaigui
 * @date 2024/11/7
 */
@Data
public class PageResult<T> {

    /**
     * 分页列表数据
     */
    @Schema(description = "分页列表数据")
    private List<T> items;

    /**
     * 总条数
     */
    private Long total;

    public PageResult() {
    }

    public PageResult(List<T> items, Long total) {
        this.items = items;
        this.total = total;
    }

    public PageResult(Long total) {
        this.items = new ArrayList<>();
        this.total = total;
    }

    public static <T> PageResult<T> empty() {
        return new PageResult<>(0L);
    }

    public static <T> PageResult<T> empty(Long total) {
        return new PageResult<>(total);
    }
}
