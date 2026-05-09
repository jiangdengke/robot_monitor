<template>
  <el-card class="page-card">
    <template #header>
      <div class="page-header">
        <div>
          <h1>代码生成</h1>
          <p>表配置、字段配置、代码预览、同步数据库和生成动作均已接入真实接口。</p>
        </div>
        <div class="header-actions">
          <el-button @click="loadRows">刷新</el-button>
          <el-button type="primary" @click="openImport">导入表</el-button>
        </div>
      </div>
    </template>

    <div class="layout-grid">
      <el-card shadow="never">
        <template #header><h2>已导入表</h2></template>
        <el-table :data="rows" border highlight-current-row @current-change="handleCurrentChange">
          <el-table-column prop="tableId" label="表 ID" width="90" />
          <el-table-column prop="tableName" label="表名" min-width="160" />
          <el-table-column prop="className" label="类名" min-width="160" />
          <el-table-column prop="moduleName" label="模块" min-width="120" />
        </el-table>
      </el-card>

      <el-card shadow="never">
        <template #header><h2>生成配置</h2></template>
        <el-form v-if="detail?.info" label-position="top">
          <el-form-item label="功能名称">
            <el-input v-model="detail.info.functionName" />
          </el-form-item>
          <el-form-item label="业务名">
            <el-input v-model="detail.info.businessName" />
          </el-form-item>
          <el-form-item label="模块名">
            <el-input v-model="detail.info.moduleName" />
          </el-form-item>
          <el-form-item label="生成模板">
            <el-select v-model="detail.info.tplCategory">
              <el-option label="单表" value="crud" />
              <el-option label="树表" value="tree" />
              <el-option label="主子表" value="sub" />
            </el-select>
          </el-form-item>
          <div class="action-row">
            <el-button type="success" @click="saveConfig">保存配置</el-button>
            <el-button @click="syncDb">同步数据库</el-button>
            <el-button @click="previewCode">预览代码</el-button>
            <el-button type="primary" @click="generateCode">生成代码</el-button>
          </div>
        </el-form>
        <el-empty v-else description="选择一张表" />
      </el-card>
    </div>

    <el-card v-if="detail?.rows?.length" shadow="never" class="column-panel">
      <template #header><h2>字段联动配置</h2></template>
      <el-table :data="detail.rows" border>
        <el-table-column prop="columnName" label="字段" min-width="150" />
        <el-table-column prop="javaField" label="Java 字段" min-width="150">
          <template #default="{ row }"><el-input v-model="row.javaField" /></template>
        </el-table-column>
        <el-table-column prop="columnComment" label="说明" min-width="160">
          <template #default="{ row }"><el-input v-model="row.columnComment" /></template>
        </el-table-column>
        <el-table-column label="查询" width="86">
          <template #default="{ row }"><el-switch v-model="row.isQuery" active-value="1" inactive-value="0" /></template>
        </el-table-column>
        <el-table-column label="列表" width="86">
          <template #default="{ row }"><el-switch v-model="row.isList" active-value="1" inactive-value="0" /></template>
        </el-table-column>
        <el-table-column label="表单" width="86">
          <template #default="{ row }"><el-switch v-model="row.isEdit" active-value="1" inactive-value="0" /></template>
        </el-table-column>
        <el-table-column label="显示类型" min-width="140">
          <template #default="{ row }">
            <el-select v-model="row.htmlType">
              <el-option label="输入框" value="input" />
              <el-option label="文本域" value="textarea" />
              <el-option label="下拉框" value="select" />
              <el-option label="单选框" value="radio" />
              <el-option label="日期控件" value="datetime" />
            </el-select>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-card v-if="previewEntries.length" shadow="never" class="preview-output">
      <template #header><h2>代码预览</h2></template>
      <el-tabs>
        <el-tab-pane v-for="item in previewEntries" :key="item.name" :label="item.name">
          <pre>{{ item.code }}</pre>
        </el-tab-pane>
      </el-tabs>
    </el-card>

    <el-dialog v-model="importVisible" title="导入数据库表" width="760px">
      <el-form inline @submit.prevent="loadDbRows">
        <el-form-item label="表名">
          <el-input v-model="dbQuery.tableName" clearable />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" native-type="submit">查询</el-button>
        </el-form-item>
      </el-form>
      <el-table :data="dbRows" border @selection-change="selectedDbRows = $event">
        <el-table-column type="selection" width="46" />
        <el-table-column prop="tableName" label="表名" min-width="160" />
        <el-table-column prop="tableComment" label="说明" min-width="220" />
      </el-table>
      <template #footer>
        <el-button @click="importVisible = false">取消</el-button>
        <el-button type="primary" :disabled="!selectedDbRows.length" @click="importTables">导入</el-button>
      </template>
    </el-dialog>

    <el-alert v-if="message" class="message-alert" :title="message" :type="messageType" :closable="false" />
  </el-card>
