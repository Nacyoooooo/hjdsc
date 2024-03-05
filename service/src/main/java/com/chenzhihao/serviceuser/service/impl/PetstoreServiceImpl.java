package com.chenzhihao.serviceuser.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.chenzhihao.serviceuser.mapper.PetstoreMapper;
import com.chenzhihao.serviceuser.model.Money;
import com.chenzhihao.serviceuser.model.Petstore;
import com.chenzhihao.serviceuser.model.Users;
import com.chenzhihao.serviceuser.model.entity.Pet;
import com.chenzhihao.serviceuser.result.Result;
import com.chenzhihao.serviceuser.service.MoneyService;
import com.chenzhihao.serviceuser.service.PetstoreService;
import com.chenzhihao.serviceuser.util.ILock;
import com.chenzhihao.serviceuser.util.PetUtil;
import com.chenzhihao.serviceuser.util.SimpleRedisLock;
import com.chenzhihao.serviceuser.util.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.chenzhihao.serviceuser.constant.RedisConstants.PETS_BAG_KEY;
import static com.chenzhihao.serviceuser.constant.RedisConstants.USER_MONEY_KEY;


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
    @Autowired
    private PetstoreMapper petstoreMapper;
    @Autowired
    private PetUtil petUtil;
    @Autowired
    private MoneyService moneyService;


    @Override
    public Result<?> getBags() {
        Users user = UserHolder.getUser();
        if(user==null){
            return Result.fail();
        }
        Integer uid = user.getId();
        String key=PETS_BAG_KEY+uid;
        Boolean existKey = stringRedisTemplate.hasKey(key);
        //如果redis中存在这个key，则直接从redis中读取
        if(existKey){
            List<String> petslist = stringRedisTemplate.opsForList().range(key, 0, -1);
            if(petslist==null||petslist.size()<=0){
                return Result.fail();
            }
            List<Pet>petstores=new ArrayList<>();
            petslist.forEach(petString->{
                Pet pet = JSONUtil.toBean(petString, Pet.class);
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
            List<Petstore> myPets = petstoreMapper.selectList(new QueryWrapper<Petstore>()
                    //玩家id
                    .eq("id", user.getId())
                    //在背包中的宠物
                    .gt("performed", 0));
            //如果玩家背包中没有宠物，则战斗结束
            if(null==myPets||myPets.size()<=0){
                return Result.fail();
            }
            List<Pet> pets=new ArrayList<>();
            //获取自己背包中宠物的信息，挂载到redis
            petUtil.getPet(pets,myPets);
            if(pets==null||pets.size()<=0){
                return Result.fail();
            }
            pets.forEach(pet -> {
                stringRedisTemplate.opsForList().rightPush(key,JSONUtil.toJsonStr(pet));
            });
            return Result.ok(pets);
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
        Integer uid = user.getId();
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
        Integer uid = user.getId();
        String key=PETS_BAG_KEY+uid;
        Boolean existKey = stringRedisTemplate.hasKey(key);
        if(!existKey){
            List<Petstore> list = list(new QueryWrapper<Petstore>()
                    .eq("uid", user.getId())
                    .ne("performed", 0));
        }
        List<String> list = stringRedisTemplate.opsForList().range(key, 0, -1);
        if(list==null||list.isEmpty()){
            return Result.fail();
        }
        //如果存在,则直接更改顺序
        return Result.ok();
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

    @Override
    public Result<?> levelup(Integer pid) {
        //从redis中找自己的宠物
        Users user = UserHolder.getUser();
        if(user==null){
            return Result.fail();
        }
        //没有要挂载
        String key=PETS_BAG_KEY+user.getId();
        Boolean exist = stringRedisTemplate.hasKey(key);
        if(!exist){
            petUtil.getPets(user.getId());
        }
        List<String> list = stringRedisTemplate.opsForList().range(key, 0, -1);
        Result result=Result.ok();
        list.forEach(l->{
            Pet bean = JSONUtil.toBean(l, Pet.class);
            if(bean.getId()==pid){
                //查看等级，等级高于100则不可升级
                Integer level = bean.getLevel();
                if(level>=100){
                    return;
                }
                String moneykey=USER_MONEY_KEY+user.getId();
                Boolean moneyExist = stringRedisTemplate.hasKey(moneykey);
                if(!moneyExist){
                    moneyService.getCount();
                }
                String s = stringRedisTemplate.opsForValue().get(moneykey);
                Money money = JSONUtil.toBean(s, Money.class);
                //计算所需要消耗的金币
                //每次升级需要100
                if(money.getCount()>=100){
                    Integer count = money.getCount();
                    count-=100;
                    ILock iLock=new SimpleRedisLock("lock:levelup:"+user.getId(),stringRedisTemplate);
                    boolean isLock = iLock.tryLock(1);
                    if(!isLock){
                        return;
                    }
                    try{
                        moneyService.update()
                                .setSql("count=count-100")
                                .eq("uid",user.getId());
                        stringRedisTemplate.opsForValue().set(moneykey,money.toString());
                        Petstore petstore = petstoreMapper.selectOne(new QueryWrapper<Petstore>().eq("id", bean.getId()));
                        if(petstore==null){
                            return;
                        }
                        level++;
                        petstore.setLevel(level);
                        petstoreMapper.updateById(petstore);
                        petUtil.getPets(user.getId());
                        result.setMsg("升级成功");
                    }finally {
                        iLock.unlock();
                    }
                }
            }
        });

        return result;
    }
}




