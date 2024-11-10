package com.ckg.books.management.common.utils.spring;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ReflectUtil;
import com.ckg.books.management.common.exception.BizErrorCodes;
import com.ckg.books.management.common.exception.ExceptionHelper;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

/**
 * Bean 处理工具
 *
 * @author chenkaigui
 * @date 2024/11/9
 */
@Slf4j
public class BeanHelper {

    public static <T> T copyProperties(Object source, Class<T> target) {
        if (null == source) {
            return null;
        }
        try {
            T t = ReflectUtil.newInstance(target);
            BeanUtils.copyProperties(source, t);
            return t;
        } catch (Exception e) {
            throw ExceptionHelper.create(BizErrorCodes.FAILED_TO_CONVERT_DATA,
                    "数据转换出错，无法转换成目标对象{}", target.getName(), e);
        }
    }

    public static <T> List<T> copyWithCollection(List<?> sourceList, Class<T> target) {
        if (CollUtil.isEmpty(sourceList)) {
            return new ArrayList<>(0);
        }
        try {
            return sourceList.stream()
                    .map(s -> copyProperties(s, target)).collect(Collectors.toList());
        } catch (Exception e) {
            throw ExceptionHelper
                    .create(BizErrorCodes.FAILED_TO_CONVERT_DATA,
                            "数据转换出错，无法转换成目标对象{}集合", target.getName(), e);
        }
    }

    public static <T> Set<T> copyWithCollection(Set<?> sourceList, Class<T> target) {
        if (CollUtil.isEmpty(sourceList)) {
            return new HashSet<>(0);
        }
        try {
            return sourceList.stream()
                    .map(s -> copyProperties(s, target)).collect(Collectors.toSet());
        } catch (Exception e) {
            throw ExceptionHelper
                    .create(BizErrorCodes.FAILED_TO_CONVERT_DATA,
                            "数据转换出错，无法转换成目标对象{}集合", target.getName(), e);
        }
    }
}
