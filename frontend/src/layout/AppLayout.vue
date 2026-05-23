<template>
  <a-layout class="app-shell">
    <a-layout-sider
      :collapsed="sidebarCollapsed"
      :width="232"
      :collapsed-width="84"
      theme="dark"
      class="shell-sider"
    >
      <div class="brand">
        <div class="brand-logo">
          <img src="/favicon.ico" alt="国航" />
        </div>
        <div v-if="!sidebarCollapsed" class="brand-copy">
          <span class="brand-title">智慧贵宾室</span>
        </div>
      </div>

      <div class="sider-scroll">
        <a-menu
          :selected-keys="[activeMenu]"
          :open-keys="openKeys"
          mode="inline"
          theme="dark"
          @open-change="(keys) => (openKeys = keys)"
        >
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
      </div>

      <div v-if="!sidebarCollapsed" class="sider-footer">
        <div class="footer-card">
          <div class="footer-line">
            <CheckCircleFilled class="footer-pulse" />
            <span>系统运行正常</span>
          </div>
        </div>
      </div>
    </a-layout-sider>

    <a-layout class="shell-body">
      <a-layout-header class="shell-header">
        <div class="header-left">
          <a-button type="text" class="collapse-trigger" @click="sidebarCollapsed = !sidebarCollapsed">
            <template #icon>
              <MenuUnfoldOutlined v-if="sidebarCollapsed" />
              <MenuFoldOutlined v-else />
            </template>
          </a-button>
          <a-breadcrumb separator="·" class="header-breadcrumb">
            <a-breadcrumb-item>首页</a-breadcrumb-item>
            <a-breadcrumb-item v-if="currentGroup">{{ currentGroup.title }}</a-breadcrumb-item>
            <a-breadcrumb-item v-if="currentTitle">
              <span class="crumb-current">{{ currentTitle }}</span>
            </a-breadcrumb-item>
          </a-breadcrumb>
        </div>
        <div class="header-right">
          <a-tooltip title="刷新页面">
            <a-button type="text" shape="circle" class="header-icon-btn" @click="reload">
              <template #icon><ReloadOutlined /></template>
            </a-button>
          </a-tooltip>
          <a-tooltip title="全屏切换">
            <a-button type="text" shape="circle" class="header-icon-btn" @click="toggleFullscreen">
              <template #icon><FullscreenOutlined /></template>
            </a-button>
          </a-tooltip>
          <a-divider type="vertical" class="header-divider" />
          <a-dropdown placement="bottomRight">
            <a class="account-link" @click.prevent>
              <a-avatar class="account-avatar">{{ avatarText }}</a-avatar>
              <span class="account-name">{{ displayName }}</span>
              <DownOutlined class="account-caret" />
            </a>
            <template #overlay>
              <a-menu @click="({ key }) => handleCommand(key)">
                <a-menu-item key="profile">
                  <UserOutlined />
                  <span>个人中心</span>
                </a-menu-item>
                <a-menu-divider />
                <a-menu-item key="logout">
                  <LogoutOutlined />
                  <span>退出登录</span>
                </a-menu-item>
              </a-menu>
            </template>
          </a-dropdown>
        </div>
      </a-layout-header>

      <div class="tags-bar">
        <div class="tags-scroller">
          <div
            v-for="tag in visitedTags"
            :key="tag.path"
            :class="['tag-chip', { active: tag.path === route.path }]"
            @click="go(tag.path)"
          >
            <span class="tag-dot" />
            <span>{{ tag.title }}</span>
            <CloseOutlined v-if="tag.path !== '/'" class="tag-close" @click.stop="closeTag(tag.path)" />
          </div>
        </div>
      </div>

      <a-layout-content class="shell-content">
        <div class="shell-content-inner">
          <router-view v-slot="{ Component }">
            <transition name="page-fade" mode="out-in">
              <component :is="Component" />
            </transition>
          </router-view>
        </div>
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
  CheckCircleFilled,
  ContainerOutlined,
  DashboardOutlined,
  DeploymentUnitOutlined,
  DownOutlined,
  FileImageOutlined,
  FileSearchOutlined,
  FormOutlined,
  FullscreenOutlined,
  FundProjectionScreenOutlined,
  LogoutOutlined,
  MenuFoldOutlined,
  MenuOutlined,
  MenuUnfoldOutlined,
  PictureOutlined,
  ProfileOutlined,
  ReadOutlined,
  ReloadOutlined,
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
const openKeys = ref([])

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

watch(
  currentGroup,
  (group) => {
    if (group?.path && !openKeys.value.includes(group.path)) {
      openKeys.value = [...openKeys.value, group.path]
    }
  },
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

function reload() {
  router.replace({ path: '/redirect', query: { target: route.fullPath } }).catch(() => {})
  setTimeout(() => router.replace(route.fullPath).catch(() => {}), 30)
}

function toggleFullscreen() {
  if (!document.fullscreenElement) {
    document.documentElement.requestFullscreen?.().catch(() => {})
  } else {
    document.exitFullscreen?.().catch(() => {})
  }
}
</script>

<style scoped>
.app-shell {
  height: 100vh;
  min-height: 0;
  background: var(--surface-app);
  overflow: hidden;
}

.shell-sider {
  background: var(--sider-bg) !important;
  box-shadow: 6px 0 24px rgb(15 23 42 / 12%);
  position: relative;
  height: 100vh;
  overflow: hidden;
  display: flex;
  flex-direction: column;
}

.shell-sider::before {
  content: '';
  position: absolute;
  inset: 0;
  background:
    radial-gradient(circle at 20% 0%, rgb(47 84 235 / 28%), transparent 60%),
    radial-gradient(circle at 80% 100%, rgb(19 194 194 / 14%), transparent 55%);
  pointer-events: none;
}

.shell-sider :deep(.ant-layout-sider-children) {
  display: flex;
  flex-direction: column;
  position: relative;
  z-index: 1;
  height: 100%;
  min-height: 0;
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
  width: 38px;
  height: 38px;
  border-radius: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, rgb(47 84 235 / 30%), rgb(19 194 194 / 26%));
  box-shadow: inset 0 0 0 1px rgb(255 255 255 / 14%);
}

