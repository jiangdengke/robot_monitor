<template>
  <el-card class="page-card">
    <template #header>
      <div class="page-header">
        <div>
          <h1>登录日志</h1>
          <p>直接读取 `/monitor/logininfor/list` 接口。</p>
        </div>
        <el-button type="primary" @click="loadRows">刷新</el-button>
      </div>
    </template>
    <el-table :data="rows" border>
      <el-table-column prop="userName" label="账号" min-width="120" />
      <el-table-column prop="ipaddr" label="IP" min-width="140" />
      <el-table-column prop="loginLocation" label="地点" min-width="160" />
      <el-table-column prop="browser" label="浏览器" min-width="140" />
      <el-table-column label="状态" width="100">
        <template #default="{ row }">
          <el-tag :type="row.status === '0' ? 'success' : 'danger'">{{ row.status === '0' ? '成功' : '失败' }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="loginTime" label="时间" min-width="180" />
    </el-table>
    <el-alert v-if="errorMessage" class="message-alert" :title="errorMessage" type="error" :closable="false" />
  </el-card>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import { listLogininfor } from '@/api/system'

const rows = ref([])
const errorMessage = ref('')

async function loadRows() {
  errorMessage.value = ''
  try {
    const response = await listLogininfor({ pageNum: 1, pageSize: 30 })
    rows.value = response.rows || []
  } catch (error) {
    errorMessage.value = error?.payload?.msg || error?.message || '加载失败'
  }
}

onMounted(loadRows)
</script>

<style scoped>
.page-card { padding: 24px; }
.page-header { display: flex; justify-content: space-between; align-items: center; gap: 16px; margin-bottom: 18px; }
.page-header h1 { margin: 0; font-size: 28px; }
.page-header p { margin: 8px 0 0; color: var(--text-soft); }
.message-alert { margin-top: 16px; }
</style>
