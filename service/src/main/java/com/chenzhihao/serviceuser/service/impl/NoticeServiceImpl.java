package com.chenzhihao.serviceuser.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chenzhihao.serviceuser.model.Notice;
import com.chenzhihao.serviceuser.model.Users;
import com.chenzhihao.serviceuser.result.Result;
import com.chenzhihao.serviceuser.service.NoticeService;
import com.chenzhihao.serviceuser.mapper.NoticeMapper;
import com.chenzhihao.serviceuser.util.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.chenzhihao.serviceuser.constant.RedisConstants.NOTICE_KEY;
import static com.chenzhihao.serviceuser.constant.UserCode.ADMIN;

/**
* @author 86159
* @description 针对表【notice】的数据库操作Service实现
* @createDate 2023-11-19 13:56:36
*/
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice>
    implements NoticeService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Result<?> getComments() {
        //先从redis中找
        Users user = UserHolder.getUser();
        if(user==null){
            return Result.fail();
        }
        Integer code=-1;
        if(user.getAuthority()==ADMIN){
            code=1;
        }
        String pattern=NOTICE_KEY+"*";
        Set<String> keys = stringRedisTemplate.keys(pattern);
        if(keys==null||keys.size()<=0){
            List<Notice> list = list();
            if(list==null||list.isEmpty()){
                return Result.fail();
            }
            list.forEach(l->{
                stringRedisTemplate.opsForValue().set(NOTICE_KEY+l.getId(), JSONUtil.toJsonStr(l));
            });
        }
        //找不到，就从数据库拿
        keys = stringRedisTemplate.keys(pattern);
        if(code==1){
            List<Notice>notices=new ArrayList<>();
            keys.forEach(key->{
                String json = stringRedisTemplate.opsForValue().get(key);
                if(json!=null){
                    Notice notice = JSONUtil.toBean(json, Notice.class);
                    notices.add(notice);
                }
            });
            return Result.ok(notices);
        }
        else {
            List<Notice>notices=new ArrayList<>();
            keys.forEach(key->{
                String json = stringRedisTemplate.opsForValue().get(key);
                if(json!=null){
                    Notice notice = JSONUtil.toBean(json, Notice.class);
                    notice.setId(null);
                    notice.setUid(null);
                    notice.setUpdatetime(null);
                    notices.add(notice);
                }
            });
            return Result.ok(notices);
        }

    }
}




