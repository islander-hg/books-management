package com.ckg.books.management.api.book.resp;

import cn.hutool.core.date.DatePattern;
import com.ckg.books.management.common.domain.resp.BaseEntityResp;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Date;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 图书借阅记录基础的响应信息
 *
 * @author chenkaigui
 * @date 2024/11/9
 */
@Data
@Accessors(chain = true)
public class BaseBookBorrowResp extends BaseEntityResp {

    /**
     * 图书借阅记录ID
     */
    @Schema(description = "图书借阅记录ID")
    private Long id;

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
     * 借阅数量
     */
    @Schema(description = "借阅数量")
    private Integer borrowQuantity;

    /**
     * 归还数量
     */
    @Schema(description = "归还数量")
    private Integer returnQuantity;

    /**
     * 借阅时间
     */
    @Schema(description = "借阅时间,格式：" + DatePattern.NORM_DATETIME_PATTERN)
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    private Date borrowTime;

    /**
     * 计划归还时间
     */
    @Schema(description = "计划归还时间,格式：" + DatePattern.NORM_DATETIME_PATTERN)
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    private Date planReturnTime;

    /**
     * 实际归还时间
     */
    @Schema(description = "实际归还时间,格式：" + DatePattern.NORM_DATETIME_PATTERN)
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    private Date finalReturnTime;

    /**
     * 借阅操作人
     */
    @Schema(description = "借阅操作人")
    private Long operator;
}
