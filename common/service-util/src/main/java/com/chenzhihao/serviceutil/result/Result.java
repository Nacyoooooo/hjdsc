package com.chenzhihao.serviceutil.result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
//统一结果返回类
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Result <T>{
    private int code;

    private String msg;

    private T data;
    private static Integer SUCCESS=200;
    private static Integer FAIL=400;
    public static  <T>Result<?> ok(){
        return new Result<>(SUCCESS,"操作成功",null);
    }
    public static  <T>Result<?> ok(T data){
        return new Result<>(SUCCESS,"操作成功",data);
    }
    public static <T> Result<T> ok(T data, String msg)
    {
        return new Result<>(SUCCESS,msg,data);
    }

    public static <T> Result<T> fail()
    {
        return new Result<>(FAIL,"操作失败",null);
    }
    public static <T> Result<T> fail(String msg)
    {
        return new Result<>(FAIL,msg,null);
    }
    public static <T> Result<T> fail(ResultCodeEnum resultCodeEnum)
    {
        return new Result<>(resultCodeEnum.getCode(),resultCodeEnum.getMessage(),null);
    }
    public static <T> Result<T> fail(T data)
    {
        return new Result<>(FAIL,"操作失败",data);
    }
    public static <T> Result<T> fail(T data,String msg)
    {
        return new Result<>(FAIL,msg,data);
    }
    public static <T> Result<T> fail(int code,String msg)
    {
        return new Result<>(code,msg,null);
    }


}

