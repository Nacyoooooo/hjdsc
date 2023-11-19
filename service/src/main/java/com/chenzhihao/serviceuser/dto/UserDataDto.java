package com.chenzhihao.serviceuser.dto;

import com.chenzhihao.serviceuser.annotation.AutoValidate;
import com.chenzhihao.serviceuser.constant.FieldType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDataDto {
    private Integer id;
    private String name;
    private String phoneNumber;
    private String email;
    private Integer gender;
    private String password;
    private String url;
}
