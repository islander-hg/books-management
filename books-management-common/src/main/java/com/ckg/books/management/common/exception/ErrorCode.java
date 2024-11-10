package com.ckg.books.management.common.exception;

import java.util.HashSet;
import java.util.Set;
import lombok.Getter;

/**
 * 错误码
 *
 * @author chenkaigui
 * @date 2024/11/10
 */
@Getter
public class ErrorCode {

    private static final int ERROR_CODE_PREFIX = 400;
    private static Set<Integer> allCodes = new HashSet();
    private int code;

    ErrorCode(int code) {
        this.code = code;
    }

    public static ErrorCode createOnce(ErrorScope errorScope, int internalCode) {
        if (internalCode >= 0 && internalCode < 1000) {
            ErrorCode code = new ErrorCode(Integer.valueOf(
                    String.format("%03d%03d%03d",
                            ERROR_CODE_PREFIX, errorScope.getScopeId(), internalCode)));
            if (allCodes.contains(code.getCode())) {
                throw new IllegalArgumentException("Duplicated error code defination.");
            } else {
                allCodes.add(code.getCode());
                return code;
            }
        } else {
            throw new IllegalArgumentException("Bad internal code range. " + internalCode);
        }
    }

    public int getCode() {
        return this.code;
    }
}
