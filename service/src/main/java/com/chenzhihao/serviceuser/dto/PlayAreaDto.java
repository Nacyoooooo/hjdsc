package com.chenzhihao.serviceuser.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayAreaDto {
    private List<PlayData> playerOne=new ArrayList<>();
    private List<PlayData> playerTwo=new ArrayList<>();
    public void addPlayerOne(PlayData playData){
        playerOne.add(playData);
    }
    public void addPlayerTwo(PlayData playData){
        playerTwo.add(playData);
    }
}
