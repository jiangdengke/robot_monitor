<template>
  <a-layout class="app-shell">
    <a-layout-sider
      :collapsed="sidebarCollapsed"
      :width="228"
      :collapsed-width="84"
      theme="dark"
      class="shell-sider"
    >
      <div class="brand">
        <img class="brand-logo" src="/legacy-dist/favicon-old.ico" alt="国航" />
        <div v-if="!sidebarCollapsed" class="brand-copy">
          <span class="brand-kicker">Air China</span>
          <span class="brand-title">智慧贵宾室</span>
        </div>
      </div>

      <a-menu :selected-keys="[activeMenu]" mode="inline" theme="dark">
        <template v-for="group in menus" :key="group.path">
          <a-menu-item v-if="!group.children?.length" :key="group.path" @click="go(group.path)">
            <template #icon><component :is="resolveIcon(group.icon)" /></template>
            <span>{{ group.title }}</span>
          </a-menu-item>
          <a-sub-menu v-else :key="group.path">
            <template #icon><component :is="resolveIcon(group.icon)" /></template>
            <template #title>{{ group.title }}</template>
            <a-menu-item v-for="item in group.children" :key="item.path" @click="go(item.path)">
              {{ item.title }}
            </a-menu-item>
          </a-sub-menu>
        </template>
      </a-menu>
    </a-layout-sider>

    <a-layout>
      <a-layout-header class="shell-header">
        <div class="header-left">
          <a-button type="text" class="collapse-trigger" @click="sidebarCollapsed = !sidebarCollapsed">
            <template #icon>
              <MenuUnfoldOutlined v-if="sidebarCollapsed" />
              <MenuFoldOutlined v-else />
            </template>
          </a-button>
          <a-breadcrumb>
            <a-breadcrumb-item>首页</a-breadcrumb-item>
            <a-breadcrumb-item v-if="currentGroup">{{ currentGroup.title }}</a-breadcrumb-item>
            <a-breadcrumb-item v-if="currentTitle">{{ currentTitle }}</a-breadcrumb-item>
          </a-breadcrumb>
        </div>
        <div class="header-right">
          <a-dropdown>
            <a class="account-link" @click.prevent>
              <a-avatar class="account-avatar">{{ avatarText }}</a-avatar>
              <span>{{ displayName }}</span>
              <DownOutlined />
            </a>
            <template #overlay>
              <a-menu @click="({ key }) => handleCommand(key)">
                <a-menu-item key="profile">个人中心</a-menu-item>
                <a-menu-divider />
                <a-menu-item key="logout">退出登录</a-menu-item>
              </a-menu>
            </template>
          </a-dropdown>
        </div>
      </a-layout-header>

      <div class="tags-bar">
        <div
          v-for="tag in visitedTags"
          :key="tag.path"
          :class="['tag-chip', { active: tag.path === route.path }]"
          @click="go(tag.path)"
        >
          <span>{{ tag.title }}</span>
          <CloseOutlined v-if="tag.path !== '/'" class="tag-close" @click.stop="closeTag(tag.path)" />
        </div>
      </div>

      <a-layout-content class="shell-content">
        <router-view />
      </a-layout-content>
    </a-layout>
  </a-layout>
</template>

