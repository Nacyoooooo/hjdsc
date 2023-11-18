package com.chenzhihao.serviceuser.util;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chenzhihao.serviceuser.constant.PlayerType;
import com.chenzhihao.serviceuser.dto.PlayData;
import com.chenzhihao.serviceuser.mapper.PetsconfigMapper;
import com.chenzhihao.serviceuser.mapper.PetstoreMapper;
import com.chenzhihao.serviceuser.mapper.SkillsMapper;
import com.chenzhihao.serviceuser.model.Petsconfig;
import com.chenzhihao.serviceuser.model.Petstore;
import com.chenzhihao.serviceuser.model.Skills;
import com.chenzhihao.serviceuser.model.entity.Pet;
import com.chenzhihao.serviceuser.model.entity.PetSkill;
import com.chenzhihao.serviceuser.model.entity.Player;
import com.chenzhihao.serviceuser.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.chenzhihao.serviceuser.constant.PowerType.MAGIC;
import static com.chenzhihao.serviceuser.constant.PowerType.PHYSICAL;
import static com.chenzhihao.serviceuser.constant.RedisConstants.*;
import static com.chenzhihao.serviceuser.constant.ResistType.COMMON;
import static com.chenzhihao.serviceuser.constant.SkillType.CHANGE;
import static com.chenzhihao.serviceuser.constant.SkillType.POWER;

@Component
public class PetUtil {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private PetsconfigMapper petsconfigMapper;
    @Autowired
    private PetstoreMapper petstoreMapper;
    @Autowired
    private SkillsMapper skillsMapper;

