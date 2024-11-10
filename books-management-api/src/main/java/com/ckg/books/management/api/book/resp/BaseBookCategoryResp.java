package com.ckg.books.management.api.book.resp;

import com.ckg.books.management.api.common.resp.BaseEntityResp;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 图书分类基础的响应信息
 *
 * @author chenkaigui
 * @date 2024/11/9
 */
@Data
@Accessors(chain = true)
public class BaseBookCategoryResp extends BaseEntityResp {

    /**
     * 图书分类ID
     */
    @Schema(description = "图书分类ID")
    private Long id;

    /**
     * 图书分类名称
     */
    @Schema(description = "图书分类名称")
    private String name;

    /**
     * 父图书分类ID
     */
    @Schema(description = "父图书分类ID")
    private Long parentId;

    /**
     * 层级
     */
    @Schema(description = "层级")
    private Integer level;

    /**
     * 显示顺序
     */
    @Schema(description = "显示顺序")
    private Integer order;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;
}
