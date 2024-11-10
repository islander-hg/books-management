package com.ckg.books.management.api.common.resp;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.annotation.JsonFormat;
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
    private String creator;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    private Date createTime;

    /**
     * 更新者
     */
    private String updater;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    private Date updateTime;
}
