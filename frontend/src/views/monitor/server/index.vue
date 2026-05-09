<template>
  <el-card class="page-card">
    <template #header>
      <div class="page-header">
        <div>
          <h1>服务监控</h1>
          <p>直接读取 `/monitor/server` 接口。</p>
        </div>
        <el-button type="primary" @click="loadInfo">刷新</el-button>
      </div>
    </template>

    <div class="info-grid">
      <el-card shadow="never" class="info-panel">
        <template #header><h2>服务器</h2></template>
        <p>主机名：{{ serverInfo.sys?.computerName || '-' }}</p>
        <p>操作系统：{{ serverInfo.sys?.osName || '-' }}</p>
        <p>IP：{{ serverInfo.sys?.computerIp || '-' }}</p>
      </el-card>
      <el-card shadow="never" class="info-panel">
        <template #header><h2>JVM</h2></template>
        <p>版本：{{ serverInfo.jvm?.version || '-' }}</p>
        <p>总内存：{{ serverInfo.jvm?.total || '-' }}</p>
        <p>已用内存：{{ serverInfo.jvm?.used || '-' }}</p>
      </el-card>
      <el-card shadow="never" class="info-panel">
        <template #header><h2>CPU</h2></template>
        <p>核心数：{{ serverInfo.cpu?.cpuNum || '-' }}</p>
        <p>系统使用率：{{ serverInfo.cpu?.sys || '-' }}</p>
        <p>用户使用率：{{ serverInfo.cpu?.used || '-' }}</p>
      </el-card>
    </div>

    <el-alert v-if="errorMessage" class="message-alert" :title="errorMessage" type="error" :closable="false" />
  </el-card>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import { getServerInfo } from '@/api/system'

const serverInfo = ref({})
const errorMessage = ref('')

async function loadInfo() {
  errorMessage.value = ''
  try {
    const response = await getServerInfo()
    serverInfo.value = response.data || {}
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
.info-grid { display: grid; grid-template-columns: repeat(3, minmax(0, 1fr)); gap: 16px; }
.info-panel h2 { margin: 0; font-size: 16px; }
.info-panel p { margin: 8px 0 0; color: var(--text-soft); }
.message-alert { margin-top: 16px; }
@media (max-width: 960px) { .info-grid { grid-template-columns: 1fr; } }
</style>
