package com.chenzhihao.serviceuser.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.chenzhihao.serviceuser.mapper.PetstoreMapper;
import com.chenzhihao.serviceuser.service.PetstoreService;
import com.chenzhihao.serviceutil.model.Petstore;
import com.chenzhihao.serviceutil.model.Users;
import com.chenzhihao.serviceutil.result.Result;
import com.chenzhihao.serviceutil.util.UserHolder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 86159
* @description 针对表【petstore】的数据库操作Service实现
* @createDate 2023-11-13 15:20:16
*/
@Service
public class PetstoreServiceImpl extends ServiceImpl<PetstoreMapper, Petstore>
    implements PetstoreService {


    @Override
    public Result<?> getBags() {
        Users user = UserHolder.getUser();
        if(user==null){
            return Result.fail();
        }
        Long uid = user.getId();
        IPage page=new Page(0,6);

        QueryWrapper<Petstore> q = new QueryWrapper<Petstore>()
                .eq("uid", uid)
                .ne("performed", 0);
        IPage page1 = page(page, q);
        List<Petstore> list = list(q);
        return Result.ok(page1);
    }

    @Override
    public Result<?> getStore(Long currentPage) {
        if(currentPage<=0){
            return Result.fail("参数异常");
        }
        Users user = UserHolder.getUser();
        if(user==null){
            return Result.fail();
        }
        Long uid = user.getId();
        IPage page=new Page(currentPage-1,3);
        QueryWrapper<Petstore> q = new QueryWrapper<Petstore>()
                .eq("uid", uid)
                .eq("performed", 0);
        IPage page1 = page(page, q);
        if(page1==null){
            return Result.fail();
        }
        return Result.ok(page1);
    }
}




