package com.ckg.books.management.common.utils.servlet;

import cn.hutool.core.util.CharsetUtil;
import com.ckg.books.management.common.exception.BizErrorCodes;
import com.ckg.books.management.common.exception.ExceptionHelper;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

/**
 * Servlet 工具类
 *
 * @author chenkaigui
 * @date 2024/11/9
 */
public class ServletUtils {

    /**
     * 向 HttpServlet 中写入响应信息
     *
     * @param response {@link HttpServletResponse}
     * @param result   要写入的响应结果
     */
    public static void renderString(HttpServletResponse response, String result) {
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(CharsetUtil.UTF_8);
        try {
            response.getWriter().print(result);
        } catch (IOException e) {
            throw ExceptionHelper
                    .create(BizErrorCodes.UNABLE_WRITE_DATA_TO_SERVLET,
                            "无法向 HttpServlet 写入响应信息", e);
        }
    }
}
