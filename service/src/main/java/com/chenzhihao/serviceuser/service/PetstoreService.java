package com.chenzhihao.serviceuser.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.chenzhihao.serviceuser.model.Petstore;
import com.chenzhihao.serviceuser.result.Result;
import org.springframework.stereotype.Service;

/**
* @author 86159
* @description 针对表【petstore】的数据库操作Service
* @createDate 2023-11-13 15:20:16
*/
@Service
public interface PetstoreService extends IService<Petstore> {

    Result<?> getBags();

    Result<?> getStore(Long currentPage);

    Result<?> setBagFirst(Long currentPet);

    Result<?> getUserPets();

    Result<?> levelup(Integer pid);
}
