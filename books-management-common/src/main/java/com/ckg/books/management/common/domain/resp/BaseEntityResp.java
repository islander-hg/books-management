package com.ckg.books.management.common.domain.resp;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Date;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 有关 Entity 的基础响应信息
 *
 * @author chenkaigui
 * @date 2024/11/7
 */
@Data
@Accessors(chain = true)
public class BaseEntityResp {

    /**
     * 创建者
     */
    @Schema(description = "创建者")
    private String creator;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间,格式：" + DatePattern.NORM_DATETIME_PATTERN)
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    private Date createTime;

    /**
     * 更新者
     */
    @Schema(description = "更新者")
    private String updater;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间,格式：" + DatePattern.NORM_DATETIME_PATTERN)
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    private Date updateTime;
}
