package com.ckg.books.management.api.book.req;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 创建图书借阅记录请求信息
 *
 * @author chenkaigui
 * @date 2024/11/8
 */
@Data
@Accessors(chain = true)
public class CreateBookBorrowReq extends BaseOperateBookBorrowReq {

    /**
     * 图书ID
     */
    @NotNull(message = "图书ID不能为空")
    private Long bookId;

    /**
     * 借阅数量
     */
    @NotNull(message = "借阅数量不能为空")
    @Min(value = 1, message = "借阅数量不能小于1")
    private Integer borrowQuantity;
}
