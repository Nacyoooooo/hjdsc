package com.chenzhihao.serviceuser.controller;

import com.chenzhihao.serviceuser.result.Result;
import com.chenzhihao.serviceuser.service.PetparkService;
import com.chenzhihao.serviceuser.service.PlayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(path = "/play")
public class PlayController {
    @Autowired
    private PlayService playService;

    /**
     * 向对方请求战斗
     * @Param opponentId对手的id
     * @return
     */
    @PostMapping("/requestfight/{opponentId}")
    @ResponseBody
    public Result<?> requestfight(@PathVariable Long opponentId){
        return playService.requestfight(opponentId);
    }
    /**
     * 向服务器请求自己的战局的最新情况
     * @Param opponentId对手的id
     * @return
     */
    @PostMapping("/requestStatus")
    @ResponseBody
    public Result<?> requestStatus(){
        return playService.requestStatus();
    }
    /**
     * 向服务器请求自己的战局的最新情况
     * @Param opponentId对手的id
     * @return
     */
    @PostMapping("/useSkill/{skillid}")
    @ResponseBody
    public Result<?> useSkill(@PathVariable Long skillid){
        return playService.useSkill(skillid);
    }
}
