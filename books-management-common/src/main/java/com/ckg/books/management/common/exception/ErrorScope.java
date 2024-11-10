package com.ckg.books.management.common.exception;

import java.util.Set;
import java.util.TreeSet;

/**
 * 错误码范围（用与区分不同的服务）
 *
 * @author chenkaigui
 * @date 2024/11/10
 */
public class ErrorScope {

    private int scopeId;

    private static Set<Integer> scopes = new TreeSet();

    private ErrorScope(int scopeId) {
        this.scopeId = scopeId;
    }

    public static ErrorScope createOnce(int scopeId) {
        if (scopeId >= 0 && scopeId < 1000) {
            if (scopes.contains(scopeId)) {
                throw new IllegalArgumentException("Duplicate scope id:" + scopeId);
            } else {
                ErrorScope scope = new ErrorScope(scopeId);
                scopes.add(scopeId);
                return scope;
            }
        } else {
            throw new IllegalArgumentException("Bad scope range:" + scopeId);
        }
    }

    public int getScopeId() {
        return this.scopeId;
    }
}
