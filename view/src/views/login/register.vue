<script setup>
import {reactive, ref} from "vue";
import axios from "axios";
import {ElMessage} from "element-plus";
import router from "@/router";
import {useUserInfoStore} from "@/store/store";
const store=useUserInfoStore()
const loginForm=reactive( {
  name: '',
  password: '',
  gender:'',
  phoneNumber:'',
  email:''
})
const loading=ref(false)
const register=async ()=>{
  await axios.post('/api/login/register',loginForm).then(res=>{
    console.log(res)
    if(res.data.code==200){
      ElMessage.success("注册成功")
      router.push('/')
    }else {
      ElMessage.error(res.data.data)
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
            v-model="loginForm.name"
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
      <el-form-item>
        <el-radio-group v-model="loginForm.gender" size="large">
          <el-radio-button label="1" >男</el-radio-button>
          <el-radio-button label="2" >女</el-radio-button>
        </el-radio-group>
      </el-form-item>
      <el-form-item>
        <span class="svg-container">
          <svg-icon icon-class="password" />
        </span>
        <el-input
            v-model="loginForm.phoneNumber"
            placeholder="phoneNumber"
        />
      </el-form-item>
      <el-form-item>
        <span class="svg-container">
          <svg-icon icon-class="password" />
        </span>
        <el-input
            v-model="loginForm.email"
            placeholder="email"
        />
      </el-form-item>

      <el-button
          :loading="loading"
          type="primary"
          style="width:100%;margin-bottom:30px;" @click="register">注册</el-button>
      <el-button
          :loading="loading"
          type="primary"
          style="width:100%;margin-bottom:30px;" @click="router.push('/')">返回</el-button>

    </el-form>
  </div>
</template>
