import { createApp } from 'vue'
import Antd from 'ant-design-vue'
import 'ant-design-vue/dist/reset.css'
import './styles/ruoyi.css'
import App from './App.vue'
import router from './router'
import { installPermissionDirective } from './utils/permission'
import * as ElementLike from './compat/element-like'

const app = createApp(App)

Object.entries(ElementLike).forEach(([name, component]) => {
  app.component(name, component)
})

installPermissionDirective(app)

app.use(router).use(Antd).mount('#app')
