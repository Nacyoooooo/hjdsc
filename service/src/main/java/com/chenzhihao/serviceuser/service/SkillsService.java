package com.chenzhihao.serviceuser.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chenzhihao.serviceuser.model.Skills;
import com.chenzhihao.serviceuser.result.Result;

/**
* @author 86159
* @description 针对表【skills】的数据库操作Service
* @createDate 2023-11-17 15:23:08
*/
public interface SkillsService extends IService<Skills> {

    Result<?> getPetSkills(Integer pageId);
}
