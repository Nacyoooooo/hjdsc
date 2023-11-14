package com.chenzhihao.serviceuser.dto;
import com.chenzhihao.serviceuser.annotation.AutoValidate;
import com.chenzhihao.serviceuser.constant.FieldType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDto {
    @AutoValidate(value = FieldType.NOTNULL)
    private String name;
    @AutoValidate(value = FieldType.NOTNULL)
    private String password;
    @AutoValidate(value = FieldType.NOTNULL)
    private String phoneNumber;
    @AutoValidate(value = FieldType.NOTNULL)
    private String email;
    @AutoValidate(value = FieldType.NOTNULL)
    private Integer gender;
}
