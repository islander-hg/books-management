package com.ckg.books.management.api.book.resp;

import cn.hutool.core.date.DatePattern;
import com.ckg.books.management.api.common.resp.BaseEntityResp;
import com.fasterxml.jackson.annotation.JsonFormat;
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

    private Long id;

    /**
     * 图书ID
     */
    private Long bookId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 借阅数量
     */
    private Integer borrowQuantity;

    /**
     * 归还数量
     */
    private Integer returnQuantity;

    /**
     * 借阅时间
     */
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    private Date borrowTime;

    /**
     * 计划归还时间
     */
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    private Date planReturnTime;

    /**
     * 实际归还时间
     */
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    private Date finalReturnTime;

    /**
     * 借阅操作人
     */
    private Long operator;
}
