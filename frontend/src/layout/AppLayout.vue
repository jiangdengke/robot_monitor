<template>
  <div :class="['app-wrapper', { hideSidebar: sidebarCollapsed }]">
    <aside class="sidebar-container">
      <div class="sidebar-logo-container">
        <span class="sidebar-logo">CA</span>
        <span class="sidebar-title">国航智慧贵宾室</span>
      </div>
      <el-scrollbar class="sidebar-scrollbar">
        <el-menu
          class="sidebar-menu"
          :default-active="activeMenu"
          :collapse="sidebarCollapsed"
          :collapse-transition="false"
          background-color="#304156"
          text-color="#bfcbd9"
          active-text-color="#409EFF"
          unique-opened
        >
          <template v-for="group in menus" :key="group.path">
            <el-menu-item v-if="!group.children?.length" :index="group.path" @click="go(group.path)">
              <el-icon><component :is="resolveIcon(group.icon)" /></el-icon>
              <span>{{ group.title }}</span>
            </el-menu-item>
            <el-sub-menu v-else :index="group.path">
              <template #title>
                <el-icon><component :is="resolveIcon(group.icon)" /></el-icon>
                <span>{{ group.title }}</span>
              </template>
              <el-menu-item
                v-for="item in group.children"
                :key="item.path"
                :index="item.path"
                @click="go(item.path)"
              >
                <el-icon><component :is="resolveIcon(item.icon)" /></el-icon>
                <span>{{ item.title }}</span>
              </el-menu-item>
            </el-sub-menu>
          </template>
        </el-menu>
      </el-scrollbar>
    </aside>

    <section class="main-container">
      <div class="navbar">
        <div class="navbar-left">
          <span class="hamburger-container" @click="sidebarCollapsed = !sidebarCollapsed">
            <el-icon :size="20">
              <Fold v-if="!sidebarCollapsed" />
              <Expand v-else />
            </el-icon>
          </span>
          <el-breadcrumb class="breadcrumb" separator="/">
            <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item v-if="currentGroup">{{ currentGroup.title }}</el-breadcrumb-item>
            <el-breadcrumb-item v-if="currentTitle">{{ currentTitle }}</el-breadcrumb-item>
          </el-breadcrumb>
        </div>
        <div class="navbar-right">
          <el-dropdown trigger="click" @command="handleCommand">
            <span class="avatar-wrapper">
              <el-avatar class="user-avatar" shape="square">{{ avatarText }}</el-avatar>
              <span>{{ displayName }}</span>
              <el-icon><ArrowDown /></el-icon>
            </span>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item command="profile">个人中心</el-dropdown-item>
                <el-dropdown-item divided command="logout">退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </div>

      <div class="tags-view-container">
        <div class="tags-view-wrapper">
          <span
            v-for="tag in visitedTags"
            :key="tag.path"
            :class="['tags-view-item', { active: tag.path === route.path }]"
            @click="go(tag.path)"
          >
            {{ tag.title }}
            <el-icon v-if="tag.path !== '/'" class="close" @click.stop="closeTag(tag.path)">
              <Close />
            </el-icon>
          </span>
        </div>
      </div>

      <main class="app-main">
        <router-view />
      </main>
    </section>
  </div>
</template>

