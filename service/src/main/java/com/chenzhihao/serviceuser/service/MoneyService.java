package com.chenzhihao.serviceuser.service;

import com.chenzhihao.serviceuser.model.Money;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chenzhihao.serviceuser.result.Result;
import org.springframework.stereotype.Service;

/**
* @author 86159
* @description 针对表【money】的数据库操作Service
* @createDate 2023-11-19 23:58:37
*/
@Service
public interface MoneyService extends IService<Money> {

    Result<?> getCount();
}
