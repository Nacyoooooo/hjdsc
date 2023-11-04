package com.chenzhihao.serviceutil.annotation.impl;

import com.chenzhihao.commonutil.MD5;
import com.chenzhihao.serviceutil.annotation.AutoFill;
import com.chenzhihao.serviceutil.constant.FillPeriod;
import com.chenzhihao.serviceutil.constant.FillType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;

import static com.chenzhihao.serviceutil.constant.FillPeriod.SAVE;
import static com.chenzhihao.serviceutil.constant.FillPeriod.UPDATE;
import static com.chenzhihao.serviceutil.constant.FillType.ENCRYPT;
import static com.chenzhihao.serviceutil.constant.FillType.TIME;

/**
 * AutoFill的实现类
 */
@Component
@Aspect
@Slf4j
public class AutoFillImpl {
    @Pointcut("@annotation(com.chenzhihao.serviceutil.annotation.AutoFill)")
    public void pointcut(){}

    @Around("pointcut()")
    public Object Around(ProceedingJoinPoint j) throws Throwable {
        //控制台显示切入情况
        log.info("AutoFill切入成功");
        //执行完毕之后，开启填充
        Object proceed = j.proceed();
        MethodSignature signature = (MethodSignature)j.getSignature();
        Method method = signature.getMethod();
        AutoFill annotation = method.getAnnotation(AutoFill.class);
        Field[] fields = proceed.getClass().getDeclaredFields();
        for (Field field : fields) {
            AutoFill a = field.getAnnotation(AutoFill.class);
            if(null!=a){
                field.setAccessible(true);
                FillType type = a.type();

                if(type==TIME){
                    if(annotation.period()==SAVE){
                        field.set(proceed,new Date());
                    }
                    else if(annotation.period()==UPDATE){
                        FillPeriod period = a.period();
                        if(period==UPDATE){
                            field.set(proceed,new Date());
                        }
                    }
                }
                else if(type==ENCRYPT){
                    field.set(proceed, MD5.encrypt((String) field.get(proceed)));
                }

            }
        }
        return proceed;
    }
}