<script setup>
import { computed, onMounted, ref, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import {
  ArrowDown,
  Bell,
  Box,
  Calendar,
  Camera,
  Close,
  CoffeeCup,
  Cpu,
  DataLine,
  Document,
  Edit,
  Expand,
  Files,
  Fold,
  Grid,
  Headset,
  Histogram,
  House,
  Key,
  Location,
  MapLocation,
  Menu as MenuIcon,
  Monitor,
  Picture,
  PieChart,
  Platform,
  ShoppingCart,
  Tools,
  User,
  UserFilled,
  VideoCamera,
  Warning
} from '@element-plus/icons-vue'
import { clearSession, hydrateSession, sessionState } from '@/stores/session'
import {
  buildFallbackMenus,
  getCanonicalMenuPath,
  getMenuIcon,
  getMenuOrder,
  getMenuTitle,
  getParentPath,
  joinMenuPath,
  normalizePath,
  sortByMenuOrder
} from '@/utils/menuCatalog'

const router = useRouter()
const route = useRoute()
const session = sessionState
const sidebarCollapsed = ref(false)
const visitedTags = ref([{ title: '首页', path: '/' }])

const iconMap = {
  system: Tools,
  monitor: Monitor,
  tool: Tools,
  peoples: UserFilled,
  people: User,
  user: User,
  tree: MenuIcon,
  post: Files,
  dict: Document,
  edit: Edit,
  message: Bell,
  online: UserFilled,
  log: Document,
  logininfor: Key,
  redis: Cpu,
  job: Calendar,
  server: Monitor,
  druid: DataLine,
  robot: Platform,
  map: MapLocation,
  image: Picture,
  table: Grid,
  sound: Headset,
  'tree-table': MenuIcon,
  'video-camera': VideoCamera,
  documentation: Document,
  'digital-twin-view': Monitor,
  walk: Location,
  chart: Histogram,
  warning: Warning,
  food: CoffeeCup,
  menuPlan: Document,
  shopping: ShoppingCart,
  calendar: Calendar,
  code: Document,
  build: Tools,
  swagger: Document,
  dashboard: House,
  list: MenuIcon,
  camera: Camera,
  box: Box
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

const menus = computed(() => {
  const source = session.routers?.length ? normalizeRouterMenus(session.routers) : buildFallbackMenus()
  return sortByMenuOrder(source, (group) => group.path)
    .map((group) => ({
      ...group,
      children: sortByMenuOrder(group.children || [], (item) => item.path)
    }))
    .filter((group) => group.path || group.children.length)
})

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

function normalizeRouterMenus(routers) {
  return routers
    .filter((group) => !group.hidden)
    .map((group) => {
      const groupPath = getCanonicalMenuPath(group.path)
      const visibleChildren = (group.children || []).filter((child) => !child.hidden)
      if (groupPath === '/' && visibleChildren.length === 1) {
        const child = visibleChildren[0]
        const path = getCanonicalMenuPath(joinMenuPath(groupPath, child.path))
        return {
          title: getMenuTitle(path, child.meta?.title || child.name || child.path),
          path,
          icon: getMenuIcon(path, child.meta?.icon || group.meta?.icon || ''),
          children: []
        }
      }
      return {
        title: getMenuTitle(groupPath, group.meta?.title || group.name || group.path),
        path: groupPath,
        icon: getMenuIcon(groupPath, group.meta?.icon || ''),
        children: visibleChildren
          .map((child) => {
            const path = getCanonicalMenuPath(joinMenuPath(groupPath, child.path))
            return {
              title: getMenuTitle(path, child.meta?.title || child.name || child.path),
              path,
              icon: getMenuIcon(path, child.meta?.icon || ''),
              component: child.component || ''
            }
          })
      }
    })
}

function addCurrentTag() {
  const path = normalizePath(route.path)
  const title = currentTitle.value || '管理后台'
  if (route.path === '/login' || route.path === '/register') {
    return
  }
  if (!visitedTags.value.some((tag) => tag.path === path)) {
    visitedTags.value.push({ title, path, order: getMenuOrder(path) })
  } else {
    visitedTags.value = visitedTags.value.map((tag) => (tag.path === path ? { ...tag, title } : tag))
  }
}

function resolveIcon(icon) {
  return iconMap[icon] || iconMap[getMenuIcon(icon)] || Document
}

function go(path) {
  router.push(path)
}

function closeTag(path) {
  const normalized = normalizePath(path)
  const index = visitedTags.value.findIndex((tag) => tag.path === normalized)
  if (index < 0) {
    return
  }
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