.brand-logo img {
  width: 26px;
  height: 26px;
  filter: drop-shadow(0 1px 2px rgb(0 0 0 / 28%));
}

.brand-copy {
  display: grid;
  line-height: 1.1;
}

.brand-title {
  font-size: 16px;
  font-weight: 700;
  color: #fff;
  letter-spacing: 0.04em;
}

.sider-scroll {
  flex: 1;
  min-height: 0;
  overflow-y: auto;
  overflow-x: hidden;
  padding: 8px 0 16px;
}

.sider-scroll :deep(.ant-menu),
.sider-scroll :deep(.ant-menu-sub),
.sider-scroll :deep(.ant-menu-inline.ant-menu-sub) {
  background: transparent;
  border-inline-end: none !important;
}

.sider-footer {
  padding: 8px 16px 18px;
}

.footer-card {
  border-radius: 12px;
  padding: 12px 14px;
  background: rgb(255 255 255 / 6%);
  border: 1px solid rgb(255 255 255 / 8%);
  color: rgb(232 238 255 / 88%);
}

.footer-line {
  display: inline-flex;
  align-items: center;
  gap: 8px;
  font-size: 13px;
  font-weight: 600;
}

.footer-pulse {
  color: #52c41a;
  filter: drop-shadow(0 0 6px rgb(82 196 26 / 60%));
}

.shell-body {
  background: var(--surface-app);
  height: 100vh;
  min-width: 0;
  min-height: 0;
  overflow: hidden;
}

.shell-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  background: var(--surface-card);
  padding: 0 24px;
  border-bottom: 1px solid var(--border-soft);
  height: 60px;
  flex: 0 0 60px;
  position: sticky;
  top: 0;
  z-index: 5;
  box-shadow: 0 1px 0 var(--border-soft);
}

.header-left,
.header-right {
  display: flex;
  align-items: center;
  gap: 10px;
}

.collapse-trigger {
  color: var(--text-default);
  font-size: 17px;
  width: 36px;
  height: 36px;
}

.header-breadcrumb {
  font-size: 13px;
}

.header-breadcrumb :deep(.ant-breadcrumb-link) {
  color: var(--text-muted);
}

.header-breadcrumb :deep(.ant-breadcrumb-separator) {
  color: var(--text-faint);
  margin: 0 8px;
}

.crumb-current {
  color: var(--text-strong);
  font-weight: 600;
}

.header-icon-btn {
  color: var(--text-muted);
  width: 36px;
  height: 36px;
}

.header-icon-btn:hover {
  color: var(--brand-primary);
  background: rgb(47 84 235 / 8%);
}

.header-divider {
  height: 22px;
  border-left-color: var(--border-default);
  margin: 0 4px;
}

.account-link {
  display: inline-flex;
  align-items: center;
  gap: 10px;
  color: var(--text-default);
  padding: 6px 12px 6px 6px;
  border-radius: 999px;
  transition: background 0.18s ease;
}

.account-link:hover {
  background: var(--surface-overlay);
}

.account-avatar {
  background: linear-gradient(135deg, var(--brand-primary), var(--brand-accent));
  color: #fff;
  font-weight: 600;
}

.account-name {
  font-weight: 600;
}

.account-caret {
  color: var(--text-faint);
  font-size: 11px;
}

.tags-bar {
  background: var(--surface-card);
  border-bottom: 1px solid var(--border-soft);
  padding: 10px 18px;
  flex: 0 0 auto;
}

.tags-scroller {
  display: flex;
  gap: 8px;
  overflow-x: auto;
  padding-bottom: 2px;
  scrollbar-width: thin;
}

.tags-scroller::-webkit-scrollbar {
  height: 4px;
}

.tag-chip {
  display: inline-flex;
  align-items: center;
  gap: 8px;
  padding: 6px 12px;
  border-radius: 999px;
  background: var(--surface-muted);
  color: var(--text-muted);
  font-size: 12px;
  font-weight: 500;
  cursor: pointer;
  white-space: nowrap;
  border: 1px solid transparent;
  transition: all 0.18s ease;
}

.tag-chip:hover {
  color: var(--brand-primary);
  background: rgb(47 84 235 / 6%);
  border-color: rgb(47 84 235 / 18%);
}

.tag-chip.active {
  background: var(--brand-primary);
  color: #fff;
  border-color: var(--brand-primary);
  box-shadow: 0 4px 12px rgb(47 84 235 / 28%);
}

.tag-chip.active .tag-dot {
  background: #fff;
}

.tag-dot {
  width: 6px;
  height: 6px;
  border-radius: 50%;
  background: var(--text-faint);
}

.tag-close {
  font-size: 11px;
  opacity: 0.7;
}

.tag-chip:hover .tag-close,
.tag-chip.active .tag-close {
  opacity: 1;
}

.shell-content {
  background: var(--surface-app);
  padding: 0;
  flex: 1;
  min-height: 0;
  overflow-y: auto;
  overflow-x: hidden;
}

.shell-content-inner {
  padding: 20px 24px 28px;
  min-height: 100%;
}

.page-fade-enter-from {
  opacity: 0;
  transform: translateY(6px);
}

.page-fade-leave-to {
  opacity: 0;
  transform: translateY(-4px);
}

.page-fade-enter-active,
.page-fade-leave-active {
  transition: all 0.22s ease;
}
</style>
