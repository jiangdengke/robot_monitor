<template>
  <el-card shadow="never">
    <template #header>
      <el-row justify="space-between" align="middle">
        <el-space direction="vertical" alignment="flex-start">
          <el-text tag="b" size="large">知识库管理</el-text>
          <el-text type="info">读取 `/ai/knowledge/list` 与 `/ai/knowledge/{id}`，并提供新增、编辑、启停、向量化和删除。</el-text>
        </el-space>
        <el-button type="primary" @click="loadRows">刷新</el-button>
      </el-row>
    </template>

    <el-row :gutter="16">
      <el-col :xs="24" :lg="14">
      <el-card shadow="never">
        <template #header>
          <el-space wrap>
            <el-button v-if="canAdd" type="primary" @click="createDraft">新增草稿</el-button>
            <el-button v-if="canEdit" type="success" :disabled="!selectedId" @click="enableSelected">启用</el-button>
            <el-button v-if="canEdit" type="warning" :disabled="!selectedId" @click="disableSelected">禁用</el-button>
            <el-button v-if="canEmbedding" :disabled="!selectedId" @click="embeddingSelected">向量化</el-button>
            <el-button v-if="canQueueNotice" :disabled="!selectedId" @click="queueNotice">队列通知</el-button>
            <el-button v-if="canRemove" type="danger" :disabled="!selectedId" @click="deleteSelected">删除</el-button>
          </el-space>
        </template>
        <el-form inline @submit.prevent="loadRows">
          <el-form-item label="来源">
            <el-input v-model.trim="query.source" clearable placeholder="来源" />
          </el-form-item>
          <el-form-item label="类型">
            <el-select v-model="query.type" clearable placeholder="类型">
              <el-option label="FAQ" value="faq" />
              <el-option label="文档" value="doc" />
              <el-option label="手工" value="manual" />
            </el-select>
          </el-form-item>
          <el-form-item label="启用">
            <el-select v-model="query.enable" clearable placeholder="启用状态">
              <el-option label="启用" value="1" />
              <el-option label="停用" value="0" />
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" native-type="submit">查询</el-button>
            <el-button @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-form>
        <el-table v-loading="loading" :data="rows" border highlight-current-row @current-change="handleCurrentChange">
          <el-table-column prop="id" label="ID" width="90" />
          <el-table-column prop="source" label="来源" min-width="120" />
          <el-table-column prop="type" label="类型" min-width="120" />
          <el-table-column label="状态" width="110">
            <template #default="{ row }">
              <el-tag :type="statusTag(row.status)">{{ statusText(row.status) }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="启用" width="100">
            <template #default="{ row }">
              <el-tag :type="String(row.enable) === '1' ? 'success' : 'info'">
                {{ String(row.enable) === '1' ? '启用' : '停用' }}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="updateTime" label="更新时间" min-width="170" />
        </el-table>
      </el-card>
      </el-col>

      <el-col :xs="24" :lg="10">
      <el-card shadow="never">
        <template #header>
          <el-text tag="b">详情与编辑</el-text>
        </template>
        <el-descriptions :column="1" border>
          <el-descriptions-item label="ID">{{ detail?.id || '-' }}</el-descriptions-item>
          <el-descriptions-item label="来源">{{ detail?.source || '-' }}</el-descriptions-item>
          <el-descriptions-item label="类型">{{ detail?.type || '-' }}</el-descriptions-item>
          <el-descriptions-item label="状态">{{ statusText(detail?.status) }}</el-descriptions-item>
          <el-descriptions-item label="启用">{{ String(detail?.enable) === '1' ? '启用' : '停用' }}</el-descriptions-item>
          <el-descriptions-item label="向量ID">{{ detail?.vectorId || '-' }}</el-descriptions-item>
        </el-descriptions>
        <el-form label-position="top">
          <el-form-item label="知识内容">
            <el-input v-model="draft.content" type="textarea" :rows="8" placeholder="知识内容" />
          </el-form-item>
          <el-form-item label="来源">
            <el-input v-model="draft.source" placeholder="来源" />
          </el-form-item>
          <el-form-item label="类型">
            <el-select v-model="draft.type" placeholder="类型">
              <el-option label="FAQ" value="faq" />
              <el-option label="文档" value="doc" />
              <el-option label="手工" value="manual" />
            </el-select>
          </el-form-item>
          <el-form-item label="备注">
            <el-input v-model="draft.remark" type="textarea" :rows="3" placeholder="备注" />
          </el-form-item>
          <el-button v-if="selectedId ? canEdit : canAdd" type="primary" @click="saveDraft">保存</el-button>
        </el-form>
      </el-card>
      </el-col>
    </el-row>

  </el-card>
</template>

<script setup>
import { computed, onMounted, reactive, ref } from 'vue'
import { request } from '@/api/http'
import { addKnowledge, deleteKnowledge, disableKnowledge, editKnowledge, embeddingKnowledge, enableKnowledge, getKnowledgeDetail } from '@/api/system'
import { hasPermission } from '@/utils/permission'
import { toastError, toastSuccess } from '@/utils/toast'

const rows = ref([])
const detail = ref(null)
const selectedId = ref(null)
const draft = ref({ content: '', source: 'manual', type: 'faq', enable: '1', status: '1', remark: '' })
const query = reactive({ source: '', type: '', enable: '' })
const errorMessage = ref('')
const message = ref('')
const loading = ref(false)
const canAdd = computed(() => hasPermission('ai:knowledge:add'))
const canEdit = computed(() => hasPermission('ai:knowledge:edit'))
const canRemove = computed(() => hasPermission('ai:knowledge:remove'))
const canEmbedding = computed(() => hasPermission('ai:knowledge:embedding'))
const canQueueNotice = computed(() => hasPermission('ai:queue:notice'))

async function loadRows() {
  errorMessage.value = ''
  message.value = ''
  loading.value = true
  try {
    const response = await request('/ai/knowledge/list', { query: { pageNum: 1, pageSize: 20, ...query } })
    rows.value = response.rows || []
    if (rows.value.length) {
      await selectRow(rows.value[0])
    }
  } catch (error) {
    errorMessage.value = error?.payload?.msg || error?.message || '加载失败'
    toastError(errorMessage.value)
  } finally {
    loading.value = false
  }
}

function handleCurrentChange(row) {
  if (row?.id) {
    selectRow(row)
  }
}

async function selectRow(row) {
  try {
    selectedId.value = row.id
    const response = await getKnowledgeDetail(row.id)
    detail.value = response.data || null
    draft.value = {
      content: detail.value?.content || '',
      source: detail.value?.source || 'manual',
      type: detail.value?.type || 'faq',
      enable: detail.value?.enable || '1',
      status: detail.value?.status || '1',
      remark: detail.value?.remark || ''
    }
  } catch (error) {
    errorMessage.value = error?.payload?.msg || error?.message || '详情加载失败'
    toastError(errorMessage.value)
  }
}

function createDraft() {
  selectedId.value = null
  detail.value = null
  draft.value = { content: '', source: 'manual', type: 'faq', enable: '1', status: '1', remark: '' }
}

async function saveDraft() {
  try {
    if (selectedId.value) {
      await editKnowledge({ id: selectedId.value, ...draft.value })
      message.value = '知识已更新'
    } else {
      await addKnowledge({ ...draft.value })
      message.value = '知识已新增'
    }
    toastSuccess(message.value)
    await loadRows()
  } catch (error) {
    errorMessage.value = error?.payload?.msg || error?.message || '保存失败'
    toastError(errorMessage.value)
  }
}

async function enableSelected() {
  if (!selectedId.value) return
  await enableKnowledge([selectedId.value])
  message.value = '已启用所选知识'
  toastSuccess(message.value)
  await loadRows()
}

async function disableSelected() {
  if (!selectedId.value) return
  await disableKnowledge([selectedId.value])
  message.value = '已禁用所选知识'
  toastSuccess(message.value)
  await loadRows()
}

async function embeddingSelected() {
  if (!selectedId.value) return
  await embeddingKnowledge([selectedId.value])
  message.value = '已提交向量化任务'
  toastSuccess(message.value)
  await loadRows()
}

async function deleteSelected() {
  if (!selectedId.value) return
  await deleteKnowledge([selectedId.value])
  message.value = '已删除所选知识'
  toastSuccess(message.value)
  createDraft()
  await loadRows()
}

async function queueNotice() {
  if (!selectedId.value) return
  try {
    await request('/ai/queue/notice', {
      method: 'POST',
      body: JSON.stringify({
        type: 'knowledge',
        id: selectedId.value,
        content: draft.value.content
      })
    })
    message.value = 'AI 队列通知已提交'
    toastSuccess(message.value)
  } catch (error) {
    errorMessage.value = error?.payload?.msg || error?.message || '队列通知提交失败'
    toastError(errorMessage.value)
  }
}

function resetQuery() {
  query.source = ''
  query.type = ''
  query.enable = ''
  loadRows()
}

function statusText(status) {
  const map = { 0: '待处理', 1: '已向量化', 2: '向量化中', 3: '已向量化', 4: '向量化失败' }
  return map[String(status)] || status || '-'
}

function statusTag(status) {
  const map = { 0: 'warning', 1: 'success', 2: 'primary', 3: 'success', 4: 'danger' }
  return map[String(status)] || 'info'
}

onMounted(loadRows)
</script>
