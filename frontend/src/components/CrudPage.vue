<template>
  <el-card class="crud-page">
    <template #header>
      <div class="crud-header">
        <div>
          <h1>{{ title }}</h1>
          <p>{{ description }}</p>
        </div>
        <div class="header-actions">
          <el-button @click="loadRows">刷新</el-button>
          <el-button v-if="enableCreate" type="primary" @click="openCreate">新增</el-button>
        </div>
      </div>
    </template>

    <el-form v-if="searchFields.length" inline class="search-form" @submit.prevent="handleSearch">
      <el-form-item v-for="field in searchFields" :key="field.prop" :label="field.label">
        <el-select v-if="field.type === 'select'" v-model="query[field.prop]" clearable :placeholder="field.placeholder || field.label">
          <el-option v-for="option in field.options || []" :key="option.value" :label="option.label" :value="option.value" />
        </el-select>
        <el-input v-else v-model.trim="query[field.prop]" clearable :placeholder="field.placeholder || field.label" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" native-type="submit">查询</el-button>
        <el-button @click="resetSearch">重置</el-button>
      </el-form-item>
    </el-form>

    <div class="table-actions">
      <el-button v-if="enableDelete" type="danger" :disabled="!selectedRows.length" @click="deleteSelected">批量删除</el-button>
      <el-button v-for="action in headerActions" :key="action.key" :type="action.type || 'default'" @click="runHeaderAction(action)">
        {{ action.label }}
      </el-button>
      <el-button v-if="uploadField" @click="uploadVisible = true">上传文件</el-button>
      <slot name="actions" :rows="rows" :load-rows="loadRows" />
    </div>

    <el-table
      v-loading="loading"
      :data="rows"
      border
      stripe
      highlight-current-row
      :row-key="rowKey"
      @selection-change="selectedRows = $event"
    >
      <el-table-column v-if="enableDelete" type="selection" width="46" />
      <el-table-column v-for="column in columns" :key="column.prop" :label="column.label" :min-width="column.minWidth" :width="column.width">
        <template #default="{ row }">
          <el-switch
            v-if="column.switch"
            :model-value="String(getRawValue(row, column))"
            :active-value="column.activeValue || '0'"
            :inactive-value="column.inactiveValue || '1'"
            :disabled="column.disabled?.(row)"
            @change="handleSwitchChange(column, row, $event)"
          />
          <el-image
            v-else-if="column.image"
            class="table-image"
            :src="displayValue(row, column)"
            fit="cover"
            :preview-src-list="[displayValue(row, column)]"
            preview-teleported
          />
          <el-tag v-else-if="column.tag" :type="resolveTagType(column, row)">{{ displayValue(row, column) }}</el-tag>
          <span v-else>{{ displayValue(row, column) }}</span>
        </template>
      </el-table-column>
      <el-table-column v-if="enableEdit || enableDelete || showDetail || rowActions.length" label="操作" fixed="right" :width="operationWidth">
        <template #default="{ row }">
          <el-button v-if="showDetail" link type="primary" @click="openDetail(row)">详情</el-button>
          <el-button v-if="enableEdit" link type="primary" @click="openEdit(row)">编辑</el-button>
          <el-button
            v-for="action in rowActions"
            :key="action.key"
            link
            :type="action.type || 'primary'"
            :disabled="action.disabled?.(row)"
            @click="runRowAction(action, row)"
          >
            {{ action.label }}
          </el-button>
          <el-button v-if="enableDelete" link type="danger" @click="deleteOne(row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination
      v-if="pagination"
      class="pagination"
      background
      layout="total, sizes, prev, pager, next, jumper"
      :current-page="pageNum"
      :page-size="pageSize"
      :page-sizes="[10, 20, 50, 100]"
      :total="total"
      @current-change="pageNum = $event; loadRows()"
      @size-change="pageSize = $event; pageNum = 1; loadRows()"
    />

    <el-dialog v-model="formVisible" :title="formMode === 'create' ? `新增${title}` : `编辑${title}`" width="720px">
      <el-form label-position="top">
        <el-form-item v-for="field in formFields" :key="field.prop" :label="field.label">
          <el-select v-if="field.type === 'select'" v-model="form[field.prop]" clearable :placeholder="field.placeholder || field.label">
            <el-option v-for="option in field.options || []" :key="option.value" :label="option.label" :value="option.value" />
          </el-select>
          <el-input-number v-else-if="field.type === 'number'" v-model="form[field.prop]" controls-position="right" :min="field.min ?? 0" />
          <el-input v-else-if="field.type === 'textarea'" v-model="form[field.prop]" type="textarea" :rows="4" :placeholder="field.placeholder || field.label" />
          <el-input v-else v-model="form[field.prop]" :placeholder="field.placeholder || field.label" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="formVisible = false">取消</el-button>
        <el-button type="primary" @click="submitForm">保存</el-button>
      </template>
    </el-dialog>

    <el-dialog v-model="detailVisible" title="详情" width="720px">
      <el-descriptions :column="2" border>
        <el-descriptions-item v-for="column in columns" :key="column.prop" :label="column.label">
          {{ displayValue(detail, column) }}
        </el-descriptions-item>
      </el-descriptions>
    </el-dialog>

    <el-dialog v-model="uploadVisible" title="上传文件" width="520px">
      <el-upload drag multiple :auto-upload="false" :on-change="handleUploadChange">
        <el-icon class="el-icon--upload"><UploadFilled /></el-icon>
        <div class="el-upload__text">拖入文件或点击选择</div>
      </el-upload>
      <el-alert v-if="uploadMessage" class="message-alert" :title="uploadMessage" :type="uploadMessageType" :closable="false" />
      <template #footer>
        <el-button @click="uploadVisible = false">关闭</el-button>
        <el-button type="primary" :disabled="!pendingFiles.length" @click="submitUpload">上传</el-button>
      </template>
    </el-dialog>

    <el-dialog v-model="promptVisible" :title="promptTitle" width="520px">
      <el-form label-position="top">
        <el-form-item v-for="field in promptFields" :key="field.prop" :label="field.label">
          <el-select v-if="field.type === 'select'" v-model="promptForm[field.prop]" clearable :placeholder="field.placeholder || field.label">
            <el-option v-for="option in field.options || []" :key="option.value" :label="option.label" :value="option.value" />
          </el-select>
          <el-input-number v-else-if="field.type === 'number'" v-model="promptForm[field.prop]" controls-position="right" :min="field.min ?? 0" />
          <el-tree-select
            v-else-if="field.type === 'tree'"
            v-model="promptForm[field.prop]"
            :data="field.options || []"
            :props="{ label: 'label', children: 'children' }"
            multiple
            check-strictly
            show-checkbox
            clearable
          />
          <el-input v-else-if="field.type === 'textarea'" v-model="promptForm[field.prop]" type="textarea" :rows="4" :placeholder="field.placeholder || field.label" />
          <el-input v-else v-model="promptForm[field.prop]" :type="field.inputType || 'text'" :placeholder="field.placeholder || field.label" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="promptVisible = false">取消</el-button>
        <el-button type="primary" @click="submitPromptAction">确认</el-button>
      </template>
    </el-dialog>

    <el-dialog v-model="importVisible" title="导入数据" width="520px">
      <el-upload drag :auto-upload="false" :limit="1" :on-change="handleImportChange">
        <el-icon class="el-icon--upload"><UploadFilled /></el-icon>
        <div class="el-upload__text">拖入 Excel 文件或点击选择</div>
      </el-upload>
      <el-checkbox v-model="importUpdateSupport" class="import-check">更新已存在数据</el-checkbox>
      <el-alert v-if="importMessage" class="message-alert" :title="importMessage" :type="importMessageType" :closable="false" />
      <template #footer>
        <el-button @click="importVisible = false">关闭</el-button>
        <el-button type="primary" :disabled="!importFile" @click="submitImport">导入</el-button>
      </template>
    </el-dialog>

    <el-alert v-if="errorMessage" class="message-alert" :title="errorMessage" type="error" :closable="false" />
    <el-alert v-if="successMessage" class="message-alert" :title="successMessage" type="success" :closable="false" />
  </el-card>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessageBox } from 'element-plus'
