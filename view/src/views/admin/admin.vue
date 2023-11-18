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
              ><span>申请</span></template
              >
              <el-menu-item-group>
                <el-menu-item index="/end/poorview">贫困等级申请</el-menu-item>

              </el-menu-item-group>
            </el-sub-menu>

            <el-sub-menu ><template #title>
              <el-icon><location /></el-icon>
              <span>审核</span>
            </template>
              <el-menu-item-group title="审核" >
                <el-menu-item index="/end/LoanReview"
                >助学贷款审核</el-menu-item
                >
              </el-menu-item-group>
            </el-sub-menu>

            <el-sub-menu >
              <template #title>
                <el-icon><location /></el-icon>
                <span>前端管理</span>
              </template>
              <el-menu-item-group title="资源页">
                <el-menu-item index="/end/homePageController"
                >首页管理</el-menu-item
                >
              </el-menu-item-group>
            </el-sub-menu>
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