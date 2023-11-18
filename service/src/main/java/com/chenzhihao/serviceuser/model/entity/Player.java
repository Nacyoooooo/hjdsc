package com.chenzhihao.serviceuser.model.entity;

import com.chenzhihao.serviceuser.constant.PlayerType;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;
@Data
public class Player {
    //操控的玩家的id
    private Integer uid;
    //该玩家上场的宠物
    private Map<Integer,Pet>pets=new HashMap<>();
    //该对局的id
    private Long situationId;
    //玩家类型
    private PlayerType playerType;
    //玩家上场的宠物的序号
    private Integer orderId;
}
