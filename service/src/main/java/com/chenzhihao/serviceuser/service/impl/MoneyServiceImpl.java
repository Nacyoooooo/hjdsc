package com.chenzhihao.serviceuser.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chenzhihao.serviceuser.model.Money;
import com.chenzhihao.serviceuser.model.Users;
import com.chenzhihao.serviceuser.result.Result;
import com.chenzhihao.serviceuser.service.MoneyService;
import com.chenzhihao.serviceuser.mapper.MoneyMapper;
import com.chenzhihao.serviceuser.util.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.chenzhihao.serviceuser.constant.RedisConstants.USER_MONEY_KEY;

/**
* @author 86159
* @description 针对表【money】的数据库操作Service实现
* @createDate 2023-11-19 23:58:37
*/
@Service
public class MoneyServiceImpl extends ServiceImpl<MoneyMapper, Money>
    implements MoneyService{
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Result<?> getCount() {
        Users user = UserHolder.getUser();
        if(user==null){
            return Result.fail();
        }
        String key=USER_MONEY_KEY+user.getId();
        Boolean exist = stringRedisTemplate.hasKey(key);
        if(!exist) {
            List<Money> list = list(new QueryWrapper<Money>().eq("uid", user.getId()));
            if(list==null||list.isEmpty()){
                return Result.fail();
            }
            list.forEach(l->{
                stringRedisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(l));
            });
        }
        String json = stringRedisTemplate.opsForValue().get(key);
        Money money = JSONUtil.toBean(json, Money.class);
        if(money==null){
            return Result.fail();
        }
        return Result.ok(money);
    }
}




