package com.chenzhihao.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDto {
    private String name;
    private String password;
    private String phoneNumber;
    private String email;
    private String url;
    private Integer gender;
    private Integer status;
}
