package com.ckg.books.management.service.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ckg.books.management.common.constants.TableNameConstant;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 图书借阅记录
 *
 * @author chenkaigui
 * @date 2024/11/9
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@TableName(TableNameConstant.BOOK_BORROW)
public class BookBorrowEntity extends BaseEntity {

    /**
     * 借阅ID
     */
    @TableId(type = IdType.AUTO)
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
    private Date borrowTime;

    /**
     * 计划归还时间
     */
    private Date planReturnTime;

    /**
     * 实际归还时间
     */
    private Date finalReturnTime;

    /**
     * 借阅操作人
     */
    private Long operator;

    /**
     * 删除标识；0-未删除，1-已删除
     */
    @TableLogic
    private Boolean deleted;
}
