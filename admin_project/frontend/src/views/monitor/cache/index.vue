<template>
  <el-card class="page-card">
    <template #header>
      <div class="page-header">
        <div>
          <h1>缓存监控</h1>
          <p>直接读取 `/monitor/cache` 接口。</p>
        </div>
        <el-button type="primary" @click="loadInfo">刷新</el-button>
      </div>
    </template>

    <div class="info-grid">
      <el-card shadow="never" class="info-panel">
        <template #header><h2>基础信息</h2></template>
        <p>Key 数量：{{ cacheInfo.dbSize || '-' }}</p>
        <p>Redis 版本：{{ cacheInfo.info?.redis_version || '-' }}</p>
        <p>运行模式：{{ cacheInfo.info?.redis_mode || '-' }}</p>
      </el-card>
      <el-card shadow="never" class="info-panel">
        <template #header><h2>命令统计</h2></template>
        <p v-for="item in cacheInfo.commandStats || []" :key="item.name">
          {{ item.name }}：{{ item.value }}
        </p>
      </el-card>
    </div>

    <el-alert v-if="errorMessage" class="message-alert" :title="errorMessage" type="error" :closable="false" />
  </el-card>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import { getCacheInfo } from '@/api/system'

const cacheInfo = ref({})
const errorMessage = ref('')

async function loadInfo() {
  errorMessage.value = ''
  try {
    const response = await getCacheInfo()
    cacheInfo.value = response.data || {}
  } catch (error) {
    errorMessage.value = error?.payload?.msg || error?.message || '加载失败'
  }
}

onMounted(loadInfo)
</script>

<style scoped>
.page-card { padding: 24px; }
.page-header { display: flex; justify-content: space-between; align-items: center; gap: 16px; margin-bottom: 18px; }
.page-header h1 { margin: 0; font-size: 28px; }
.page-header p { margin: 8px 0 0; color: var(--text-soft); }
.info-grid { display: grid; grid-template-columns: repeat(2, minmax(0, 1fr)); gap: 16px; }
.info-panel h2 { margin: 0; font-size: 16px; }
.info-panel p { margin: 8px 0 0; color: var(--text-soft); }
.message-alert { margin-top: 16px; }
@media (max-width: 960px) { .info-grid { grid-template-columns: 1fr; } }
</style>
