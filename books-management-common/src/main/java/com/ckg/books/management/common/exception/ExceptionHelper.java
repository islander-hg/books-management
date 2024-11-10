package com.ckg.books.management.common.exception;

import cn.hutool.core.util.ArrayUtil;
import com.ckg.books.management.common.utils.text.StrFormatter;

/**
 * 异常助手
 *
 * @author chenkaigui
 * @date 2024/11/10
 */
public final class ExceptionHelper {

    private ExceptionHelper() {
    }

    public static BizException create(ErrorCode errorCode, String message) {
        return new BizException(errorCode.getCode(), message);
    }

    public static BizException create(ErrorCode errorCode, String message, Object... args) {
        BizException ex = new BizException(errorCode.getCode(), StrFormatter.format(message, args));
        if (lastArgIsThrowable(args)) {
            ex.initCause((Throwable) args[args.length - 1]);
        }
        return ex;
    }

    private static boolean lastArgIsThrowable(Object... args) {
        return ArrayUtil.isNotEmpty(args)
                && args[args.length - 1] != null
                && args[args.length - 1] instanceof Throwable;
    }
}
