<template>
  <el-card class="page-card">
    <template #header>
      <div class="page-header">
        <div>
          <h1>Swagger 文档</h1>
          <p>保留原后台接口文档入口，并提供本地可用性检查。</p>
        </div>
        <el-button type="primary" @click="openSwagger">打开 Swagger</el-button>
      </div>
    </template>

    <el-row :gutter="16">
      <el-col :span="8">
        <el-card shadow="never" class="entry-card">
          <h2>Swagger UI</h2>
          <p>{{ swaggerUrl }}</p>
          <el-button @click="openUrl(swaggerUrl)">新窗口打开</el-button>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card shadow="never" class="entry-card">
          <h2>OpenAPI JSON</h2>
          <p>{{ apiDocsUrl }}</p>
          <el-button @click="openUrl(apiDocsUrl)">查看 JSON</el-button>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card shadow="never" class="entry-card">
          <h2>后端跳转</h2>
          <p>{{ toolSwaggerUrl }}</p>
          <el-button @click="openUrl(toolSwaggerUrl)">访问原入口</el-button>
        </el-card>
      </el-col>
    </el-row>

    <el-card shadow="never" class="status-card">
      <template #header>
        <div class="panel-header">
          <h2>连通性</h2>
          <el-button size="small" @click="checkDocs">检查</el-button>
        </div>
      </template>
      <el-descriptions :column="1" border>
        <el-descriptions-item label="接口前缀">{{ API_BASE }}</el-descriptions-item>
        <el-descriptions-item label="检查结果">
          <el-tag :type="statusType">{{ statusText }}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="说明">
          本地后端未启用 Swagger UI 时，页面仍会保留跳转入口；不影响管理后台其他接口。
        </el-descriptions-item>
      </el-descriptions>
    </el-card>
  </el-card>
</template>

<script setup>
import { computed, ref } from 'vue'
import { API_BASE } from '@/api/http'

const statusText = ref('未检查')
const statusType = ref('info')
const origin = window.location.origin
const swaggerUrl = computed(() => `${origin}${API_BASE}/swagger-ui.html`)
const apiDocsUrl = computed(() => `${origin}${API_BASE}/v3/api-docs`)
const toolSwaggerUrl = computed(() => `${origin}${API_BASE}/tool/swagger`)

function openSwagger() {
  openUrl(swaggerUrl.value)
}

function openUrl(url) {
  window.open(url, '_blank', 'noopener,noreferrer')
}

async function checkDocs() {
  statusText.value = '检查中'
  statusType.value = 'warning'
  try {
    const response = await fetch(`${API_BASE}/v3/api-docs`, { credentials: 'include' })
    statusText.value = response.ok ? 'OpenAPI 可访问' : `接口返回 HTTP ${response.status}`
    statusType.value = response.ok ? 'success' : 'danger'
  } catch (error) {
    statusText.value = error.message || '接口不可访问'
    statusType.value = 'danger'
  }
}
</script>

<style scoped>
.page-card { padding: 24px; }
.page-header,
.panel-header { display: flex; justify-content: space-between; align-items: center; gap: 16px; }
.page-header h1 { margin: 0; font-size: 28px; }
.page-header p,
.entry-card p { margin: 8px 0 0; color: var(--text-soft); }
h2 { margin: 0; font-size: 16px; }
.entry-card { min-height: 170px; }
.entry-card p { min-height: 44px; word-break: break-all; }
.status-card { margin-top: 16px; }
@media (max-width: 1080px) {
  :deep(.el-col) { max-width: 100%; flex: 0 0 100%; margin-bottom: 12px; }
}
</style>
