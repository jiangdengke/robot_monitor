<template>
  <el-card shadow="never">
    <template #header>
      <el-row justify="space-between" align="middle">
        <el-space direction="vertical" alignment="flex-start">
          <el-text tag="b" size="large">Swagger 文档</el-text>
          <el-text type="info">保留原后台接口文档入口，并提供本地可用性检查。</el-text>
        </el-space>
        <el-button type="primary" @click="openSwagger">打开 Swagger</el-button>
      </el-row>
    </template>

    <el-row :gutter="16">
      <el-col :xs="24" :md="8">
        <el-card shadow="never">
          <template #header><el-text tag="b">Swagger UI</el-text></template>
          <el-text>{{ swaggerUrl }}</el-text>
          <el-divider />
          <el-button @click="openUrl(swaggerUrl)">新窗口打开</el-button>
        </el-card>
      </el-col>
      <el-col :xs="24" :md="8">
        <el-card shadow="never">
          <template #header><el-text tag="b">OpenAPI JSON</el-text></template>
          <el-text>{{ apiDocsUrl }}</el-text>
          <el-divider />
          <el-button @click="openUrl(apiDocsUrl)">查看 JSON</el-button>
        </el-card>
      </el-col>
      <el-col :xs="24" :md="8">
        <el-card shadow="never">
          <template #header><el-text tag="b">后端跳转</el-text></template>
          <el-text>{{ toolSwaggerUrl }}</el-text>
          <el-divider />
          <el-button @click="openUrl(toolSwaggerUrl)">访问原入口</el-button>
        </el-card>
      </el-col>
    </el-row>

    <el-card shadow="never">
      <template #header>
        <el-row justify="space-between" align="middle">
          <el-text tag="b">连通性</el-text>
          <el-button size="small" @click="checkDocs">检查</el-button>
        </el-row>
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
