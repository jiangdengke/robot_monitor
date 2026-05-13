<template>
  <el-card shadow="never">
    <template #header>
      <el-row justify="space-between" align="middle">
        <el-space direction="vertical" alignment="flex-start">
          <el-text tag="b" size="large">服务监控</el-text>
          <el-text type="info">对齐原后台服务监控页面，展示 CPU、内存、JVM、服务器和磁盘信息。</el-text>
        </el-space>
        <el-button type="primary" :loading="loading" @click="loadInfo">刷新</el-button>
      </el-row>
    </template>

    <el-row :gutter="16">
      <el-col :xs="24" :sm="12" :lg="6">
        <el-card shadow="never">
          <el-statistic title="CPU 核心数" :value="serverInfo.cpu?.cpuNum ?? '-'" />
          <el-text type="info">系统 {{ formatPercent(serverInfo.cpu?.sys) }} / 用户 {{ formatPercent(serverInfo.cpu?.used) }}</el-text>
          <el-progress :percentage="numberValue(serverInfo.cpu?.used)" :stroke-width="10" />
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="12" :lg="6">
        <el-card shadow="never">
          <el-statistic title="内存使用" :value="formatPercent(serverInfo.mem?.usage)" />
          <el-text type="info">{{ serverInfo.mem?.used ?? '-' }} GB / {{ serverInfo.mem?.total ?? '-' }} GB</el-text>
          <el-progress :percentage="numberValue(serverInfo.mem?.usage)" :stroke-width="10" :status="progressStatus(serverInfo.mem?.usage)" />
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="12" :lg="6">
        <el-card shadow="never">
          <el-statistic title="JVM 使用" :value="formatPercent(serverInfo.jvm?.usage)" />
          <el-text type="info">{{ serverInfo.jvm?.used ?? '-' }} MB / {{ serverInfo.jvm?.total ?? '-' }} MB</el-text>
          <el-progress :percentage="numberValue(serverInfo.jvm?.usage)" :stroke-width="10" :status="progressStatus(serverInfo.jvm?.usage)" />
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="12" :lg="6">
        <el-card shadow="never">
          <el-statistic title="CPU 空闲" :value="formatPercent(serverInfo.cpu?.free)" />
          <el-text type="info">等待 {{ formatPercent(serverInfo.cpu?.wait) }}</el-text>
          <el-progress :percentage="numberValue(serverInfo.cpu?.free)" :stroke-width="10" status="success" />
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="16">
      <el-col :xs="24" :lg="12">
        <el-card shadow="never">
          <template #header><el-text tag="b">服务器信息</el-text></template>
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
        <el-card shadow="never">
          <template #header><el-text tag="b">Java 虚拟机信息</el-text></template>
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

    <el-card shadow="never">
      <template #header>
        <el-space direction="vertical" alignment="flex-start">
          <el-text tag="b">磁盘状态</el-text>
          <el-text type="info">展示挂载目录、文件系统类型、容量和使用率。</el-text>
        </el-space>
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

  </el-card>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import { getServerInfo } from '@/api/system'
import { toastError } from '@/utils/toast'

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
    toastError(errorMessage.value)
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
