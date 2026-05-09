<template>
  <el-card class="page-card">
    <template #header>
      <div class="page-header">
        <div>
          <h1>问题统计</h1>
          <p>读取 `/rest/ai/ai-question-stat-list`，按机器人、问题和会话类型统计 AI 问答。</p>
        </div>
        <el-button type="primary" @click="loadRows">刷新</el-button>
      </div>
    </template>

    <el-form inline class="toolbar" @submit.prevent="loadRows">
      <el-form-item label="机器人">
        <el-input v-model.trim="query.robotId" clearable placeholder="robotId" />
      </el-form-item>
      <el-form-item label="问题">
        <el-input v-model.trim="query.question" clearable placeholder="问题关键字" />
      </el-form-item>
      <el-form-item label="类型">
        <el-input v-model.trim="query.chatType" clearable placeholder="chatType" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" native-type="submit">查询</el-button>
        <el-button @click="runClassification">运行自动分类</el-button>
        <el-button type="success" @click="testAiChat">提交测试问答</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="16" class="summary-row">
      <el-col :xs="24" :sm="8">
        <el-card shadow="never"><strong>{{ total }}</strong><span>问题记录</span></el-card>
      </el-col>
      <el-col :xs="24" :sm="8">
        <el-card shadow="never"><strong>{{ robotCount }}</strong><span>机器人数量</span></el-card>
      </el-col>
      <el-col :xs="24" :sm="8">
        <el-card shadow="never"><strong>{{ typeCount }}</strong><span>会话类型</span></el-card>
      </el-col>
    </el-row>

    <el-table :data="rows" border>
      <el-table-column prop="robotId" label="机器人" min-width="120" />
      <el-table-column prop="robotName" label="机器人名称" min-width="140" />
      <el-table-column prop="deptName" label="贵宾室" min-width="140" />
      <el-table-column prop="question" label="问题" min-width="260" />
      <el-table-column prop="answer" label="回答" min-width="260" />
      <el-table-column prop="chatType" label="会话类型" min-width="120" />
      <el-table-column label="自动分类" min-width="120">
        <template #default="{ row }">
          <el-tag :type="String(row.aiAutoClassification) === '1' ? 'success' : 'warning'">
            {{ String(row.aiAutoClassification) === '1' ? '已分类' : '待分类' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="count" label="次数" width="100" />
      <el-table-column prop="createTime" label="时间" min-width="170" />
    </el-table>

    <el-alert v-if="message" class="message-alert" :title="message" type="success" :closable="false" />
    <el-alert v-if="errorMessage" class="message-alert" :title="errorMessage" type="error" :closable="false" />
  </el-card>
</template>

<script setup>
import { computed, onMounted, reactive, ref } from 'vue'
import { request } from '@/api/http'

const rows = ref([])
const total = ref(0)
const query = reactive({ robotId: '', question: '', chatType: '' })
const errorMessage = ref('')
const message = ref('')
const robotCount = computed(() => new Set(rows.value.map((item) => item.robotId).filter(Boolean)).size)
const typeCount = computed(() => new Set(rows.value.map((item) => item.chatType).filter(Boolean)).size)

async function loadRows() {
  errorMessage.value = ''
  message.value = ''
  try {
    const response = await request('/rest/ai/ai-question-stat-list', {
      query: {
        pageNum: 1,
        pageSize: 50,
        ...query
      }
    })
    rows.value = response.rows || []
    total.value = response.total || rows.value.length
  } catch (error) {
    errorMessage.value = error?.payload?.msg || error?.message || '加载失败'
  }
}

async function runClassification() {
  try {
    await request('/rest/ai/run-ai-auto-classification')
    await loadRows()
    message.value = '已提交自动分类任务'
  } catch (error) {
    errorMessage.value = error?.payload?.msg || error?.message || '提交失败'
  }
}

async function testAiChat() {
  try {
    await request('/rest/ai/robot-chat', {
      method: 'POST',
      body: JSON.stringify({
        robotId: query.robotId || 'robot-001',
        message: query.question || '贵宾室在哪里？',
        language: 'CN',
        isNeedVoice: false
      })
    })
    await loadRows()
    message.value = '测试问答已写入统计'
  } catch (error) {
    errorMessage.value = error?.payload?.msg || error?.message || '测试问答提交失败'
  }
}

onMounted(loadRows)
</script>

<style scoped>
.page-card { padding: 24px; }
.page-header { display: flex; justify-content: space-between; align-items: center; gap: 16px; margin-bottom: 18px; }
.page-header h1 { margin: 0; font-size: 28px; }
.page-header p { margin: 8px 0 0; color: var(--text-soft); }
.toolbar { margin-bottom: 16px; }
.summary-row { margin-bottom: 16px; }
.summary-row :deep(.el-card__body) { display: grid; gap: 6px; }
.summary-row strong { font-size: 24px; }
.summary-row span { color: var(--text-soft); }
.message-alert { margin-top: 16px; }
</style>
