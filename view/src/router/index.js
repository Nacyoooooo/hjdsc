import { createRouter, createWebHashHistory } from 'vue-router'

const routes = [
  {
    path: '/',
    component: () => import('../views/login/login.vue')
  },
  {
    path: '/register',
    component: () => import('../views/login/register.vue')
  },
  {
    path: '/home',
    component:()=>import('../views/home.vue')
  }
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router
