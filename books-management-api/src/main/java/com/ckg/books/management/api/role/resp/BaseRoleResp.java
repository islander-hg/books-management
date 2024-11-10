package com.ckg.books.management.api.role.resp;

import com.ckg.books.management.api.common.resp.BaseEntityResp;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 角色基础的响应信息
 *
 * @author chenkaigui
 * @date 2024/11/9
 */
@Data
@Accessors(chain = true)
public class BaseRoleResp extends BaseEntityResp {

    /**
     * 角色ID
     */
    private Long id;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 显示顺序
     */
    private Integer order;

    /**
     * 状态,0-正常, 1-停用
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;
}
