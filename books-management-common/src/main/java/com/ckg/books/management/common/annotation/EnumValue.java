package com.ckg.books.management.common.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import com.ckg.books.management.common.annotation.EnumValue.List;
import com.ckg.books.management.common.validator.EnumValueConstraintValidator;
import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * 用于校验枚举值的注解
 *
 * @author chenkaigui
 * @date 2024/11/10
 */
@Target({METHOD, FIELD})
@Retention(RUNTIME)
@Repeatable(List.class)
@Documented
@Constraint(validatedBy = {EnumValueConstraintValidator.class})
public @interface EnumValue {

    String message() default "无效的枚举值";

    String field() default "code";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Class<? extends Enum<?>> enumClass();

    /**
     * Defines several {@link EnumValue} annotations on the same element.
     *
     * @see EnumValue
     */
    @Target({METHOD, FIELD})
    @Retention(RUNTIME)
    @Documented
    @interface List {

        EnumValue[] value();
    }
}
