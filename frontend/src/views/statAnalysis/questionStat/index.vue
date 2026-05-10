<template>
  <el-card shadow="never">
    <template #header>
      <el-row justify="space-between" align="middle">
        <el-space direction="vertical" alignment="flex-start">
          <el-text tag="b" size="large">问题统计</el-text>
          <el-text type="info">读取 `/rest/ai/ai-question-stat-list`，按机器人、问题和会话类型统计 AI 问答。</el-text>
        </el-space>
        <el-button type="primary" @click="loadRows">刷新</el-button>
      </el-row>
    </template>

    <el-form inline @submit.prevent="loadRows">
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

    <el-row :gutter="16">
      <el-col :xs="24" :sm="8">
        <el-card shadow="never">
          <el-statistic title="问题记录" :value="total" />
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="8">
        <el-card shadow="never">
          <el-statistic title="机器人数量" :value="robotCount" />
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="8">
        <el-card shadow="never">
          <el-statistic title="会话类型" :value="typeCount" />
        </el-card>
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

    <el-alert v-if="message" :title="message" type="success" :closable="false" />
    <el-alert v-if="errorMessage" :title="errorMessage" type="error" :closable="false" />
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
