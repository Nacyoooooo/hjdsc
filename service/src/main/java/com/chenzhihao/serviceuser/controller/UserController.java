package com.chenzhihao.serviceuser.controller;

import com.chenzhihao.serviceuser.service.SignsService;
import com.chenzhihao.serviceuser.service.UsersService;
import com.chenzhihao.serviceutil.dto.LoginDto;
import com.chenzhihao.serviceutil.dto.UserDataDto;
import com.chenzhihao.serviceutil.model.Users;
import com.chenzhihao.serviceutil.result.Result;
import com.chenzhihao.serviceutil.util.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户相关信息的专用controller
 */
@CrossOrigin
@RestController
@RequestMapping(path = "/user")
@Slf4j
public class UserController {
    @Autowired
    private UsersService usersService;
    @Autowired
    private SignsService signsService;
    @PostMapping("/sign")
    @ResponseBody
    public Result<?> sign(){
        return signsService.sign();
    }
    @PostMapping("/signCount")
    @ResponseBody
    public Result<?> signCount(){
        return signsService.signCount();
    }
    @PostMapping("/getInfo")
    @ResponseBody
    public Result<?> getInfo(){
        return usersService.getInfo();
    }

    @PostMapping("/updateInfo")
    @ResponseBody
    public Result<?> updateInfo(@RequestBody UserDataDto users){
        return usersService.updateInfo(users);
    }
}
