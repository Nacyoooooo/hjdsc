package com.chenzhihao.serviceutil.annotation;

import com.chenzhihao.serviceutil.constant.FillPeriod;
import com.chenzhihao.serviceutil.constant.FillType;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static com.chenzhihao.serviceutil.constant.FillPeriod.UPDATE;
import static com.chenzhihao.serviceutil.constant.FillType.TIME;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 自定义注解，用于自动填充字段，根据设定的规则进行填充
 * 作用于方法，方便aop识别
 * 作用于字段，方便灵活填充
 */
@Target({FIELD,METHOD,LOCAL_VARIABLE})
@Retention(RUNTIME)
public @interface AutoFill {
    FillType type() default TIME;
    FillPeriod period()default UPDATE;
}
