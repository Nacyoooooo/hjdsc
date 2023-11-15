package com.chenzhihao.serviceuser.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.chenzhihao.serviceuser.mapper.CapturerecordMapper;
import com.chenzhihao.serviceuser.mapper.PetparkMapper;
import com.chenzhihao.serviceuser.mapper.PetsconfigMapper;
import com.chenzhihao.serviceuser.model.Capturerecord;
import com.chenzhihao.serviceuser.model.Petpark;
import com.chenzhihao.serviceuser.model.Petsconfig;
import com.chenzhihao.serviceuser.model.Users;
import com.chenzhihao.serviceuser.result.Result;
import com.chenzhihao.serviceuser.service.PetparkService;

import com.chenzhihao.serviceuser.util.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
* @author 86159
* @description 针对表【petpark】的数据库操作Service实现
* @createDate 2023-11-14 16:49:22
*/
@Service
public class PetparkServiceImpl extends ServiceImpl<PetparkMapper, Petpark>
    implements PetparkService {
    @Autowired
    private PetsconfigMapper petsconfigMapper;
    @Autowired
    private CapturerecordMapper capturerecordMapper;

    @Override
    public Result<?> setPark(Long id, Long count) {
        //检验数量是否大于0
        // 小于0则失败
        if(id==null||count==null||count<0){
            return Result.fail();
        }
        //校验被添加的宠物是否存在
        ;
        Petsconfig petsconfig = petsconfigMapper.selectOne(new QueryWrapper<Petsconfig>()
                .eq("pid", id));
        //不存在就失败
        if(null==petsconfig){
            return Result.fail();
        }
        //存在就添加
        Petpark petpark=new Petpark();
        petpark.setId(id.intValue());
        petpark.setCount(count.intValue());
        petpark.setCreatetime(new Date());
        petpark.setUpdatetime(new Date());
        boolean save = save(petpark);
        if(!save){
            return Result.fail();
        }
        return Result.ok();
    }

    @Override
    public Result<?> getCaputedPets() {
        List<Petpark> catched = list(new QueryWrapper<Petpark>()
                .eq("catched", 1)
                .gt("count",0));
        if(catched==null){
            return Result.fail();
        }
        return Result.ok(catched);
    }

    @Override
    public Result<?> getPet(Long parkid) {
        Users user = UserHolder.getUser();
        if(null==user){
            return Result.fail();
        }
        QueryWrapper<Petpark> park = new QueryWrapper<Petpark>()
                //该数据是否存在
                .eq("id", parkid)
                //是否可被捕捉
                .eq("catched", 1)
                //数量是否大于0
                .gt("count", 0);
        Petpark one = getOne(park);
        if(one==null){
            return Result.fail();
        }
        one.setCount(one.getCount()-1);
        one.setUpdatetime(new Date());
        boolean b = saveOrUpdate(one);
        if(!b){
            return Result.fail();
        }
        Capturerecord capturerecord = new Capturerecord();
        capturerecord.setCid(parkid.intValue());
        capturerecord.setPid(one.getPid());
        capturerecord.setUid(user.getId().intValue());
        capturerecord.setCreatetime(new Date());
        capturerecord.setUpdatetime(new Date());
        int insert = capturerecordMapper.insert(capturerecord);
        if(insert<=0){
            return Result.fail();
        }
        return Result.ok();
    }
}




