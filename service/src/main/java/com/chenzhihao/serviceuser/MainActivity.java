package com.chenzhihao.serviceuser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@SpringBootApplication
@ComponentScan("com.chenzhihao")
public class MainActivity {

    public static void main(String[] args) {
        SpringApplication.run(MainActivity.class, args);
    }

}
