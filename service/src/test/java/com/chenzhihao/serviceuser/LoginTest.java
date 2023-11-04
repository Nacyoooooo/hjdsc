package com.chenzhihao.serviceuser;

import com.chenzhihao.serviceutil.dto.LoginDto;

import com.chenzhihao.serviceutil.dto.RegisterDto;
import com.chenzhihao.serviceuser.service.UsersService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class LoginTest {
    @Autowired
    private UsersService usersService;
    @Test
    public void LoginTest1(){
        LoginDto loginDto = new LoginDto();
        loginDto.setId(1);
        loginDto.setPassword("123456");
        System.out.println(usersService.Login(loginDto));
    }
    @Test
    public void register(){
        RegisterDto registerDto = new RegisterDto();
        registerDto.setName("TestRegister");
        registerDto.setPassword("123456");
        registerDto.setEmail("939832920@qq.com");
        registerDto.setGender(1);
        registerDto.setPhoneNumber("159");
        System.out.println(usersService.register(registerDto));
    }

}
