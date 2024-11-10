package com.ckg.books.management.service.dao.mapper;

import com.ckg.books.management.common.mapper.BaseMapperX;
import com.ckg.books.management.service.dao.entity.BookEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * 图书 MAPPER
 *
 * @author chenkaigui
 * @date 2024/11/8
 */
@Mapper
public interface BookMapper extends BaseMapperX<BookEntity> {

    @Update({"<script>"
            + " update book set borrowing_quantity = borrowing_quantity + #{borrowQuantity}, available_quantity = available_quantity - #{borrowQuantity}"
            + " WHERE id = #{id} and deleted = ${@com.ckg.books.management.common.enums.DeletedFlag@UNDELETED.getCode()}"
            + " and available_quantity &gt;= #{borrowQuantity}"
            + "</script>"})
    int increaseBorrowingQuantity(
            @Param("id") Long id, @Param("borrowQuantity") Integer borrowQuantity);

    @Update({"<script>"
            + " update book set borrowing_quantity = borrowing_quantity - #{returnQuantity}, available_quantity = available_quantity + #{returnQuantity}"
            + " WHERE id = #{id} and deleted = ${@com.ckg.books.management.common.enums.DeletedFlag@UNDELETED.getCode()}"
            + " and borrowing_quantity &gt;= #{returnQuantity}"
            + "</script>"})
    int decreaseBorrowingQuantity(
            @Param("id") Long id, @Param("returnQuantity") Integer returnQuantity);
}
