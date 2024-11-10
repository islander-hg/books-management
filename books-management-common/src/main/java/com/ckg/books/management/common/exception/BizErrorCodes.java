package com.ckg.books.management.common.exception;

/**
 * 业务异常编码常量
 *
 * @author chenkaigui
 * @date 2024/11/10
 */
public class BizErrorCodes {

    private static ErrorScope SCOPE_BOOKS_MANAGEMENT = ErrorScope.createOnce(100);

    // ========== 用户错误 100-199==========
    public static ErrorCode ACCOUNT_ABNORMALITY =
            createOnce(BizErrorType.USER, 0); // 数据转换错误
    public static ErrorCode PASSWORD_UNCHANGED =
            createOnce(BizErrorType.USER, 1); // 新旧密码相同

    // ========== 图书借阅错误 500-599==========
    public static ErrorCode INSUFFICIENT_QUANTITY =
            createOnce(BizErrorType.BOOK_BORROW, 0); // 可借阅数不足
    public static ErrorCode RETURN_VERIFY_FAILED =
            createOnce(BizErrorType.BOOK_BORROW, 1); // 归还校验异常
    public static ErrorCode BORROW_VERIFY_FAILED =
            createOnce(BizErrorType.BOOK_BORROW, 2); // 借阅校验异常


    // ========== 工具错误 800-899==========
    public static ErrorCode FAILED_TO_CONVERT_DATA =
            createOnce(BizErrorType.UTIL, 0); // 数据转换错误

    // ========== 通用错误 900-999==========
    public static ErrorCode UNABLE_CREATE_TABLE_RECORD_BECAUSE_UNKNOWN =
            createOnce(BizErrorType.COMMON, 0); // 未知错误导致无法创建表数据
    public static ErrorCode UNABLE_UPDATE_TABLE_RECORD_BECAUSE_UNKNOWN =
            createOnce(BizErrorType.COMMON, 1); // 未知错误导致无法修改表数据
    public static ErrorCode UNABLE_DELETE_TABLE_RECORD_BECAUSE_UNKNOWN =
            createOnce(BizErrorType.COMMON, 2); // 未知错误导致无法删除表数据
    public static ErrorCode TABLE_RECORD_DUPLICATE =
            createOnce(BizErrorType.COMMON, 3); // 表数据重复
    public static ErrorCode TABLE_RECORD_NOT_EXIST =
            createOnce(BizErrorType.COMMON, 4); // 表数据不存在
    public static ErrorCode UNABLE_WRITE_DATA_TO_SERVLET =
            createOnce(BizErrorType.COMMON, 5); // 向servlet写入数据失败

    public static ErrorCode AUTH_FAILED =
            createOnce(BizErrorType.COMMON, 6); // 认证失败
    public static ErrorCode LOGIN_FAILED =
            createOnce(BizErrorType.COMMON, 7); // 登录失败
    public static ErrorCode LOGIN_RESTRICTION = createOnce(BizErrorType.COMMON, 8); // 登录限制

    public static ErrorCode EMAIL_CAPTCHA_INVALID = createOnce(BizErrorType.COMMON, 94); // 邮件验证码不匹配
    public static ErrorCode EMAIL_CAPTCHA_EXPIRED = createOnce(BizErrorType.COMMON, 95); // 邮件验证码过期
    public static ErrorCode EMAIL_BUILD_FAILED = createOnce(BizErrorType.COMMON, 96); // 邮件构建失败
    public static ErrorCode EMAIL_SEND_FAILED = createOnce(BizErrorType.COMMON, 97); // 邮件发送失败
    public static ErrorCode CURRENTLY_UNSUPPORTED = createOnce(BizErrorType.COMMON, 98); // 当前不支持
    public static ErrorCode UNKNOWN_ERROR = createOnce(BizErrorType.COMMON, 99); // 未知错误

    private static ErrorCode createOnce(BizErrorType bizErrorType, int internalCode) {
        return ErrorCode.createOnce(SCOPE_BOOKS_MANAGEMENT,
                Integer.valueOf(String.format("%01d%02d", bizErrorType.getCode(), internalCode)));
    }

}
