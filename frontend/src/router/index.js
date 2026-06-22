import { createRouter, createWebHistory } from 'vue-router'
import { getToken } from '@/api/http'
import AppLayout from '@/layout/AppLayout.vue'
import LoginView from '@/views/login.vue'
import RegisterView from '@/views/register.vue'
import NotFoundView from '@/views/error/404.vue'
import UnauthorizedView from '@/views/error/401.vue'
import WelcomeView from '@/views/index.vue'
import { getPlatformTitle, isPlatformModuleEnabled } from '@/stores/platform'
import { getMenuTitle, normalizePath } from '@/utils/menuCatalog'

const pageTitle = (path, fallback = '') => getMenuTitle(path, fallback) || fallback

const moduleRoutes = [
  { path: 'system/user', module: 'system', component: () => import('@/views/system/user/index.vue') },
  { path: 'system/platformBootstrap', module: 'system', component: () => import('@/views/system/platformBootstrap/index.vue') },
  { path: 'profile', component: () => import('@/views/system/user/profile/index.vue') },
  { path: 'profile/userInfo', component: () => import('@/views/system/user/profile/userInfo.vue') },
  { path: 'profile/resetPwd', component: () => import('@/views/system/user/profile/resetPwd.vue') },
  { path: 'profile/userAvatar', component: () => import('@/views/system/user/profile/userAvatar.vue') },
  { path: 'monitor/logininfor', module: 'logs', component: () => import('@/views/monitor/logininfor/index.vue') },
  { path: 'monitor/operlog', module: 'logs', component: () => import('@/views/monitor/operlog/index.vue') },
  { path: 'configManagment/robot', module: 'config', component: () => import('@/views/configManagment/robot/index.vue') },
  { path: 'config/robot', module: 'config', component: () => import('@/views/configManagment/robot/index.vue') },
  { path: 'configManagment/photo', module: 'config', component: () => import('@/views/configManagment/photo/index.vue') },
  { path: 'config/photo', module: 'config', component: () => import('@/views/configManagment/photo/index.vue') },
  { path: 'configManagment/robotAudio', module: 'config', component: () => import('@/views/configManagment/robotAudio/index.vue') },
  { path: 'config/robotAudio', module: 'config', component: () => import('@/views/configManagment/robotAudio/index.vue') },
  { path: 'configManagment/audio', module: 'config', component: () => import('@/views/configManagment/audio/index.vue') },
  { path: 'config/audio', module: 'config', component: () => import('@/views/configManagment/audio/index.vue') },
  { path: 'configManagment/monitorDevice', module: 'config', component: () => import('@/views/configManagment/monitorDevice/index.vue') },
  { path: 'config/monitorDevice', module: 'config', component: () => import('@/views/configManagment/monitorDevice/index.vue') },
  { path: 'configManagment/vipRoom', module: 'config', component: () => import('@/views/configManagment/vipRoom/index.vue') },
  { path: 'config/vipRoom', module: 'config', component: () => import('@/views/configManagment/vipRoom/index.vue') },
  { path: 'configManagment/vipRoomRegion', module: 'config', component: () => import('@/views/configManagment/vipRoomRegion/index.vue') },
  { path: 'config/region', module: 'config', component: () => import('@/views/configManagment/vipRoomRegion/index.vue') },
  { path: 'configManagment/areaManagment', module: 'config', component: () => import('@/views/configManagment/areaManagment/index.vue') },
  { path: 'config/areaManagment', module: 'config', component: () => import('@/views/configManagment/areaManagment/index.vue') },
  { path: 'configManagment/vedio', module: 'config', component: () => import('@/views/configManagment/vedio/index.vue') },
  { path: 'config/vedio', module: 'config', component: () => import('@/views/configManagment/vedio/index.vue') },
  { path: 'configManagment/complaintRecord', module: 'config', component: () => import('@/views/configManagment/complaintRecord/index.vue') },
  { path: 'config/complaintRecord', module: 'config', component: () => import('@/views/configManagment/complaintRecord/index.vue') },
  { path: 'config/task', module: 'config', component: () => import('@/views/taskManagment/taskList/index.vue') },
  { path: 'taskManagment/taskList', module: 'config', component: () => import('@/views/taskManagment/taskList/index.vue') },
  { path: 'digitalTwin', module: 'digitalTwin', component: () => import('@/views/digitalTwin/index.vue') },
  { path: 'digitalTwin/v15', module: 'digitalTwin', component: () => import('@/views/digitalTwin/v15.vue') },
  { path: 'digitalTwin/screen', module: 'digitalTwin', component: () => import('@/views/digitalTwin/index0202.vue') },
  { path: 'statAnalysis/goingStat', module: 'statistics', component: () => import('@/views/statAnalysis/goingStat/index.vue') },
  { path: 'flight/goingStat', module: 'statistics', component: () => import('@/views/statAnalysis/goingStat/index.vue') },
  { path: 'statAnalysis/inLoungeList', module: 'statistics', component: () => import('@/views/statAnalysis/inLoungeList/index.vue') },
  { path: 'flight/passenger', module: 'statistics', component: () => import('@/views/statAnalysis/inLoungeList/index.vue') },
  { path: 'viewManagment/outGoing', module: 'statistics', component: () => import('@/views/viewManagment/outGoing/index.vue') },
  { path: 'flight/outGoing', module: 'statistics', component: () => import('@/views/viewManagment/outGoing/index.vue') },
  { path: 'statAnalysis/inquiry', module: 'statistics', component: () => import('@/views/statAnalysis/inquiry/index.vue') },
  { path: 'statAnalysis/guide', module: 'statistics', component: () => import('@/views/statAnalysis/guide/index.vue') },
  { path: 'tool/swagger', module: 'system', component: () => import('@/views/tool/swagger/index.vue') },
  { path: 'redirect', component: () => import('@/views/redirect/index.vue') }
].map((route) => ({
  ...route,
  meta: {
    ...(route.meta || {}),
    module: route.module || route.meta?.module,
    title: pageTitle(`/${route.path.split('/:')[0]}`, route.meta?.title || '')
  }
}))

const routes = [
  { path: '/login', component: LoginView, meta: { title: '登录' } },
  { path: '/register', component: RegisterView, meta: { title: '注册' } },
  { path: '/401', component: UnauthorizedView, meta: { title: '401' } },
  { path: '/404', component: NotFoundView, meta: { title: '404' } },
  {
    path: '/',
    component: AppLayout,
    children: [
      { path: '', component: WelcomeView, meta: { title: '首页' } },
      ...moduleRoutes
    ]
  },
  { path: '/:pathMatch(.*)*', component: NotFoundView }
]

const router = createRouter({
  history: createWebHistory(),
  routes,
  scrollBehavior() {
    return { top: 0 }
  }
})

router.beforeEach((to) => {
  const token = getToken()
  if (to.path === '/login' || to.path === '/register') {
    return true
  }
  if (!token) {
    return '/login'
  }
  if (!isPlatformModuleEnabled(to.meta?.module)) {
    return '/404'
  }
  return true
})

router.afterEach((to) => {
  const routeTitle = getMenuTitle(normalizePath(to.path), to.meta?.title || '')
  document.title = getPlatformTitle(routeTitle)
})

export default router
