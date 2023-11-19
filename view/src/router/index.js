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
  },
  {
    path: '/userdata',
    component:()=>import('../views/userdata.vue')
  },
  {
    path: '/play',
    component:()=>import('../views/play/play.vue')
  },
  {
    path: '/admin',
    component:()=>import('../views/admin/admin.vue'),
    children:[
      {
        path: 'userData',
        component:()=>import('../views/admin/userData.vue'),
      },
      {
        path: 'petConfig',
        component:()=>import('../views/admin/petConfig.vue'),
      }
      ,
      {
        path: 'petSkills',
        component:()=>import('../views/admin/petSkills.vue'),
      }
      ,
      {
        path: 'petPark',
        component:()=>import('../views/admin/petPark.vue'),
      }
      ,
      {
        path: 'notice',
        component:()=>import('../views/admin/notice.vue'),
      }
    ]
  }
  ,{
    path: '/homepage',
    component:()=>import('../views/user/homePage.vue'),
    children: [
      {
        path: 'petpark',
        component:()=>import('../views/user/petPark.vue'),
      },{
        path: 'userData',
        component:()=>import('../views/user/userdata.vue'),
      }
    ]
  }
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})
router.beforeEach((to,from,next)=>{
next()
})
export default router
