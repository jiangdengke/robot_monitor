import { createApp } from 'vue'
import ElementPlus from 'element-plus'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import 'element-plus/dist/index.css'
import './styles/ruoyi.css'
import App from './App.vue'
import router from './router'
import { installPermissionDirective } from './utils/permission'

const app = createApp(App)

Object.entries(ElementPlusIconsVue).forEach(([name, component]) => {
  app.component(name, component)
})

installPermissionDirective(app)
app.use(router).use(ElementPlus).mount('#app')
