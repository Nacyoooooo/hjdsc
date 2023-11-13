package com.chenzhihao.serviceuser.service.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chenzhihao.commonutil.JwtHelper;
import com.chenzhihao.commonutil.MD5;
import com.chenzhihao.serviceutil.annotation.AutoFill;
import com.chenzhihao.serviceutil.dto.LoginDto;
import com.chenzhihao.serviceutil.dto.RegisterDto;
import com.chenzhihao.serviceutil.dto.UserDataDto;
import com.chenzhihao.serviceutil.model.Users;
import com.chenzhihao.serviceutil.annotation.AutoValidate;
import com.chenzhihao.serviceuser.mapper.UsersMapper;
import com.chenzhihao.serviceuser.service.UsersService;
import com.chenzhihao.serviceutil.result.Result;
import com.chenzhihao.serviceutil.result.ResultCodeEnum;
import com.chenzhihao.serviceutil.util.UserHolder;
import com.chenzhihao.serviceutil.util.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.BitFieldSubCommands;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.chenzhihao.serviceutil.constant.RedisConstants.*;


/**
 * @author 86159
 * @description 针对表【users】的数据库操作Service实现
 * @createDate 2023-11-02 22:42:59
 */
@Component
@Slf4j
@EnableTransactionManagement
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
            String token = JwtHelper.createToken(one.getId(),one.getAuthority().longValue());
            log.info(token);
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
            //如果是管理员
            String tokenkey;
            if(user.getAuthority().equals(1)){
                tokenkey=LOGIN_ADMIN_KEY+one.getId();
            }
            //如果是用户
            else {
                tokenkey=LOGIN_USER_KEY+one.getId();
            }
            //创建redis查询时使用的key

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

    /**
     * 今日签到
     * @return 今日签到
     */
    @Override
    public Result<?> sign() {
        //TODO 还未保存到数据库
        Users user = UserHolder.getUser();
        if(null==user){
            return Result.fail();
        }
        Long id = user.getId();
        LocalDateTime now = LocalDateTime.now();
        String keySuffix = now.format(DateTimeFormatter.ofPattern(":yyyyMM"));
        String key="sign:"+id+keySuffix;
        int dayOfMonth = now.getDayOfMonth();
        stringRedisTemplate.opsForValue().setBit(key,dayOfMonth-1,true);
        return Result.ok();
    }

    /**
     * @1.计算当前连续最长签到天数
     * @2.计算本月最长连续签到天数
     * @3.计算本月签到的天数
     * @4.计算本月那天签到了
     *
     */
    @Override
    public Result<?> signCount() {
        Users user = UserHolder.getUser();
        if(null==user){
            return Result.fail();
        }
        Long id = user.getId();
        LocalDateTime now = LocalDateTime.now();
        String keySuffix = now.format(DateTimeFormatter.ofPattern(":yyyyMM"));
        String key="sign:"+id+keySuffix;
        int dayOfMonth = now.getDayOfMonth();

        List<Long> results = stringRedisTemplate.opsForValue().bitField(
                key, BitFieldSubCommands.create()
                        .get(BitFieldSubCommands.BitFieldType.unsigned(dayOfMonth)).valueAt(0)
        );
        if(null==results||results.isEmpty()){
            return Result.ok(0);
        }
        Long num = results.get(0);
        if(null==num||num==0){
            return Result.ok(0);
        }
        //TODO 计算当前连续签到天数(从今天算起还是明天算起）
        int count=0;
        //计算本月最大连续签到天数
        int max=0;
        //计算本月总共签到天数
        int sum=0;
        //计算本月那天签到了
        int length = now.getMonth().length(true);
        Map<Object,Object> monthSign=new HashMap<>();
        for (int i = 1; i <= length; i++) {
            monthSign.put(i,0);
        }
        int temp=0;
        int flag=0;
        //开始计算
        while (true){
            //位运算结束标识
            if(num==0){
                break;
            }
            //没有签到
            if((num&1)==0){
                temp=0;
                flag=1;
            }
            //签到了
            else {
                monthSign.put(dayOfMonth,1);
                sum++;
                temp++;
                if(temp>max){
                    max=temp;
                }
                if(flag==0){
                    count++;
                }
            }
            dayOfMonth--;
            num>>>=1;
        }
        Map map=new HashMap();
        map.put("count",count);
        map.put("max",max);
        map.put("sum",sum);
        map.put("monthSign",monthSign);
        return Result.ok(map);
    }

    /**
     * @用户获取个人信息
     * @return
     */
    @Override
    public Result<?> getInfo() {
        //TODO 用户获取个人信息
        Users user = UserHolder.getUser();
        if(null==user){
            return  Result.fail();
        }
        UserDataDto userDataDto = util.createUserDataDto(user);
        if(userDataDto==null){
            return Result.fail();
        }
        return Result.ok(userDataDto);
    }

    @Override
    public Result<?> updateInfo(UserDataDto users) {
        Users user = UserHolder.getUser();
        if(null==user){
            return  Result.fail();
        }
        if(user.getId()!=users.getId()){
            return Result.fail();
        }
        Users user1 = util.createUser(users);
        boolean b = saveOrUpdate(user1);
        if(!b){
            return Result.fail();
        }
        UserHolder.saveUser(user1);
        QueryWrapper q=new QueryWrapper();
        q.eq("id",user.getId());
        Users one = getOne(q);
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

        return Result.ok();
    }
}




