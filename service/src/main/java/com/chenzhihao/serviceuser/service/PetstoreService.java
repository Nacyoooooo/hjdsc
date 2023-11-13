package com.chenzhihao.serviceuser.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.chenzhihao.serviceutil.model.Petstore;
import com.chenzhihao.serviceutil.result.Result;

/**
* @author 86159
* @description 针对表【petstore】的数据库操作Service
* @createDate 2023-11-13 15:20:16
*/
public interface PetstoreService extends IService<Petstore> {

    Result<?> getBags();

    Result<?> getStore(Long currentPage);
}
