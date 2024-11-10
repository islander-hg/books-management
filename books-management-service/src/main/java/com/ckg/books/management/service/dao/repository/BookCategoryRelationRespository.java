package com.ckg.books.management.service.dao.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ckg.books.management.service.dao.entity.BookCategoryRelationEntity;
import java.util.Collection;
import java.util.List;

/**
 * 图书和图书分类关联 Respository 接口
 *
 * @author chenkaigui
 * @date 2024/11/9
 */
public interface BookCategoryRelationRespository extends IService<BookCategoryRelationEntity> {

    /**
     * 插入图书和图书分类关联信息
     *
     * @param bookId      图书ID
     * @param categoryIds 图书分类ID列表
     */
    void insertRelation(Long bookId, Collection<Long> categoryIds);

    /**
     * 通过图书ID删除图书和图书分类关联关系
     *
     * @param bookId 图书ID
     */
    void deleteByBookId(Long bookId);

    /**
     * 通过分类ID删除图书和图书分类关联关系
     *
     * @param categoryId 分类ID
     */
    void deleteByCategoryId(Long categoryId);

    /**
     * 根据图书ID和图书分类ID获取图书和图书分类关联信息
     *
     * @param bookId             图书ID
     * @param categoryId         图书分类ID
     * @param throwNotFoundError 关联信息不存在时是否抛异常; true-是 false-否
     * @return 图书图书分类关联信息
     */
    BookCategoryRelationEntity getByBookIdAndCategoryId(
            Long bookId, Long categoryId, boolean throwNotFoundError);

    /**
     * 根据分类ID列表获取图书图书分类关联信息列表
     *
     * @param categoryIds 分类ID列表
     * @return 图书图书分类关联信息列表
     */
    List<BookCategoryRelationEntity> findByCategoryIdIn(Collection<Long> categoryIds);

    /**
     * 根据图书ID列表获取图书图书分类关联信息列表
     *
     * @param bookIds 图书ID列表
     * @return 图书图书分类关联信息列表
     */
    List<BookCategoryRelationEntity> findByBookIdIn(Collection<Long> bookIds);

}
