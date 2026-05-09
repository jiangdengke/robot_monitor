import { createRouter, createWebHistory } from 'vue-router'
import { getToken } from '@/api/http'
import AppLayout from '@/layout/AppLayout.vue'
import LoginView from '@/views/login.vue'
import RegisterView from '@/views/register.vue'
import NotFoundView from '@/views/error/404.vue'
import UnauthorizedView from '@/views/error/401.vue'
import WelcomeView from '@/views/index.vue'

const moduleRoutes = [
  { path: 'system/user', component: () => import('@/views/system/user/index.vue') },
  { path: 'system/user-auth/role/:userId', component: () => import('@/views/system/user/authRole.vue') },
  { path: 'system/role', component: () => import('@/views/system/role/index.vue') },
  { path: 'system/role-auth/user/:roleId', component: () => import('@/views/system/role/authUser.vue') },
  { path: 'system/role-auth/selectUser/:roleId', component: () => import('@/views/system/role/selectUser.vue') },
  { path: 'system/dept', component: () => import('@/views/system/dept/index.vue') },
  { path: 'system/post', component: () => import('@/views/system/post/index.vue') },
  { path: 'system/menu', component: () => import('@/views/system/menu/index.vue') },
  { path: 'system/dict', component: () => import('@/views/system/dict/index.vue') },
  { path: 'system/dict-data/index/:dictId', component: () => import('@/views/system/dict/data.vue') },
  { path: 'system/config', component: () => import('@/views/system/config/index.vue') },
  { path: 'system/notice', component: () => import('@/views/system/notice/index.vue') },
  { path: 'monitor/online', component: () => import('@/views/monitor/online/index.vue') },
  { path: 'monitor/logininfor', component: () => import('@/views/monitor/logininfor/index.vue') },
  { path: 'monitor/login-log', component: () => import('@/views/monitor/logininfor/index.vue') },
  { path: 'monitor/operlog', component: () => import('@/views/monitor/operlog/index.vue') },
  { path: 'monitor/oper-log', component: () => import('@/views/monitor/operlog/index.vue') },
  { path: 'monitor/server', component: () => import('@/views/monitor/server/index.vue') },
  { path: 'monitor/cache', component: () => import('@/views/monitor/cache/index.vue') },
  { path: 'monitor/druid', component: () => import('@/views/monitor/druid/index.vue') },
  { path: 'monitor/job', component: () => import('@/views/monitor/job/index.vue') },
  { path: 'monitor/job-log', component: () => import('@/views/monitor/job/log.vue') },
  { path: 'monitor/job/log/:jobId?', component: () => import('@/views/monitor/job/log.vue') },
  { path: 'configManagment/robot', component: () => import('@/views/configManagment/robot/index.vue') },
  { path: 'config/robot', component: () => import('@/views/configManagment/robot/index.vue') },
  { path: 'configManagment/photo', component: () => import('@/views/configManagment/photo/index.vue') },
  { path: 'config/photo', component: () => import('@/views/configManagment/photo/index.vue') },
  { path: 'configManagment/robotAudio', component: () => import('@/views/configManagment/robotAudio/index.vue') },
  { path: 'config/audio', component: () => import('@/views/configManagment/robotAudio/index.vue') },
  { path: 'configManagment/monitorDevice', component: () => import('@/views/configManagment/monitorDevice/index.vue') },
  { path: 'config/monitorDevice', component: () => import('@/views/configManagment/monitorDevice/index.vue') },
  { path: 'configManagment/vipRoom', component: () => import('@/views/configManagment/vipRoom/index.vue') },
  { path: 'config/vipRoom', component: () => import('@/views/configManagment/vipRoom/index.vue') },
  { path: 'configManagment/vipRoomRegion', component: () => import('@/views/configManagment/vipRoomRegion/index.vue') },
  { path: 'config/region', component: () => import('@/views/configManagment/vipRoomRegion/index.vue') },
  { path: 'configManagment/areaManagment', component: () => import('@/views/configManagment/areaManagment/index.vue') },
  { path: 'config/areaManagment', component: () => import('@/views/configManagment/areaManagment/index.vue') },
  { path: 'configManagment/vedio', component: () => import('@/views/configManagment/vedio/index.vue') },
  { path: 'config/vedio', component: () => import('@/views/configManagment/vedio/index.vue') },
  { path: 'configManagment/complaintRecord', component: () => import('@/views/configManagment/complaintRecord/index.vue') },
  { path: 'config/complaintRecord', component: () => import('@/views/configManagment/complaintRecord/index.vue') },
  { path: 'config/table', component: () => import('@/views/foodManagment/foodTable/index.vue') },
  { path: 'config/task', component: () => import('@/views/taskManagment/taskList/index.vue') },
  { path: 'digitalTwin', component: () => import('@/views/digitalTwin/index.vue') },
  { path: 'flight/digitalTwin', component: () => import('@/views/digitalTwin/index.vue') },
  { path: 'knowledgeManagment/ai/knowledge', component: () => import('@/views/knowledgeManagment/ai/knowledge/index.vue') },
  { path: 'ai/knowledge', component: () => import('@/views/knowledgeManagment/ai/knowledge/index.vue') },
  { path: 'knowledgeManagment/ai/log', component: () => import('@/views/knowledgeManagment/ai/log/index.vue') },
  { path: 'ai/log', component: () => import('@/views/knowledgeManagment/ai/log/index.vue') },
  { path: 'foodManagment/food', component: () => import('@/views/foodManagment/food/index.vue') },
  { path: 'food/foodConfig', component: () => import('@/views/foodManagment/food/index.vue') },
  { path: 'foodManagment/foodMenu', component: () => import('@/views/foodManagment/foodMenu/index.vue') },
  { path: 'food/foodOrder', component: () => import('@/views/foodManagment/foodMenu/index.vue') },
  { path: 'foodManagment/foodTable', component: () => import('@/views/foodManagment/foodTable/index.vue') },
  { path: 'food/foodTable', component: () => import('@/views/foodManagment/foodTable/index.vue') },
  { path: 'foodManagment/menuPlan', component: () => import('@/views/foodManagment/menuPlan/index.vue') },
  { path: 'food/dailyMenu', component: () => import('@/views/foodManagment/menuPlan/index.vue') },
  { path: 'foodManagment/foodPlan', component: () => import('@/views/foodManagment/foodPlan/index.vue') },
  { path: 'food/foodPlan', component: () => import('@/views/foodManagment/foodPlan/index.vue') },
  { path: 'taskManagment/taskList', component: () => import('@/views/taskManagment/taskList/index.vue') },
  { path: 'statAnalysis/goingStat', component: () => import('@/views/statAnalysis/goingStat/index.vue') },
  { path: 'flight/goingStat', component: () => import('@/views/statAnalysis/goingStat/index.vue') },
  { path: 'statAnalysis/inLoungeList', component: () => import('@/views/statAnalysis/inLoungeList/index.vue') },
  { path: 'flight/passenger', component: () => import('@/views/statAnalysis/inLoungeList/index.vue') },
  { path: 'statAnalysis/moveStat', component: () => import('@/views/statAnalysis/moveStat/index.vue') },
  { path: 'flight/flightInfo', component: () => import('@/views/statAnalysis/moveStat/index.vue') },
  { path: 'statAnalysis/passengerWarningLog', component: () => import('@/views/statAnalysis/passengerWarningLog/index.vue') },
  { path: 'flight/passengerWarning', component: () => import('@/views/statAnalysis/passengerWarningLog/index.vue') },
  { path: 'statAnalysis/questionStat', component: () => import('@/views/statAnalysis/questionStat/index.vue') },
  { path: 'flight/questionStat', component: () => import('@/views/statAnalysis/questionStat/index.vue') },
  { path: 'tool/gen', component: () => import('@/views/tool/gen/index.vue') },
  { path: 'tool/build', component: () => import('@/views/tool/build/index.vue') },
  { path: 'tool/swagger', component: () => import('@/views/tool/swagger/index.vue') },
  { path: 'viewManagment/outGoing', component: () => import('@/views/viewManagment/outGoing/index.vue') },
  { path: 'flight/outGoing', component: () => import('@/views/viewManagment/outGoing/index.vue') },
  { path: 'numberModel', component: () => import('@/views/numberModel/index.vue') },
  { path: 'redirect', component: () => import('@/views/redirect/index.vue') }
]

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
  return true
})

router.afterEach((to) => {
  const title = to.meta?.title ? `${to.meta.title} - 国航智慧贵宾室管理系统` : '国航智慧贵宾室管理系统'
  document.title = title
})

export default router
