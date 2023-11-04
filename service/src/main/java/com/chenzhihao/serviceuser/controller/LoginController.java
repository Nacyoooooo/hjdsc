package com.chenzhihao.serviceuser.controller;

import com.chenzhihao.serviceutil.dto.LoginDto;
import com.chenzhihao.serviceuser.service.UsersService;
import com.chenzhihao.serviceutil.dto.RegisterDto;
import com.chenzhihao.serviceutil.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 登录接口
 */
@CrossOrigin
@RestController
@RequestMapping(path = "/login")
public class LoginController {
    @Autowired
    private UsersService usersService;
    @PostMapping
    @ResponseBody
    public Result<?> login(LoginDto user){
        return usersService.Login(user);
    }
    @PostMapping("register")
    @ResponseBody
    public Result<?> register(RegisterDto user){
        return usersService.register(user);
    }
    @PostMapping("/test")
    @ResponseBody
    public Result<?> test(){

        return Result.fail();
    }

    @PostMapping("/testExclude")
    @ResponseBody
    public Result<?> test2(){
        return Result.ok();
    }
}
