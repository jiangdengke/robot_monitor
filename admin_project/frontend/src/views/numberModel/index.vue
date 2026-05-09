<template>
  <el-card class="page-card">
    <template #header>
      <div class="page-header">
        <div>
          <h1>桌台模型</h1>
          <p>桌台列表、桌态提交、机器人引导、查找和路径预览已接入真实接口。</p>
        </div>
        <el-button type="primary" @click="loadTables">刷新桌台</el-button>
      </div>
    </template>

    <el-row :gutter="16" class="table-summary">
      <el-col v-for="table in tables" :key="table.id" :xs="24" :sm="12" :md="8" :lg="6">
        <el-card shadow="hover" class="metric-card">
          <strong>{{ table.tableNo }}</strong>
          <span>{{ table.regionName || table.roomCode || '-' }}</span>
          <el-tag size="small" :type="String(table.status) === '1' ? 'warning' : 'success'">
            {{ String(table.status) === '1' ? '翻台' : '空闲' }}
          </el-tag>
        </el-card>
      </el-col>
    </el-row>

    <el-tabs v-model="mode" class="mode-tabs">
      <el-tab-pane label="执行" name="execute">
        <DoTable :tables="tables" @refresh="loadTables" />
      </el-tab-pane>
      <el-tab-pane label="查找" name="find">
        <FindPanel :tables="tables" />
      </el-tab-pane>
      <el-tab-pane label="路径" name="walk">
        <WalkPanel :tables="tables" />
      </el-tab-pane>
    </el-tabs>
    <el-alert v-if="errorMessage" class="message-alert" :title="errorMessage" type="error" :closable="false" />
  </el-card>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import DoTable from './doTable.vue'
import FindPanel from './find.vue'
import WalkPanel from './walk.vue'
import { request } from '@/api/http'

const mode = ref('execute')
const tables = ref([])
const errorMessage = ref('')

async function loadTables() {
  errorMessage.value = ''
  try {
    const response = await request('/rest/food/tableList', { query: { pageNum: 1, pageSize: 100 } })
    tables.value = response.rows || response.data || []
  } catch (error) {
    errorMessage.value = error?.payload?.msg || error?.message || '桌台加载失败'
  }
}

onMounted(loadTables)
</script>

<style scoped>
.page-card { padding: 24px; }
.page-header { display: flex; justify-content: space-between; align-items: center; gap: 16px; }
.page-header h1 { margin: 0; font-size: 28px; }
.page-header p { margin: 8px 0 0; color: var(--text-soft); }
.table-summary { margin-top: 18px; }
.metric-card { display: grid; gap: 8px; }
.metric-card strong { font-size: 20px; }
.metric-card span { color: var(--text-soft); }
.mode-tabs { margin-top: 18px; }
.message-alert { margin-top: 16px; }
</style>
