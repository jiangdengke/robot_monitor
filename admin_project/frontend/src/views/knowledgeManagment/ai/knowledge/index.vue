<template>
  <el-card class="page-card">
    <template #header>
      <div class="page-header">
        <div>
          <h1>知识库管理</h1>
          <p>读取 `/ai/knowledge/list` 与 `/ai/knowledge/{id}`，并提供新增、编辑、启停、向量化和删除。</p>
        </div>
        <el-button type="primary" @click="loadRows">刷新</el-button>
      </div>
    </template>

    <div class="layout-grid">
      <el-card shadow="never" class="table-panel">
        <template #header>
          <div class="toolbar">
            <el-button type="primary" @click="createDraft">新增草稿</el-button>
            <el-button type="success" :disabled="!selectedId" @click="enableSelected">启用</el-button>
            <el-button type="warning" :disabled="!selectedId" @click="disableSelected">禁用</el-button>
            <el-button :disabled="!selectedId" @click="embeddingSelected">向量化</el-button>
            <el-button type="danger" :disabled="!selectedId" @click="deleteSelected">删除</el-button>
          </div>
        </template>
        <el-table :data="rows" border highlight-current-row @current-change="handleCurrentChange">
          <el-table-column prop="id" label="ID" width="90" />
          <el-table-column prop="source" label="来源" min-width="120" />
          <el-table-column prop="type" label="类型" min-width="120" />
          <el-table-column prop="status" label="状态" width="100" />
          <el-table-column label="启用" width="100">
            <template #default="{ row }">
              <el-tag :type="String(row.enable) === '1' ? 'success' : 'info'">
                {{ String(row.enable) === '1' ? '启用' : '停用' }}
              </el-tag>
            </template>
          </el-table-column>
        </el-table>
      </el-card>

      <el-card shadow="never" class="detail-panel">
        <template #header>
          <h2>详情与编辑</h2>
        </template>
        <div class="detail-meta">
          <p>ID：{{ detail?.id || '-' }}</p>
          <p>来源：{{ detail?.source || '-' }}</p>
          <p>类型：{{ detail?.type || '-' }}</p>
          <p>状态：{{ detail?.status || '-' }}</p>
          <p>启用：{{ detail?.enable || '-' }}</p>
        </div>
        <el-form label-position="top" class="draft-form">
          <el-form-item label="知识内容">
            <el-input v-model="draft.content" type="textarea" :rows="8" placeholder="知识内容" />
          </el-form-item>
          <el-form-item label="来源">
            <el-input v-model="draft.source" placeholder="来源" />
          </el-form-item>
          <el-form-item label="类型">
            <el-input v-model="draft.type" placeholder="类型" />
          </el-form-item>
          <el-button type="primary" @click="saveDraft">保存</el-button>
        </el-form>
      </el-card>
    </div>

    <el-alert v-if="message" class="message-alert" :title="message" type="success" :closable="false" />
    <el-alert v-if="errorMessage" class="message-alert" :title="errorMessage" type="error" :closable="false" />
  </el-card>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import { request } from '@/api/http'
import { addKnowledge, deleteKnowledge, disableKnowledge, editKnowledge, embeddingKnowledge, enableKnowledge, getKnowledgeDetail } from '@/api/system'

const rows = ref([])
const detail = ref(null)
const selectedId = ref(null)
const draft = ref({ content: '', source: 'manual', type: 'faq', enable: '1', status: '1' })
const errorMessage = ref('')
const message = ref('')

async function loadRows() {
  errorMessage.value = ''
  message.value = ''
  try {
    const response = await request('/ai/knowledge/list', { query: { pageNum: 1, pageSize: 20 } })
    rows.value = response.rows || []
    if (rows.value.length) {
      await selectRow(rows.value[0])
    }
  } catch (error) {
    errorMessage.value = error?.payload?.msg || error?.message || '加载失败'
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
      status: detail.value?.status || '1'
    }
  } catch (error) {
    errorMessage.value = error?.payload?.msg || error?.message || '详情加载失败'
  }
}

function createDraft() {
  selectedId.value = null
  detail.value = null
  draft.value = { content: '', source: 'manual', type: 'faq', enable: '1', status: '1' }
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
    await loadRows()
  } catch (error) {
    errorMessage.value = error?.payload?.msg || error?.message || '保存失败'
  }
}

async function enableSelected() {
  if (!selectedId.value) return
  await enableKnowledge([selectedId.value])
  message.value = '已启用所选知识'
  await loadRows()
}

async function disableSelected() {
  if (!selectedId.value) return
  await disableKnowledge([selectedId.value])
  message.value = '已禁用所选知识'
  await loadRows()
}

async function embeddingSelected() {
  if (!selectedId.value) return
  await embeddingKnowledge([selectedId.value])
  message.value = '已提交向量化任务'
  await loadRows()
}

async function deleteSelected() {
  if (!selectedId.value) return
  await deleteKnowledge([selectedId.value])
  message.value = '已删除所选知识'
  createDraft()
  await loadRows()
}

onMounted(loadRows)
</script>

<style scoped>
.page-card { padding: 24px; }
.page-header { display: flex; justify-content: space-between; align-items: center; gap: 16px; margin-bottom: 18px; }
.page-header h1 { margin: 0; font-size: 28px; }
.page-header p { margin: 8px 0 0; color: var(--text-soft); }
.layout-grid { display: grid; grid-template-columns: 1.2fr 0.8fr; gap: 16px; }
.toolbar { display: flex; flex-wrap: wrap; gap: 8px; }
.detail-panel h2 { margin: 0; font-size: 18px; }
.detail-meta p { margin: 8px 0 0; color: var(--text-soft); }
.draft-form { margin-top: 16px; }
.message-alert { margin-top: 16px; }
@media (max-width: 960px) { .layout-grid { grid-template-columns: 1fr; } }
</style>
