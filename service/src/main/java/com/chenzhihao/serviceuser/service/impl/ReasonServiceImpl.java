package com.chenzhihao.serviceuser.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chenzhihao.serviceuser.model.Reason;
import com.chenzhihao.serviceuser.service.ReasonService;
import com.chenzhihao.serviceuser.mapper.ReasonMapper;
import org.springframework.stereotype.Service;

/**
* @author 86159
* @description 针对表【reason】的数据库操作Service实现
* @createDate 2023-11-20 13:26:33
*/
@Service
public class ReasonServiceImpl extends ServiceImpl<ReasonMapper, Reason>
    implements ReasonService{

}




