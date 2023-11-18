package com.chenzhihao.serviceuser.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 对战区域，记录对战双方的id
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FightArea {
    //玩家一id
    private Integer playerOneId;
    //玩家2id
    private Integer playerTwoId;
    //玩家1信息
    private Player playerOne;
    //玩家2信息
    private Player playerTwo;
}
