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
            <h class="mb-2">后台管理中心</h>
            <el-menu-item index="/admin/userData">
              <el-icon><icon-menu /></el-icon>
              <span>用户管理</span>
            </el-menu-item>

            <el-sub-menu >
              <template #title
              ><el-icon><document></document></el-icon
              ><span>游戏内容管理</span></template
              >
              <el-menu-item-group>
                <el-menu-item index="/admin/petConfig">宠物配置</el-menu-item>
                <el-menu-item index="/admin/petSkills">宠物技能</el-menu-item>

              </el-menu-item-group>
            </el-sub-menu>

            <el-menu-item index="/admin/petPark">
              <el-icon><icon-menu /></el-icon>
              <span>宠物池管理</span>
            </el-menu-item>

            <el-menu-item index="/admin/notice">
              <el-icon><icon-menu /></el-icon>
              <span>公告管理</span>
            </el-menu-item>
            <el-menu-item index="/end/admin">
              <el-icon><setting /></el-icon>
              <template #title>管理</template>
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