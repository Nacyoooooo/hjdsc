package com.chenzhihao.serviceutil.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
//登录专用dto
public class LoginDto implements Serializable {
    private Integer id;
    private String password;
}
