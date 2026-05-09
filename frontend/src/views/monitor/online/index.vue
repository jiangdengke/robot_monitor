<template>
  <el-card class="page-card">
    <template #header>
      <div class="page-header">
        <div>
          <h1>在线用户</h1>
          <p>直接读取 `/monitor/online/list` 接口。</p>
        </div>
        <el-button type="primary" @click="loadRows">刷新</el-button>
      </div>
    </template>
    <el-table :data="rows" border>
      <el-table-column prop="userName" label="账号" min-width="120" />
      <el-table-column prop="ipaddr" label="IP" min-width="140" />
      <el-table-column prop="browser" label="浏览器" min-width="140" />
      <el-table-column prop="os" label="系统" min-width="140" />
      <el-table-column prop="loginTime" label="登录时间" min-width="180" />
    </el-table>
    <el-alert v-if="errorMessage" class="message-alert" :title="errorMessage" type="error" :closable="false" />
  </el-card>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import { listOnlineUsers } from '@/api/system'

const rows = ref([])
const errorMessage = ref('')

async function loadRows() {
  errorMessage.value = ''
  try {
    const response = await listOnlineUsers({ pageNum: 1, pageSize: 50 })
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
