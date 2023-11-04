package com.chenzhihao.serviceuser;

import com.chenzhihao.serviceutil.model.Users;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootTest
public class RedisTest {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Test
    public void t1(){
        Users users = new Users();
        users.setName("虎哥");
        Object name = redisTemplate.opsForValue().get("name");
        redisTemplate.opsForHash().put("user:100","name","虎哥");
        redisTemplate.opsForHash().put("user:100","age","21");
        System.out.println(name);
    }
}
