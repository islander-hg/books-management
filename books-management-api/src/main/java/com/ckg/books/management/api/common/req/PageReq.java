package com.ckg.books.management.api.common.req;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * 分页参数
 *
 * @author chenkaigui
 * @date 2024/11/7
 */
@Data
public class PageReq {

    private static final Integer PAGE_NO = 1;

    private static final Integer PAGE_SIZE = 100;

    /**
     * 页码，从 1 开始
     */
    @NotNull(message = "页码不能为空")
    @Min(value = 1, message = "页码最小值为 1")
    private Integer pageNo = PAGE_NO;

    /**
     * 每页条数，最大值为 100
     */
    @NotNull(message = "每页条数不能为空")
    @Min(value = 1, message = "每页条数最小值为 1")
    @Max(value = 100, message = "每页条数最大值为 100")
    private Integer pageSize = PAGE_SIZE;

}
