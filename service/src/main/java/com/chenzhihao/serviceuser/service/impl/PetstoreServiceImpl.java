package com.chenzhihao.serviceuser.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.chenzhihao.serviceuser.mapper.PetstoreMapper;
import com.chenzhihao.serviceuser.model.Petstore;
import com.chenzhihao.serviceuser.model.Users;
import com.chenzhihao.serviceuser.result.Result;
import com.chenzhihao.serviceuser.service.PetstoreService;
import com.chenzhihao.serviceuser.util.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.chenzhihao.serviceuser.constant.RedisConstants.PETS_BAG_KEY;


/**
 * @author 86159
 * @description 针对表【petstore】的数据库操作Service实现
 * @createDate 2023-11-13 15:20:16
 */
@Service
public class PetstoreServiceImpl extends ServiceImpl<PetstoreMapper, Petstore>
        implements PetstoreService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @Override
    public Result<?> getBags() {
        Users user = UserHolder.getUser();
        if(user==null){
            return Result.fail();
        }
        Long uid = user.getId();
        String key=PETS_BAG_KEY+uid;
        Boolean existKey = stringRedisTemplate.hasKey(key);
        //如果redis中存在这个key，则直接从redis中读取
        if(existKey){
            List<String> petslist = stringRedisTemplate.opsForList().range(key, 0, -1);
            if(petslist==null||petslist.size()<=0){
                return Result.fail();
            }
            List<Petstore>petstores=new ArrayList<>();
            petslist.forEach(petString->{
                Petstore pet = JSONUtil.toBean(petString, Petstore.class);
                petstores.add(pet);
            });
            if(petstores.size()>0){
                return Result.ok(petstores);
            }else {
                return Result.fail();
            }
        }
        //不存在则优先从数据库中读取数据，随后写入redis
        else {
            //确定分页逻辑
            IPage page=new Page(0,6);
            QueryWrapper<Petstore> q = new QueryWrapper<Petstore>()
                    .eq("uid", uid)
                    .ne("performed",0);
            IPage page1 = page(page, q);
            //读取数据
            List<Petstore> list = list(q);
            if (list.size()>0){
                list.forEach(petstore -> {
                    stringRedisTemplate.opsForList().rightPush(key, JSONUtil.toJsonStr(petstore));
                });
            }
            return Result.ok(list);
        }
    }

    @Override
    public Result<?> getStore(Long currentPage) {
        if(null==currentPage||currentPage<=0){
            return Result.fail("参数异常");
        }
        Users user = UserHolder.getUser();
        if(user==null){
            return Result.fail();
        }
        Long uid = user.getId();
        String key="pets:store:"+uid;
        Boolean existKey = stringRedisTemplate.hasKey(key);
        //如果redis中存在这个key，则直接从redis中读取
        if(existKey){
            IPage page=new Page(currentPage,3);
            List<String> list = stringRedisTemplate.opsForList()
                    .range(key, (currentPage - 1) * 3, (currentPage - 1) * 3 + 2);
            List<Petstore>petstores=new ArrayList<>();
            list.forEach(petString->{
                Petstore bean = JSONUtil.toBean(petString, Petstore.class);
                petstores.add(bean);
            });
            page.setRecords(petstores);
            if(page==null){
                return Result.fail();
            }
            return Result.ok(page);
        }
        //如果不存在，则需要去数据库读取，并存入redis
        else {
            IPage page=new Page(currentPage,3);
            QueryWrapper<Petstore> q = new QueryWrapper<Petstore>()
                    .eq("uid", uid)
                    .eq("performed", 0);
            List<Petstore> list = list(q);
            if(list==null||list.size()<=0){
                return Result.fail();
            }
            list.forEach(petstore -> {
                stringRedisTemplate.opsForList().rightPush(key,JSONUtil.toJsonStr(petstore));
            });
            List<String> first = stringRedisTemplate.opsForList().range(key, 0, 2);
            List<Petstore>petstores=new ArrayList<>();
            first.forEach(petString->{
                Petstore bean = JSONUtil.toBean(petString, Petstore.class);
                petstores.add(bean);
            });
            page.setRecords(petstores);
            if(page==null){
                return Result.fail();
            }
            return Result.ok(page);
        }

    }

    @Override
    public Result<?> setBagFirst(Long currentPet) {
        if(null==currentPet||currentPet<=0||currentPet>6){
            return Result.fail("参数异常");
        }
        Users user = UserHolder.getUser();
        if(user==null){
            return Result.fail();
        }
        Long uid = user.getId();
        String key="pets:bag:"+uid;
        Boolean existKey = stringRedisTemplate.hasKey(key);
        //如果存在,则直接更改顺序
        if(existKey){
            String current = stringRedisTemplate.opsForList().index(key, currentPet-1);
            stringRedisTemplate.opsForList().remove(key,0,current);
            stringRedisTemplate.opsForList().leftPush(key,current);
            List<String> list = stringRedisTemplate.opsForList().range(key, 0, -1);
            if(null==list||list.size()<=0||list.size()>6){
                return Result.fail();
            }
            return Result.ok(list);
        }
        //如果不存在，则直接查询数据库
        else {
            QueryWrapper<Petstore> q = new QueryWrapper<Petstore>()
                    .eq("uid", uid)
                    .ne("performed", 0);
            List<Petstore> pets = list(q);
            pets.forEach(pet->{
                if (pet.getPerformed().equals(currentPet)){
                    pet.setPerformed(1);
                } else if (pet.getPerformed().equals(0)) {
                    pet.setPerformed(currentPet.intValue());
                }
            });
            boolean b = saveOrUpdateBatch(pets);
            if(!b){
                return Result.fail("修改失败");
            }
            Boolean delete = stringRedisTemplate.delete(key);
            pets.forEach(petstore -> {
                stringRedisTemplate.opsForList().rightPush(key,JSONUtil.toJsonStr(petstore));
            });
            return Result.ok(pets);
        }
    }

    @Override
    public Result<?> getUserPets() {
        LambdaQueryWrapper<Petstore> select = new QueryWrapper<Petstore>().lambda()
                .select(Petstore.class,i -> {
                    return  //查询id
                            i.getProperty().equals("id")
                                    //查询宠物的编号
                                    || i.getProperty().equals("pid")
                                    //查询用户的id
                                    || i.getProperty().equals("uid")
                                    //查询宠物的获取时间
                                    || i.getProperty().equals("createtime");
                });
        List<Petstore> list = list(select);
        if(null==list||list.size()<=0){
            return Result.fail();
        }
        return Result.ok(list);
    }
}




