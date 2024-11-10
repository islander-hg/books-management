package com.ckg.books.management.api.book.req;

import cn.hutool.core.date.DatePattern;
import com.ckg.books.management.api.annotation.EnumValue;
import com.ckg.books.management.api.common.enums.BookStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Date;
import java.util.List;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 * 通用的操作图书请求信息
 *
 * @author chenkaigui
 * @date 2024/11/8
 */
@Data
public class BaseOperateBookReq {

    /**
     * 图书名称
     */
    @Schema(description = "图书名称")
    @NotBlank(message = "图书名称不能为空")
    @Size(max = 50, message = "图书名称长度不允许超过50位字符")
    private String name;

    /**
     * 作者名称
     */
    @Schema(description = "作者名称")
    @NotBlank(message = "作者名称不能为空")
    @Size(max = 50, message = "作者名称长度不允许超过50位字符")
    private String author;

    /**
     * 可借阅数量
     */
    @Schema(description = "可借阅数量")
    @NotNull(message = "可借阅数量不能为空")
    @Min(value = 0, message = "可借阅数量不允许小于0")
    private Integer availableQuantity;

    /**
     * 价格，单位:分
     */
    @Schema(description = "价格，单位:分")
    @NotNull(message = "价格不能为空")
    @Min(value = 0, message = "价格不允许小于0")
    private Long price;

    /**
     * 出版社
     */
    @Schema(description = "出版社")
    private String publishingHouse;

    /**
     * 出版时间
     */
    @Schema(description = "出版时间,格式：" + DatePattern.NORM_DATETIME_PATTERN)
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    private Date publicationTime;

    /**
     * 状态，0-正常, 1-无效, 2-不允许借阅
     */
    @Schema(description = "状态，0-正常, 1-无效, 2-不允许借阅")
    @EnumValue(enumClass = BookStatus.class)
    private Integer status;

    /**
     * 授权关联的图书分类ID列表
     */
    @Schema(description = "授权关联的图书分类ID列表")
    private List<Long> categoryIds;
}
