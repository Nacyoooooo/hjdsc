package com.chenzhihao.serviceuser.util;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.chenzhihao.serviceuser.constant.RedisConstants;
import com.chenzhihao.serviceuser.model.Users;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.chenzhihao.serviceuser.constant.RedisConstants.LOGIN_ADMIN_KEY;
import static com.chenzhihao.serviceuser.constant.RedisConstants.LOGIN_USER_KEY;
import static com.chenzhihao.serviceuser.constant.UserCode.ADMIN;

@Slf4j
public class RefreshTokenInterceptor implements HandlerInterceptor {
    private StringRedisTemplate stringRedisTemplate;

    public RefreshTokenInterceptor(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1.获取请求头中的token
        String token = request.getHeader("authorization");
        //如果是空的就放行，让它去登录
        if(StrUtil.isBlank(token)){
            log.info("token为空");
            return true;
        }
        //如果不为空，就去解析token，获取用户id
        Integer userId = JwtHelper.getUserId(token);
        Integer userAuthority = JwtHelper.getUserAuthority(token);
        String key;
        if(userAuthority.equals(ADMIN)){
            key= LOGIN_ADMIN_KEY+userId;
        }
        else {
            key=LOGIN_USER_KEY+userId;
        }
        //从redis中找用户，如果没有，则放行，让它去登录
        Map<Object, Object> entries = stringRedisTemplate.opsForHash().entries(key);
        if(entries==null||entries.isEmpty()){
            return true;
        }
        //将查询到的用户转化为Users对象
        Users users = BeanUtil.fillBeanWithMap(entries, new Users(), false);
        UserHolder.saveUser(users);
        //刷新token有效期
        stringRedisTemplate.expire(key, RedisConstants.LOGIN_USER_TTL, TimeUnit.MINUTES);
        //如果有，则放行
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