import { UploadFilled } from '@element-plus/icons-vue'
import { createResource, deleteResource, getResource, listResource, normalizeRows, normalizeTotal, updateResource, uploadFiles } from '@/api/crud'
import { request } from '@/api/http'

const props = defineProps({
  title: { type: String, required: true },
  description: { type: String, default: '' },
  basePath: { type: String, required: true },
  listPath: { type: String, default: '' },
  listMethod: { type: String, default: 'GET' },
  rowKey: { type: String, required: true },
  columns: { type: Array, default: () => [] },
  searchFields: { type: Array, default: () => [] },
  formFields: { type: Array, default: () => [] },
  defaults: { type: Object, default: () => ({}) },
  enableCreate: { type: Boolean, default: true },
  enableEdit: { type: Boolean, default: true },
  enableDelete: { type: Boolean, default: true },
  showDetail: { type: Boolean, default: true },
  pagination: { type: Boolean, default: true },
  createPath: { type: String, default: '' },
  updatePath: { type: String, default: '' },
  deletePath: { type: String, default: '' },
  createMethod: { type: String, default: 'POST' },
  updateMethod: { type: String, default: 'PUT' },
  deleteMethod: { type: String, default: 'DELETE' },
  uploadField: { type: String, default: '' },
  headerActions: { type: Array, default: () => [] },
  rowActions: { type: Array, default: () => [] },
  importAction: { type: Function, default: null },
  operationWidth: { type: Number, default: 260 },
  beforeSubmit: { type: Function, default: null },
  transformDetail: { type: Function, default: null }
})

