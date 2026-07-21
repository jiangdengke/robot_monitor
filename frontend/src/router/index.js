import { createRouter, createWebHistory } from 'vue-router'
import { getToken } from '@/api/http'
import AppLayout from '@/layout/AppLayout.vue'
import LoginView from '@/views/login.vue'
import RegisterView from '@/views/register.vue'
import NotFoundView from '@/views/error/404.vue'
import UnauthorizedView from '@/views/error/401.vue'
import WelcomeView from '@/views/index.vue'
import { getMenuTitle, normalizePath } from '@/utils/menuCatalog'

const productTitle = '机器人管理系统'
const pageTitle = (path, fallback = '') => getMenuTitle(path, fallback) || fallback

const businessRoutes = [
  { path: 'system/user', component: () => import('@/views/system/user/index.vue') },
  { path: 'profile', component: () => import('@/views/system/user/profile/index.vue') },
  { path: 'profile/userInfo', component: () => import('@/views/system/user/profile/userInfo.vue') },
  { path: 'profile/resetPwd', component: () => import('@/views/system/user/profile/resetPwd.vue') },
  { path: 'profile/userAvatar', component: () => import('@/views/system/user/profile/userAvatar.vue') },
  { path: 'monitor/logininfor', component: () => import('@/views/monitor/logininfor/index.vue') },
  { path: 'monitor/operlog', component: () => import('@/views/monitor/operlog/index.vue') },
  { path: 'config/robot', component: () => import('@/views/configManagment/robot/index.vue') },
  { path: 'config/device', component: () => import('@/views/configManagment/monitorDevice/index.vue') },
  { path: 'config/site', component: () => import('@/views/configManagment/site/index.vue') },
  { path: 'config/area', component: () => import('@/views/configManagment/area/index.vue') },
  { path: 'config/point', component: () => import('@/views/configManagment/point/index.vue') },
  { path: 'config/task', component: () => import('@/views/taskManagment/taskList/index.vue') },
  { path: 'redirect', component: () => import('@/views/redirect/index.vue') }
].map((route) => ({
  ...route,
  meta: {
    ...(route.meta || {}),
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
      ...businessRoutes
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
  return true
})

router.afterEach((to) => {
  const routeTitle = getMenuTitle(normalizePath(to.path), to.meta?.title || '')
  document.title = routeTitle ? `${routeTitle} - ${productTitle}` : productTitle
})

export default router
