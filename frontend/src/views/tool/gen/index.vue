<template>
  <el-card class="page-card">
    <template #header>
      <div class="page-header">
        <div>
          <h1>代码生成</h1>
          <p>数据库表导入、生成配置、字段联动、代码预览、同步和下载均接入本地后端。</p>
        </div>
        <div class="header-actions">
          <el-button @click="loadRows">刷新</el-button>
          <el-button type="primary" @click="openImport">导入表</el-button>
          <el-button :disabled="!selectedRows.length" @click="batchDownload">批量生成</el-button>
          <el-button type="danger" plain :disabled="!selectedRows.length" @click="batchRemove">批量删除</el-button>
        </div>
      </div>
    </template>

    <el-form class="search-form" inline @submit.prevent="loadRows">
      <el-form-item label="表名">
        <el-input v-model="query.tableName" clearable placeholder="请输入表名" />
      </el-form-item>
      <el-form-item label="说明">
        <el-input v-model="query.tableComment" clearable placeholder="请输入表说明" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" native-type="submit">查询</el-button>
        <el-button @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <div class="layout-grid">
      <el-card shadow="never">
        <template #header><h2>已导入表</h2></template>
        <el-table
          :data="rows"
          border
          highlight-current-row
          row-key="tableId"
          @selection-change="handleSelectionChange"
          @current-change="handleCurrentChange"
        >
          <el-table-column type="selection" width="46" />
          <el-table-column prop="tableName" label="表名" min-width="150" />
          <el-table-column prop="tableComment" label="说明" min-width="160" />
          <el-table-column prop="className" label="类名" min-width="150" />
          <el-table-column prop="moduleName" label="模块" width="100" />
          <el-table-column label="操作" width="260" fixed="right">
            <template #default="{ row }">
              <el-button link type="primary" @click="selectRow(row)">配置</el-button>
              <el-button link @click="previewCode(row)">预览</el-button>
              <el-button link @click="syncDb(row)">同步</el-button>
              <el-button link @click="downloadCode(row)">下载</el-button>
              <el-button link type="danger" @click="removeRow(row)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-card>

      <el-card shadow="never">
        <template #header><h2>生成配置</h2></template>
        <el-form v-if="detail?.info" label-position="top">
          <el-row :gutter="12">
            <el-col :span="12">
              <el-form-item label="表名">
                <el-input v-model="detail.info.tableName" disabled />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="实体类名">
                <el-input v-model="detail.info.className" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="功能名称">
                <el-input v-model="detail.info.functionName" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="作者">
                <el-input v-model="detail.info.functionAuthor" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="包路径">
                <el-input v-model="detail.info.packageName" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="生成路径">
                <el-input v-model="detail.info.genPath" />
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="模块名">
                <el-input v-model="detail.info.moduleName" />
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="业务名">
                <el-input v-model="detail.info.businessName" />
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="模板">
                <el-select v-model="detail.info.tplCategory">
                  <el-option label="单表 CRUD" value="crud" />
                  <el-option label="树表" value="tree" />
                  <el-option label="主子表" value="sub" />
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
          <div class="action-row">
            <el-button type="success" @click="saveConfig">保存配置和字段</el-button>
            <el-button @click="syncDb()">同步数据库</el-button>
            <el-button @click="previewCode()">预览代码</el-button>
            <el-button type="primary" @click="generateCode">生成</el-button>
            <el-button @click="downloadCode()">下载</el-button>
          </div>
        </el-form>
        <el-empty v-else description="选择一张表后配置生成规则" />
      </el-card>
    </div>

    <el-card v-if="detail?.rows?.length" shadow="never" class="column-panel">
      <template #header>
        <div class="panel-header">
          <h2>字段联动配置</h2>
          <span>字段开关、查询方式、表单控件和字典类型会随保存落库。</span>
        </div>
      </template>
      <el-table :data="detail.rows" border row-key="columnId">
        <el-table-column label="排序" width="82">
          <template #default="{ $index }">
            <el-button-group>
              <el-button size="small" :disabled="$index === 0" @click="moveColumn($index, -1)">上</el-button>
              <el-button size="small" :disabled="$index === detail.rows.length - 1" @click="moveColumn($index, 1)">下</el-button>
            </el-button-group>
          </template>
        </el-table-column>
        <el-table-column prop="columnName" label="字段" min-width="150" />
        <el-table-column prop="javaField" label="Java 字段" min-width="150">
          <template #default="{ row }"><el-input v-model="row.javaField" /></template>
        </el-table-column>
        <el-table-column prop="columnComment" label="说明" min-width="170">
          <template #default="{ row }"><el-input v-model="row.columnComment" /></template>
        </el-table-column>
        <el-table-column label="Java 类型" min-width="130">
          <template #default="{ row }">
            <el-select v-model="row.javaType">
              <el-option label="String" value="String" />
              <el-option label="Long" value="Long" />
              <el-option label="Integer" value="Integer" />
              <el-option label="BigDecimal" value="BigDecimal" />
              <el-option label="LocalDateTime" value="LocalDateTime" />
            </el-select>
          </template>
        </el-table-column>
        <el-table-column label="必填" width="76">
          <template #default="{ row }"><el-switch v-model="row.isRequired" active-value="1" inactive-value="0" /></template>
        </el-table-column>
        <el-table-column label="新增" width="76">
          <template #default="{ row }"><el-switch v-model="row.isInsert" active-value="1" inactive-value="0" /></template>
        </el-table-column>
        <el-table-column label="编辑" width="76">
          <template #default="{ row }"><el-switch v-model="row.isEdit" active-value="1" inactive-value="0" /></template>
        </el-table-column>
        <el-table-column label="列表" width="76">
          <template #default="{ row }"><el-switch v-model="row.isList" active-value="1" inactive-value="0" /></template>
        </el-table-column>
        <el-table-column label="查询" width="76">
          <template #default="{ row }"><el-switch v-model="row.isQuery" active-value="1" inactive-value="0" /></template>
        </el-table-column>
        <el-table-column label="查询方式" min-width="120">
          <template #default="{ row }">
            <el-select v-model="row.queryType" :disabled="row.isQuery !== '1'">
              <el-option label="等于" value="EQ" />
              <el-option label="模糊" value="LIKE" />
              <el-option label="范围" value="BETWEEN" />
              <el-option label="不等于" value="NE" />
            </el-select>
          </template>
        </el-table-column>
        <el-table-column label="控件" min-width="130">
          <template #default="{ row }">
            <el-select v-model="row.htmlType">
              <el-option label="输入框" value="input" />
              <el-option label="文本域" value="textarea" />
              <el-option label="下拉框" value="select" />
              <el-option label="单选框" value="radio" />
              <el-option label="复选框" value="checkbox" />
              <el-option label="日期控件" value="datetime" />
            </el-select>
          </template>
        </el-table-column>
        <el-table-column label="字典类型" min-width="160">
          <template #default="{ row }"><el-input v-model="row.dictType" placeholder="如 sys_normal_disable" /></template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-card v-if="previewEntries.length" shadow="never" class="preview-output">
      <template #header>
        <div class="panel-header">
          <h2>代码预览</h2>
          <el-button size="small" @click="copyActivePreview">复制当前代码</el-button>
        </div>
      </template>
      <el-tabs v-model="activePreview">
        <el-tab-pane v-for="item in previewEntries" :key="item.name" :name="item.name" :label="item.name">
          <pre>{{ item.code }}</pre>
        </el-tab-pane>
      </el-tabs>
    </el-card>

    <el-dialog v-model="importVisible" title="导入数据库表" width="820px">
      <el-form inline @submit.prevent="loadDbRows">
        <el-form-item label="表名">
          <el-input v-model="dbQuery.tableName" clearable placeholder="支持模糊查询" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" native-type="submit">查询</el-button>
        </el-form-item>
      </el-form>
      <el-table :data="dbRows" border row-key="tableName" @selection-change="handleDbSelectionChange">
        <el-table-column type="selection" width="46" />
        <el-table-column prop="tableName" label="表名" min-width="180" />
        <el-table-column prop="tableComment" label="说明" min-width="240" />
      </el-table>
      <template #footer>
        <el-button @click="importVisible = false">取消</el-button>
        <el-button type="primary" :disabled="!selectedDbRows.length" @click="importTables">导入</el-button>
      </template>
    </el-dialog>
  </el-card>
