package com.ckg.books.management.api.book.req;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 通用的操作图书借阅记录请求信息
 *
 * @author chenkaigui
 * @date 2024/11/8
 */
@Data
@Accessors(chain = true)
public class BaseOperateBookBorrowReq {

    /**
     * 用户ID
     */
    @NotNull(message = "用户ID不能为空")
    private Long userId;

    /**
     * 借阅时间
     */
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    @NotNull(message = "借阅时间不能为空")
    private Date borrowTime;

    /**
     * 计划归还时间
     */
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    @NotNull(message = "计划归还时间不能为空")
    private Date planReturnTime;

    /**
     * 实际归还时间
     */
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    private Date finalReturnTime;

    /**
     * 借阅操作人
     */
    @NotNull(message = "借阅操作人不能为空")
    private Long operator;
}
