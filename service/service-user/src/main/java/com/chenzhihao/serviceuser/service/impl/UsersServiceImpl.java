package com.chenzhihao.serviceuser.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chenzhihao.commonutil.JwtHelper;
import com.chenzhihao.commonutil.MD5;
import com.chenzhihao.dto.LoginDto;
import com.chenzhihao.model.Users;
import com.chenzhihao.serviceuser.mapper.UsersMapper;
import com.chenzhihao.serviceuser.service.UsersService;
import com.chenzhihao.serviceutil.result.Result;
import com.chenzhihao.serviceutil.result.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
* @author 86159
* @description 针对表【users】的数据库操作Service实现
* @createDate 2023-11-02 22:42:59
*/
@Service
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
    public Result register() {
        return null;
    }

    @Override
    public Result forgetPassword() {
        return null;
    }
}




