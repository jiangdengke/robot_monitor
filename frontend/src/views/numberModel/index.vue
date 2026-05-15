<template>
  <el-card shadow="never" class="page-card">
    <template #header>
      <el-row justify="space-between" align="middle">
        <el-space direction="vertical" alignment="flex-start">
          <el-text tag="b" size="large">桌台模型</el-text>
          <el-text type="info">桌台列表、桌态切换、查找和路径预览已接入当前单体后端。</el-text>
        </el-space>
        <el-button type="primary" @click="loadTables">刷新桌台</el-button>
      </el-row>
    </template>

    <el-row :gutter="16">
      <el-col v-for="table in tables" :key="table.id" :xs="24" :sm="12" :md="8" :lg="6">
        <el-card shadow="hover">
          <el-statistic title="桌号" :value="table.tableNo" />
          <el-text type="info">{{ table.regionName || table.roomCode || '-' }}</el-text>
          <el-tag size="small" :type="String(table.status) === '1' ? 'warning' : 'success'">
            {{ String(table.status) === '1' ? '翻台' : '空闲' }}
          </el-tag>
        </el-card>
      </el-col>
    </el-row>

    <el-tabs v-model="mode" type="card">
      <el-tab-pane label="找客" name="find">
        <FindPanel :tables="tables" />
      </el-tab-pane>
      <el-tab-pane label="巡检" name="walk">
        <WalkPanel :tables="tables" />
      </el-tab-pane>
      <el-tab-pane label="翻台" name="table">
        <DoTable :tables="tables" @refresh="loadTables" />
      </el-tab-pane>
    </el-tabs>
  </el-card>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import DoTable from './doTable.vue'
import FindPanel from './find.vue'
import WalkPanel from './walk.vue'
import { listTables } from '@/api/system'
import { toastError } from '@/utils/toast'

const mode = ref('find')
const tables = ref([])
const errorMessage = ref('')

async function loadTables() {
  errorMessage.value = ''
  try {
    const response = await listTables()
    tables.value = response.rows || response.data || []
  } catch (error) {
    errorMessage.value = error?.payload?.msg || error?.message || '桌台加载失败'
    toastError(errorMessage.value)
  }
}

onMounted(loadTables)
</script>