const router = useRouter()
const rows = ref([])
const total = ref(0)
const pageNum = ref(1)
const pageSize = ref(20)
const loading = ref(false)
const selectedRows = ref([])
const errorMessage = ref('')
const successMessage = ref('')
const formVisible = ref(false)
const detailVisible = ref(false)
const uploadVisible = ref(false)
const uploadMessage = ref('')
const uploadMessageType = ref('success')
const pendingFiles = ref([])
const promptVisible = ref(false)
const promptTitle = ref('')
const promptFields = ref([])
const promptForm = reactive({})
const pendingPromptAction = ref(null)
const pendingPromptRow = ref(null)
const importVisible = ref(false)
const importFile = ref(null)
const importUpdateSupport = ref(false)
const importMessage = ref('')
const importMessageType = ref('success')
const formMode = ref('create')
const detail = ref({})
const form = reactive({})
const query = reactive({})

function resetObject(target, value = {}) {
  Object.keys(target).forEach((key) => delete target[key])
  Object.assign(target, value)
}

function getByPath(target, path) {
  return path.split('.').reduce((current, key) => current?.[key], target)
}

function getRawValue(row, column) {
  return column.prop.includes('.') ? getByPath(row, column.prop) : row?.[column.prop]
}

function displayValue(row, column) {
  const raw = getRawValue(row, column)
  if (column.formatter) {
    return column.formatter(raw, row)
  }
  const value = column.map ? column.map[String(raw)] : raw
  return value === undefined || value === null || value === '' ? '-' : value
}

function resolveTagType(column, row) {
  const raw = column.prop.includes('.') ? getByPath(row, column.prop) : row?.[column.prop]
  return column.tagMap?.[String(raw)] || column.tag || 'info'
}

async function loadRows() {
  loading.value = true
  errorMessage.value = ''
  try {
    const params = {
      ...(props.pagination ? { pageNum: pageNum.value, pageSize: pageSize.value } : {}),
      ...query
    }
    const payload = props.listPath
      ? await request(props.listPath, { method: props.listMethod, query: params })
      : await listResource(props.basePath, params, props.listMethod)
    rows.value = normalizeRows(payload)
    total.value = normalizeTotal(payload)
  } catch (error) {
    errorMessage.value = error?.payload?.msg || error?.message || '加载失败'
  } finally {
    loading.value = false
  }
}

function handleSearch() {
  pageNum.value = 1
  loadRows()
}

function resetSearch() {
  resetObject(query)
  handleSearch()
}

function openCreate() {
  formMode.value = 'create'
  resetObject(form, { ...props.defaults })
  formVisible.value = true
}

async function openEdit(row) {
  formMode.value = 'edit'
  errorMessage.value = ''
  try {
    const response = await getResource(props.basePath, row[props.rowKey])
    const detailData = props.transformDetail ? props.transformDetail(response, row) : (response.data || row)
    resetObject(form, { ...props.defaults, ...detailData })
    formVisible.value = true
  } catch (error) {
    resetObject(form, { ...props.defaults, ...row })
    formVisible.value = true
  }
}

function openDetail(row) {
  detail.value = row
  detailVisible.value = true
}

async function submitForm() {
  try {
    const payload = props.beforeSubmit ? props.beforeSubmit({ ...form }, formMode.value) : { ...form }
    if (formMode.value === 'create') {
      const path = props.createPath || props.basePath
      await createResource(path, payload, props.createMethod)
      successMessage.value = '新增成功'
    } else {
      const path = props.updatePath || props.basePath
      await updateResource(path, payload, props.updateMethod)
      successMessage.value = '保存成功'
    }
    formVisible.value = false
    await loadRows()
  } catch (error) {
    errorMessage.value = error?.payload?.msg || error?.message || '保存失败'
  }
}

async function handleSwitchChange(column, row, value) {
  const previous = getRawValue(row, column)
  row[column.prop] = value
  try {
    await column.action(row, value)
    successMessage.value = column.successMessage || '状态已更新'
    await loadRows()
  } catch (error) {
    row[column.prop] = previous
    errorMessage.value = error?.payload?.msg || error?.message || '状态更新失败'
  }
}

