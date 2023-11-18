package com.chenzhihao.serviceuser.service;


import com.baomidou.mybatisplus.extension.service.IService;

import com.chenzhihao.serviceuser.dto.LoginDto;
import com.chenzhihao.serviceuser.dto.RegisterDto;
import com.chenzhihao.serviceuser.dto.UserDataDto;
import com.chenzhihao.serviceuser.model.Users;
import com.chenzhihao.serviceuser.result.Result;
import org.springframework.stereotype.Service;


/**
* @author 86159
* @description 针对表【users】的数据库操作Service
* @createDate 2023-11-02 22:42:59
*/
@Service
public interface UsersService extends IService<Users> {
    //用户登录接口
public Result Login(LoginDto user);
public Result register(RegisterDto user);
public Result forgetPassword();

    public Result<?> sign();

    Result<?> signCount();

    Result<?> getInfo();

    Result<?> updateInfo(UserDataDto users);

    Result<?> getUserInfo();

    Result<?> banUser(Long uid);

    Result<?> getUserInfo(Integer pageId);

    Result<?> getUserCount();
}
