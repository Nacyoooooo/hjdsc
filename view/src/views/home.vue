<script setup>
import axios from "axios";
import {useUserInfoStore} from "@/store/store";
import router from "@/router";
import {ref} from "vue";
const store=useUserInfoStore()
const petId=ref(0)
const storePage=ref(1)
const orderid=ref(1)
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
  await  axios.post('/api/pets/getStore/'+storePage.value,{},{
    headers:{
      "authorization":store.getToken()
    }
  }).then(res=>{
    console.log(res)
  })
}
const setorderid=async ()=>{
  await  axios.post('/api/pets/setBags/'+orderid.value,{},{
    headers:{
      "authorization":store.getToken()
    }
  }).then(res=>{
    console.log(res)
  })
}
const getUserInfo=async ()=>{
  await  axios.post('/api/admins/getUserInfo',{},{
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
  <el-input v-model="orderid"></el-input>
  <el-button @click="setorderid">更换宠物背包位次</el-button>
  <el-button @click="getUserInfo">获取用户信息</el-button>

</template>

<style scoped>

</style>