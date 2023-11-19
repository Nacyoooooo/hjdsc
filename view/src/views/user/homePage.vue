<template>
  <div class="common-layout">
    <el-menu>
      <el-container>
        <el-aside class="el-menu-vertical-demo">
          <el-menu
              default-active="1"
              class="el-menu-vertical-demo"
              @open="handleOpen"
              @close="handleClose"
              router
          >
            <h class="mb-2">用户中心</h>
            <el-menu-item index="/homepage/petpark">
              <el-icon><icon-menu /></el-icon>
              <span>宠物池</span>
            </el-menu-item>
            <el-menu-item index="/homepage/userData">
              <el-icon><icon-menu /></el-icon>
              <span>个人信息页</span>
            </el-menu-item>
          </el-menu></el-aside
        >
        <el-main> <router-view></router-view></el-main>
      </el-container>
    </el-menu>
  </div>
</template>
<script  lang="js" setup>

</script>

<style scoped>
.el-aside {
  width: 200px;
}
.el-menu-vertical-demo {
  width: 200px;
  height: 800px;
  overflow: auto;
}
</style>