    public void getPet(List<Pet> pets,List<Petstore> myPets){
        if(null==myPets||myPets.size()<=0){
            return;
        }
        myPets.forEach(pet->{
            //获取该宠物的id
            Integer pid = pet.getPid();
            //确认宠物的配置是否已经挂载到redis中
            String pkey=PETS_CONFIG_KEY+pid;
            Boolean pExist = stringRedisTemplate.hasKey(pkey);
            //如果这个宠物的配置没有挂载到redis中，则挂载
            if(!pExist){
                List<Petsconfig> config = petsconfigMapper.selectList(new QueryWrapper<Petsconfig>()
                        //通过该宠物的pid，获取对应的配置
                        .eq("id", pet.getPid()));
                //如果数据库中不存在对应的配置信息，则说明该宠物的信息是非法的
                if(config==null||config.size()<=0){
                    return;
                }
                //将配置数据挂载到redis中
                Map<String, Object> configMap = BeanUtil.beanToMap(config.get(0), new HashMap<>(),
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
                stringRedisTemplate.opsForHash().putAll(pkey,configMap);
            }
            //如果已经挂载，则从redis中读取数据
            Map<Object, Object> configMaps = stringRedisTemplate.opsForHash().entries(pkey);
            Petsconfig petsconfig = BeanUtil.fillBeanWithMap(configMaps, new Petsconfig(), false);
            if(petsconfig==null){
                return;
            }
            //创建对象
            Pet petNew=new Pet();
            //设置配置
            setPetConfig(petNew,petsconfig);
            //设置宠物的天赋值
            setPetTalent(petNew,pet);
            //TODO 初始化pet的数值
            //根据配置初始化技能
            getSkill(petNew,pet);
            //初始化属性
            init(petNew);
            //添加到表中
            pets.add(petNew);
        });
    }
    private void setSkill(PetSkill petSkill,Skills skills){
        petSkill.setId(skills.getId());
        petSkill.setDescription(skills.getDescription());
        petSkill.setName(skills.getName());
        petSkill.setPower(skills.getPower());
        petSkill.setUsedTimes(skills.getUsetimes());
        petSkill.setMaxTimes(skills.getUsetimes());
        if(skills.getType()==1){
            petSkill.setSkillType(POWER);
        }else {
            petSkill.setSkillType(CHANGE);
        }
        if (skills.getPowertype()==1){
            petSkill.setPowerType(PHYSICAL);
        }else {
            petSkill.setPowerType(MAGIC);
        }
    }
    private void setPetTalent(Pet pet,Petstore petstore){
        pet.setMaxBloodTalent(petstore.getHealthpoint());
        pet.setSpeedTalent(petstore.getSpeed());
        pet.setPhysicalAttackTalent(petstore.getPhysicaldamagepoint());
        pet.setMagicAttackTalent(petstore.getMagicaldamagepoin());
        pet.setPhysicalResistanceTalent(petstore.getPhysicaldefence());
        pet.setMagicResistanceTalent(petstore.getMagicaldefence());
        pet.setLevel(petstore.getLevel());
        pet.setUid(petstore.getUid());
        pet.setPid(petstore.getPid());
        pet.setOrder(petstore.getPerformed());
    }
    public void setPetConfig(Pet pet, Petsconfig petsconfig){
        pet.setAttribute(petsconfig.getAttribute());
        pet.setSecondaryAttribute(petsconfig.getSecondaryattribute());
        pet.setPetsconfig(petsconfig);
    }
    private void getSkill(Pet pet,Petstore petstore){
        Map<Integer, PetSkill> skills = pet.getSkills();
        skills.put(1,getSkillConfig(petstore.getSkillone()));
        skills.put(2,getSkillConfig(petstore.getSkilltwo()));
        skills.put(3,getSkillConfig(petstore.getSkillthree()));
        skills.put(4,getSkillConfig(petstore.getSkillfour()));
    }
    private PetSkill getSkillConfig(Integer skillNumber){
        if(skillNumber!=null){
            String skillKey=PLAY_SKILL_KEY+skillNumber;
            //如果技能配置没有挂载到redis，则从数据库中读取数据，并挂载
            Boolean skillExist = stringRedisTemplate.hasKey(skillKey);
            if(!skillExist){
                List<Skills> skillss = skillsMapper.selectList(new QueryWrapper<Skills>()
                        //通过技能的id信息获取配置
                        .eq("id", skillNumber));
                if(skillss==null||skillss.size()<=0){
                    return null;
                }
                Map<String, Object> skillMap = BeanUtil.beanToMap(skillss.get(0), new HashMap<>(),
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
                stringRedisTemplate.opsForHash().putAll(skillKey,skillMap);
            }
            //如果有，则从redis中读取数据，并保存到Pet对象中，并挂载到redis中
            Map<Object, Object> skillsMap = stringRedisTemplate.opsForHash().entries(skillKey);
            Skills skills = BeanUtil.fillBeanWithMap(skillsMap, new Skills(), false);
            PetSkill petSkill=new PetSkill();
            setSkill(petSkill,skills);
            return petSkill;
        }
        return null;
    }
    private void init(Pet pet){
        Petsconfig petsconfig = pet.getPetsconfig();
        if(petsconfig==null){
            return;
        }
        //血量
        Integer blood = MathUtil.AttributeCalculation(petsconfig.getHealthpoint(), pet.getMaxBloodTalent(), pet.getLevel());
        pet.setMaxBlood(blood);
        pet.setCurrentBlood(blood);
        //速度
        Integer speed = MathUtil.AttributeCalculation(petsconfig.getSpeed(), pet.getSpeedTalent(), pet.getLevel());
        pet.setSpeed(speed);
        //物攻
        Integer PhysicalAttack = MathUtil.AttributeCalculation(petsconfig.getPhysicaldamagepoint(), pet.getPhysicalAttackTalent(), pet.getLevel());
        pet.setPhysicalAttack(PhysicalAttack);
        //魔攻
        Integer magicAttack = MathUtil.AttributeCalculation(petsconfig.getMagicaldamagepoin(), pet.getMagicAttackTalent(), pet.getLevel());
        pet.setMagicAttack(magicAttack);
        //物抗
        Integer PhysicalResistance = MathUtil.AttributeCalculation(petsconfig.getPhysicaldefence(), pet.getPhysicalResistanceTalent(), pet.getLevel());
        pet.setPhysicalResistance(PhysicalResistance);
        //魔抗
        Integer MagicResistance = MathUtil.AttributeCalculation(petsconfig.getMagicaldefence(), pet.getMagicResistanceTalent(), pet.getLevel());
        pet.setMagicResistance(MagicResistance);
    }

    public PlayData setPetDto(Pet pet){
        if(pet==null){
            return null;
        }
        PlayData playData = new PlayData();
        playData.setName(pet.getPetsconfig().getName());
        playData.setCurrentBlood(pet.getCurrentBlood());
        playData.setMaxBlood(pet.getMaxBlood());
        playData.setLevel(pet.getLevel());
        playData.setSkills(pet.getSkills());
        playData.setOrder(pet.getOrder());
        return playData;
    }
    public boolean useSkill(Pet pet,Pet enemypet,Integer skillid,PlayerType playerType){

        if(pet==null||enemypet==null||skillid==null){
            return false;
        }
        Integer attack;
        Integer defence;
        Map<Integer, PetSkill> skills = pet.getSkills();
        if(skills==null){
            return false;
        }
        //如果是机器人，则自动判断使用哪个技能，左优先
        Integer orderId=skillid;
        switch (playerType){
            case PLAYER:break;
            case ROBOT:
                for (Map.Entry<Integer, PetSkill> entry : skills.entrySet()) {
                    PetSkill value = entry.getValue();
                    if(value==null){
                        continue;
                    }
                    if(value.getUsedTimes()>=0){
                        skillid=entry.getKey();
                        break;
                    }
                }
                break;
        }
        PetSkill petSkill = skills.get(skillid.intValue());
        //如果是物理伤害
        if(petSkill.getPowerType()==PHYSICAL){
            attack=pet.getPhysicalAttack();
            defence=enemypet.getPhysicalResistance();
        }
        else {
            attack=pet.getMagicAttack();
            defence=enemypet.getMagicResistance();
        }

        Integer attack1 = MathUtil.attack(pet.getLevel(), petSkill.getPower(), COMMON, attack, defence);
        //威力技能，则对方扣血
        if(petSkill.getSkillType()== POWER){
            Integer currentBlood = enemypet.getCurrentBlood();
            if(currentBlood==null){
                return false;
            }
            currentBlood=currentBlood-attack1;
            currentBlood=currentBlood>=0?currentBlood:0;
            enemypet.setCurrentBlood(currentBlood);
        }
        //变化技能，则己方回血
        else if (petSkill.getSkillType()== CHANGE) {
            Integer currentBlood = pet.getCurrentBlood();
            if(currentBlood==null){
                return false;
            }
            currentBlood=currentBlood+attack1;
            Integer maxBlood = pet.getMaxBlood();
            if(maxBlood==null){
                return false;
            }
            currentBlood=currentBlood<=maxBlood?currentBlood:maxBlood;
            pet.setCurrentBlood(currentBlood);
        }
        //使用过的技能要扣减使用次数
        Integer usedTimes = petSkill.getUsedTimes();
        if(usedTimes==null){
            return false;
        }
        usedTimes--;
        petSkill.setUsedTimes(usedTimes);
        skills.put(skillid,petSkill);
        pet.setSkills(skills);
        return true;
    }
    public Player getPlayer(Integer userId){
        String myKey=PLAY_STATUS_KEY+userId;
        Boolean exist = stringRedisTemplate.hasKey(myKey);
        if(!exist){
            return null;
        }
        String statusJson = stringRedisTemplate.opsForValue().get(myKey);
        Player myself = JSONUtil.toBean(statusJson, Player.class);
        if(myself==null){
            return null;
        }
        return myself;
    }

}
