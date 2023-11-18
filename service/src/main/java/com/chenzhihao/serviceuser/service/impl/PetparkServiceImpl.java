package com.chenzhihao.serviceuser.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.chenzhihao.serviceuser.mapper.CapturerecordMapper;
import com.chenzhihao.serviceuser.mapper.PetparkMapper;
import com.chenzhihao.serviceuser.mapper.PetsconfigMapper;
import com.chenzhihao.serviceuser.model.Capturerecord;
import com.chenzhihao.serviceuser.model.Petpark;
import com.chenzhihao.serviceuser.model.Petsconfig;
import com.chenzhihao.serviceuser.model.Users;
import com.chenzhihao.serviceuser.result.Result;
import com.chenzhihao.serviceuser.service.PetparkService;

import com.chenzhihao.serviceuser.util.RedisIdWorker;
import com.chenzhihao.serviceuser.util.UserHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.stream.*;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;


/**
 * @author 86159
 * @description 针对表【petpark】的数据库操作Service实现
 * @createDate 2023-11-14 16:49:22
 */
@Service
@Slf4j
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
    private static final ExecutorService SECKILL_ORDER_EXECUTOR= Executors.newSingleThreadExecutor();
    @PostConstruct
    private void init(){
        SECKILL_ORDER_EXECUTOR.submit(new ParkOrderHandler());
    }
    private class ParkOrderHandler implements Runnable{
        @Override
        public void run() {
            String queueName="stream.orders";
            while (true) {

                try {
                    // 1.获取消息队列中的订单信息 XREADGROUP GROUP g1 c1 COUNT 1 BLOCK 2000 STREAMS s1 >
                    List<MapRecord<String, Object, Object>> list = stringRedisTemplate.opsForStream().read(
                            Consumer.from("g1", "c1"),
                            StreamReadOptions.empty().count(1).block(Duration.ofSeconds(2)),
                            StreamOffset.create(queueName, ReadOffset.lastConsumed())
                    );
                    // 2.判断订单信息是否为空
                    if (list == null || list.isEmpty()) {
                        // 如果为null，说明没有消息，继续下一次循环
                        continue;
                    }
                    // 解析数据
                    MapRecord<String, Object, Object> record = list.get(0);
                    Map<Object, Object> value = record.getValue();
                    Capturerecord capturerecord = BeanUtil.mapToBean(value, Capturerecord.class, true,
                            CopyOptions.create()
                                    .setIgnoreNullValue(true)
                                    .setIgnoreError(true)
                                    .setFieldValueEditor((fieldName, fieldValue) -> {
                                                if (fieldValue == null) {
                                                    return null;
                                                }
                                                return fieldValue.toString();
                                            }
                                    ));
                    capturerecord.setCreatetime(new Date());
                    capturerecord.setUpdatetime(new Date());
                    int insert = capturerecordMapper.insert(capturerecord);
                    if(insert<=0){

                    }
                    // 4.确认消息 XACK
                    stringRedisTemplate.opsForStream().acknowledge("s1", "g1", record.getId());
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
        if(catched==null){
            return Result.fail();
        }
        return Result.ok(catched);
    }

    @Override
    public Result<?> getPet(Long parkid) {
        //先判断redis中是否存在
        String key="caputered:stock:"+parkid;
        String key2="caputered:order:"+parkid;
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
        Long userId = UserHolder.getUser().getId();
        Long result = stringRedisTemplate.execute(
                SECKILL_SCRIPT,
                Collections.emptyList(),
                parkid.toString(), userId.toString(),String.valueOf(order)
        );
        if (null==result){
            return Result.fail("异常");
        }
        int r = result.intValue();
        if(r!=0){
            return Result.fail(r==1?"宠物数量不足":"不能重复捕捉");
        }

        Capturerecord capturerecord = new Capturerecord();
        capturerecord.setCid(parkid.intValue());
        capturerecord.setUid(userId.intValue());
        capturerecord.setId((int) order);
        capturerecord.setCreatetime(new Date());
        capturerecord.setUpdatetime(new Date());
        try {
            capturerecordsTasks.put(capturerecord);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return Result.ok();
        /*
        Users user = UserHolder.getUser();
        if(null==user){
            return Result.fail();
        }
        QueryWrapper<Petpark> park = new QueryWrapper<Petpark>()
                //该数据是否存在
                .eq("id", parkid)
                //是否可被捕捉
                .eq("catched", 1)
                //数量是否大于0
                .gt("count", 0);
        Petpark one = getOne(park);
        if(one==null){
            return Result.fail();
        }

        SimpleRedisLock lock = new SimpleRedisLock("captured:" + user.getId(), stringRedisTemplate);
        //获取代理对象（事务）
        boolean isLock = lock.tryLock(1200);
        if(!isLock){
            //获取锁失败,返回错误或重试
            return Result.fail("不允许重复下单");
        }
        try {
            PetparkService proxy = (PetparkService)AopContext.currentProxy();
            return proxy.createCaptureRecord(parkid);
        } finally {
            //释放锁
            lock.unlock();
        }*/

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
}



