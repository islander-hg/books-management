package com.ckg.books.management.common.utils.json;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * JSON 工具类
 *
 * @author chenkaigui
 * @date 2024/11/9
 */
@Slf4j
public class JsonUtils {

    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

    /**
     * 初始化 objectMapper 属性
     * <p>
     * 通过这样的方式，使用 Spring 创建的 ObjectMapper Bean
     *
     * @param objectMapper ObjectMapper 对象
     */
    public static void init(ObjectMapper objectMapper) {
        JsonUtils.objectMapper = objectMapper;
    }

    @SneakyThrows
    public static String toJsonString(Object object) {
        return objectMapper.writeValueAsString(object);
    }

    @SneakyThrows
    public static byte[] toJsonByte(Object object) {
        return objectMapper.writeValueAsBytes(object);
    }

    @SneakyThrows
    public static String toJsonPrettyString(Object object) {
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
    }

    public static <T> T parseObject(String text, Class<T> clazz) {
        if (StrUtil.isBlank(text)) {
            return null;
        }
        try {
            return objectMapper.readValue(text, clazz);
        } catch (Exception e) {
            log.error("json parse err,json:{}", text, e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 将字符串解析成指定类型的对象 使用 {@link #parseObject(String, Class)} 时，在@JsonTypeInfo(use =
     * JsonTypeInfo.Id.CLASS) 的场景下， 如果 text 没有 class 属性，则会报错。此时，使用这个方法，可以解决。
     *
     * @param text  字符串
     * @param clazz 类型
     * @return 对象
     */
    public static <T> T parseObject2(String text, Class<T> clazz) {
        if (StrUtil.isBlank(text)) {
            return null;
        }
        return parseObject(text, clazz);
    }

    public static <T> T parseObject(byte[] bytes, Class<T> clazz) {
        if (ArrayUtil.isEmpty(bytes)) {
            return null;
        }
        try {
            return objectMapper.readValue(bytes, clazz);
        } catch (IOException e) {
            log.error("json parse err,json:{}", bytes, e);
            throw new RuntimeException(e);
        }
    }

    public static <T> T parseObject(String text, TypeReference<T> typeReference) {
        try {
            return objectMapper.readValue(text, typeReference);
        } catch (IOException e) {
            log.error("json parse err,json:{}", text, e);
            throw new RuntimeException(e);
        }
    }

    public static <T> List<T> parseArray(String text, Class<T> clazz) {
        if (StrUtil.isBlank(text)) {
            return new ArrayList<>();
        }
        try {
            return objectMapper.readValue(text,
                    objectMapper.getTypeFactory().constructCollectionType(List.class, clazz));
        } catch (IOException e) {
            log.error("json parse err,json:{}", text, e);
            throw new RuntimeException(e);
        }
    }

    public static JsonNode parseTree(String text) {
        try {
            return objectMapper.readTree(text);
        } catch (IOException e) {
            log.error("json parse err,json:{}", text, e);
            throw new RuntimeException(e);
        }
    }

    public static ObjectNode toObjectNode(Object obj) {
        try {
            return objectMapper.valueToTree(obj);
        } catch (IllegalArgumentException e) {
            log.error("json parse err, obj:{}", obj, e);
            throw new RuntimeException(e);
        }
    }

    public static ArrayNode toArrayNode(Object obj) {
        try {
            return objectMapper.valueToTree(obj);
        } catch (IllegalArgumentException e) {
            log.error("json parse err, obj:{}", obj, e);
            throw new RuntimeException(e);
        }
    }

    public static JsonNode parseTree(byte[] text) {
        try {
            return objectMapper.readTree(text);
        } catch (IOException e) {
            log.error("json parse err,json:{}", text, e);
            throw new RuntimeException(e);
        }
    }

    public static <T> T convertValue(JsonNode jsonNode, Class<T> clazz) {
        try {
            return objectMapper.convertValue(jsonNode, clazz);
        } catch (Exception e) {
            log.error("json parse err,json:{}", jsonNode, e);
            throw new RuntimeException(e);
        }
    }

    public static boolean isJson(String text) {
        return isTypeJSONObject(text) || isTypeJSONArray(text);
    }

    /**
     * 是否为JSONObject类型字符串，首尾都为大括号判定为JSONObject字符串
     *
     * @param str 字符串
     * @return 是否为JSON字符串
     * @since 5.7.22
     */
    public static boolean isTypeJSONObject(String str) {
        if (StrUtil.isBlank(str)) {
            return false;
        }
        return isWrap(str.trim(), '{', '}');
    }

    /**
     * 是否为JSONArray类型的字符串，首尾都为中括号判定为JSONArray字符串
     *
     * @param str 字符串
     * @return 是否为JSONArray类型字符串
     * @since 5.7.22
     */
    public static boolean isTypeJSONArray(String str) {
        if (StrUtil.isBlank(str)) {
            return false;
        }
        return isWrap(str.trim(), '[', ']');
    }

    /**
     * 指定字符串是否被包装
     *
     * @param str        字符串
     * @param prefixChar 前缀
     * @param suffixChar 后缀
     * @return 是否被包装
     */
    public static boolean isWrap(CharSequence str, char prefixChar, char suffixChar) {
        if (null == str) {
            return false;
        }

        return str.charAt(0) == prefixChar && str.charAt(str.length() - 1) == suffixChar;
    }
}
