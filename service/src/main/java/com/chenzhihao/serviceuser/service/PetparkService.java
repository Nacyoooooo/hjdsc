package com.chenzhihao.serviceuser.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chenzhihao.serviceuser.model.Petpark;
import com.chenzhihao.serviceuser.result.Result;
import org.springframework.stereotype.Service;


/**
* @author 86159
* @description 针对表【petpark】的数据库操作Service
* @createDate 2023-11-14 16:49:22
*/
@Service
public interface PetparkService extends IService<Petpark> {

    Result<?> setPark(Long id, Long count);

    Result<?> getCaputedPets();

    Result<?> getPet(Long parkid) throws InterruptedException;

    Result<?> createCaptureRecord(Long parkid);

    Result<?> getPetPark(Integer pageId);
}
