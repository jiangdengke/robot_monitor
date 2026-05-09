<template>
  <div class="layout-shell">
    <aside class="layout-sidebar">
      <div class="brand-block">
        <div class="brand-mark">CA</div>
        <div>
          <div class="brand-title">智慧贵宾室</div>
          <div class="brand-subtitle">单体后台重建工程</div>
        </div>
      </div>

      <div v-if="session.user" class="user-brief">
        <div class="user-name">{{ session.user.nickName || session.user.userName }}</div>
        <div class="user-meta">{{ session.user.userName }} · {{ session.roles.join(', ') }}</div>
      </div>

      <div class="menu-list">
        <div v-for="group in normalizedMenus" :key="group.path" class="menu-block">
          <div class="menu-group">{{ group.title }}</div>
          <el-menu
            :default-active="route.path"
            background-color="transparent"
            text-color="#edf4fb"
            active-text-color="#ffffff"
          >
            <el-menu-item
              v-for="item in group.children"
              :key="`${group.path}-${item.path}`"
              :index="resolvePath(group.path, item.path)"
              @click="router.push(resolvePath(group.path, item.path))"
            >
              <span>{{ item.title }}</span>
            </el-menu-item>
          </el-menu>
        </div>
      </div>
    </aside>

    <main class="layout-main">
      <el-card class="topbar">
        <div>
          <div class="topbar-title">{{ currentTitle }}</div>
          <div class="topbar-subtitle">当前前端页面已直接对接本地运行中的单体 Spring Boot 后端。</div>
        </div>
        <el-button @click="handleLogout">退出</el-button>
      </el-card>

      <section class="content-shell">
        <router-view />
      </section>
    </main>
  </div>
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

<style scoped>
.layout-shell {
  display: grid;
  grid-template-columns: 320px 1fr;
  min-height: 100vh;
}

.layout-sidebar {
  padding: 24px 18px;
  background: linear-gradient(180deg, rgba(11, 52, 96, 0.98) 0%, rgba(7, 34, 63, 0.98) 100%);
  color: #edf4fb;
}

.brand-block {
  display: flex;
  align-items: center;
  gap: 14px;
  padding: 18px;
  margin-bottom: 18px;
  border: 1px solid rgba(255, 255, 255, 0.12);
  border-radius: 18px;
  background: rgba(255, 255, 255, 0.08);
}

.brand-mark {
  display: grid;
  place-items: center;
  width: 54px;
  height: 54px;
  border-radius: 16px;
  background: linear-gradient(135deg, #ffffff 0%, #c6def8 100%);
  color: #0d4c8f;
  font-weight: 700;
}

.brand-title {
  font-size: 20px;
  font-weight: 700;
}

.brand-subtitle {
  margin-top: 4px;
  font-size: 12px;
  color: rgba(237, 244, 251, 0.7);
}

.user-brief {
  padding: 16px;
  margin-bottom: 14px;
  border-radius: 14px;
  background: rgba(255, 255, 255, 0.08);
}

.user-name {
  font-weight: 700;
}

.user-meta {
  margin-top: 6px;
  font-size: 12px;
  color: rgba(237, 244, 251, 0.72);
}

.menu-list { display: grid; gap: 10px; }
.menu-block :deep(.el-menu) { border-right: 0; }
.menu-block :deep(.el-menu-item) { height: 44px; border-radius: 12px; margin-bottom: 6px; }
.menu-block :deep(.el-menu-item.is-active) { background: rgba(255,255,255,.12); }

.menu-group {
  margin-top: 6px;
  font-size: 12px;
  text-transform: uppercase;
  letter-spacing: 0.08em;
  color: rgba(237, 244, 251, 0.54);
}
.layout-main {
  padding: 24px;
}

.topbar {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 12px;
  padding: 20px 24px;
}

.topbar-title {
  font-size: 24px;
  font-weight: 700;
}

.topbar-subtitle {
  margin-top: 6px;
  color: var(--text-soft);
  font-size: 13px;
}
.content-shell {
  margin-top: 18px;
}

@media (max-width: 1080px) {
  .layout-shell {
    grid-template-columns: 1fr;
  }
}
</style>
