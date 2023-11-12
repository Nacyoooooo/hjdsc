package com.chenzhihao.serviceuser.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.chenzhihao.serviceuser.mapper.SignsMapper;
import com.chenzhihao.serviceuser.service.SignsService;
import com.chenzhihao.serviceutil.model.Signs;
import com.chenzhihao.serviceutil.model.Users;
import com.chenzhihao.serviceutil.result.Result;
import com.chenzhihao.serviceutil.util.UserHolder;
import com.chenzhihao.serviceutil.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.BitFieldSubCommands;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 86159
 * @description 针对表【signs】的数据库操作Service实现
 * @createDate 2023-11-12 23:17:47
 */
@Service
public class SignsServiceImpl extends ServiceImpl<SignsMapper, Signs>
        implements SignsService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private UserUtil util;

    /**
     * 今日签到
     * @return 今日签到
     */
    @Override
    public Result<?> sign() {
        Users user = UserHolder.getUser();
        if(null==user){
            return Result.fail();
        }

        Long id = user.getId();
        LocalDateTime now = LocalDateTime.now();
        String keySuffix = now.format(DateTimeFormatter.ofPattern(":yyyyMM"));
        String key="sign:"+id+keySuffix;
        int dayOfMonth = now.getDayOfMonth();

        Boolean existKey = stringRedisTemplate.hasKey(key);
        //如果是空的，说明还未从数据库读取数据
        if(!existKey){
            QueryWrapper q=new QueryWrapper();
            q.eq("uid",id);
            q.eq("signYear",now.getYear());
            q.eq("signMonth",now.getMonth());
            Signs one = getOne(q);
            //如果没有查出，说明还没有签到过，不用统计
            if(null==one){
                return Result.fail("还未签到过");
            }
            //开始将得到的数据处理,转化为二进制字符串，并转化为数组
            char[] binaryChar = Integer.toBinaryString(one.getSigndata()).toCharArray();
            //如果数组长度为0，说明没有签到，开始签到
            if(binaryChar.length<=0){
                return Result.fail();
            }
            //如果数组长度小于天数，则对数组扩容
            if (binaryChar.length<dayOfMonth){
                binaryChar = Arrays.copyOf(binaryChar, dayOfMonth);
                binaryChar[binaryChar.length-1]='1';//赋值为1，以示签到
            }
            //先写入数据库
            String binaryString = new String(binaryChar);
            int newSignData = Integer.parseInt(binaryString,2);
            one.setSigndata(newSignData);
            boolean b = saveOrUpdate(one);
            if(!b){
                return Result.fail("签到状态更新失败！");
            }
            //再写入redis
            int temp=0;
            for (char c : binaryChar) {
                boolean equals = Character.valueOf(c).equals('1');
                stringRedisTemplate.opsForValue().setBit(key,temp,equals);
                temp++;
            }
            //最后返回签到结果
            return Result.ok();
        }

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
        //TODO 未从数据库读取数据
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
}




