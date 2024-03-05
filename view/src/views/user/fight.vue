<script setup>
import axios from "axios";
import {onMounted, reactive} from "vue";
import {useUserInfoStore} from "@/store/store";
import router from "@/router";
const store=useUserInfoStore()
const enemies=reactive({
  data:[]
})
onMounted(()=>{
  getEnemy()
})
const getEnemy=async ()=>{
  await  axios.post('/api/admins/getUserInfo/1',{},{
    headers:{
      "authorization":store.getToken()
    }
  }).then(res=>{
    console.log(res)
    if(res.data.code==200){
      enemies.data=res.data.data
    }
  })
}
const fight=async (id)=>{
  await  axios.post('/api/play/requestfight/'+id,{},{
    headers:{
      "authorization":store.getToken()
    }
  }).then(res=>{
    console.log(res)
    if(res.data.code==200){
      router.push('/play')
    }
  })
}
</script>

<template>
<div>这是战斗申请页面</div>
  <div v-for="enemy in enemies.data">
<div>{{enemy.name}}<el-button @click="fight(enemy.id)">挑战</el-button></div>
  </div>
</template>

<style scoped>

</style>