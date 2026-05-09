<template>
  <el-card class="page-card">
    <template #header>
      <div class="page-header">
        <div>
          <h1>视频资源</h1>
          <p>接入 `/rest/video/start`、`/rest/video/stop`、`/rest/video/active` 管理机器人视频流。</p>
        </div>
        <el-button type="primary" @click="loadActiveStreams">刷新</el-button>
      </div>
    </template>

    <el-form inline class="toolbar" @submit.prevent="startStream">
      <el-form-item label="机器人">
        <el-input v-model.trim="form.robotId" placeholder="robotId" />
      </el-form-item>
      <el-form-item label="用户">
        <el-input v-model.trim="form.userId" placeholder="userId" />
      </el-form-item>
      <el-form-item>
        <el-button type="success" native-type="submit">开始视频流</el-button>
        <el-button type="danger" :disabled="!form.robotId" @click="stopStream">停止视频流</el-button>
      </el-form-item>
    </el-form>

    <el-table :data="activeRows" border>
      <el-table-column prop="robotId" label="机器人" min-width="140" />
      <el-table-column prop="userId" label="用户" min-width="140" />
      <el-table-column label="操作" width="120">
        <template #default="{ row }">
          <el-button link type="danger" @click="stopStream(row.robotId)">停止</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-alert v-if="message" class="message-alert" :title="message" type="success" :closable="false" />
    <el-alert v-if="errorMessage" class="message-alert" :title="errorMessage" type="error" :closable="false" />
  </el-card>
</template>

<script setup>
import { computed, onMounted, reactive, ref } from 'vue'
import { request } from '@/api/http'

const form = reactive({ robotId: '', userId: 'admin' })
const activeStreams = ref({})
const message = ref('')
const errorMessage = ref('')
const activeRows = computed(() => Object.entries(activeStreams.value || {}).map(([robotId, userId]) => ({ robotId, userId })))

async function loadActiveStreams() {
  errorMessage.value = ''
  try {
    const response = await request('/rest/video/active')
    activeStreams.value = response.data || {}
  } catch (error) {
    errorMessage.value = error?.payload?.msg || error?.message || '加载失败'
  }
}

async function startStream() {
  try {
    await request('/rest/video/start', {
      method: 'POST',
      body: JSON.stringify({ robotId: form.robotId, userId: form.userId })
    })
    message.value = '视频流已启动'
    await loadActiveStreams()
  } catch (error) {
    errorMessage.value = error?.payload?.msg || error?.message || '启动失败'
  }
}

async function stopStream(robotId = form.robotId) {
  try {
    await request('/rest/video/stop', {
      method: 'POST',
      body: JSON.stringify({ robotId })
    })
    message.value = '视频流已停止'
    await loadActiveStreams()
  } catch (error) {
    errorMessage.value = error?.payload?.msg || error?.message || '停止失败'
  }
}

onMounted(loadActiveStreams)
</script>

<style scoped>
.page-card { padding: 24px; }
.page-header { display: flex; justify-content: space-between; align-items: center; gap: 16px; margin-bottom: 18px; }
.page-header h1 { margin: 0; font-size: 28px; }
.page-header p { margin: 8px 0 0; color: var(--text-soft); }
.toolbar { margin-bottom: 16px; }
.message-alert { margin-top: 16px; }
</style>
