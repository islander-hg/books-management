package com.ckg.books.management.service.dao.mapper;

import com.ckg.books.management.common.mapper.BaseMapperX;
import com.ckg.books.management.service.dao.entity.BookBorrowEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * 图书借阅记录 MAPPER
 *
 * @author chenkaigui
 * @date 2024/11/8
 */
@Mapper
public interface BookBorrowMapper extends BaseMapperX<BookBorrowEntity> {

    @Update({"<script>"
            + " update book_borrow set return_quantity = return_quantity + #{returnQuantity}, final_return_time = NOW()"
            + " WHERE id = #{id} and deleted = ${@com.ckg.books.management.common.enums.DeletedFlag@UNDELETED.getCode()}"
            + " and borrow_quantity - return_quantity &gt;= #{returnQuantity}"
            + "</script>"})
    int increaseReturnQuantity(
            @Param("id") Long id, @Param("returnQuantity") Integer returnQuantity);
}
