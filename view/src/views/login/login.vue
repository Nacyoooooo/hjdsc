<script setup>
import {reactive, ref} from "vue";
import axios from "axios";
import {ElMessage} from "element-plus";
import router from "@/router";
import {useUserInfoStore} from "@/store/store";
const store=useUserInfoStore()
const loginForm=reactive( {
  id: '',
  password: '',
  authority:'1'
})
const loading=ref(false)
const login=async ()=>{
  await axios.post('/api/login',loginForm).then(res=>{
    console.log(res)
    if(res.data.code==200){
      ElMessage.success("登录成功")
      store.setToken(res.data.data)
      if(loginForm.authority==1){
        router.push('/admin')
      }
      else {
        router.push(('/homepage'))
      }

    }else {
      ElMessage.error('登录失败')
    }
  })
}
</script>

<template>
  <div class="login-container">
    <el-form
             :model="loginForm"
             label-position="left">

      <div class="title-container">
        <h3 class="title">皇家斗兽场</h3>
      </div>

      <el-form-item prop="username">
        <span class="svg-container">
          <svg-icon icon-class="user" />
        </span>
        <el-input
            ref="username"
            v-model="loginForm.id"
            placeholder="Username"
        />
      </el-form-item>

      <el-form-item>
        <span class="svg-container">
          <svg-icon icon-class="password" />
        </span>
        <el-input
            v-model="loginForm.password"
            placeholder="Password"
        />
      </el-form-item>

      <el-button
          :loading="loading"
          type="primary"
          style="width:100%;margin-bottom:30px;" @click="login">登录</el-button>
      <el-button
          :loading="loading"
          type="primary"
          style="width:100%;margin-bottom:30px;" @click="router.push('/register')">注册</el-button>

      <div class="tips">
        <span style="margin-right:20px;">id: 1</span>
        <span> password: 123456</span>
      </div>
      <div class="mb-2 flex items-center text-sm">
        <el-radio-group v-model="loginForm.authority" class="ml-4">
          <el-radio label="1" size="large">管理员</el-radio>
          <el-radio label="2" size="large">玩家</el-radio>
        </el-radio-group>
      </div>
    </el-form>
  </div>
</template>
