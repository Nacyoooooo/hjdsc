package com.chenzhihao.serviceuser.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chenzhihao.serviceuser.mapper.PetstoreMapper;
import com.chenzhihao.serviceuser.model.Mainpet;
import com.chenzhihao.serviceuser.model.Petstore;
import com.chenzhihao.serviceuser.model.Users;
import com.chenzhihao.serviceuser.result.Result;
import com.chenzhihao.serviceuser.service.MainpetService;
import com.chenzhihao.serviceuser.mapper.MainpetMapper;
import com.chenzhihao.serviceuser.util.ILock;
import com.chenzhihao.serviceuser.util.SimpleRedisLock;
import com.chenzhihao.serviceuser.util.UserHolder;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.chenzhihao.serviceuser.constant.RedisConstants.PETS_MAINPET_KEY;

/**
* @author 86159
* @description 针对表【mainpet】的数据库操作Service实现
* @createDate 2023-11-19 22:55:04
*/
@Service
@Transactional(rollbackFor = {Exception.class})
public class MainpetServiceImpl extends ServiceImpl<MainpetMapper, Mainpet>
    implements MainpetService{
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private PetstoreMapper petstoreMapper;
    private static final ExecutorService MAINPET_EXECUTOR= Executors.newSingleThreadExecutor();
    @PostConstruct
    private void init(){
        MAINPET_EXECUTOR.submit(()->{
            while (true){
                ILock iLock=new SimpleRedisLock("lock:mainpet",stringRedisTemplate);
                boolean isLock = iLock.tryLock(1);
                if(!isLock){
                    continue;
                }
                try {
                    List<Mainpet> list = list(new QueryWrapper<Mainpet>().eq("status", 1));
                    if(list==null||list.isEmpty()){

                    }else {
                        list.forEach(l->{
                            if(l.getStatus()!=null&&l.getStatus().equals(1)){
                            Petstore petstore=new Petstore();
                            petstore.setPid(l.getPid());
                            petstore.setUid(l.getUid());
                            petstore.setExperience(0);
                            petstore.setLevel(10);
                            petstore.init();
                            int insert = petstoreMapper.insert(petstore);
                            if(insert>0){
                                l.setStatus(2);
                                saveOrUpdate(l);
                            }
                        }
                        });
                    }
                }finally {
                    iLock.unlock();
                }

            }
        });
    }
    @Transactional(rollbackFor = {Exception.class})
    public void updateMainpet(Mainpet l){

    }
    @Override
    public Result<?> getMainPets(Integer pid) {
        Users user = UserHolder.getUser();
        if(user==null) {
            return Result.fail();
        }
        //redis
        String key=PETS_MAINPET_KEY;
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
        //database
        ILock lock=new SimpleRedisLock("lock:mainpet:"+user.getId(),stringRedisTemplate);
        boolean isLock = lock.tryLock(60);
        if(!isLock){
            return Result.fail();
        }else {
            try {
                Mainpet mainpet=new Mainpet();
                mainpet.setUid(user.getId());
                mainpet.setPid(pid);
                mainpet.setCreatetime(new Date());
                mainpet.setUpdatetime(new Date());
                mainpet.setStatus(1);
                saveOrUpdate(mainpet);
                stringRedisTemplate.opsForSet().add(key,user.getId().toString());
            }finally {
                lock.unlock();
            }
        }
        return Result.ok();
    }
}




