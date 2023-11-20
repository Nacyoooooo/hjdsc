<script setup>
import axios from "axios";
import {onMounted, reactive} from "vue";
import {useUserInfoStore} from "@/store/store";
onMounted(async ()=>{
  getPetConfig()
  signCount()
  getMoney()
})
const info=reactive({
  data:[],
  signData:[],
  money:[]
})
const store=useUserInfoStore()
const getPetConfig=async ()=>{
  await  axios.post('/api/user/getInfo',{},{
    headers:{
      "authorization":store.getToken()
    }
  }).then(res=>{
    console.log(res)
    if(res.data.code==200){
      info.data=res.data.data
    }
  })
}
const sign=async()=>{
  await  axios.post('/api/user/sign',{},{
    headers:{
      "authorization":store.getToken()
    }
  }).then(res=>{
    console.log(res)
    if(res.data.code==200){
      signCount()
    }
  })
}
const signCount=async()=>{
  await  axios.post('/api/user/signCount',{},{
    headers:{
      "authorization":store.getToken()
    }
  }).then(res=>{
    console.log(res)
    if(res.data.code==200){
      info.signData=res.data.data
    }
  })
}
const getMoney=async()=>{
  await  axios.post('/api/user/getCount',{},{
    headers:{
      "authorization":store.getToken()
    }
  }).then(res=>{
    console.log(res)
    if(res.data.code==200){
      info.money=res.data.data
    }
  })
}
</script>

<template>
<h>这是个人信息页</h>
  <div>余额：{{info.money.count}}</div>
  <div v-if="info.data">
    <div>姓名：{{info.data.name}}</div>
    <div>性别：{{info.data.gender}}</div>
    <div>id：{{info.data.id}}</div>
    <div>邮箱：{{info.data.email}}</div>
    <div>手机号：{{info.data.phoneNumber}}</div>
  </div>
  <div v-if="info.signData">
    <el-button @click="sign">签到</el-button>
    <div>最大连续签到天数:{{info.signData.max}}</div>
    <div>最近连续签到天数:{{info.signData.count}}</div>
    <div>本月签到总数:{{info.signData.sum}}</div>
    <div>签到了的天数</div>
    <el-card class="box-card">
      <div v-for="(day,index) in info.signData.monthSign" :key="index" class="text item">
        <div v-if="day==1"> {{index}}号</div>
      </div>
    </el-card>
  </div>


</template>

<style scoped>

</style>