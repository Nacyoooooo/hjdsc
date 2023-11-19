package com.chenzhihao.serviceuser.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chenzhihao.serviceuser.mapper.SkillsMapper;
import com.chenzhihao.serviceuser.model.Petsconfig;
import com.chenzhihao.serviceuser.model.Skills;
import com.chenzhihao.serviceuser.result.Result;
import com.chenzhihao.serviceuser.service.SkillsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.chenzhihao.serviceuser.constant.RedisConstants.PETS_CONFIG_KEY;
import static com.chenzhihao.serviceuser.constant.RedisConstants.PLAY_SKILL_KEY;

/**
* @author 86159
* @description 针对表【skills】的数据库操作Service实现
* @createDate 2023-11-17 15:23:08
*/
@Service
public class SkillsServiceImpl extends ServiceImpl<SkillsMapper, Skills>
    implements SkillsService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private static Integer number=0;
    @Override
    public Result<?> getPetSkills(Integer pageId) {
        //从redis中查找
        String pattern=PLAY_SKILL_KEY+"*";
        Set<String> keys = stringRedisTemplate.keys(pattern);
        //没有从数据库中找
        if(keys==null||keys.isEmpty()||number<=keys.size()){
            number=keys.size();
            List<Skills> list = list();
            list.forEach(l->{
                String key=PLAY_SKILL_KEY+l.getId();
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
        List<Skills>Skills=new ArrayList<>();
        keys.forEach(key->{
            Map<Object, Object> entries = stringRedisTemplate.opsForHash().entries(key);
            Skills petsconfig = BeanUtil.fillBeanWithMap(entries, new Skills(), false);
            Skills.add(petsconfig);
        });
        return Result.ok(Skills);
    }
}




