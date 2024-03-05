package com.chenzhihao.serviceuser.service;

import com.chenzhihao.serviceuser.model.Mainpet;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chenzhihao.serviceuser.result.Result;
import org.springframework.stereotype.Service;

/**
* @author 86159
* @description 针对表【mainpet】的数据库操作Service
* @createDate 2023-11-19 22:55:04
*/
@Service
public interface MainpetService extends IService<Mainpet> {

    Result<?> getMainPets(Integer pid);

    void updateMainpet(Mainpet l);
}
