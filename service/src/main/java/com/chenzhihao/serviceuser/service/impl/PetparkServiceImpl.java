package com.chenzhihao.serviceuser.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.chenzhihao.serviceuser.mapper.CapturerecordMapper;
import com.chenzhihao.serviceuser.mapper.PetparkMapper;
import com.chenzhihao.serviceuser.mapper.PetsconfigMapper;
import com.chenzhihao.serviceuser.model.*;
import com.chenzhihao.serviceuser.result.Result;
import com.chenzhihao.serviceuser.service.PetparkService;

import com.chenzhihao.serviceuser.util.ILock;
import com.chenzhihao.serviceuser.util.RedisIdWorker;
import com.chenzhihao.serviceuser.util.SimpleRedisLock;
import com.chenzhihao.serviceuser.util.UserHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.stream.*;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.*;

import static com.chenzhihao.serviceuser.constant.RedisConstants.*;
import static com.chenzhihao.serviceuser.constant.RedisConstants.PLAY_SKILL_KEY;


/**
 * @author 86159
 * @description 针对表【petpark】的数据库操作Service实现
 * @createDate 2023-11-14 16:49:22
 */
@Service
@Slf4j
@EnableTransactionManagement
public class PetparkServiceImpl extends ServiceImpl<PetparkMapper, Petpark>
        implements PetparkService {

    @Autowired
    private PetsconfigMapper petsconfigMapper;
    @Autowired
    private CapturerecordMapper capturerecordMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisIdWorker redisIdWorker;
    private BlockingQueue<Capturerecord> capturerecordsTasks=new ArrayBlockingQueue(1024*1024);
    @Deprecated
    private static final ExecutorService SECKILL_ORDER_EXECUTOR= Executors.newSingleThreadExecutor();
    @PostConstruct
    private void init(){
        SECKILL_ORDER_EXECUTOR.submit(new ParkOrderHandler());
    }
    @Deprecated
    private class ParkOrderHandler implements Runnable{

        @Override
        public void run() {
            while (true) {

                try {
                    //从阻塞队列中拿数据
                    Capturerecord capturerecord = capturerecordsTasks.take();
                    if(capturerecord==null){
                        continue;
                    }
                    //将数据写入数据库
                    PetparkServiceImpl petparkService = (PetparkServiceImpl) AopContext.currentProxy();
                    petparkService.createCaptureRecord(capturerecord);
                } catch (Exception e) {
                    log.error("处理订单异常", e);
                }
            }
        }
    }
    private static final DefaultRedisScript<Long> SECKILL_SCRIPT;
    static {
        SECKILL_SCRIPT=new DefaultRedisScript<>();
        SECKILL_SCRIPT.setLocation(new ClassPathResource("seckill.lua"));
        SECKILL_SCRIPT.setResultType(Long.class);
    }

    @Override
    public Result<?> setPark(Long id, Long count) {
        //检验数量是否大于0
        // 小于0则失败
        if(id==null||count==null||count<0){
            return Result.fail();
        }
        //校验被添加的宠物是否存在
        ;
        Petsconfig petsconfig = petsconfigMapper.selectOne(new QueryWrapper<Petsconfig>()
                .eq("id", id));
        //不存在就失败
        if(null==petsconfig){
            return Result.fail();
        }
        //存在就添加
        Petpark petpark=new Petpark();
        petpark.setId((int)redisIdWorker.nextId("park"));
        petpark.setPid(id.intValue());
        petpark.setCount(count.intValue());
        petpark.setCreatetime(new Date());
        petpark.setUpdatetime(new Date());
        boolean save = save(petpark);
        if(!save){
            return Result.fail();
        }
        stringRedisTemplate.opsForValue().set("caputered:stock:"+petpark.getId(),petpark.getCount().toString());
        return Result.ok();
    }

    @Override
    public Result<?> getCaputedPets() {
        List<Petpark> catched = list(new QueryWrapper<Petpark>()
                .eq("catched", 1)
                .gt("count",0));
        if(catched==null||catched.isEmpty()){
            return Result.fail();
        }
        catched.forEach(c->{
            String key=PET_PARK_KEY+c.getId();
            Map<String, Object> stringObjectMap = BeanUtil.beanToMap(c, new HashMap<>(),
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
        return Result.ok(catched);
    }

    @Override
    public Result<?> getPet(Long parkid) throws InterruptedException {
        //先判断redis中是否存在
        String key=PET_PARK_CAPTURED+parkid;
        String key2=PET_PARK_ORDER+parkid;
        Boolean exist = stringRedisTemplate.hasKey(key);
        Boolean exist2 = stringRedisTemplate.hasKey(key2);
        //不存在则从数据库中读取
        if(!exist||!exist2){
            Petpark one = getOne(new QueryWrapper<Petpark>()
                    .eq("id", parkid));
            if(one==null){
                return Result.fail();
            }
            stringRedisTemplate.opsForValue().set(key,one.getCount().toString());
            List<Capturerecord> users = capturerecordMapper.selectList(new QueryWrapper<Capturerecord>()
                    .eq("cid", one.getId()));
            if(users!=null){
                users.forEach(user->{
                    stringRedisTemplate.opsForSet().add(key2,user.getUid().toString());
                });
            }
        }
        long order = redisIdWorker.nextId("order");
        Integer userId = UserHolder.getUser().getId();
        Long result = stringRedisTemplate.execute(
                SECKILL_SCRIPT,
                Collections.emptyList(),
                parkid.toString(), userId.toString(),String.valueOf(order),String.valueOf(10)
        );
        Capturerecord capturerecord=new Capturerecord();
        capturerecord.setCid(parkid.intValue());
        capturerecord.setPid(parkid.intValue());
        capturerecord.setUid(userId);
        capturerecord.setCreatetime(new Date());
        capturerecord.setUpdatetime(new Date());
        capturerecord.setStatus(1);
        capturerecord.setLevel(10);
        capturerecordsTasks.put(capturerecord);
        if (null==result){
            return Result.fail("异常");
        }
        int r = result.intValue();
        if(r!=0){
            return Result.fail(r==1?"宠物数量不足":"不能重复捕捉");
        }
        return Result.ok();
    }

    @Transactional
    @Deprecated
    public Result createCaptureRecord(Long parkid){
        Users user = UserHolder.getUser();
        Wrapper check = new QueryWrapper<>()
                .eq("uid", user.getId().longValue())
                .eq("pid", parkid);
        Long l = capturerecordMapper.selectCount(check);
        if(l>0){
            return Result.fail();
        }
        boolean success = update()
                .setSql("count=count-1")
                .eq("id", parkid)
                .gt("count", 0).update();
        if(!success){
            return Result.fail();
        }
        Capturerecord capturerecord = new Capturerecord();
        capturerecord.setCid(parkid.intValue());
        capturerecord.setPid(parkid.intValue());
        capturerecord.setUid(user.getId().intValue());
        capturerecord.setCreatetime(new Date());
        capturerecord.setUpdatetime(new Date());
        int insert = capturerecordMapper.insert(capturerecord);
        if(insert<=0){
            return Result.fail();
        }
        return Result.ok();
    }
    @Transactional(rollbackFor = {Exception.class})
    public void createCaptureRecord(Capturerecord capturerecord) throws Exception {
        Wrapper check = new QueryWrapper<>()
                .eq("uid", capturerecord.getUid())
                .eq("pid", capturerecord.getCid());
        Long l = capturerecordMapper.selectCount(check);
        if(l>0){
            throw new Exception();
        }
        boolean success = update()
                .setSql("count=count-1")
                .eq("id", capturerecord.getCid())
                .gt("count", 0).update();
        if(!success){
            throw new Exception();
        }
        int insert = capturerecordMapper.insert(capturerecord);
        if(insert<=0) {
            throw new Exception();
        }
        String key=PET_PARK_KEY+capturerecord.getCid();
        Boolean exist = stringRedisTemplate.hasKey(key);
        if(!exist){
            throw new Exception();
        }
        Map<Object, Object> entries = stringRedisTemplate.opsForHash().entries(key);
        Petpark petpark = BeanUtil.fillBeanWithMap(entries, new Petpark(), false);
        if(petpark==null){
            throw new Exception();
        }
        String pKey=PET_PARK_KEY+petpark.getId();
        Map<String, Object> stringObjectMap = BeanUtil.beanToMap(petpark, new HashMap<>(),
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
        stringRedisTemplate.opsForHash().putAll(pKey,stringObjectMap);
    }
    private static Integer number=0;
    @Override
    public Result<?> getPetPark(Integer pageId) {
        //从redis中查找
        String pattern=PET_PARK_KEY+"*";
        Set<String> keys = stringRedisTemplate.keys(pattern);
        //没有从数据库中找
        if(keys==null||keys.isEmpty()||number<=keys.size()){
            number=keys.size();
            List<Petpark> list = list();
            list.forEach(l->{
                String key=PET_PARK_KEY+l.getId();
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
            keys=stringRedisTemplate.keys(pattern);
        }
        //挂载到redis上
        List<Petpark>Skills=new ArrayList<>();
        keys.forEach(key->{
            Map<Object, Object> entries = stringRedisTemplate.opsForHash().entries(key);
            Petpark Petpark = BeanUtil.fillBeanWithMap(entries, new Petpark(), false);
            Skills.add(Petpark);
        });
        return Result.ok(Skills);
    }
}