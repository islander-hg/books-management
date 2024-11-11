package com.ckg.books.management.service.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ckg.books.management.common.constants.EntityFieldConstant;
import com.ckg.books.management.common.constants.TableNameConstant;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 图书分类
 *
 * @author chenkaigui
 * @date 2024/11/9
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@TableName(TableNameConstant.BOOK_CATEGORY)
public class BookCategoryEntity extends BaseEntity {

    /**
     * 分类ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 分类名称
     */
    private String name;

    /**
     * 父分类ID
     */
    private Long parentId;

    /**
     * 层级
     */
    private Integer level;

    /**
     * 显示顺序
     */
    @TableField("`order`")
    private Integer order;

    /**
     * 备注
     */
    private String remark;

    /**
     * 删除标识；0-未删除，值为ID时-已删除
     */
    @TableLogic(delval = EntityFieldConstant.ID)
    private Long deleted;
}
