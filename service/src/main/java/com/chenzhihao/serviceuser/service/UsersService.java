package com.chenzhihao.serviceuser.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.chenzhihao.dto.LoginDto;
import com.chenzhihao.dto.RegisterDto;
import com.chenzhihao.model.Users;
import com.chenzhihao.serviceutil.result.Result;
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
}