<script setup>
import { computed, onMounted, ref, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import {
  AlertOutlined,
  ApartmentOutlined,
  AppstoreOutlined,
  BellOutlined,
  CameraOutlined,
  CoffeeOutlined,
  ContainerOutlined,
  DashboardOutlined,
  DeploymentUnitOutlined,
  DownOutlined,
  FileImageOutlined,
  FileSearchOutlined,
  FormOutlined,
  FundProjectionScreenOutlined,
  MenuFoldOutlined,
  MenuOutlined,
  MenuUnfoldOutlined,
  PictureOutlined,
  ProfileOutlined,
  ReadOutlined,
  RobotOutlined,
  ScheduleOutlined,
  SettingOutlined,
  SoundOutlined,
  TableOutlined,
  TeamOutlined,
  UserOutlined,
  VideoCameraOutlined,
  CloseOutlined
} from '@ant-design/icons-vue'
import { clearSession, hydrateSession, sessionState } from '@/stores/session'
import {
  buildFallbackMenus,
  getCanonicalMenuPath,
  getMenuIcon,
  getMenuOrder,
  getMenuTitle,
  getParentPath,
  normalizePath,
  sortByMenuOrder
} from '@/utils/menuCatalog'

const router = useRouter()
const route = useRoute()
const session = sessionState
const sidebarCollapsed = ref(false)
const visitedTags = ref([{ title: '首页', path: '/' }])

const iconMap = {
  system: SettingOutlined,
  monitor: DashboardOutlined,
  tool: AppstoreOutlined,
  peoples: TeamOutlined,
  people: UserOutlined,
  user: UserOutlined,
  tree: ApartmentOutlined,
  post: ProfileOutlined,
  dict: ReadOutlined,
  edit: FormOutlined,
  message: BellOutlined,
  online: TeamOutlined,
  log: ContainerOutlined,
  logininfor: ScheduleOutlined,
  redis: DeploymentUnitOutlined,
  job: ScheduleOutlined,
  server: DashboardOutlined,
  druid: FundProjectionScreenOutlined,
  robot: RobotOutlined,
  map: FundProjectionScreenOutlined,
  image: PictureOutlined,
  table: TableOutlined,
  sound: SoundOutlined,
  'tree-table': ApartmentOutlined,
  'video-camera': VideoCameraOutlined,
  documentation: ReadOutlined,
  'digital-twin-view': FundProjectionScreenOutlined,
  walk: FileSearchOutlined,
  chart: DashboardOutlined,
  warning: AlertOutlined,
  food: CoffeeOutlined,
  menuPlan: ProfileOutlined,
  shopping: CoffeeOutlined,
  calendar: ScheduleOutlined,
  code: AppstoreOutlined,
  build: AppstoreOutlined,
  swagger: ReadOutlined,
  dashboard: DashboardOutlined,
  list: MenuOutlined,
  camera: CameraOutlined,
  box: FileImageOutlined
}

onMounted(async () => {
  if (!session.user && session.token) {
    try {
      await hydrateSession()
    } catch {
      clearSession()
      router.push('/login')
    }
  }
})

const menus = computed(() =>
  sortByMenuOrder(buildFallbackMenus(), (group) => group.path).map((group) => ({
    ...group,
    children: sortByMenuOrder(group.children || [], (item) => item.path)
  }))
)

const flatMenus = computed(() =>
  menus.value.flatMap((group) =>
    group.children?.length
      ? group.children.map((item) => ({ ...item, parentPath: group.path }))
      : [{ ...group, parentPath: '' }]
  )
)

const activeMenu = computed(() => {
  const normalized = getCanonicalMenuPath(route.path)
  const matched = flatMenus.value
    .filter((item) => normalized === item.path || normalized.startsWith(`${item.path}/`))
    .sort((left, right) => right.path.length - left.path.length)[0]
  return matched?.path || normalized
})

const currentItem = computed(() => flatMenus.value.find((item) => item.path === activeMenu.value))
const currentGroup = computed(() => menus.value.find((group) => group.path === (currentItem.value?.parentPath || getParentPath(route.path))))
const currentTitle = computed(() => getMenuTitle(route.path, route.meta?.title || currentItem.value?.title || ''))
const displayName = computed(() => session.user?.nickName || session.user?.userName || '管理员')
const avatarText = computed(() => (displayName.value || 'A').slice(0, 1).toUpperCase())

watch(
  () => route.fullPath,
  () => addCurrentTag(),
  { immediate: true }
)

function addCurrentTag() {
  const path = normalizePath(route.path)
  const title = currentTitle.value || '管理后台'
  if (route.path === '/login' || route.path === '/register') return
  if (!visitedTags.value.some((tag) => tag.path === path)) {
    visitedTags.value.push({ title, path, order: getMenuOrder(path) })
  } else {
    visitedTags.value = visitedTags.value.map((tag) => (tag.path === path ? { ...tag, title } : tag))
  }
}

function resolveIcon(icon) {
  return iconMap[icon] || iconMap[getMenuIcon(icon)] || AppstoreOutlined
}

function go(path) {
  router.push(path)
}

function closeTag(path) {
  const normalized = normalizePath(path)
  const index = visitedTags.value.findIndex((tag) => tag.path === normalized)
  if (index < 0) return
  visitedTags.value.splice(index, 1)
  if (route.path === normalized) {
    const next = visitedTags.value[index - 1] || visitedTags.value[index] || visitedTags.value[0]
    router.push(next?.path || '/')
  }
}

function handleCommand(command) {
  if (command === 'profile') {
    router.push('/profile')
    return
  }
  if (command === 'logout') {
    clearSession()
    router.push('/login')
  }
}
</script>

<style scoped>
.app-shell {
  min-height: 100vh;
}

.shell-sider {
  box-shadow: 2px 0 10px rgb(15 23 42 / 10%);
}

.brand {
  height: 64px;
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 0 18px;
  border-bottom: 1px solid rgb(255 255 255 / 8%);
}

.brand-logo {
  width: 34px;
  height: 34px;
}

.brand-copy {
  display: grid;
  line-height: 1.1;
}

.brand-kicker {
  font-size: 11px;
  color: rgb(255 255 255 / 55%);
  letter-spacing: 0.08em;
  text-transform: uppercase;
}

.brand-title {
  font-size: 16px;
  font-weight: 700;
  color: #fff;
}

.shell-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  background: #fff;
  padding: 0 20px;
  border-bottom: 1px solid #f0f0f0;
}

.header-left,
.header-right {
  display: flex;
  align-items: center;
  gap: 12px;
}

.collapse-trigger {
  color: #111827;
}

.account-link {
  display: inline-flex;
  align-items: center;
  gap: 10px;
  color: #111827;
}

.account-avatar {
  background: #1677ff;
}

.tags-bar {
  display: flex;
  gap: 8px;
  padding: 10px 16px;
  background: #fff;
  border-bottom: 1px solid #f0f0f0;
  overflow-x: auto;
}

.tag-chip {
  display: inline-flex;
  align-items: center;
  gap: 6px;
  padding: 4px 10px;
  border-radius: 999px;
  background: #f5f5f5;
  color: #4b5563;
  cursor: pointer;
  white-space: nowrap;
}

.tag-chip.active {
  background: #1677ff;
  color: #fff;
}

.tag-close {
  font-size: 12px;
}

.shell-content {
  padding: 16px;
}
</style>
