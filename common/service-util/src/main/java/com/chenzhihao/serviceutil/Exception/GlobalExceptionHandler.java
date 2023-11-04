package com.chenzhihao.serviceutil.Exception;

import com.chenzhihao.serviceutil.result.ResultCodeEnum;
import com.chenzhihao.serviceutil.result.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class) //异常处理器
    @ResponseBody  //返回json数据
    public Result error(Exception e) {
        e.printStackTrace();
        //统一返回服务器异常信息
        return Result.fail(ResultCodeEnum.NETWORK_ERROR);
    }
    @ExceptionHandler(JwtException.class) //异常处理器
    @ResponseBody  //返回json数据
    public Result jwterror(Exception e) {
        e.printStackTrace();
        //统一返回服务器异常信息
        return Result.fail(ResultCodeEnum.AUTH_ERROR);
    }
}
