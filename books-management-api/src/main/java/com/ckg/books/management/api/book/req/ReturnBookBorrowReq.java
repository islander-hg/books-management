package com.ckg.books.management.api.book.req;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 归还图书请求信息
 *
 * @author chenkaigui
 * @date 2024/11/9
 */
@Data
@Accessors(chain = true)
public class ReturnBookBorrowReq {

    /**
     * 归还数量
     */
    @NotNull(message = "归还数量不能为空")
    @Min(value = 1, message = "归还数量不能小于1")
    private Integer returnQuantity;
}
