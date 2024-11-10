package com.ckg.books.management.api.book.req;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
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
    @NotBlank(message = "图书名称不能为空")
    @Size(max = 50, message = "图书名称长度不允许超过50位字符")
    private String name;

    /**
     * 作者名称
     */
    @NotBlank(message = "作者名称不能为空")
    @Size(max = 50, message = "作者名称长度不允许超过50位字符")
    private String author;

    /**
     * 可借阅数量
     */
    @NotNull(message = "可借阅数量不能为空")
    @Min(value = 0, message = "可借阅数量不允许小于0")
    private Integer availableQuantity;

    /**
     * 价格，单位:分
     */
    @NotNull(message = "价格不能为空")
    @Min(value = 0, message = "价格不允许小于0")
    private Long price;

    /**
     * 出版社
     */
    private String publishingHouse;

    /**
     * 出版时间
     */
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    private Date publicationTime;

    /**
     * 状态，0-正常, 1-无效, 2-不允许借阅
     */
    private Integer status;
}
