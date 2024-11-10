package com.ckg.books.management.api.book.req;


import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 修改图书请求信息
 *
 * @author chenkaigui
 * @date 2024/11/8
 */
@Data
@Accessors(chain = true)
public class UpdateBookReq extends BaseOperateBookReq {

    /**
     * 丢失数量
     */
    @Schema(description = "丢失数量")
    @NotNull(message = "丢失数量不能为空")
    @Min(value = 0, message = "丢失数量不允许小于0")
    private Integer lostQuantity;
}
