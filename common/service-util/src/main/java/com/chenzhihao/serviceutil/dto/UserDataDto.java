package com.chenzhihao.serviceutil.dto;

import com.chenzhihao.serviceutil.annotation.AutoValidate;
import com.chenzhihao.serviceutil.constant.FieldType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDataDto {
    private Long id;
    private String name;
    private String phoneNumber;
    private String email;
    private Integer gender;
    private String password;
    private String url;
}
