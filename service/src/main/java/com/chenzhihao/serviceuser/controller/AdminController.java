package com.chenzhihao.serviceuser.controller;

import com.chenzhihao.serviceuser.service.UsersService;
import com.chenzhihao.serviceutil.dto.LoginDto;
import com.chenzhihao.serviceutil.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(path = "/admins")
@Slf4j
public class AdminController {
    @Autowired
    private UsersService usersService;
    @PostMapping("getUserInfo")
    @ResponseBody
    public Result<?> getUserInfo(){
        return usersService.getUserInfo();
    }
    @PostMapping("banUser/{uid}")
    @ResponseBody
    public Result<?> banUser(@PathVariable Long uid){
        return usersService.banUser(uid);
    }
}
