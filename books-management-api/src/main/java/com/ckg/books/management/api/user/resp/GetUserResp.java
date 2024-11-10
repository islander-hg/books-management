package com.ckg.books.management.api.user.resp;

import com.ckg.books.management.api.role.resp.GetRoleResp;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 用户详情信息
 *
 * @author chenkaigui
 * @date 2024/11/9
 */
@Data
@Accessors(chain = true)
public class GetUserResp extends BaseUserResp {

    /**
     * 用户角色列表
     */
    @Schema(description = "用户角色列表")
    private List<GetRoleResp> roleList;
}
