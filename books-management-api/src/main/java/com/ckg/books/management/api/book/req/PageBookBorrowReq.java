package com.ckg.books.management.api.book.req;

import cn.hutool.core.date.DatePattern;
import com.ckg.books.management.common.domain.req.PageReq;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "图书ID")
    private Long bookId;

    /**
     * 用户ID
     */
    @Schema(description = "用户ID")
    private Long userId;

    /**
     * 最小借阅数量
     */
    @Schema(description = "最小借阅数量")
    private Integer minBorrowQuantity;

    /**
     * 最大借阅数量
     */
    @Schema(description = "最大借阅数量")
    private Integer maxBorrowQuantity;

    /**
     * 开始借阅时间
     */
    @Schema(description = "开始借阅时间,格式：" + DatePattern.NORM_DATETIME_PATTERN)
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    private Date fromBorrowTime;

    /**
     * 结束借阅时间
     */
    @Schema(description = "结束借阅时间,格式：" + DatePattern.NORM_DATETIME_PATTERN)
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    private Date toBorrowTime;

    /**
     * 开始计划归还时间
     */
    @Schema(description = "开始计划归还时间,格式：" + DatePattern.NORM_DATETIME_PATTERN)
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    private Date fromPlanReturnTime;

    /**
     * 结束计划归还时间
     */
    @Schema(description = "结束计划归还时间,格式：" + DatePattern.NORM_DATETIME_PATTERN)
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    private Date toPlanReturnTime;

    /**
     * 开始实际归还时间
     */
    @Schema(description = "开始实际归还时间,格式：" + DatePattern.NORM_DATETIME_PATTERN)
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    private Date fromFinalReturnTime;

    /**
     * 结束实际归还时间
     */
    @Schema(description = "结束实际归还时间,格式：" + DatePattern.NORM_DATETIME_PATTERN)
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    private Date toFinalReturnTime;

    /**
     * 借阅操作人
     */
    @Schema(description = "借阅操作人")
    private Long operator;
}
