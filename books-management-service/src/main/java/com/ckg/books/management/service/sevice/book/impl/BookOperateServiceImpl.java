package com.ckg.books.management.service.sevice.book.impl;

import com.ckg.books.management.api.book.req.CreateBookReq;
import com.ckg.books.management.api.book.req.UpdateBookReq;
import com.ckg.books.management.common.exception.BizErrorCodes;
import com.ckg.books.management.common.exception.ExceptionHelper;
import com.ckg.books.management.common.utils.spring.BeanHelper;
import com.ckg.books.management.service.dao.entity.BookEntity;
import com.ckg.books.management.service.dao.repository.BookRespository;
import com.ckg.books.management.service.dao.utils.EntityHelper;
import com.ckg.books.management.service.sevice.book.BookOperateService;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 图书新增、变更操作 Service 接口
 *
 * @author chenkaigui
 * @date 2024/11/8
 */
@Slf4j
@Service
public class BookOperateServiceImpl implements BookOperateService {

    @Resource
    private BookRespository bookRespository;

    @Override
    public void create(CreateBookReq createReq) {
        BookEntity toBeCreatedEntity = BeanHelper.copyProperties(createReq, BookEntity.class);
        EntityHelper.fillBaseFieldValue(toBeCreatedEntity, true);
        boolean created = bookRespository.save(toBeCreatedEntity);
        if (!created) {
            throw ExceptionHelper.create(BizErrorCodes.UNABLE_CREATE_TABLE_RECORD_BECAUSE_UNKNOWN,
                    "未知异常导致无法新增图书：{}", createReq.getName());
        }
    }

    @Override
    public void update(Long id, UpdateBookReq updateReq) {
        //1. 校验存在性
        BookEntity book = bookRespository.getById(id);

        //2. 修改
        BookEntity toBeUpdatedEntity = BeanHelper.copyProperties(updateReq, BookEntity.class);
        toBeUpdatedEntity.setId(id);
        EntityHelper.fillBaseFieldValue(toBeUpdatedEntity, false);

        boolean updated = bookRespository.updateById(toBeUpdatedEntity);
        if (!updated) {
            throw ExceptionHelper.create(BizErrorCodes.UNABLE_UPDATE_TABLE_RECORD_BECAUSE_UNKNOWN,
                    "未知异常导致无法修改图书：{}", book.getName());
        }
    }

    @Override
    public void delete(Long id) {
        //1. 校验存在性 并 执行删除
        bookRespository.getById(id, true);
        boolean deleted = bookRespository.removeById(id);
        if (!deleted) {
            throw ExceptionHelper.create(BizErrorCodes.UNABLE_DELETE_TABLE_RECORD_BECAUSE_UNKNOWN,
                    "未知异常导致无法删除图书：{}", id);
        }
    }

}
