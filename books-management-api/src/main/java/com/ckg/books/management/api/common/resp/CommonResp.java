package com.ckg.books.management.api.common.resp;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 通用响应信息
 *
 * @author chenkaigui
 * @date 2024/11/7
 */
@Data
@Accessors(chain = true)
public class CommonResp<T> extends BaseResp {

    /**
     * 返回数据
     */
    private T data;

    /**
     * 初始化一个新创建的 CommonResp 对象，使其表示一个空消息。
     */
    public CommonResp() {
    }

    /**
     * 初始化一个新创建的 CommonResp 对象
     *
     * @param code    状态码
     * @param message 返回消息
     */
    public CommonResp(int code, String message) {
        super(code, message);
    }

    /**
     * 初始化一个新创建的 CommonResp 对象
     *
     * @param code    状态码
     * @param message 返回消息
     * @param data    返回数据
     */
    public CommonResp(int code, String message, T data) {
        this(code, message);
        this.data = data;
    }

    /**
     * 返回成功消息
     *
     * @return 成功消息
     */
    public static CommonResp success() {
        return CommonResp.success("操作成功");
    }

    /**
     * 返回成功数据
     *
     * @param data 返回数据
     * @return 成功消息
     */
    public static <T> CommonResp<T> success(T data) {
        return CommonResp.success("操作成功", data);
    }

    /**
     * 返回成功消息
     *
     * @param message 返回消息
     * @return 成功消息
     */
    public static CommonResp success(String message) {
        return CommonResp.success(message, null);
    }

    /**
     * 返回成功消息
     *
     * @param message 返回消息
     * @param data    返回数据
     * @return 成功消息
     */
    public static <T> CommonResp<T> success(String message, T data) {
        return new CommonResp(0, message, data);
    }

    /**
     * 返回错误消息
     *
     * @return 错误消息
     */
    public static CommonResp error() {
        return CommonResp.error("操作失败");
    }

    /**
     * 返回错误消息
     *
     * @param message 返回消息
     * @return 警告消息
     */
    public static CommonResp error(String message) {
        return CommonResp.error(message, null);
    }

    /**
     * 返回错误消息
     *
     * @param message 返回消息
     * @param data    返回数据
     * @return 警告消息
     */
    public static <T> CommonResp<T> error(String message, T data) {
        return new CommonResp(500, message, data);
    }

    /**
     * 返回异常消息
     *
     * @return CommonResp
     */
    public static CommonResp exception() {
        return CommonResp.exception("参数异常");
    }

    /**
     * 返回异常消息
     *
     * @return CommonResp
     */
    public static CommonResp exception(String message) {
        return new CommonResp(500, message);
    }

    /**
     * 返回错误消息
     *
     * @param code    状态码
     * @param message 返回消息
     * @return 警告消息
     */
    public static CommonResp error(int code, String message) {
        return new CommonResp(code, message, null);
    }

}
