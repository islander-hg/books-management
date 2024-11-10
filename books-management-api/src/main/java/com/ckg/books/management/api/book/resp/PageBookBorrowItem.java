package com.ckg.books.management.api.book.resp;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 分页查询图书借阅记录响应的列表项信息
 *
 * @author chenkaigui
 * @date 2024/11/9
 */
@Data
@Accessors(chain = true)
public class PageBookBorrowItem extends BaseBookBorrowResp {

}
