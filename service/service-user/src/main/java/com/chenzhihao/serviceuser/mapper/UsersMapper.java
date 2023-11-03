package com.chenzhihao.serviceuser.mapper;

import com.chenzhihao.model.Users;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 86159
* @description 针对表【users】的数据库操作Mapper
* @createDate 2023-11-02 22:42:59
* @Entity com.chenzhihao.model.Users
*/
@Mapper
public interface UsersMapper extends BaseMapper<Users> {

}




