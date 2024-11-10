package com.ckg.books.management.service.sevice.book.impl;

import com.ckg.books.management.api.book.req.PageBookBorrowReq;
import com.ckg.books.management.api.book.resp.GetBookBorrowResp;
import com.ckg.books.management.api.book.resp.PageBookBorrowItem;
import com.ckg.books.management.common.domain.resp.PageResult;
import com.ckg.books.management.common.utils.spring.BeanHelper;
import com.ckg.books.management.service.dao.entity.BookBorrowEntity;
import com.ckg.books.management.service.dao.repository.BookBorrowRespository;
import com.ckg.books.management.service.sevice.book.BookBorrowQueryService;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 图书借阅记录查询操作 Service 接口实现
 *
 * @author chenkaigui
 * @date 2024/11/8
 */
@Slf4j
@Service
public class BookBorrowQueryServiceImpl implements BookBorrowQueryService {

    @Resource
    private BookBorrowRespository bookBorrowRespository;

    @Override
    public GetBookBorrowResp get(Long id) {
        BookBorrowEntity roleEntity = bookBorrowRespository.getById(id, true);
        return BeanHelper.copyProperties(roleEntity, GetBookBorrowResp.class);
    }

    @Override
    public PageResult<PageBookBorrowItem> searchPage(PageBookBorrowReq pageReq) {
        PageResult<BookBorrowEntity> pageResult = bookBorrowRespository.searchPage(pageReq);
        return new PageResult(
                BeanHelper.copyWithCollection(pageResult.getItems(), PageBookBorrowItem.class),
                pageResult.getTotal());
    }

}
