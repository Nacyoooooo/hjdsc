package com.chenzhihao.serviceuser.service.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chenzhihao.commonutil.JwtHelper;
import com.chenzhihao.commonutil.MD5;
import com.chenzhihao.serviceutil.annotation.AutoFill;
import com.chenzhihao.serviceutil.dto.LoginDto;
import com.chenzhihao.serviceutil.dto.RegisterDto;
import com.chenzhihao.serviceutil.model.Users;
import com.chenzhihao.serviceutil.annotation.AutoValidate;
import com.chenzhihao.serviceuser.mapper.UsersMapper;
import com.chenzhihao.serviceuser.service.UsersService;
import com.chenzhihao.serviceutil.result.Result;
import com.chenzhihao.serviceutil.result.ResultCodeEnum;
import com.chenzhihao.serviceutil.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.chenzhihao.serviceutil.constant.RedisConstants.LOGIN_USER_KEY;
import static com.chenzhihao.serviceutil.constant.RedisConstants.LOGIN_USER_TTL;


/**
 * @author 86159
 * @description 针对表【users】的数据库操作Service实现
 * @createDate 2023-11-02 22:42:59
 */
@Component
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users>
        implements UsersService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private UserUtil util;
    @AutoValidate
    @Override
    public Result  Login(LoginDto user) {
        //检查user的参数是否合法
        //如果合法，开始校验
        //根据id查找用户
        QueryWrapper q=new QueryWrapper();
        q.eq("id",user.getId());
        Users one = getOne(q);
        // 如果不存在，则返回错误信息
        if(user==null){
            return Result.fail(ResultCodeEnum.USER_UNEXIST);
        }
        //如果存在，则开始校验密码
        // 密码校验成功，则返回登录成功信息和token
        if(MD5.encrypt(user.getPassword()).equals(one.getPassword())){
            //创建token
            String token = JwtHelper.createToken(one.getId());
            //将该对象的基本信息拆分成map集合，方便后续存入redis
            Map<String, Object> stringObjectMap = BeanUtil.beanToMap(one, new HashMap<>(),
                    CopyOptions.create()
                            .setIgnoreNullValue(true)
                            .setIgnoreError(true)
                            .setFieldValueEditor((fieldName, fieldValue) -> {
                                        if(fieldValue==null){
                                            return null;
                                        }
                                        return fieldValue.toString() ;
                                    }
                            ));
            //创建redis查询时使用的key
            String tokenkey=LOGIN_USER_KEY+one.getId();
            //存入hash结构
            stringRedisTemplate.opsForHash().putAll(tokenkey,stringObjectMap);
            //刷新token的时间
            stringRedisTemplate.expire(tokenkey, LOGIN_USER_TTL, TimeUnit.MINUTES);
            //返回token供前端调用并存储
            return Result.ok(token);
        }
        //密码校验失败，则返回错误信息
        return Result.fail(ResultCodeEnum.PASSWORD_ERROR);
    }
    @AutoValidate
    @Override
    public Result register(RegisterDto user) {
        //校验用户信息是否为空
        //如果不缺少，则开始校验
        // 要求：用户名不重复，手机号和邮箱可以重复
        QueryWrapper q=new QueryWrapper();
        q.eq("name",user.getName());
        Users one = getOne(q);
        if(null!=one){
            return Result.fail(ResultCodeEnum.USER_EXIST);
        }
        //如果符合要求，则开始创建角色，并初始化
        Users users = util.createUser(user);
        boolean save = save(users);
        if(save){
            return Result.ok();
        }
        //创建之后查询并确认其是否注册成功
        return Result.fail();
    }

    @Override
    public Result forgetPassword() {
        //TODO 忘记密码的方法
        return null;
    }
}




