<script setup>
import axios from "axios";
import {onMounted, reactive, ref} from "vue";
import {useUserInfoStore} from "@/store/store";
const pageId=ref(1)
const petConfig=reactive({
  data:[]
})
const  store =useUserInfoStore()
onMounted(async ()=>{
  getPetConfig()
})
const getPetConfig=async ()=>{
  await  axios.post('/api/admins/getPetSkills/'+pageId.value,{},{
    headers:{
      "authorization":store.getToken()
    }
  }).then(res=>{
    console.log(res)
    if(res.data.code==200){
      petConfig.data=res.data.data
    }
  })
}
</script>

<template>
  <h>这是宠物技能表</h>
  <el-table :data="petConfig.data" border style="width: 100%">
    <el-table-column prop="id" label="id" width="180" />
    <el-table-column prop="name" label="Name" width="180" />
    <el-table-column prop="description" label="描述" />
    <el-table-column prop="power" label="威力" />
    <el-table-column prop="description" label="描述" />
    <el-table-column prop="usetimes" label="可使用次数" />
    <el-table-column prop="createtime" label="createtime" />
    <el-table-column prop="createtime" label="createtime" >
      <template #default="scope">
        <el-button @click="banUser(scope.row.id)">删除</el-button>
        <el-button>更新</el-button>
      </template>
    </el-table-column>
  </el-table>

</template>

<style scoped>

</style>