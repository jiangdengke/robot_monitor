<template>
  <el-container>
    <el-aside width="280px">
      <el-card shadow="never">
        <el-space direction="vertical" fill>
          <el-space>
            <el-avatar shape="square">CA</el-avatar>
            <el-space direction="vertical" alignment="flex-start">
              <el-text tag="b">智慧贵宾室</el-text>
              <el-text type="info" size="small">单体后台重建工程</el-text>
            </el-space>
          </el-space>

          <el-descriptions v-if="session.user" :column="1" border>
            <el-descriptions-item label="当前用户">
              {{ session.user.nickName || session.user.userName }}
            </el-descriptions-item>
            <el-descriptions-item label="账号">
              {{ session.user.userName }}
            </el-descriptions-item>
            <el-descriptions-item label="角色">
              {{ session.roles.join(', ') }}
            </el-descriptions-item>
          </el-descriptions>

          <el-menu :default-active="route.path">
            <el-sub-menu v-for="group in normalizedMenus" :key="group.path" :index="group.path">
              <template #title>
                <span>{{ group.title }}</span>
              </template>
              <el-menu-item
                v-for="item in group.children"
                :key="`${group.path}-${item.path}`"
                :index="resolvePath(group.path, item.path)"
                @click="router.push(resolvePath(group.path, item.path))"
              >
                {{ item.title }}
              </el-menu-item>
            </el-sub-menu>
          </el-menu>
        </el-space>
      </el-card>
    </el-aside>

    <el-container>
      <el-header height="auto">
        <el-card shadow="never">
          <el-row justify="space-between" align="middle">
            <el-space direction="vertical" alignment="flex-start">
              <el-text tag="b" size="large">{{ currentTitle }}</el-text>
              <el-text type="info" size="small">当前前端页面已直接对接本地运行中的单体 Spring Boot 后端。</el-text>
            </el-space>
            <el-button @click="handleLogout">退出</el-button>
          </el-row>
        </el-card>
      </el-header>

      <el-main>
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { computed, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { clearSession, hydrateSession, sessionState } from '@/stores/session'

const router = useRouter()
const route = useRoute()
const session = sessionState

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

const normalizedMenus = computed(() =>
  (session.routers || []).map((item) => ({
    title: item.meta?.title || item.name || item.path,
    path: item.path,
    children: (item.children || []).map((child) => ({
      title: child.meta?.title || child.name || child.path,
      path: child.path,
      component: child.component || ''
    }))
  }))
)

const currentTitle = computed(() => {
  const currentPath = route.path
  for (const group of normalizedMenus.value) {
    for (const item of group.children) {
      if (resolvePath(group.path, item.path) === currentPath) {
        return item.title
      }
    }
  }
  return '管理后台'
})

function resolvePath(parent, child) {
  return `${parent}/${child}`.replace(/\/+/g, '/')
}

function handleLogout() {
  clearSession()
  router.push('/login')
}
</script>
