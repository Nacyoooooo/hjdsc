package com.chenzhihao.serviceuser.dto;

import com.chenzhihao.serviceuser.model.entity.PetSkill;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayData {
    private Integer maxBlood;
    private Integer currentBlood;
    private String name;
    private Integer level;
    private Map<Integer,PetSkill> skills;
    private Integer order;
}
