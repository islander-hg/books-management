package com.ckg.books.management.api.menu.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 创建菜单请求信息
 *
 * @author chenkaigui
 * @date 2024/11/8
 */
@Data
@Accessors(chain = true)
public class CreateMenuReq extends BaseOperateMenuReq {

    /**
     * 父菜单ID
     */
    @Schema(description = "父菜单ID")
    private Long parentId;
}
