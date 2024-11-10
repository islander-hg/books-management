package com.ckg.books.management.service.dao.mapper;

import com.ckg.books.management.common.mapper.BaseMapperX;
import com.ckg.books.management.service.dao.entity.BookCategoryRelationEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 图书和图书分类关联 MAPPER
 *
 * @author chenkaigui
 * @date 2024/11/8
 */
@Mapper
public interface BookCategoryRelationMapper extends BaseMapperX<BookCategoryRelationEntity> {

}
