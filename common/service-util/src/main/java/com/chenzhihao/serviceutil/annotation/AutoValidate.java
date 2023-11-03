package com.chenzhihao.serviceutil.annotation;

import com.chenzhihao.serviceutil.constant.FieldType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解，用于校验字段是否为空值
 */
@Target({ElementType.FIELD,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoValidate {
    FieldType value() default FieldType.NOTNULL;
}
