package com.chenzhihao.serviceuser.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.chenzhihao.serviceutil.model.Petsconfig;
import com.chenzhihao.serviceutil.result.Result;
import org.springframework.stereotype.Service;

/**
* @author 86159
* @description 针对表【petsconfig】的数据库操作Service
* @createDate 2023-11-07 22:56:52
*/
@Service
public interface PetsconfigService extends IService<Petsconfig> {

    Result<?> getPetConfig(Integer id);

    Result<?> setPetConfig(Petsconfig petsconfig);

    Result<?> updatePets(Petsconfig petsconfig);

    Result<?> deletePets(Long id);
}
