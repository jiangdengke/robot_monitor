<template>
  <el-card class="page-card server-page">
    <template #header>
      <div class="page-header">
        <div>
          <h1>服务监控</h1>
          <p>对齐原后台服务监控页面，展示 CPU、内存、JVM、服务器和磁盘信息。</p>
        </div>
        <el-button type="primary" :loading="loading" @click="loadInfo">刷新</el-button>
      </div>
    </template>

    <el-row :gutter="16" class="metric-row">
      <el-col :xs="24" :sm="12" :lg="6">
        <el-card shadow="never" class="metric-card">
          <span>CPU 核心数</span>
          <strong>{{ serverInfo.cpu?.cpuNum ?? '-' }}</strong>
          <small>系统 {{ formatPercent(serverInfo.cpu?.sys) }} / 用户 {{ formatPercent(serverInfo.cpu?.used) }}</small>
          <el-progress :percentage="numberValue(serverInfo.cpu?.used)" :stroke-width="10" />
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="12" :lg="6">
        <el-card shadow="never" class="metric-card">
          <span>内存使用</span>
          <strong>{{ formatPercent(serverInfo.mem?.usage) }}</strong>
          <small>{{ serverInfo.mem?.used ?? '-' }} GB / {{ serverInfo.mem?.total ?? '-' }} GB</small>
          <el-progress :percentage="numberValue(serverInfo.mem?.usage)" :stroke-width="10" :status="progressStatus(serverInfo.mem?.usage)" />
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="12" :lg="6">
        <el-card shadow="never" class="metric-card">
          <span>JVM 使用</span>
          <strong>{{ formatPercent(serverInfo.jvm?.usage) }}</strong>
          <small>{{ serverInfo.jvm?.used ?? '-' }} MB / {{ serverInfo.jvm?.total ?? '-' }} MB</small>
          <el-progress :percentage="numberValue(serverInfo.jvm?.usage)" :stroke-width="10" :status="progressStatus(serverInfo.jvm?.usage)" />
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="12" :lg="6">
        <el-card shadow="never" class="metric-card">
          <span>CPU 空闲</span>
          <strong>{{ formatPercent(serverInfo.cpu?.free) }}</strong>
          <small>等待 {{ formatPercent(serverInfo.cpu?.wait) }}</small>
          <el-progress :percentage="numberValue(serverInfo.cpu?.free)" :stroke-width="10" status="success" />
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="16" class="detail-row">
      <el-col :xs="24" :lg="12">
        <el-card shadow="never" class="info-panel">
          <template #header><h2>服务器信息</h2></template>
          <el-descriptions :column="1" border>
            <el-descriptions-item label="服务器名称">{{ serverInfo.sys?.computerName || '-' }}</el-descriptions-item>
            <el-descriptions-item label="服务器 IP">{{ serverInfo.sys?.computerIp || '-' }}</el-descriptions-item>
            <el-descriptions-item label="操作系统">{{ serverInfo.sys?.osName || '-' }}</el-descriptions-item>
            <el-descriptions-item label="系统架构">{{ serverInfo.sys?.osArch || '-' }}</el-descriptions-item>
            <el-descriptions-item label="项目路径">{{ serverInfo.sys?.userDir || '-' }}</el-descriptions-item>
          </el-descriptions>
        </el-card>
      </el-col>

      <el-col :xs="24" :lg="12">
        <el-card shadow="never" class="info-panel">
          <template #header><h2>Java 虚拟机信息</h2></template>
          <el-descriptions :column="1" border>
            <el-descriptions-item label="JVM 名称">{{ serverInfo.jvm?.name || '-' }}</el-descriptions-item>
            <el-descriptions-item label="Java 版本">{{ serverInfo.jvm?.version || '-' }}</el-descriptions-item>
            <el-descriptions-item label="启动时间">{{ serverInfo.jvm?.startTime || '-' }}</el-descriptions-item>
            <el-descriptions-item label="运行时长">{{ serverInfo.jvm?.runTime || '-' }}</el-descriptions-item>
            <el-descriptions-item label="安装路径">{{ serverInfo.jvm?.home || '-' }}</el-descriptions-item>
          </el-descriptions>
        </el-card>
      </el-col>
    </el-row>

    <el-card shadow="never" class="disk-panel">
      <template #header>
        <div class="panel-header">
          <h2>磁盘状态</h2>
          <span>展示挂载目录、文件系统类型、容量和使用率。</span>
        </div>
      </template>
      <el-table :data="serverInfo.sysFiles || []" border>
        <el-table-column prop="dirName" label="盘符路径" min-width="180" show-overflow-tooltip />
        <el-table-column prop="sysTypeName" label="文件系统" min-width="110" />
        <el-table-column prop="typeName" label="类型名称" min-width="150" show-overflow-tooltip />
        <el-table-column prop="total" label="总大小" width="110" />
        <el-table-column prop="free" label="可用大小" width="110" />
        <el-table-column prop="used" label="已用大小" width="110" />
        <el-table-column label="使用率" min-width="180">
          <template #default="{ row }">
            <el-progress :percentage="numberValue(row.usage)" :status="progressStatus(row.usage)" />
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-alert v-if="errorMessage" class="message-alert" :title="errorMessage" type="error" :closable="false" />
  </el-card>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import { getServerInfo } from '@/api/system'

const serverInfo = ref({})
const errorMessage = ref('')
const loading = ref(false)

async function loadInfo() {
  errorMessage.value = ''
  loading.value = true
  try {
    const response = await getServerInfo()
    serverInfo.value = response.data || {}
  } catch (error) {
    errorMessage.value = error?.payload?.msg || error?.message || '加载失败'
  } finally {
    loading.value = false
  }
}

function numberValue(value) {
  const parsed = Number(value)
  if (Number.isNaN(parsed)) {
    return 0
  }
  return Math.min(100, Math.max(0, Number(parsed.toFixed(2))))
}

function formatPercent(value) {
  return `${numberValue(value)}%`
}

function progressStatus(value) {
  const usage = numberValue(value)
  if (usage >= 90) return 'exception'
  if (usage >= 75) return 'warning'
  return 'success'
}

onMounted(loadInfo)
</script>

<style scoped>
.page-card { padding: 24px; }
.page-header,
.panel-header { display: flex; justify-content: space-between; align-items: center; gap: 16px; }
.page-header { margin-bottom: 18px; }
.page-header h1 { margin: 0; font-size: 28px; }
.page-header p { margin: 8px 0 0; color: var(--text-soft); }
.metric-row,
.detail-row { row-gap: 16px; margin-bottom: 16px; }
.metric-card :deep(.el-card__body) { display: grid; gap: 10px; }
.metric-card span,
.metric-card small,
.panel-header span { color: var(--text-soft); }
.metric-card strong { font-size: 30px; line-height: 1; }
.info-panel h2,
.disk-panel h2 { margin: 0; font-size: 16px; }
.disk-panel { margin-top: 16px; }
.message-alert { margin-top: 16px; }
</style>
