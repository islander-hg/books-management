package com.ckg.books.management.service.dao.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ckg.books.management.api.book.req.PageBookReq;
import com.ckg.books.management.common.domain.resp.PageResult;
import com.ckg.books.management.service.dao.entity.BookEntity;
import java.util.List;

/**
 * 图书 Respository 接口实现
 *
 * @author chenkaigui
 * @date 2024/11/8
 */
public interface BookRespository extends IService<BookEntity> {

    /**
     * 根据图书ID获取图书信息
     *
     * @param id                 图书ID
     * @param throwNotFoundError 图书不存在时是否抛异常; true-是 false-否
     * @return 图书信息
     */
    BookEntity getById(Long id, boolean throwNotFoundError);

    /**
     * 通过图书名模糊查询获取图书信息列表
     *
     * @param name 图书名称
     * @return 图书信息列表
     */
    List<BookEntity> findByNameLike(String name);

    /**
     * 分页查询图书信息
     *
     * @param pageReq 分页请求信息
     * @return 图书信息分页结果
     */
    PageResult<BookEntity> searchPage(PageBookReq pageReq);

    /**
     * 增加借阅中数量
     *
     * @param id             图书ID
     * @param borrowQuantity 借阅数量
     * @return true-增加成功 false-增加失败
     */
    boolean increaseBorrowingQuantity(Long id, Integer borrowQuantity);

    /**
     * 减少借阅中数量
     *
     * @param id             图书ID
     * @param returnQuantity 归还数量
     * @return true-扣减成功 false-扣减失败
     */
    boolean decreaseBorrowingQuantity(Long id, Integer returnQuantity);
}
