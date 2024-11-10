package com.ckg.books.management.api.book.req;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 创建图书请求信息
 *
 * @author chenkaigui
 * @date 2024/11/8
 */
@Data
@Accessors(chain = true)
public class CreateBookReq extends BaseOperateBookReq {

}