async function runHeaderAction(action) {
  try {
    if (action.confirm) {
      await ElMessageBox.confirm(action.confirm, action.confirmTitle || '操作确认', { type: action.confirmType || 'warning' })
    }
    if (action.kind === 'import') {
      importVisible.value = true
      importMessage.value = ''
      importFile.value = null
      return
    }
    if (action.route) {
      await router.push(typeof action.route === 'function' ? action.route({ query: { ...query }, selectedRows: selectedRows.value }) : action.route)
      return
    }
    await action.handler?.({ rows: rows.value, selectedRows: selectedRows.value, query: { ...query }, loadRows })
    successMessage.value = action.successMessage || successMessage.value
    if (action.reload !== false) {
      await loadRows()
    }
  } catch (error) {
    if (error !== 'cancel') {
      errorMessage.value = error?.payload?.msg || error?.message || action.errorMessage || '操作失败'
    }
  }
}

async function runRowAction(action, row) {
  try {
    if (action.promptFields?.length) {
      promptTitle.value = action.promptTitle || action.label
      promptFields.value = await resolvePromptFields(action, row)
      resetObject(promptForm, typeof action.promptDefaults === 'function' ? action.promptDefaults(row) : { ...(action.promptDefaults || {}) })
      pendingPromptAction.value = action
      pendingPromptRow.value = row
      promptVisible.value = true
      return
    }
    if (action.route) {
      await router.push(typeof action.route === 'function' ? action.route(row) : action.route)
      return
    }
    if (action.confirm) {
      await ElMessageBox.confirm(typeof action.confirm === 'function' ? action.confirm(row) : action.confirm, action.confirmTitle || '操作确认', { type: action.confirmType || 'warning' })
    }
    await action.handler?.(row, { loadRows })
    successMessage.value = action.successMessage || '操作成功'
    if (action.reload !== false) {
      await loadRows()
    }
  } catch (error) {
    if (error !== 'cancel') {
      errorMessage.value = error?.payload?.msg || error?.message || action.errorMessage || '操作失败'
    }
  }
}

async function resolvePromptFields(action, row) {
  const fields = typeof action.promptFields === 'function' ? await action.promptFields(row) : action.promptFields
  return fields || []
}

async function submitPromptAction() {
  try {
    await pendingPromptAction.value?.handler?.(pendingPromptRow.value, { form: { ...promptForm }, loadRows })
    promptVisible.value = false
    successMessage.value = pendingPromptAction.value?.successMessage || '操作成功'
    await loadRows()
  } catch (error) {
    errorMessage.value = error?.payload?.msg || error?.message || '操作失败'
  }
}

async function deleteSelected() {
  await deleteByIds(selectedRows.value.map((row) => row[props.rowKey]))
}

async function deleteOne(row) {
  await deleteByIds(row[props.rowKey])
}

async function deleteByIds(ids) {
  try {
    await ElMessageBox.confirm('确认删除所选数据？', '删除确认', { type: 'warning' })
    const path = props.deletePath || props.basePath
    await deleteResource(path, ids, props.deleteMethod)
    successMessage.value = '删除成功'
    await loadRows()
  } catch (error) {
    if (error !== 'cancel') {
      errorMessage.value = error?.payload?.msg || error?.message || '删除失败'
    }
  }
}

function handleUploadChange(file, fileList) {
  pendingFiles.value = fileList.map((item) => item.raw).filter(Boolean)
}

async function submitUpload() {
  try {
    const response = await uploadFiles(pendingFiles.value)
    uploadMessageType.value = 'success'
    uploadMessage.value = `上传成功：${response.originalFilenames || response.fileNames || ''}`
    if (props.uploadField && formVisible.value) {
      form[props.uploadField] = String(response.fileNames || '').split(',')[0] || form[props.uploadField]
    }
  } catch (error) {
    uploadMessageType.value = 'error'
    uploadMessage.value = error?.payload?.msg || error?.message || '上传失败'
  }
}

function handleImportChange(file) {
  importFile.value = file.raw
}

async function submitImport() {
  if (!props.importAction) {
    return
  }
  try {
    const response = await props.importAction(importFile.value, importUpdateSupport.value)
    importMessageType.value = 'success'
    importMessage.value = response.msg || response.data || '导入成功'
    await loadRows()
  } catch (error) {
    importMessageType.value = 'error'
    importMessage.value = error?.payload?.msg || error?.message || '导入失败'
  }
}

onMounted(loadRows)
</script>

<style scoped>
.crud-page { padding: 24px; }
.crud-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 16px;
}
.crud-header h1 { margin: 0; font-size: 28px; }
.crud-header p { margin: 8px 0 0; color: var(--text-soft); }
.header-actions,
.table-actions { display: flex; flex-wrap: wrap; gap: 8px; }
.search-form { margin: 18px 0 6px; }
.table-actions { margin: 10px 0 16px; }
.pagination { justify-content: flex-end; margin-top: 18px; }
.message-alert { margin-top: 16px; }
.table-image { width: 56px; height: 40px; border-radius: 6px; }
.import-check { margin-top: 14px; }
</style>
