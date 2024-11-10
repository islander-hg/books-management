package com.ckg.books.management.api.book.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 创建图书分类请求信息
 *
 * @author chenkaigui
 * @date 2024/11/8
 */
@Data
@Accessors(chain = true)
public class CreateBookCategoryReq extends BaseOperateBookCategoryReq {

    /**
     * 层级
     */
    @Schema(description = "层级")
    private Integer level;
}
