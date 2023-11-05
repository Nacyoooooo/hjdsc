package com.chenzhihao.serviceutil.util;

import com.chenzhihao.commonutil.MD5;
import com.chenzhihao.serviceutil.annotation.AutoValidate;
import com.chenzhihao.serviceutil.constant.FillPeriod;
import com.chenzhihao.serviceutil.dto.UserDataDto;
import com.chenzhihao.serviceutil.model.Users;
import com.chenzhihao.serviceutil.annotation.AutoFill;
import com.chenzhihao.serviceutil.constant.UserCode;
import com.chenzhihao.serviceutil.dto.RegisterDto;

import static com.chenzhihao.serviceutil.constant.FillPeriod.SAVE;
import static com.chenzhihao.serviceutil.constant.FillPeriod.UPDATE;

/**
 *user对象相关的util类
 */
public class UserUtil {
    /**
     * 本方法用于注册新用户
     * @return
     */
    @AutoFill(period = SAVE)
    public Users createUser(RegisterDto user){
        Users users=new Users();
        users.setName(user.getName());
        users.setEmail(user.getEmail());
        users.setPassword(MD5.encrypt(user.getPassword()));
        users.setPhonenumber(user.getPhoneNumber());
        users.setStatus(UserCode.NORMAL);
        users.setGender(user.getGender());
        return users;
    }
    @AutoFill(period = UPDATE)
    public Users createUser(UserDataDto user){
        Users users=new Users();
        users.setId(user.getId());
        users.setName(user.getName());
        users.setEmail(user.getEmail());
        users.setPassword(MD5.encrypt(user.getPassword()));
        users.setPhonenumber(user.getPhoneNumber());
        users.setStatus(UserCode.NORMAL);
        users.setGender(user.getGender());
        users.setUrl(user.getUrl());
        return users;
    }

    /**
     * 本方法用于Users对象脱敏
     * @return
     */
    public UserDataDto createUserDataDto(Users users){
        UserDataDto userDataDto = new UserDataDto();
        userDataDto.setId(users.getId());
        userDataDto.setEmail(users.getEmail());
        userDataDto.setName(users.getName());
        userDataDto.setGender(users.getGender());
        userDataDto.setPhoneNumber(users.getPhonenumber());
        userDataDto.setUrl(users.getUrl());
        return userDataDto;
    }
}
