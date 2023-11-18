package com.chenzhihao.serviceuser.controller;

import com.chenzhihao.serviceuser.model.Petsconfig;
import com.chenzhihao.serviceuser.result.Result;
import com.chenzhihao.serviceuser.service.PetparkService;
import com.chenzhihao.serviceuser.service.PetsconfigService;
import com.chenzhihao.serviceuser.service.PetstoreService;
import com.chenzhihao.serviceuser.service.UsersService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(path = "/admins")
@Slf4j
public class AdminController {
    @Autowired
    private UsersService usersService;
    @Autowired
    private PetsconfigService petsconfigService;
    @Autowired
    private PetparkService petparkService;
    @Autowired
    private PetstoreService petstoreService;
    @PostMapping("getUserInfo")
    @ResponseBody
    public Result<?> getUserInfo(){
        return usersService.getUserInfo();
    }

    /**
     * 分页查询
     * @return
     */
    @PostMapping("getUserInfo/{pageId}")
    @ResponseBody
    public Result<?> getUserInfoPagination(@PathVariable Integer pageId){
        return usersService.getUserInfo(pageId);
    }
    @PostMapping("/banUser/{uid}")
    @ResponseBody
    public Result<?> banUser(@PathVariable Long uid){
        return usersService.banUser(uid);
    }
    @PostMapping("/setPets")
    @ResponseBody
    public Result<?> setPets(@RequestBody Petsconfig petsconfig){
        return petsconfigService.setPetConfig(petsconfig);
    }
    @PostMapping("/updatePets")
    @ResponseBody
    public Result<?> updatePets(@RequestBody Petsconfig petsconfig){
        return petsconfigService.updatePets(petsconfig);
    }
    @PostMapping("/deletePets/{id}")
    @ResponseBody
    public Result<?> deletePets(@PathVariable Long id){
        return petsconfigService.deletePets(id);
    }

    @PostMapping("/setPark/{id}/{count}")
    @ResponseBody
    public Result<?> setPark(@PathVariable Long id,@PathVariable Long count){
        return petparkService.setPark(id,count);
    }
    @PostMapping("/getUserPets")
    @ResponseBody
    public Result<?> getUserPets(){
        return petstoreService.getUserPets();
    }
    @PostMapping("/getUserCount")
    @ResponseBody
    public Result<?> getUserCount(){
        return usersService.getUserCount();
    }
}
