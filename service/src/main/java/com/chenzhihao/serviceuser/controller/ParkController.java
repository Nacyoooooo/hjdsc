package com.chenzhihao.serviceuser.controller;

import com.chenzhihao.serviceuser.result.Result;
import com.chenzhihao.serviceuser.service.PetparkService;
import com.chenzhihao.serviceuser.service.PetsconfigService;
import com.chenzhihao.serviceuser.service.PetstoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(path = "/park")
@Slf4j
public class ParkController {
    @Autowired
    private PetparkService petparkService;
    @PostMapping("/get")
    @ResponseBody
    public Result<?> get(){
        return petparkService.getCaputedPets();
    }
}
