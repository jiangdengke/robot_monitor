<template>
  <el-card class="page-card video-page">
    <template #header>
      <div class="page-header">
        <div>
          <h1>视频资源</h1>
          <p>本地 mock 视频流控制台，可启动、停止、刷新并查看活跃机器人视频会话。</p>
        </div>
        <el-button type="primary" :loading="loading" @click="loadActiveStreams">刷新</el-button>
      </div>
    </template>

    <el-row :gutter="16" class="summary-row">
      <el-col :xs="24" :sm="8">
        <el-card shadow="never" class="summary-card">
          <span>活跃视频流</span>
          <strong>{{ activeRows.length }}</strong>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="8">
        <el-card shadow="never" class="summary-card">
          <span>当前机器人</span>
          <strong>{{ form.robotId || '-' }}</strong>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="8">
        <el-card shadow="never" class="summary-card">
          <span>控制模式</span>
          <strong>mock</strong>
        </el-card>
      </el-col>
    </el-row>

    <el-card shadow="never" class="control-panel">
      <template #header><h2>视频流控制</h2></template>
      <el-form ref="formRef" :model="form" :rules="rules" inline class="toolbar" @submit.prevent="startStream">
        <el-form-item label="机器人" prop="robotId">
          <el-input v-model.trim="form.robotId" placeholder="如 ROBOT-001" />
        </el-form-item>
        <el-form-item label="用户" prop="userId">
          <el-input v-model.trim="form.userId" placeholder="如 admin" />
        </el-form-item>
        <el-form-item>
          <el-button type="success" native-type="submit" :loading="submitting">开始视频流</el-button>
          <el-button type="danger" :disabled="!form.robotId" :loading="submitting" @click="stopStream()">停止视频流</el-button>
          <el-button @click="fillExample">示例</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <el-table v-loading="loading" class="stream-table" :data="activeRows" border>
      <el-table-column prop="robotId" label="机器人" min-width="140" />
      <el-table-column prop="userId" label="用户" min-width="120" />
      <el-table-column prop="mode" label="模式" width="110" />
      <el-table-column prop="startTime" label="开始时间" min-width="170" />
      <el-table-column prop="frameCount" label="帧数" width="90" />
      <el-table-column prop="lastFrameAt" label="最后帧时间" min-width="170">
        <template #default="{ row }">{{ row.lastFrameAt || '-' }}</template>
      </el-table-column>
      <el-table-column label="状态" width="100">
        <template #default>
          <el-tag type="success">传输中</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="140" fixed="right">
        <template #default="{ row }">
          <el-button link type="primary" @click="useRow(row)">填入</el-button>
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

const formRef = ref(null)
const form = reactive({ robotId: '', userId: 'admin' })
const activeStreams = ref({})
const loading = ref(false)
const submitting = ref(false)
const message = ref('')
const errorMessage = ref('')
const rules = {
  robotId: [{ required: true, message: '请输入机器人编号', trigger: 'blur' }],
  userId: [{ required: true, message: '请输入用户编号', trigger: 'blur' }]
}
const activeRows = computed(() =>
  Object.entries(activeStreams.value || {}).map(([robotId, value]) => {
    if (value && typeof value === 'object') {
      return { robotId, ...value }
    }
    return { robotId, userId: value, mode: 'legacy', startTime: '', frameCount: 0, lastFrameAt: '' }
  })
)

async function loadActiveStreams() {
  loading.value = true
  errorMessage.value = ''
  try {
    const response = await request('/rest/video/active')
    activeStreams.value = response.data || {}
  } catch (error) {
    errorMessage.value = error?.payload?.msg || error?.message || '加载失败'
  } finally {
    loading.value = false
  }
}

async function startStream() {
  errorMessage.value = ''
  try {
    await formRef.value?.validate()
  } catch {
    return
  }
  submitting.value = true
  try {
    const response = await request('/rest/video/start', {
      method: 'POST',
      body: JSON.stringify({ robotId: form.robotId, userId: form.userId })
    })
    activeStreams.value = response.data || activeStreams.value
    message.value = '视频流已启动'
    await loadActiveStreams()
  } catch (error) {
    errorMessage.value = error?.payload?.msg || error?.message || '启动失败'
  } finally {
    submitting.value = false
  }
}

async function stopStream(robotId = form.robotId) {
  submitting.value = true
  errorMessage.value = ''
  try {
    const response = await request('/rest/video/stop', {
      method: 'POST',
      body: JSON.stringify({ robotId })
    })
    activeStreams.value = response.data || activeStreams.value
    message.value = '视频流已停止'
    await loadActiveStreams()
  } catch (error) {
    errorMessage.value = error?.payload?.msg || error?.message || '停止失败'
  } finally {
    submitting.value = false
  }
}

function fillExample() {
  form.robotId = 'ROBOT-001'
  form.userId = 'admin'
}

function useRow(row) {
  form.robotId = row.robotId
  form.userId = row.userId || 'admin'
}

onMounted(loadActiveStreams)
</script>

<style scoped>
.page-card { padding: 24px; }
.page-header { display: flex; justify-content: space-between; align-items: center; gap: 16px; margin-bottom: 18px; }
.page-header h1 { margin: 0; font-size: 28px; }
.page-header p { margin: 8px 0 0; color: var(--text-soft); }
.summary-row { row-gap: 16px; margin-bottom: 16px; }
.summary-card :deep(.el-card__body) { display: grid; gap: 8px; }
.summary-card span { color: var(--text-soft); }
.summary-card strong { font-size: 28px; line-height: 1; }
.control-panel h2 { margin: 0; font-size: 16px; }
.toolbar { margin-bottom: -18px; }
.stream-table { margin-top: 16px; }
.message-alert { margin-top: 16px; }
</style>
