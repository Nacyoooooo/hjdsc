package com.chenzhihao.serviceuser.dto;

import com.chenzhihao.serviceuser.annotation.AutoValidate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
//登录专用dto
public class LoginDto implements Serializable {
    @AutoValidate
    private Integer id;
    @AutoValidate
    private String password;
    @AutoValidate
    private Integer authority;
}
