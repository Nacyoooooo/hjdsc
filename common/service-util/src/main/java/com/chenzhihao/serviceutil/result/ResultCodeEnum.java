package com.chenzhihao.serviceutil.result;

import lombok.Getter;
//统一结果封装枚举类
@Getter
public enum ResultCodeEnum {
    SUCCESS(200,"成功"),
    FAIL(201, "失败"),
    DAT_ERROR(202,"传输数据异常"),
    PASSWORD_ERROR(203,"密码错误"),
    USER_UNEXIST(400,"用户不存在"),
    USER_EXIST(401,"用户已存在"),
    NETWORK_ERROR(500,"服务器异常"),
    AUTH_ERROR(501,"登录信息异常");
    private Integer code;

    private String message;

    private ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
