package com.ckg.books.management.api.role.req;

import com.ckg.books.management.common.domain.req.PageReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 分页查询角色请求信息
 *
 * @author chenkaigui
 * @date 2024/11/8
 */
@Data
@Accessors(chain = true)
public class PageRoleReq extends PageReq {

    /**
     * 角色名（模糊搜索）
     */
    @Schema(description = "色名（模糊搜索）")
    private String name;

    /**
     * 状态,0-正常, 1-停用
     */
    @Schema(description = "状态,0-正常, 1-停用")
    private Integer status;

    /**
     * 备注（模糊搜索）
     */
    @Schema(description = "备注（模糊搜索）")
    private String remark;
}
