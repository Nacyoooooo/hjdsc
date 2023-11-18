package com.chenzhihao.serviceuser.model.entity;

import com.chenzhihao.serviceuser.model.Petsconfig;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pet {
    //该宠物对应的玩家id
    private Integer uid;
    //该宠物对应的id
    private Integer pid;
    //宠物当前的等级
    private Integer level;
    //宠物的属性
    private Integer attribute;
    //宠物的副属性
    private Integer secondaryAttribute;
    //宠物所拥有的技能
    private Map<Integer,PetSkill> skills=new HashMap<>();
    //宠物的当前血量
    private Integer currentBlood;
    //宠物的最大血量
    private Integer maxBlood;
    //宠物自己的天赋值
    private Integer maxBloodTalent;
    //宠物的速度
    private Integer speed;
    private Integer speedTalent;
    //宠物的物抗
    private Integer physicalResistance;
    private Integer physicalResistanceTalent;
    //宠物的魔抗
    private Integer magicResistance;
    private Integer magicResistanceTalent;
    //宠物的物攻
    private Integer physicalAttack;
    private Integer physicalAttackTalent;
    //宠物的魔攻
    private Integer magicAttack;
    private Integer magicAttackTalent;
    //宠物的配置信息
    private Petsconfig petsconfig;
    //宠物的出战顺序
    private Integer order;

}
