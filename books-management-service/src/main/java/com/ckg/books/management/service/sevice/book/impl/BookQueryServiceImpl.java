package com.ckg.books.management.service.sevice.book.impl;

import com.ckg.books.management.api.book.req.PageBookReq;
import com.ckg.books.management.api.book.resp.GetBookResp;
import com.ckg.books.management.api.book.resp.PageBookItem;
import com.ckg.books.management.common.domain.resp.PageResult;
import com.ckg.books.management.common.utils.spring.BeanHelper;
import com.ckg.books.management.service.dao.entity.BookEntity;
import com.ckg.books.management.service.dao.repository.BookRespository;
import com.ckg.books.management.service.sevice.book.BookQueryService;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 图书查询操作 Service 接口实现
 *
 * @author chenkaigui
 * @date 2024/11/8
 */
@Slf4j
@Service
public class BookQueryServiceImpl implements BookQueryService {

    @Resource
    private BookRespository bookRespository;

    @Override
    public GetBookResp get(Long id) {
        BookEntity bookEntity = bookRespository.getById(id, true);
        return BeanHelper.copyProperties(bookEntity, GetBookResp.class);
    }

    @Override
    public PageResult<PageBookItem> searchPage(PageBookReq pageReq) {
        PageResult<BookEntity> pageResult = bookRespository.searchPage(pageReq);
        return new PageResult(
                BeanHelper.copyWithCollection(pageResult.getItems(), PageBookItem.class),
                pageResult.getTotal());
    }

}
