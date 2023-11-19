<script setup>
import axios from "axios";
import {onMounted, reactive} from "vue";
import {useUserInfoStore} from "@/store/store";
onMounted(async ()=>{
  getPetConfig()
})
const info=reactive({
  data:[]
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
</script>

<template>
<h>这是个人信息页</h>
  <div>姓名：{{info.data.name}}</div>
  <div>性别：{{info.data.gender}}</div>
  <div>id：{{info.data.id}}</div>
  <div>邮箱：{{info.data.email}}</div>
  <div>手机号：{{info.data.phoneNumber}}</div>
</template>

<style scoped>

</style>