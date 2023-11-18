<script setup>
import axios from "axios";
import {useUserInfoStore} from "@/store/store";
import router from "@/router";
import {reactive, ref} from "vue";
// import {useWebSocket} from '../hooks'
const store=useUserInfoStore()
const petId=ref(0)
const storePage=ref(1)
const orderid=ref(1)
const users=reactive({
  data:[]
})
// const ws=useWebSocket(handleMessage);
// function handleMessage(e){
//
// }
const petConfig=reactive({
  name:'',
  description:'',
  healthpoint:'',
  physicaldamagepoint:'',
  magicaldamagepoin:'',
  physicaldefence:'',
  magicaldefence:'',
  speed:'',
  attribute:'',
  secondaryattribute:''
})
const petPark=reactive({
  data:[]
})
const userStore=reactive({
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
const setPets=async ()=>{
  await  axios.post('/api/admins/setPets',petConfig,{
    headers:{
      "authorization":store.getToken()
    }
  }).then(res=>{
    console.log(res)
  })
}
const getPetParkInfo=async ()=>{
  await  axios.post('/api/park/get',{},{
    headers:{
      "authorization":store.getToken()
    }
  }).then(res=>{
    console.log(res)
    petPark.data=res.data.data
  })
}
const getPet=async (cid)=>{
  await  axios.post('/api/park/getPet/'+cid,{},{
    headers:{
      "authorization":store.getToken()
    }
  }).then(res=>{
    console.log(res)
  })
}
const getUserStoreInfo=async ()=>{
  await  axios.post('/api/admins/getUserPets',{},{
    headers:{
      "authorization":store.getToken()
    }
  }).then(res=>{
    console.log(res)
    userStore.data=res.data.data
  })
}
const op=ref(1)
const requestfight=async ()=>{
  await  axios.post('/api/play/requestfight/'+op.value,{},{
    headers:{
      "authorization":store.getToken()
    }
  }).then(res=>{
    console.log(res)
    const data=res.data
    if(data.code==200){
      router.push('/play')
    }
  })
}
</script>

<template>
  <h>helloworld</h>
  <el-input v-model="op"></el-input>
  <el-button @click="requestfight">请求对战</el-button>
  <el-button @click="router.push('/play')">去对战页</el-button>
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
        <el-button @click="getPet(scope.row.id)">封禁</el-button>
      </template>
    </el-table-column>

  </el-table>
  <el-form
      :label-position="labelPosition"
      label-width="100px"
      :model="petConfig"
      style="max-width: 460px"
  >
    <el-form-item label="名称">
      <el-input v-model="petConfig.name" />
    </el-form-item>
    <el-form-item label="描述">
      <el-input v-model="petConfig.description" />
    </el-form-item>
    <el-form-item label="精力种族值">
      <el-input v-model="petConfig.healthpoint" />
    </el-form-item>
    <el-form-item label="物攻种族值">
      <el-input v-model="petConfig.physicaldamagepoint" />
    </el-form-item>
    <el-form-item label="魔攻种族值">
      <el-input v-model="petConfig.magicaldamagepoin" />
    </el-form-item>
    <el-form-item label="物抗种族值">
      <el-input v-model="petConfig.physicaldefence" />
    </el-form-item>
    <el-form-item label="魔抗种族值">
      <el-input v-model="petConfig.magicaldefence" />
    </el-form-item>
    <el-form-item label="速度种族值">
      <el-input v-model="petConfig.speed" />
    </el-form-item>
    <el-form-item label="主属性">
      <el-input v-model="petConfig.attribute" />
    </el-form-item>
    <el-form-item label="副属性">
      <el-input v-model="petConfig.secondaryattribute" />
    </el-form-item>
    <el-button @click="setPets">提交</el-button>
  </el-form>
  <el-button @click="getPetParkInfo">获取宠物园信息</el-button>
  <el-table :data="petPark.data" border style="width: 100%">
    <el-table-column prop="id" label="项目号" width="180" />
    <el-table-column prop="pid" label="宠物编号" width="180" />
    <el-table-column prop="count" label="剩余数量" />
    <el-table-column prop="catched" label="是否可被捕捉" />
    <el-table-column prop="createtime" label="创建时间" />
    <el-table-column prop="updatetime" label="更新时间" />
    <el-table-column label="操作" >
      <template #default="scope">
        <el-button @click="getPet(scope.row.id)">获取</el-button>
      </template>
    </el-table-column>

  </el-table>
  <el-button @click="getUserStoreInfo">获取用户所拥有的宠物信息</el-button>
  <el-table :data="userStore.data" border style="width: 100%">
    <el-table-column prop="id" label="id" width="180" />
    <el-table-column prop="pid" label="宠物编号" width="180" />
    <el-table-column prop="uid" label="玩家id" />
    <el-table-column prop="createtime" label="获取时间" />

  </el-table>
</template>

<style scoped>

</style>