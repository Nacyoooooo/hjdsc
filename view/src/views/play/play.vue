<script setup>
import {onMounted, reactive, ref} from "vue";
import axios from "axios";
import router from "@/router";
import {useUserInfoStore} from "@/store/store";
import {ElMessage} from "element-plus";
const player=reactive({
  data:[]
})
const p1=reactive({
  data:[],
  performer:[]
})
const p2=reactive({
  data:[],
  performer:[]
})
const performedOne=ref(0)
const performedTwo=ref(0)

const store=useUserInfoStore()
//玩家的对战选择，换宠，捕捉，还是逃跑
const choose=ref(1)
//对战类型，是否可以捕捉
const BattleType=ref(false)
//设置对战选择，根据不同的选择切换不同的展示效果
const setChoose=(newChoose)=>{
  choose.value=newChoose
}
const requestStatus=async()=>{
  await  axios.post('/api/play/requestStatus',{},{
    headers:{
      "authorization":store.getToken()
    }
  }).then(res=>{
    player.data=res.data.data
    p1.data=player.data.playerOne
    p2.data=player.data.playerTwo
    //获取展示位的数据
    p1.performer=p1.data[performedOne.value]
    p2.performer=p2.data[performedTwo.value]

  })
}
const usedSkill=ref(1)
const useSkill=async(number)=>{
  usedSkill.value=number
  await  axios.post('/api/play/useSkill/'+usedSkill.value,{},{
    headers:{
      "authorization":store.getToken()
    }
  }).then(res=>{
    console.log(res)
    if(res.data.code==300){
      ElMessage.success(res.data.msg)
    }
  })
}
onMounted(async ()=>{
  requestStatus()
  setInterval(()=>{
    requestStatus()
  },500)
})
</script>

<template>
  <h>这是对战页</h>
  <div class="common-layout" v-if="p1">
    <el-container>
      <el-header>
        血条展示区
        <el-row :gutter="20">
          <el-col :span="6">
            <div>
              <el-avatar
                  src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"
              />
            </div>
            <div>{{p1.performer.name}}</div>
            <div>Lv.{{p1.performer.level}}</div>
            <div>
              属性：
            </div>

          </el-col>
          <el-col :span="6"><div class="slider-demo-block">
            <span class="demonstration" >{{p1.performer.currentBlood}}/{{p1.performer.maxBlood}}</span>
            <el-slider disabled
                       :max="p1.performer.maxBlood"
                       :min="0"
                       v-model="p1.performer.currentBlood"
            />
          </div></el-col>
          <el-col :span="6">
            <div class="slider-demo-block">
              <span class="demonstration" >{{p2.performer.currentBlood}}/{{p2.performer.maxBlood}}</span>
              <el-slider disabled
                         :max="p2.performer.maxBlood"
                         :min="0"
                         v-model="p2.performer.currentBlood"
              />
            </div>
          </el-col>
          <el-col :span="6"><div>
            <el-avatar
                src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"
            />
          </div>
            <div>{{p2.performer.name}}</div>
            <div>Lv.{{p2.performer.level}}</div>
            <div>
              属性：
            </div></el-col>
        </el-row>
        <el-divider />
      </el-header>
      <el-main>
        宠物摆放区
        <el-row :gutter="20">
          <el-col :span="6">
            <div class="demo-fit">
              <div  class="block">
                <el-avatar shape="square" :size="220"
                           src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png" />
              </div>
            </div>
          </el-col>
          <el-col :span="12">
          </el-col>
          <el-col :span="6">
            <div class="demo-fit">
              <div  class="block">
                <el-avatar shape="square" :size="220"
                           src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png" />
              </div>
            </div>
          </el-col>
          <el-col :span="3">
          </el-col>
        </el-row>
        <el-divider />
      </el-main>
      <el-footer>
        技能区
        <el-row :gutter="12">
          <el-col :span="5">
            <el-timeline>
              <el-timeline-item
              >
                test1
              </el-timeline-item>
              <el-timeline-item
              >
                test2
              </el-timeline-item>
            </el-timeline>
          </el-col>
          <el-col :span="14" >
            <!--            技能显示区-->
            <div v-if="choose==1">
              <div v-if="p1.performer" >
                <div v-for="(skill,key) in p1.performer.skills" :key="key">
                  <el-col :span="7">
                    <el-popover
                        placement="top-start"
                        :width="200"
                        trigger="hover"
                        :content="skill.description"
                    >
                      <template #reference>
                        <el-card shadow="hover" @click="useSkill(key)">
                          <div>{{skill.name}}</div>
                          <div>威力:{{skill.power}}</div>
                          <div v-if="skill.skillType=='POWER'">技能类型:威力技能</div>
                          <div v-else>技能类型:变化技能</div>
                          <div>可用次数:
                            <span class="demonstration" >{{skill.usedTimes}}/{{skill.maxTimes}}</span>
                            <el-slider disabled
                                       :max="skill.maxTimes"
                                       :min="0"
                                       v-model="skill.usedTimes"
                            />
                          </div>
                        </el-card>
                      </template>
                    </el-popover>
                  </el-col>
                </div>
              </div>

            </div>
            <div v-if="choose==2">
              <el-col :span="4">
                捕捉
              </el-col>
            </div>
            <div v-if="choose==3">
              <el-col :span="4">
                换宠
              </el-col>
            </div>
          </el-col>
          <el-col :span="4">
            <el-button-group>
              <el-button type="warning" :disabled="BattleType" @click="setChoose(2)">捕捉</el-button>
              <el-button type="primary" @click="setChoose(3)">
                换宠
              </el-button>
              <el-button type="success" @click="setChoose(1)">
                对战
              </el-button>
              <el-button type="danger" >
                逃跑
              </el-button>
            </el-button-group>
          </el-col>
        </el-row>
      </el-footer>
    </el-container>
  </div>
</template>

<style scoped>
.el-row {
  margin-bottom: 20px;
}
.el-row:last-child {
  margin-bottom: 0;
}
.el-col {
  border-radius: 4px;
}

.grid-content {
  border-radius: 4px;
  min-height: 36px;
}
</style>