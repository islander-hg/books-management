package com.ckg.books.management.server.interceptor;

import com.ckg.books.management.api.common.resp.CommonResp;
import com.ckg.books.management.common.exception.BizErrorCodes;
import com.ckg.books.management.common.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理
 *
 * @author chenkaigui
 * @date 2024/11/10
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(BizException.class)
    public CommonResp businessExceptionHandler(BizException ex) {
        log.error("业务异常，错误码：{} ，错误信息：{}", ex.getCode(), ex.getMessage(), ex);
        return CommonResp.error(ex.getCode(), ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public CommonResp businessExceptionHandler(Exception ex) {
        log.error("未知错误，错误信息：{}", ex.getMessage(), ex);
        return CommonResp.error(BizErrorCodes.UNKNOWN_ERROR.getCode(), ex.getMessage());
    }
}
