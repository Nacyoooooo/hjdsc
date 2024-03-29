package com.chenzhihao.serviceuser.controller;


import com.chenzhihao.serviceuser.dto.LoginDto;
import com.chenzhihao.serviceuser.dto.RegisterDto;
import com.chenzhihao.serviceuser.result.Result;
import com.chenzhihao.serviceuser.service.UsersService;
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
    public Result<?> login(@RequestBody LoginDto user){
        return usersService.Login(user);
    }
    @PostMapping("register")
    @ResponseBody
    public Result<?> register(@RequestBody RegisterDto user){
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
