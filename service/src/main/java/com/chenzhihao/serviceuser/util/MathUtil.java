package com.chenzhihao.serviceuser.util;

import com.chenzhihao.serviceuser.constant.PowerType;
import com.chenzhihao.serviceuser.constant.ResistType;

import static com.chenzhihao.serviceuser.constant.ResistType.COMMON;

public class MathUtil {
    public static Integer AttributeCalculation(Integer RaceValue,Integer Talent,Integer Level){
        if(RaceValue==null||Talent==null||Level==null){
            return  0;
        }
        double raceValue = RaceValue.doubleValue();
        double talent = Talent.doubleValue();
        double level = Level.doubleValue();
        double math=(raceValue*2+talent)*level/100+level+10;
        return Double.valueOf(math).intValue();
    }

    public static Integer attack(
            //攻击者等级
            Integer Level,
            //技能威力
            Integer Power,
            //克制关系
            ResistType resistType,
            //攻击力
            Integer Attack,
            //对手防御力
            Integer Defence){
        if(Level==null||Power==null||Attack==null){
            return 0;
        }
        double level = Level.doubleValue();
        double power = Power.doubleValue();
        double attack = Attack.doubleValue();
        double defence = Defence.doubleValue();
        double resistCoefficient=0;
        switch (resistType){
            case COMMON:resistCoefficient=1;break;
            case RESIST:resistCoefficient=0.5;break;
            case RESTRAIN:resistCoefficient=2;break;
        }
        double math=((level*0.4+2)*power*(attack/defence)/50+2)*resistCoefficient;
        return Double.valueOf(math).intValue();
    }
}
