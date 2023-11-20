package com.chenzhihao.serviceuser;

import com.chenzhihao.serviceuser.util.JwtHelper;
import com.chenzhihao.serviceuser.util.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.stream.*;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 自定义注解的测试类
 */
@SpringBootTest
public class AnnotationTest {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Test
    public void AutoFillTest(){
        String queueName="stream.orders";
        Boolean exist = stringRedisTemplate.hasKey(queueName);
        if(!exist){
            Map<Integer,Integer>integerIntegerMap=new HashMap<>();
            stringRedisTemplate.opsForStream().createGroup(queueName,ReadOffset.lastConsumed(),"g1");
        }
        List<MapRecord<String, Object, Object>> list = stringRedisTemplate.opsForStream().read(
                Consumer.from("g1", "c1"),
                StreamReadOptions.empty().count(1).block(Duration.ofSeconds(2)),
                StreamOffset.create(queueName, ReadOffset.lastConsumed())
        );
        System.out.println(list);
    }
    @Autowired
    private RedisUtil redisUtil;
    @Test
    public void tesst(){
        Boolean b = redisUtil.hasKey("stream.orders");
        System.out.println(b);
    }
    @Test
    public void tesst2(){
        boolean tokenExpired = JwtHelper.isTokenExpired("eyJhbGciOiJIUzUxMiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAAAKtWKi5NUrJSyshKKU7WDQ12DVLSUUqtKFCyMjQ3MLQ0trCwMNRRKi1OLfJMAYrpKCWWlmTkF2WWVAJ5tQBbIDFZPgAAAA.Mgu93CWGDqtqAde4b7LjW-9RGHLiTM5sYiNoFnti0WPErvxq8aEyUIadhxgEgQQFkSbf3yco0QLpmUhQ_fC0oQ");
        System.out.println(tokenExpired);
    }

}
