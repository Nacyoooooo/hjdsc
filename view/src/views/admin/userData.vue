<script setup>
import {onMounted, reactive, ref} from "vue";
import axios from "axios";
import {useUserInfoStore} from "@/store/store";
import {ElMessage} from "element-plus";
const store=useUserInfoStore()
const pageCount=ref(0)
const pageId=ref(1)
const pageData=reactive({
  data:[]
})
const banReason=reactive({
  id:[],
  description:[],
  type:1
})
onMounted(async ()=>{
  getUserCount()
  getUserInfo()
})
const getUserCount=async ()=>{
  await  axios.post('/api/admins/getUserCount',{},{
    headers:{
      "authorization":store.getToken()
    }
  }).then(res=>{
    if(res.data.code==200){
      pageCount.value=res.data.data
    }

  })
}
const getUserInfo=async ()=>{
  await  axios.post('/api/admins/getUserInfo/'+pageId.value,{},{
    headers:{
      "authorization":store.getToken()
    }
  }).then(res=>{
    if(res.data.code==200){
      pageData.data=res.data.data
    }
  })
}
const banUser=async ()=>{
  await  axios.post('/api/admins/banUser',banReason,{
    headers:{
      "authorization":store.getToken()
    }
  }).then(res=>{
    if(res.data.code==200){
      ElMessage.success(res.data.msg)
    }
  })
  dialogVisible.value = false
}
const dialogVisible=ref(false)
const ban=(id)=>{
  banReason.id=id
  dialogVisible.value=true
  console.log(banReason)
}
</script>

<template>
<h>这是用户信息查看界面</h>
  <el-dialog
      v-model="dialogVisible"
      title="Tips"
      width="30%"
      :before-close="handleClose"
  >
    <span>封禁原因</span>
    <el-input v-model="banReason.description"></el-input>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">Cancel</el-button>
        <el-button type="primary" @click="banUser">
          Confirm
        </el-button>
      </span>
    </template>
  </el-dialog>
  <el-pagination
      background
      :page-size="10"
      :pager-count="11"
      v-model:current-page="pageId"
      @current-change="currentChange"
      :total="pageCount"
  />
  <el-table :data="pageData.data" border style="width: 100%">
    <el-table-column prop="id" label="id" width="180" />
    <el-table-column prop="name" label="Name" width="180" />
    <el-table-column prop="phonenumber" label="手机号" />
    <el-table-column prop="email" label="email" />
    <el-table-column label="性别" >
      <template #default="scope">
        <div v-if="scope.row.gender==1">男</div>
        <div v-if="scope.row.gender==2">女</div>
      </template>
    </el-table-column>
    <el-table-column label="账号状态" >
      <template #default="scope">
        <div v-if="scope.row.status==1">正常</div>
        <div v-if="scope.row.status==2">封禁中</div>
      </template>
    </el-table-column>
    <el-table-column prop="createtime" label="createtime" />
    <el-table-column label="createtime" >
      <template #default="scope">
        <el-button @click="ban(scope.row.id)">封禁</el-button>
        <el-button >解禁</el-button>
      </template>
    </el-table-column>
  </el-table>
</template>

<style scoped>

</style>