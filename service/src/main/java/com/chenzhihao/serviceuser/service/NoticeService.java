package com.chenzhihao.serviceuser.service;

import com.chenzhihao.serviceuser.model.Notice;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chenzhihao.serviceuser.result.Result;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

/**
* @author 86159
* @description 针对表【notice】的数据库操作Service
* @createDate 2023-11-19 13:56:36
*/
@Service
public interface NoticeService extends IService<Notice> {

    Result<?> getComments();
}
