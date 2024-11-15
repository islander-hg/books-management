package com.ckg.books.management.common.domain.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import lombok.Data;

/**
 * 基础返回信息
 *
 * @author chenkaigui
 * @date 2024/11/7
 */
@Data
public class BaseResp implements Serializable {

    /**
     * 状态码
     */
    @Schema(description = "状态码")
    private int code;

    /**
     * 返回消息
     */
    @Schema(description = "返回消息")
    private String message;


    /**
     * 初始化一个新创建的 BaseResp 对象，使其表示一个空消息。
     */
    public BaseResp() {
    }

    /**
     * 初始化一个新创建的 BaseResp 对象
     *
     * @param code    状态码
     * @param message 返回消息
     */
    public BaseResp(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
