<script setup>
import axios from "axios";
import {onMounted, reactive, ref} from "vue";
import {useUserInfoStore} from "@/store/store";
import {ElMessage} from "element-plus";
const pageId=ref(1)
const petConfig=reactive({
  data:[],
  config:[],
  store:[]
})
const  store =useUserInfoStore()
onMounted(async ()=>{
  getPetConfig()
  getPetStoreConfig()
  setInterval(()=>{
  },500)
})
const getPetConfig=async ()=>{
  await  axios.post('/api/pets/getBags',{},{
    headers:{
      "authorization":store.getToken()
    }
  }).then(res=>{
    console.log(res)
    if(res.data.code==200){
      petConfig.data=res.data.data
      petConfig.config=petConfig.data.petsconfig
    }
  })
}
const getPetStoreConfig=async ()=>{
  await  axios.post('/api/pets/getStore/'+pageId.value,{},{
    headers:{
      "authorization":store.getToken()
    }
  }).then(res=>{
    console.log(res)
    if(res.data.code==200){
      petConfig.store=res.data.data.records
    }
  })
}
const getMainPet=async (id)=>{
  await  axios.post('/api/pets/getMainPets/'+id,{},{
    headers:{
      "authorization":store.getToken()
    }
  }).then(res=>{
    console.log(res)
    if(res.data.code==200){

    }
  })
}
const setFirst=(id)=>{

}
</script>

<template>
<h>这是宠物背包</h>
  <el-table :data="petConfig.data" border style="width: 100%">
    <el-table-column prop="level" label="level" width="180" />
    <el-table-column prop="pid" label="宠物编号" width="180" />
    <el-table-column prop="order" label="出战顺序" width="180" />
    <el-table-column prop="maxBlood" label="最大血量" width="180" />
    <el-table-column prop="experience" label="升至下一级经验" width="180" />
    <el-table-column prop="createtime" label="createtime" >
      <template #default="scope">
        <el-button @click="setFirst(scope.row.id)">置于第一</el-button>
        <el-button @click="setFirst(scope.row.id)">升级</el-button>
      </template>
    </el-table-column>
  </el-table>
  <el-button @click="getMainPet(1)">领取喵喵</el-button>
  <el-button @click="getMainPet(2)">领取火花</el-button>
  <el-button @click="getMainPet(3)">领取水蓝蓝</el-button>
  <div>这是宠物仓库</div>
  <div v-for="(pet,index) in petConfig.store">
    <div>{{pet}}</div>
  </div>
</template>

<style scoped>

</style>