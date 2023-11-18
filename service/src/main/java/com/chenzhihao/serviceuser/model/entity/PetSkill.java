package com.chenzhihao.serviceuser.model.entity;

import com.chenzhihao.serviceuser.constant.PowerType;
import com.chenzhihao.serviceuser.constant.SkillType;
import lombok.Data;

@Data
public class PetSkill {
    //技能编号id
    private Integer id;
    //技能名称
    private String name;
    //剩余可使用的次数
    private Integer usedTimes;
    //最大可使用次数
    private Integer maxTimes;
    //技能文本描述
    private String description;
    //技能威力
    private Integer power;
    //技能类型
    private SkillType skillType;
    //威力类型
    private PowerType powerType;
}
