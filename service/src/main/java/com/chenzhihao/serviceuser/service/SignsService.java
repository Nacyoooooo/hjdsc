package com.chenzhihao.serviceuser.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.chenzhihao.serviceutil.model.Signs;
import com.chenzhihao.serviceutil.result.Result;

/**
* @author 86159
* @description 针对表【signs】的数据库操作Service
* @createDate 2023-11-12 23:17:47
*/
public interface SignsService extends IService<Signs> {
    public Result<?> sign();

    Result<?> signCount();
}
