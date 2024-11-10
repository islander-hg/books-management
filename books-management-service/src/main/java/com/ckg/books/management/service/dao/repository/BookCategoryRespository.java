package com.ckg.books.management.service.dao.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ckg.books.management.service.dao.entity.BookCategoryEntity;
import java.util.Collection;
import java.util.List;

/**
 * 图书分类Respository 接口实现
 *
 * @author chenkaigui
 * @date 2024/11/8
 */
public interface BookCategoryRespository extends IService<BookCategoryEntity> {

    /**
     * 根据图书分类ID获取图书分类信息
     *
     * @param id                 图书分类ID
     * @param throwNotFoundError 图书分类不存在时是否抛异常; true-是 false-否
     * @return 图书分类信息
     */
    BookCategoryEntity getById(Long id, boolean throwNotFoundError);

    /**
     * 通过父图书分类ID和图书分类名模糊查询获取图书分类信息
     *
     * @param parentId 父图书分类ID
     * @param name     图书分类名称
     * @return 图书分类信息
     */
    BookCategoryEntity getByParentIdAndName(Long parentId, String name);

    /**
     * 通过图书分类ID列表查询获取图书分类信息列表
     *
     * @param ids 图书分类ID列表
     * @return 图书分类信息列表
     */
    List<BookCategoryEntity> findByIdIn(Collection<Long> ids);

    /**
     * 是否有子节点
     *
     * @param id 图书分类ID
     * @return true-有子节点 false-无子节点
     */
    boolean hasChildren(Long id);

}
