package com.chenzhihao.serviceuser.controller;

import com.chenzhihao.serviceuser.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 测试专用controller
 */
@CrossOrigin
@RestController
@RequestMapping(path = "/test")
@Slf4j
public class TestController {
    @PostMapping("/testToken")
    @ResponseBody
    public Result<?> testToken(){
        log.info("test...");
        return Result.ok();
    }
}