</template>

<script setup>
import { ElMessage, ElMessageBox } from 'element-plus'
import { computed, onMounted, reactive, ref } from 'vue'
import { downloadFile, request } from '@/api/http'

const rows = ref([])
const selectedRows = ref([])
const detail = ref(null)
const selectedTable = ref(null)
const previewMap = ref({})
const activePreview = ref('')
const importVisible = ref(false)
const dbRows = ref([])
const selectedDbRows = ref([])
const query = reactive({ tableName: '', tableComment: '' })
const dbQuery = reactive({ tableName: '' })
const previewEntries = computed(() => Object.entries(previewMap.value || {}).map(([name, code]) => ({ name, code })))

async function loadRows() {
  const response = await request('/tool/gen/list', { query: { pageNum: 1, pageSize: 100, ...query } })
  rows.value = response.rows || []
  if (!selectedTable.value && rows.value.length) {
    await selectRow(rows.value[0])
  }
}

function resetQuery() {
  query.tableName = ''
  query.tableComment = ''
  loadRows()
}

function handleSelectionChange(selection) {
  selectedRows.value = selection
}

function handleDbSelectionChange(selection) {
  selectedDbRows.value = selection
}

function handleCurrentChange(row) {
  if (row?.tableId) selectRow(row)
}

async function selectRow(row) {
  selectedTable.value = row
  detail.value = await request(`/tool/gen/${row.tableId}`).then((res) => res.data || res)
  normalizeDetail()
  previewMap.value = {}
  activePreview.value = ''
}