</template>

<script setup>
import { computed, onMounted, reactive, ref } from 'vue'
import { request } from '@/api/http'

const rows = ref([])
const detail = ref(null)
const selectedTable = ref(null)
const previewMap = ref({})
const importVisible = ref(false)
const dbRows = ref([])
const selectedDbRows = ref([])
const dbQuery = reactive({ tableName: '' })
const message = ref('')
const messageType = ref('success')
const previewEntries = computed(() => Object.entries(previewMap.value || {}).map(([name, code]) => ({ name, code })))

async function loadRows() {
  const response = await request('/tool/gen/list', { query: { pageNum: 1, pageSize: 50 } })
  rows.value = response.rows || []
  if (!selectedTable.value && rows.value.length) await selectRow(rows.value[0])
}

function handleCurrentChange(row) {
  if (row?.tableId) selectRow(row)
}

async function selectRow(row) {
  selectedTable.value = row
  detail.value = await request(`/tool/gen/${row.tableId}`).then((res) => res.data || res)
  previewMap.value = {}
}

async function saveConfig() {
  await request('/tool/gen', { method: 'PUT', body: JSON.stringify(detail.value.info) })
  messageType.value = 'success'
  message.value = '生成配置已保存'
  await selectRow(selectedTable.value)
}

async function previewCode() {
  const response = await request(`/tool/gen/preview/${detail.value.info.tableId}`)
  previewMap.value = response.data || {}
}

async function generateCode() {
  await request(`/tool/gen/genCode/${detail.value.info.tableName}`)
  messageType.value = 'success'
  message.value = `已提交生成：${detail.value.info.tableName}`
}

async function syncDb() {
  await request(`/tool/gen/synchDb/${detail.value.info.tableName}`)
  messageType.value = 'success'
  message.value = `已同步数据库：${detail.value.info.tableName}`
  await selectRow(selectedTable.value)
}

function openImport() {
  importVisible.value = true
  loadDbRows()
}

async function loadDbRows() {
  const response = await request('/tool/gen/db/list', { query: { pageNum: 1, pageSize: 50, ...dbQuery } })
  dbRows.value = response.rows || []
}

async function importTables() {
  await request('/tool/gen/importTable', {
    method: 'POST',
    query: { tables: selectedDbRows.value.map((item) => item.tableName).join(',') }
  })
  importVisible.value = false
  messageType.value = 'success'
  message.value = '数据库表已导入'
  await loadRows()
}

onMounted(loadRows)
</script>

<style scoped>
.page-card { padding: 24px; }
.page-header { display: flex; justify-content: space-between; align-items: center; gap: 16px; }
.page-header h1 { margin: 0; font-size: 28px; }
.page-header p { margin: 8px 0 0; color: var(--text-soft); }
.header-actions,
.action-row { display: flex; flex-wrap: wrap; gap: 8px; }
h2 { margin: 0; font-size: 16px; }
.layout-grid { display: grid; grid-template-columns: 1.2fr .8fr; gap: 16px; margin-top: 18px; }
.column-panel,
.preview-output { margin-top: 16px; }
.preview-output pre { margin: 0; white-space: pre-wrap; color: var(--text-soft); }
.message-alert { margin-top: 16px; }
@media (max-width: 1080px) { .layout-grid { grid-template-columns: 1fr; } }
</style>
