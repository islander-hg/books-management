package com.ckg.books.management.common.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 业务异常类
 *
 * @author chenkaigui
 * @date 2024/11/10
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
public class BizException extends RuntimeException {

    /**
     * 错误码
     */
    private Integer code;

    /**
     * 错误提示
     */
    private String message;

}
