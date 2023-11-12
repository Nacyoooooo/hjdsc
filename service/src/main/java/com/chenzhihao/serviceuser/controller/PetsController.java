package com.chenzhihao.serviceuser.controller;

import com.chenzhihao.serviceuser.service.PetsconfigService;
import com.chenzhihao.serviceutil.model.Petsconfig;
import com.chenzhihao.serviceutil.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 获取宠物信息
 */
@CrossOrigin
@RestController
@RequestMapping(path = "/pets")
@Slf4j
public class PetsController {
    @Autowired
    private PetsconfigService petsconfigService;

    /**
     * 根据宠物id获取宠物基本配置信息
     * @param id
     * @return
     */
    @PostMapping("/get/{id}")
    @ResponseBody
    public Result<?> get(@PathVariable Integer id){
        return petsconfigService.getPetConfig(id);
    }

    /**
     * 设置宠物配置信息
     * @param petsconfig
     * @return
     */
    @PostMapping("/set")
    @ResponseBody
    public Result<?> set(@RequestBody Petsconfig petsconfig){
        return petsconfigService.setPetConfig(petsconfig);
    }
}
