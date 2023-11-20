package com.chenzhihao.serviceuser.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chenzhihao.serviceuser.model.Mainpet;
import com.chenzhihao.serviceuser.model.Users;
import com.chenzhihao.serviceuser.result.Result;
import com.chenzhihao.serviceuser.service.MainpetService;
import com.chenzhihao.serviceuser.mapper.MainpetMapper;
import com.chenzhihao.serviceuser.util.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.chenzhihao.serviceuser.constant.RedisConstants.PETS_MAINPET_KEY;

/**
* @author 86159
* @description 针对表【mainpet】的数据库操作Service实现
* @createDate 2023-11-19 22:55:04
*/
@Service
public class MainpetServiceImpl extends ServiceImpl<MainpetMapper, Mainpet>
    implements MainpetService{
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Result<?> getMainPets(Integer pid) {
        Users user = UserHolder.getUser();
        if(user==null) {
            return Result.fail();
        }
        //redis
        String key=PETS_MAINPET_KEY+pid;
        Boolean exist= stringRedisTemplate.hasKey(key);
        if(!exist){
            List<Mainpet> list = list();
            if(list==null||list.isEmpty()){
                return Result.fail();
            }
            list.forEach(l->{
                stringRedisTemplate.opsForSet().add(key, l.getId().toString());
            });
        }
        Boolean isReceived = stringRedisTemplate.opsForSet().isMember(key, user.getId().toString());
        //领取过
        if(isReceived){
            return Result.fail("你已领取过");
        }
        //TODO 未领取过,用高并发方案来解决生成宠物进入仓库
        //database
        return Result.ok();
    }
}




