package com.ckg.books.management.api.book.req;

import cn.hutool.core.date.DatePattern;
import com.ckg.books.management.api.common.req.PageReq;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
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
    private String name;

    /**
     * 作者名称（模糊搜索）
     */
    private String author;

    /**
     * 最小可借阅数量
     */
    private Integer minAvailableQuantity;

    /**
     * 最大可借阅数量
     */
    private Integer maxAvailableQuantity;

    /**
     * 最小借阅中数量
     */
    private Integer minBorrowingQuantity;

    /**
     * 最大借阅中数量
     */
    private Integer maxBorrowingQuantity;

    /**
     * 最小价格，单位:分
     */
    private Long minPrice;

    /**
     * 最大价格，单位:分
     */
    private Long maxPrice;

    /**
     * 出版社(模糊搜索)
     */
    private String publishingHouse;

    /**
     * 开始出版时间
     */
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    private Date fromPublicationTime;

    /**
     * 结束出版时间
     */
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    private Date toPublicationTime;

    /**
     * 状态，0-正常, 1-无效, 2-不允许借阅
     */
    private Integer status;
}
