package com.ckg.books.management.api.book.req;

import cn.hutool.core.date.DatePattern;
import com.ckg.books.management.common.domain.req.PageReq;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Date;
import java.util.List;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 分页查询图书请求信息
 *
 * @author chenkaigui
 * @date 2024/11/8
 */
@Data
@Accessors(chain = true)
public class PageBookReq extends PageReq {

    /**
     * 图书名称（模糊搜索）
     */
    @Schema(description = "图书名称（模糊搜索）")
    private String name;

    /**
     * 作者名称（模糊搜索）
     */
    @Schema(description = "作者名称（模糊搜索）")
    private String author;

    /**
     * 最小可借阅数量
     */
    @Schema(description = "最小可借阅数量")
    private Integer minAvailableQuantity;

    /**
     * 最大可借阅数量
     */
    @Schema(description = "最大可借阅数量")
    private Integer maxAvailableQuantity;

    /**
     * 最小借阅中数量
     */
    @Schema(description = "最小借阅中数量")
    private Integer minBorrowingQuantity;

    /**
     * 最大借阅中数量
     */
    @Schema(description = "最大借阅中数量")
    private Integer maxBorrowingQuantity;

    /**
     * 最小价格，单位:分
     */
    @Schema(description = "最小价格，单位:分")
    private Long minPrice;

    /**
     * 最大价格，单位:分
     */
    @Schema(description = "最大价格，单位:分")
    private Long maxPrice;

    /**
     * 出版社(模糊搜索)
     */
    @Schema(description = "出版社(模糊搜索)")
    private String publishingHouse;

    /**
     * 开始出版时间
     */
    @Schema(description = "开始出版时间,格式：" + DatePattern.NORM_DATETIME_PATTERN)
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    private Date fromPublicationTime;

    /**
     * 结束出版时间
     */
    @Schema(description = "结束出版时间,格式：" + DatePattern.NORM_DATETIME_PATTERN)
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    private Date toPublicationTime;

    /**
     * 状态，0-正常, 1-无效, 2-不允许借阅
     */
    @Schema(description = "状态，0-正常, 1-无效, 2-不允许借阅")
    private Integer status;

    /**
     * 类别ID列表
     */
    @Schema(description = "类别ID列表")
    private List<Long> categoryIds;
}
