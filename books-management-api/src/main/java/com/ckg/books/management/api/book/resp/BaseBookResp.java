package com.ckg.books.management.api.book.resp;

import cn.hutool.core.date.DatePattern;
import com.ckg.books.management.api.common.resp.BaseEntityResp;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Date;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 图书基础的响应信息
 *
 * @author chenkaigui
 * @date 2024/11/9
 */
@Data
@Accessors(chain = true)
public class BaseBookResp extends BaseEntityResp {

    /**
     * 图书ID
     */
    @Schema(description = "图书ID")
    private Long id;

    /**
     * 图书名称
     */
    @Schema(description = "图书名称")
    private String name;

    /**
     * 作者
     */
    @Schema(description = "作者")
    private String author;

    /**
     * 可借阅数量
     */
    @Schema(description = "可借阅数量")
    private Integer availableQuantity;

    /**
     * 借阅中数量
     */
    @Schema(description = "借阅中数量")
    private Integer borrowingQuantity;

    /**
     * 丢失数量
     */
    @Schema(description = "丢失数量")
    private Integer lostQuantity;

    /**
     * 价格，单位:分
     */
    @Schema(description = "价格，单位:分")
    private Long price;

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
    private Integer status;
}
