package com.ckg.books.management.api.book.req;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 修改图书分类请求信息
 *
 * @author chenkaigui
 * @date 2024/11/8
 */
@Data
@Accessors(chain = true)
public class UpdateBookCategoryReq extends BaseOperateBookCategoryReq {

}
