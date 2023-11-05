<script setup>
import axios from "axios";
import {useUserInfoStore} from "@/store/store";
const store=useUserInfoStore()
const getInfo=()=>{
  axios.post('/api/user/getInfo',{},{
    headers:{
      "authorization":store.getToken()
    }
  }).then(res=>{
    console.log(res)
  })
}
</script>

<template>
<h1>这里是用户页</h1>
  <el-button @click="getInfo">获取用户信息</el-button>
</template>

<style scoped>

</style>