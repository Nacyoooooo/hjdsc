<script setup>
import axios from "axios";
import {useUserInfoStore} from "@/store/store";
import router from "@/router";
import {reactive, ref} from "vue";
const store=useUserInfoStore()
const petId=ref(0)
const storePage=ref(1)
const orderid=ref(1)
const users=reactive({
  data:[]
})
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
    users.data=res.data.data
  })
  console.log(users.data)
}
const banUser=async (id)=>{
  await  axios.post('/api/admins/banUser/'+id,{},{
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
    <el-table :data="users.data" border style="width: 100%">
      <el-table-column prop="id" label="学号" width="180" />
      <el-table-column prop="name" label="名字" width="180" />
      <el-table-column prop="status" label="账号状态" />

        <el-table-column label="账号状态" >
          <template #default="scope">
            <el-button @click="banUser(scope.row.id)">封禁</el-button>
          </template>
        </el-table-column>

    </el-table>
</template>

<style scoped>

</style>