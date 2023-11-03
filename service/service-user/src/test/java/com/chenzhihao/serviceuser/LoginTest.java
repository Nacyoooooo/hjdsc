package com.chenzhihao.serviceuser;

import com.chenzhihao.dto.LoginDto;
import com.chenzhihao.serviceuser.service.UsersService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LoginTest {
    @Autowired
    private UsersService usersService;
    @Test
    public void LoginTest(){
        LoginDto loginDto = new LoginDto();
        loginDto.setId(1);
        loginDto.setPassword("123456");
        System.out.println(usersService.Login(loginDto));
    }

}
