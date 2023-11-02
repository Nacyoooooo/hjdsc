package com.chenzhihao.serviceutil.result;

import lombok.Getter;
//统一结果封装枚举类
@Getter
public enum ResultCodeEnum {
    SUCCESS(200,"成功"),
    FAIL(201, "失败"),
    NETWORK_ERROR(500,"服务器异常");
    private Integer code;

    private String message;

    private ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
