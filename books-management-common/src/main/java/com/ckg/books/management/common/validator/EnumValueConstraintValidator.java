package com.ckg.books.management.common.validator;

import cn.hutool.core.util.EnumUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.ckg.books.management.common.annotation.EnumValue;
import java.util.Collections;
import java.util.List;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * EnumValue 校验实现
 *
 * @author chenkaigui
 * @date 2024/11/10
 */
public class EnumValueConstraintValidator implements ConstraintValidator<EnumValue, Object> {

    private Class enumClass;

    private List<Object> values;

    @Override
    public void initialize(EnumValue constraintAnnotation) {
        enumClass = constraintAnnotation.enumClass();

        if (StrUtil.isNotBlank(constraintAnnotation.field())) {
            values = EnumUtil.getFieldValues(enumClass, constraintAnnotation.field());
        } else {
            values = EnumUtil.getNames(enumClass);
        }

        values = ObjUtil.defaultIfNull(values, Collections.emptyList());
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (null == value) {
            return true;
        }
        for (Object obj : values) {
            if (obj.equals(value)) {
                return true;
            }
        }
        return false;
    }
}
