package com.ckg.books.management.api.book.req;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 * 通用的操作图书分类请求信息
 *
 * @author chenkaigui
 * @date 2024/11/8
 */
@Data
public class BaseOperateBookCategoryReq {

    /**
     * 图书分类名称
     */
    @Schema(description = "图书分类名称")
    @NotBlank(message = "图书分类名不能为空")
    @Size(max = 50, message = "图书分类名长度不允许超过50位字符")
    private String name;

    /**
     * 父图书分类ID
     */
    @Schema(description = "父图书分类ID")
    private Long parentId;

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
