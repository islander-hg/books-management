package com.ckg.books.management.service.dao.entity;

import cn.hutool.core.date.DatePattern;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ckg.books.management.common.constants.TableNameConstant;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 图书
 *
 * @author chenkaigui
 * @date 2024/11/7
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@TableName(TableNameConstant.BOOK)
public class BookEntity extends BaseEntity {

    /**
     * 图书ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 图书名称
     */
    private String name;

    /**
     * 作者
     */
    private String author;

    /**
     * 可借阅数量
     */
    private Integer availableQuantity;

    /**
     * 借阅中数量
     */
    private Integer borrowingQuantity;

    /**
     * 丢失数量
     */
    private Integer lostQuantity;

    /**
     * 价格，单位:分
     */
    private Long price;

    /**
     * 出版社
     */
    private String publishingHouse;

    /**
     * 出版时间
     */
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    private Date publishingTime;

    /**
     * 状态，0-正常, 1-无效, 2-不允许借阅
     */
    private Integer status;

    /**
     * 删除标识；0-未删除，1-已删除
     */
    @TableLogic
    private Boolean deleted;
}
