package com.chenzhihao.serviceuser.annotation.impl;


import com.chenzhihao.serviceuser.annotation.AutoValidate;

import com.chenzhihao.serviceuser.constant.FieldType;
import com.chenzhihao.serviceuser.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;


import java.lang.reflect.Field;

/**
 * 自定义注解，实现字段校验
 * 此为AutoValidate的解释器
 */
@Component
@Aspect
@Slf4j
public class AutoValidateImpl{
    @Pointcut("@annotation(com.chenzhihao.serviceuser.annotation.AutoValidate)")
    public void pointcut(){}

    @Around("pointcut()")
    public Object before(ProceedingJoinPoint j) throws Throwable {
        //控制台显示切入情况
        log.info("AutoValidate切入成功");
        //获取其参数列表
        Object[] args =  j.getArgs();
        //循环遍历其参数列表
        for (Object arg : args) {
            //获取每个参数的字段数组
            Field[] fields = arg.getClass().getDeclaredFields();
            //遍历字段数组
            for (Field field : fields) {
                //从中寻找标记了需要字段校验的字段，如果为空就是不需要校验
                AutoValidate a = field.getAnnotation(AutoValidate.class);
                if(a!=null){
                    //获取注解需要校验的字段的校验规则
                    FieldType value = a.value();
                    //默认非空判断

                    boolean notnull = NOTNULL(arg, field);
                        if(!notnull){
                            return Result.fail(field.getName()+"为空");
                        }
                        if(value==FieldType.TIME){

                        }

                }
            }
        }
        Object proceed = j.proceed();
        return proceed;
    }

    public static boolean NOTNULL(Object arg,Field  field) throws IllegalAccessException {
        field.setAccessible(true);
        Object o = field.get(arg);
        if(o==null){
            return false;
        }
        return true;
    }
    public static boolean TIME(Object arg,Field  field){
        return false;
    }

}
