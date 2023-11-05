<script setup>
import axios from "axios";
import {useUserInfoStore} from "@/store/store";
const store=useUserInfoStore()
const testToken=()=>{
  axios.post('/api/test/testToken',
      {},
      {headers:{
        "authorization":store.getToken()
        }}).then(res=>{
    console.log(res)
  })
}
</script>

<template>
<h>helloworld</h>
  <el-button @click="testToken">测试token是否可被携带</el-button>
</template>

<style scoped>

</style>