import { createApp } from 'vue'
import { createPinia } from 'pinia'
import App from './App.vue'
import './registerServiceWorker'
import router from './router'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import axios from 'axios'
axios.defaults.headers.post['Content-Type']='application/json;charset=UTF-8';
createApp(App)
    .use(router)
    .use(ElementPlus)
    .use(createPinia())
    .mount('#app')
