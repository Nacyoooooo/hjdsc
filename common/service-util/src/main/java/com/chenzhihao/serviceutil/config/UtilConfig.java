package com.chenzhihao.serviceutil.config;

import com.chenzhihao.serviceutil.util.UserUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UtilConfig {
    @Bean
    public UserUtil util(){
        return new UserUtil();
    }
}
