package com.chenzhihao.serviceuser;

import com.chenzhihao.serviceutil.annotation.AutoFill;
import com.chenzhihao.serviceutil.model.Users;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 自定义注解的测试类
 */
@SpringBootTest
public class AnnotationTest {
    @Test
    public void AutoFillTest(){
        Users users = new Users();
        set(users);
        System.out.println(users);
    }
    @AutoFill
    void set(Users users){

    }
}
