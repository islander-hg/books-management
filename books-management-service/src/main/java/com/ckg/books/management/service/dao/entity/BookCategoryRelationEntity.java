package com.ckg.books.management.service.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ckg.books.management.common.constants.TableNameConstant;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 图书和图书分类关联信息
 *
 * @author chenkaigui
 * @date 2024/11/9
 */
@Data
@Accessors(chain = true)
@TableName(TableNameConstant.BOOK_CATEGORY_RELATION)
public class BookCategoryRelationEntity {

    /**
     * 图书ID
     */
    private Long bookId;

    /**
     * 分类ID
     */
    private Long categoryId;
}
