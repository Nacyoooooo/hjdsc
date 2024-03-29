package com.chenzhihao.serviceuser.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.chenzhihao.serviceuser.mapper.PetsconfigMapper;
import com.chenzhihao.serviceuser.model.Petsconfig;
import com.chenzhihao.serviceuser.result.Result;
import com.chenzhihao.serviceuser.service.PetsconfigService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.chenzhihao.serviceuser.constant.RedisConstants.PETS_CONFIG_KEY;
import static com.chenzhihao.serviceuser.constant.RedisConstants.PETS_CONFIG_TTL;


/**
* @author 86159
* @description 针对表【petsconfig】的数据库操作Service实现
* @createDate 2023-11-07 22:56:52
*/
@Service
public class PetsconfigServiceImpl extends ServiceImpl<PetsconfigMapper, Petsconfig>
    implements PetsconfigService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Result<?> getPetConfig(Integer id) {
        //设置查询的key
        String key= PETS_CONFIG_KEY+id;
        //从redis中查找
        Map<Object, Object> entries = stringRedisTemplate.opsForHash().entries(key);
        //如果是不是空的，则将其转化为对象，并返回给前端
        if(!entries.isEmpty()){
            //将查询到的用户转化为petsconfig对象
            Petsconfig petsconfig = BeanUtil.fillBeanWithMap(entries, new Petsconfig(), false);
            if(petsconfig!=null){
                return Result.ok(petsconfig);
            }
            else {
                return Result.fail();
            }
        }
        //如果是空的，则说明redis中没有对应宠物的配置信息
        //从数据库中查找
        QueryWrapper q=new QueryWrapper();
        q.eq("id",id);
        Petsconfig one = getOne(q);
        //如果查出来不是空的，则封装并返回
        if(one!=null){
            //写入redis
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
            stringRedisTemplate.opsForHash().putAll(key,stringObjectMap);
            stringRedisTemplate.expire(key, PETS_CONFIG_TTL, TimeUnit.MINUTES);
            //将数据返回
            return Result.ok(one);
        }
        return Result.fail();
    }
    @Override
    public Result<?> setPetConfig(Petsconfig petsconfig) {
        if(petsconfig.getId()!=null){
            return Result.fail("不能含有id");
        }
        Date now = new Date();
        petsconfig.setCreatetime(now);
        petsconfig.setUpdatetime(now);
        boolean save = save(petsconfig);
        if(save){
            return Result.ok();
        }
        return Result.fail();
    }

    @Override
    public Result<?> updatePets(Petsconfig petsconfig) {
        if(petsconfig.getId()==null){
            return Result.fail("id不能为空");
        }
        Date now = new Date();
        petsconfig.setUpdatetime(now);
        boolean save = save(petsconfig);
        if(save){
            return Result.ok();
        }
        return Result.fail();
    }

    @Override
    public Result<?> deletePets(Long id) {
        boolean b = removeById(id);
        if(b){
            return Result.ok();
        }
        return Result.fail();
    }

    @Override
    public Result<?> getPetConfigs(Integer pageId) {
        //从redis中查找
        String pattern=PETS_CONFIG_KEY+"*";
        Set<String> keys = stringRedisTemplate.keys(pattern);
        //没有从数据库中找
        if(keys==null||keys.isEmpty()){
            List<Petsconfig> list = list();
            list.forEach(l->{
                String key=PETS_CONFIG_KEY+l.getId();
                Map<String, Object> stringObjectMap = BeanUtil.beanToMap(l, new HashMap<>(),
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
                stringRedisTemplate.opsForHash().putAll(key,stringObjectMap);
            });
        }
        //挂载到redis上
        List<Petsconfig>petsconfigs=new ArrayList<>();
        keys.forEach(key->{
            Map<Object, Object> entries = stringRedisTemplate.opsForHash().entries(key);
            Petsconfig petsconfig = BeanUtil.fillBeanWithMap(entries, new Petsconfig(), false);
            petsconfigs.add(petsconfig);
        });
        return Result.ok(petsconfigs);
    }


}




