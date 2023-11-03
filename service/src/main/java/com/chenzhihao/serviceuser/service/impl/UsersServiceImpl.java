package com.chenzhihao.serviceuser.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chenzhihao.commonutil.DateUtil;
import com.chenzhihao.commonutil.JwtHelper;
import com.chenzhihao.commonutil.MD5;
import com.chenzhihao.dto.LoginDto;
import com.chenzhihao.dto.RegisterDto;
import com.chenzhihao.model.Users;
import com.chenzhihao.serviceuser.mapper.UsersMapper;
import com.chenzhihao.serviceuser.service.UsersService;
import com.chenzhihao.serviceutil.constant.UserCode;
import com.chenzhihao.serviceutil.result.Result;
import com.chenzhihao.serviceutil.result.ResultCodeEnum;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;


/**
* @author 86159
* @description 针对表【users】的数据库操作Service实现
* @createDate 2023-11-02 22:42:59
*/
@Component
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users>
    implements UsersService {


    @Override
    public Result  Login(LoginDto user) {
        //检查user的参数是否合法
        // 如果非法，返回异常
        if (user.getId()==null||user.getPassword()==null){
            return Result.fail();
        }
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
            String token = JwtHelper.createToken(one.getId(), one.getPassword());
            return Result.ok(token);
        }
        //密码校验失败，则返回错误信息
        return Result.fail(ResultCodeEnum.PASSWORD_ERROR);
    }

    @Override
    public Result register(RegisterDto user) {
        //TODO 用户注册的方法
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
        Users users = new Users();
        users.setName(user.getName());
        users.setEmail(user.getEmail());
        users.setPassword(MD5.encrypt(user.getPassword()));
        users.setPhonenumber(user.getPhoneNumber());
        users.setStatus(UserCode.NORMAL);
        users.setCreatetime(new Date());

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