function normalizeDetail() {
  if (!detail.value?.info) return
  detail.value.info.tplCategory ||= 'crud'
  detail.value.info.genType ||= '0'
  detail.value.info.genPath ||= '/'
  detail.value.info.functionAuthor ||= 'system'
  detail.value.rows = (detail.value.rows || []).map((row, index) => ({
    ...row,
    sort: row.sort || index + 1,
    isPk: row.isPk || '0',
    isIncrement: row.isIncrement || '0',
    isRequired: row.isRequired || '0',
    isInsert: row.isInsert || '1',
    isEdit: row.isEdit || '1',
    isList: row.isList || '1',
    isQuery: row.isQuery || '0',
    queryType: row.queryType || 'EQ',
    htmlType: row.htmlType || 'input',
    dictType: row.dictType || ''
  }))
}

async function saveConfig() {
  if (!detail.value?.info) return
  await request('/tool/gen', {
    method: 'PUT',
    body: JSON.stringify({ info: detail.value.info, rows: detail.value.rows })
  })
  ElMessage.success('生成配置和字段配置已保存')
  await selectRow(selectedTable.value)
  await loadRows()
}

async function previewCode(row = selectedTable.value) {
  const tableId = row?.tableId || detail.value?.info?.tableId
  if (!tableId) return
  if (row?.tableId && row.tableId !== detail.value?.info?.tableId) {
    await selectRow(row)
  }
  const response = await request(`/tool/gen/preview/${tableId}`)
  previewMap.value = response.data || {}
  activePreview.value = previewEntries.value[0]?.name || ''
}

async function generateCode() {
  if (!detail.value?.info?.tableName) return
  await request(`/tool/gen/genCode/${detail.value.info.tableName}`)
  ElMessage.success(`已提交生成：${detail.value.info.tableName}`)
}

