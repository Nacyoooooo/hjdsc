<script setup>
import axios from "axios";
import {useUserInfoStore} from "@/store/store";
import router from "@/router";
import {ref} from "vue";
const store=useUserInfoStore()
const petId=ref(0)
const storePage=ref(1)
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
const getPetConfig=async ()=>{
  axios.post('/api/pets/get/'+petId.value,{},{
    headers:{
      "authorization":store.getToken()
    }
  }).then(res=>{
    console.log(res)
  })
}
const getPetBag=async ()=>{
  axios.post('/api/pets/getBags/',{},{
    headers:{
      "authorization":store.getToken()
    }
  }).then(res=>{
    console.log(res)
  })
}
const currentChange=async ()=>{
  console.log(storePage.value)
  await  axios.post('/api/pets/getStore/'+storePage.value,{},{
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
  <el-input v-model="petId"></el-input>
  <el-button @click="getPetConfig">查询宠物信息</el-button>
  <el-button @click="getPetBag">查询自己的宠物背包信息</el-button>
  <el-pagination
      :page-size="10"
      :pager-count="11"
      v-model:current-page="storePage"
      @current-change="currentChange"
      :total="40"
  />
</template>

<style scoped>

</style>