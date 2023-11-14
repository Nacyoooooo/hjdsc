package com.chenzhihao.serviceuser.config;

import com.chenzhihao.serviceuser.util.UserUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UtilConfig {
    @Bean
    public UserUtil util(){
        return new UserUtil();
    }
}
