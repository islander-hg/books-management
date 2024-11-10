package com.ckg.books.management.api.book.req;

import cn.hutool.core.date.DatePattern;
import com.ckg.books.management.api.common.req.PageReq;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 分页查询图书借阅记录请求信息
 *
 * @author chenkaigui
 * @date 2024/11/8
 */
@Data
@Accessors(chain = true)
public class PageBookBorrowReq extends PageReq {

    /**
     * 图书ID
     */
    private Long bookId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 最小借阅数量
     */
    private Integer minBorrowQuantity;

    /**
     * 最大借阅数量
     */
    private Integer maxBorrowQuantity;

    /**
     * 开始借阅时间
     */
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    private Date fromBorrowTime;

    /**
     * 结束借阅时间
     */
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    private Date toBorrowTime;

    /**
     * 开始计划归还时间
     */
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    private Date fromPlanReturnTime;

    /**
     * 结束计划归还时间
     */
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    private Date toPlanReturnTime;

    /**
     * 开始实际归还时间
     */
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    private Date fromFinalReturnTime;

    /**
     * 结束实际归还时间
     */
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    private Date toFinalReturnTime;

    /**
     * 借阅操作人
     */
    private Long operator;
}
