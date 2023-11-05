<script setup>
import axios from "axios";
import {useUserInfoStore} from "@/store/store";
import router from "@/router";
const store=useUserInfoStore()
const testToken=()=>{
  axios.post('/api/test/testToken',
      {},
      {headers:{
        "authorization":store.getToken()
        }}).then(res=>{
    console.log(res)
  })
}
const sign=async ()=>{
  axios.post('/api/user/sign',{},{
    headers:{
      "authorization":store.getToken()
    }
  }).then(res=>{
    console.log(res)
  })
}
const signCount=async ()=>{
  axios.post('/api/user/signCount',{},{
    headers:{
      "authorization":store.getToken()
    }
  }).then(res=>{
    console.log(res)
  })
}
</script>

<template>
<h>helloworld</h>
  <el-button @click="router.push('/userdata')">去用户页</el-button>
  <el-button @click="testToken">测试token是否可被携带</el-button>
  <el-button @click="sign">签到</el-button>
  <el-button @click="signCount">签到天数统计</el-button>
</template>

<style scoped>

</style>