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
  await  axios.post('/api/admins/getPetPark/'+pageId.value,{},{
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
    <el-table-column prop="id" label="序列号" width="180" />
    <el-table-column prop="pid" label="宠物编号" width="180" />
    <el-table-column prop="level" label="初始等级" width="180" />
    <el-table-column prop="count" label="剩余数量" />
    <el-table-column label="是否可被捕捉" >
      <template #default="scope">
        <div v-if="scope.row.catched==1">可以</div>
        <div v-if="scope.row.catched==2">不可以</div>
      </template>

    </el-table-column>
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