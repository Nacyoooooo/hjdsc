package com.chenzhihao.serviceuser.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chenzhihao.serviceuser.dto.PlayAreaDto;
import com.chenzhihao.serviceuser.mapper.PetsconfigMapper;
import com.chenzhihao.serviceuser.mapper.PetstoreMapper;
import com.chenzhihao.serviceuser.mapper.SkillsMapper;
import com.chenzhihao.serviceuser.model.Petstore;
import com.chenzhihao.serviceuser.model.Users;
import com.chenzhihao.serviceuser.model.entity.FightArea;
import com.chenzhihao.serviceuser.model.entity.Pet;
import com.chenzhihao.serviceuser.model.entity.Player;
import com.chenzhihao.serviceuser.result.Result;
import com.chenzhihao.serviceuser.service.PlayService;
import com.chenzhihao.serviceuser.util.PetUtil;
import com.chenzhihao.serviceuser.util.RedisIdWorker;
import com.chenzhihao.serviceuser.util.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.chenzhihao.serviceuser.constant.PlayerType.PLAYER;
import static com.chenzhihao.serviceuser.constant.PlayerType.ROBOT;
import static com.chenzhihao.serviceuser.constant.RedisConstants.*;

@Service
public class PlayServiceImpl implements PlayService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private PetsconfigMapper petsconfigMapper;
    @Autowired
    private PetstoreMapper petstoreMapper;
    @Autowired
    private PetUtil petUtil;
    @Autowired
    private SkillsMapper skillsMapper;
    @Autowired
    private RedisIdWorker redisIdWorker;
    @Override
    public Result<?> requestfight(Long opponentId) {
        //判断是否有用户
        Users user = UserHolder.getUser();
        //没有则拒拒绝请求
        if(user==null){
            return Result.fail();
        }
        //不能与自己战斗
        if(user.getId().equals(opponentId)){
            return Result.fail("不能与自己战斗");
        }
        //查看对方是否在战斗
        String key=PLAY_STATUS_KEY+opponentId;
        Boolean exist = stringRedisTemplate.hasKey(key);
        //正在战斗，无法与之单挑
        if(exist){
            return Result.fail("对方正在战斗");
        }
        //开启战斗，载入双方资源
        //观察背包资源是否载入
        String myKey=PETS_BAG_KEY+user.getId();
        exist = stringRedisTemplate.hasKey(myKey);
        //如果没有载入，则从数据库读取资源
        if(!exist){
            //从仓库中读取玩家背包中的宠物
            List<Petstore> myPets = petstoreMapper.selectList(new QueryWrapper<Petstore>()
                    //玩家id
                    .eq("id", user.getId())
                    //在背包中的宠物
                    .gt("performed", 0));
            //如果玩家背包中没有宠物，则战斗结束
            if(null==myPets||myPets.size()<=0){
                return Result.fail();
            }
            List<Pet> pets=new ArrayList<>();
            //获取自己背包中宠物的信息，挂载到redis
            petUtil.getPet(pets,myPets);
            if(pets==null||pets.size()<=0){
                return Result.fail();
            }
            pets.forEach(pet -> {
                stringRedisTemplate.opsForList().rightPush(myKey,JSONUtil.toJsonStr(pet));
            });
        }
        String otherKey=PETS_BAG_KEY+opponentId;
        exist = stringRedisTemplate.hasKey(otherKey);
        //如果没有载入，则从数据库读取资源
        if(!exist){
            //从仓库中读取玩家背包中的宠物
            List<Petstore> myPets = petstoreMapper.selectList(new QueryWrapper<Petstore>()
                    //玩家id
                    .eq("id", opponentId)
                    //在背包中的宠物
                    .gt("performed", 0));
            //如果玩家背包中没有宠物，则战斗结束
            if(null==myPets||myPets.size()<=0){
                return Result.fail();
            }
            List<Pet> pets=new ArrayList<>();
            //获取自己背包中宠物的信息，挂载到redis
            petUtil.getPet(pets,myPets);
            if(pets==null||pets.size()<=0){
                return Result.fail();
            }
            pets.forEach(pet -> {
                stringRedisTemplate.opsForList().rightPush(otherKey,JSONUtil.toJsonStr(pet));
            });
        }
        //将资源转化成对战资源，对方的也是

        //获取背包中的所有数据
        // 如果载入失败，则告知前端
        long fight = redisIdWorker.nextId("fight");
        List<String> myPets = stringRedisTemplate.opsForList().range(myKey, 0, -1);
        if(myPets==null||myPets.size()<=0){
            return Result.fail();
        }
        List<Pet>pets=new ArrayList<>();
        myPets.forEach(pet->{
            Pet bean = JSONUtil.toBean(pet, Pet.class);
            pets.add(bean);
        });
        Player Myself = new Player();
        Myself.setSituationId(fight);
        Myself.setUid(user.getId().intValue());
        Myself.setPlayerType(PLAYER);
        pets.forEach(pet->{
            Myself.getPets().put(pet.getOrder(),pet);
        });
        Myself.setOrderId(1);
        //将游戏状态写入redis
        String playKey=PLAY_STATUS_KEY+user.getId();
        stringRedisTemplate.opsForValue().set(playKey,JSONUtil.toJsonStr(Myself));
        /****************************对手******************************************/
        List<String> otherPets = stringRedisTemplate.opsForList().range(otherKey, 0, -1);
        if(otherPets==null||otherPets.size()<=0){
            return Result.fail();
        }
        List<Pet>otherpets=new ArrayList<>();
        otherPets.forEach(pet->{
            Pet bean = JSONUtil.toBean(pet, Pet.class);
            otherpets.add(bean);
        });
        Player otherself = new Player();
        otherself.setPlayerType(ROBOT);
        otherself.setSituationId( fight);
        otherself.setUid(opponentId.intValue());
        otherpets.forEach(pet->{
            otherself.getPets().put(pet.getOrder(),pet);
        });
        otherself.setOrderId(1);
        //将游戏状态写入redis
        String otherplayKey=PLAY_STATUS_KEY+opponentId;
        stringRedisTemplate.opsForValue().set(otherplayKey,JSONUtil.toJsonStr(otherself));
        /****************************对手******************************************/
        String fightKey="play:fight:"+fight;
        FightArea fightArea = new FightArea();
        fightArea.setPlayerOneId(user.getId().intValue());
        fightArea.setPlayerTwoId(opponentId.intValue());
        stringRedisTemplate.opsForValue().set(fightKey,JSONUtil.toJsonStr(fightArea));
        //请求成功
        return Result.ok();
    }

    @Override
    public Result<?> requestStatus() {
        Users user = UserHolder.getUser();
        if(user==null){
            return Result.fail("没有该用户！");
        }
        String key=PLAY_STATUS_KEY+user.getId();
        Boolean exist = stringRedisTemplate.hasKey(key);
        if(!exist){
            return Result.fail("您未处于对战中！");
        }
        String playerJson = stringRedisTemplate.opsForValue().get(key);
        Player player = JSONUtil.toBean(playerJson, Player.class);
        if(player==null){
            return Result.fail();
        }
        String fightKey="play:fight:"+player.getSituationId();
        Boolean fightExist = stringRedisTemplate.hasKey(fightKey);
        if(!fightExist){
            return Result.fail("对局不存在！");
        }
        String fightJson = stringRedisTemplate.opsForValue().get(fightKey);
        FightArea fightArea = JSONUtil.toBean(fightJson, FightArea.class);
        if(fightArea==null){
            return Result.fail();
        }
        String p2Key=PLAY_STATUS_KEY+fightArea.getPlayerTwoId();
        Boolean p2Exist = stringRedisTemplate.hasKey(p2Key);
        if(!p2Exist){
            return Result.fail();
        }
        String p2Json = stringRedisTemplate.opsForValue().get(p2Key);
        Player playerTwo = JSONUtil.toBean(p2Json, Player.class);
        FightArea fightArea2 = new FightArea();
        fightArea2.setPlayerOne(player);
        fightArea2.setPlayerTwo(playerTwo);
        PlayAreaDto playAreaDto=new PlayAreaDto();
        player.getPets().forEach((order,pet)->{
            playAreaDto.addPlayerOne(petUtil.setPetDto(pet));
        });
        playerTwo.getPets().forEach((order,pet)->{
            playAreaDto.addPlayerTwo(petUtil.setPetDto(pet));
        });
        return Result.ok(playAreaDto);
    }

    @Override
    public Result<?> useSkill(Long skillid) {
        if(skillid==null){
            return Result.fail();
        }
        if(skillid<1||skillid>4){
            return Result.fail("非法参数");
        }
        Users user = UserHolder.getUser();
        if(user==null){
            return Result.fail();
        }
        //从redis中获取战局情况
        //获取自己的目前信息
        String myKey=PLAY_STATUS_KEY+user.getId();
        Player myself = petUtil.getPlayer(user.getId().intValue());
        if(myself==null){
            return Result.fail();
        }
        //获取战局信息
        String situationKey=PLAY_FIGHT_KEY+myself.getSituationId();
        Boolean situationExist = stringRedisTemplate.hasKey(situationKey);
        if(!situationExist){
            return Result.fail();
        }
        String situationJson = stringRedisTemplate.opsForValue().get(situationKey);
        FightArea fightArea = JSONUtil.toBean(situationJson, FightArea.class);
        if(fightArea==null){
            return Result.fail();
        }
        if(fightArea.getWinnerId()!=-1){
            Result<Object> fail = Result.fail("游戏已结束！");
            fail.setCode(300);
            return fail;
        }
        Integer enemyId;
        if(fightArea.getPlayerOneId().equals(user.getId().intValue())){
            enemyId=fightArea.getPlayerTwoId();
        }
        else {
            enemyId=fightArea.getPlayerOneId();
        }
        String enemyKey=PLAY_STATUS_KEY+enemyId;
        Boolean enemyExist = stringRedisTemplate.hasKey(enemyKey);
        if(!enemyExist){
            return Result.fail();
        }
        String enemyJson = stringRedisTemplate.opsForValue().get(enemyKey);
        Player enemy = JSONUtil.toBean(enemyJson, Player.class);
        if(enemy==null){
            return Result.fail();
        }
        //计算该技能对对方或是己方的影响
        //获取了我myself 和 对手 enemy的数据
        Map<Integer, Pet> pets = myself.getPets();

        if(pets==null||pets.size()<=0){
            return Result.fail();
        }
        Integer orderId = myself.getOrderId();
        if(orderId==null){
            return Result.fail();
        }
        Pet pet = pets.get(orderId);
        if(pet==null){
            return Result.fail();
        }
        Map<Integer, Pet> enemypets = enemy.getPets();
        if(enemypets==null||enemypets.size()<=0){
            return Result.fail();
        }
        Integer enemyorderId = enemy.getOrderId();
        if(enemyorderId==null){
            return Result.fail();
        }
        Pet enemypet = enemypets.get(enemyorderId);
        if(enemypet==null){
            return Result.fail();
        }
        boolean success = petUtil.useSkill(pet, enemypet, skillid.intValue(), myself.getPlayerType());
        if(!success){
            return Result.fail();
        }
        //对手已阵亡
        if(enemypet.getCurrentBlood()<=0){
            //从宠物列表中寻找能代替上去的宠物
            Integer o=enemy.getOrderId();
            for (Map.Entry<Integer, Pet> entry : enemypets.entrySet()) {
                if(entry.getKey()!=enemy.getOrderId()){
                    o=entry.getKey();
                }
            }
            if(o==enemy.getOrderId()){
                //如果没有可替换的，则直接判对手输
                fightArea.setWinnerId(myself.getOrderId());
                stringRedisTemplate.opsForValue().set(situationKey,JSONUtil.toJsonStr(fightArea));
                myself.setPets(pets);
                stringRedisTemplate.opsForValue().set(myKey,JSONUtil.toJsonStr(myself));
                enemypets.put(enemyorderId,enemypet);
                enemy.setPets(enemypets);
                stringRedisTemplate.opsForValue().set(enemyKey,JSONUtil.toJsonStr(enemy));
                return Result.ok();
            }
        }
        success=petUtil.useSkill(enemypet,pet,1, enemy.getPlayerType());
        if(!success){
            return Result.fail();
        }
        if(pet.getCurrentBlood()<=0){
            //从宠物列表中寻找能代替上去的宠物
            Integer o=myself.getOrderId();
            for (Map.Entry<Integer, Pet> entry : pets.entrySet()) {
                if(entry.getKey()!=myself.getOrderId()){
                    o=entry.getKey();
                }
            }
            if(o==myself.getOrderId()){
                //如果没有可替换的，则直接判对手输
                fightArea.setWinnerId(enemy.getOrderId());
                stringRedisTemplate.opsForValue().set(situationKey,JSONUtil.toJsonStr(fightArea));
                myself.setPets(pets);
                stringRedisTemplate.opsForValue().set(myKey,JSONUtil.toJsonStr(myself));
                enemypets.put(enemyorderId,enemypet);
                enemy.setPets(enemypets);
                stringRedisTemplate.opsForValue().set(enemyKey,JSONUtil.toJsonStr(enemy));
                return Result.ok();
            }
        }
        /********************************************************/
/********************************************************/
//        Map<Integer, PetSkill> enemypetSkills = enemypet.getSkills();
/********************************************************/
        //写入redis
        myself.setPets(pets);
        stringRedisTemplate.opsForValue().set(myKey,JSONUtil.toJsonStr(myself));
        enemypets.put(enemyorderId,enemypet);
        enemy.setPets(enemypets);
        stringRedisTemplate.opsForValue().set(enemyKey,JSONUtil.toJsonStr(enemy));
        return Result.ok();
    }
}