async function syncDb(row = selectedTable.value) {
  const tableName = row?.tableName || detail.value?.info?.tableName
  if (!tableName) return
  await request(`/tool/gen/synchDb/${tableName}`)
  ElMessage.success(`已同步数据库：${tableName}`)
  await selectRow(row || selectedTable.value)
}

function downloadCode(row = selectedTable.value) {
  const tableName = row?.tableName || detail.value?.info?.tableName
  if (!tableName) return
  downloadFile(`/tool/gen/download/${tableName}`, { fileName: `${tableName}-code.txt` })
}

function batchDownload() {
  const tableNames = selectedRows.value.map((item) => item.tableName).join(',')
  if (!tableNames) return
  downloadFile('/tool/gen/batchGenCode', { query: { tables: tableNames }, fileName: 'robotmonitor-code.txt' })
}

async function removeRow(row) {
  await ElMessageBox.confirm(`确认删除生成表 "${row.tableName}"？`, '删除确认', { type: 'warning' })
  await request(`/tool/gen/${row.tableId}`, { method: 'DELETE' })
  ElMessage.success('已删除生成表')
  if (selectedTable.value?.tableId === row.tableId) {
    selectedTable.value = null
    detail.value = null
    previewMap.value = {}
  }
  await loadRows()
}

async function batchRemove() {
  await ElMessageBox.confirm('确认删除所选生成表？', '删除确认', { type: 'warning' })
  await request(`/tool/gen/${selectedRows.value.map((item) => item.tableId).join(',')}`, { method: 'DELETE' })
  ElMessage.success('已批量删除')
  selectedTable.value = null
  detail.value = null
  previewMap.value = {}
  await loadRows()
}

function moveColumn(index, direction) {
  const target = index + direction
  if (target < 0 || target >= detail.value.rows.length) return
  const moved = detail.value.rows.splice(index, 1)[0]
  detail.value.rows.splice(target, 0, moved)
  detail.value.rows.forEach((row, rowIndex) => {
    row.sort = rowIndex + 1
  })
}

function openImport() {
  importVisible.value = true
  selectedDbRows.value = []
  loadDbRows()
}

async function loadDbRows() {
  const response = await request('/tool/gen/db/list', { query: { pageNum: 1, pageSize: 100, ...dbQuery } })
  dbRows.value = response.rows || []
}

async function importTables() {
  await request('/tool/gen/importTable', {
    method: 'POST',
    query: { tables: selectedDbRows.value.map((item) => item.tableName).join(',') }
  })
  importVisible.value = false
  ElMessage.success('数据库表已导入')
  selectedTable.value = null
  await loadRows()
}

async function copyActivePreview() {
  const item = previewEntries.value.find((entry) => entry.name === activePreview.value)
  if (!item) return
  await navigator.clipboard?.writeText(item.code)
  ElMessage.success('代码已复制')
}

onMounted(loadRows)
</script>

<style scoped>
.page-card { padding: 24px; }
.page-header,
.panel-header { display: flex; justify-content: space-between; align-items: center; gap: 16px; }
.page-header h1 { margin: 0; font-size: 28px; }
.page-header p { margin: 8px 0 0; color: var(--text-soft); }
.header-actions,
.action-row { display: flex; flex-wrap: wrap; gap: 8px; }
.search-form { margin-top: 18px; }
h2 { margin: 0; font-size: 16px; }
.layout-grid { display: grid; grid-template-columns: 1.1fr .9fr; gap: 16px; margin-top: 8px; }
.column-panel,
.preview-output { margin-top: 16px; }
.panel-header span { color: var(--text-soft); font-size: 13px; }
.preview-output pre { margin: 0; max-height: 560px; overflow: auto; white-space: pre-wrap; color: var(--text-soft); }
@media (max-width: 1180px) { .layout-grid { grid-template-columns: 1fr; } }
</style>
