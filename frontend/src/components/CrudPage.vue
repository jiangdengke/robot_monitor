<template>
  <div class="app-container crud-page">
    <el-form v-if="resolvedSearchFields.length" inline @submit.prevent="handleSearch">
      <el-form-item v-for="field in resolvedSearchFields" :key="field.prop" :label="field.label">
        <el-select v-if="field.type === 'select'" v-model="query[field.prop]" clearable :placeholder="field.placeholder || field.label">
          <el-option v-for="option in field.options || []" :key="option.value" :label="option.label" :value="option.value" />
        </el-select>
        <el-date-picker
          v-else-if="field.type === 'date' || field.type === 'datetime'"
          v-model="query[field.prop]"
          :type="field.type"
          :value-format="field.valueFormat || (field.type === 'datetime' ? 'YYYY-MM-DD HH:mm:ss' : 'YYYY-MM-DD')"
          :format="field.format || (field.type === 'datetime' ? 'YYYY-MM-DD HH:mm:ss' : 'YYYY-MM-DD')"
          clearable
          :placeholder="field.placeholder || field.label"
        />
        <el-tree-select
          v-else-if="field.type === 'tree'"
          v-model="query[field.prop]"
          :data="field.options || []"
          :props="field.props || treeSelectProps"
          :check-strictly="field.checkStrictly ?? true"
          clearable
          filterable
          :placeholder="field.placeholder || field.label"
        />
        <el-input v-else v-model.trim="query[field.prop]" clearable :placeholder="field.placeholder || field.label" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" native-type="submit">查询</el-button>
        <el-button @click="resetSearch">重置</el-button>
      </el-form-item>
    </el-form>

    <el-space class="mb8" wrap>
      <el-button v-if="canCreate" type="primary" @click="openCreate">新增</el-button>
      <el-button v-if="canDelete && enableBatchDelete" type="danger" :disabled="!selectedRows.length" @click="deleteSelected">批量删除</el-button>
      <el-button
        v-for="action in visibleHeaderActions"
        :key="action.key"
        :type="action.type || 'default'"
        :disabled="action.disabled?.({ selectedRows, query, rows })"
        @click="runHeaderAction(action)"
      >
        {{ action.label }}
      </el-button>
      <el-button v-if="uploadField" @click="uploadVisible = true">上传文件</el-button>
      <slot name="actions" :rows="rows" :load-rows="loadRows" />
    </el-space>

    <el-table
      v-loading="loading"
      :data="rows"
      border
      stripe
      highlight-current-row
      :row-key="rowKey"
      :default-expand-all="treeTable"
      :tree-props="treeProps"
      @selection-change="selectedRows = $event"
    >
      <el-table-column v-if="canDelete && enableBatchDelete" type="selection" width="46" />
      <el-table-column v-for="column in visibleColumns" :key="column.prop" :label="column.label" :min-width="column.minWidth" :width="column.width">
        <template #default="{ row }">
          <el-switch
            v-if="column.switch"
            :model-value="String(getRawValue(row, column))"
            :active-value="column.activeValue || '0'"
            :inactive-value="column.inactiveValue || '1'"
            :disabled="column.disabled?.(row)"
            @change="handleSwitchChange(column, row, $event)"
          />
          <el-avatar
            v-else-if="column.image"
            shape="square"
            :src="displayImageValue(row, column)"
          />
          <el-tag v-else-if="column.tag" :type="resolveTagType(column, row)">{{ displayValue(row, column) }}</el-tag>
          <span v-else>{{ displayValue(row, column) }}</span>
        </template>
      </el-table-column>
      <el-table-column v-if="canEdit || canDelete || canShowDetail || rowActions.length" label="操作" fixed="right" :width="operationWidth">
        <template #default="{ row }">
          <el-button v-if="canShowDetail" link type="primary" @click="openDetail(row)">详情</el-button>
          <el-button v-if="canEdit" link type="primary" @click="openEdit(row)">编辑</el-button>
          <el-button
            v-for="action in visibleRowActions"
            :key="action.key"
            link
            :type="action.type || 'primary'"
            :disabled="action.disabled?.(row)"
            @click="runRowAction(action, row)"
          >
            {{ action.label }}
          </el-button>
          <el-button v-if="canDelete" link type="danger" @click="deleteOne(row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination
      v-if="pagination"
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
        <el-form-item v-for="field in visibleFormFields" :key="field.prop" :label="field.label">
          <el-select
            v-if="field.type === 'select'"
            v-model="form[field.prop]"
            :multiple="field.multiple"
            collapse-tags
            collapse-tags-tooltip
            clearable
            :placeholder="field.placeholder || field.label"
            @change="handleFieldChange(field, form[field.prop])"
          >
            <el-option v-for="option in field.options || []" :key="option.value" :label="option.label" :value="option.value" />
          </el-select>
          <el-input-number v-else-if="field.type === 'number'" v-model="form[field.prop]" controls-position="right" :min="field.min ?? 0" />
          <el-date-picker
            v-else-if="field.type === 'date' || field.type === 'datetime'"
            v-model="form[field.prop]"
            :type="field.type"
            :value-format="field.valueFormat || (field.type === 'datetime' ? 'YYYY-MM-DD HH:mm:ss' : 'YYYY-MM-DD')"
            :format="field.format || (field.type === 'datetime' ? 'YYYY-MM-DD HH:mm:ss' : 'YYYY-MM-DD')"
            clearable
            :placeholder="field.placeholder || field.label"
          />
          <el-tree-select
            v-else-if="field.type === 'tree'"
            v-model="form[field.prop]"
            :data="field.options || []"
            :props="field.props || treeSelectProps"
            :multiple="field.multiple"
            :show-checkbox="field.showCheckbox ?? field.multiple"
            :check-strictly="field.checkStrictly ?? true"
            clearable
            filterable
            default-expand-all
            :placeholder="field.placeholder || field.label"
          />
          <el-radio-group v-else-if="field.type === 'radio'" v-model="form[field.prop]">
            <el-radio v-for="option in field.options || []" :key="option.value" :value="option.value">{{ option.label }}</el-radio>
          </el-radio-group>
          <el-space v-else-if="field.type === 'icon'" direction="vertical" fill>
            <el-input v-model="form[field.prop]" :placeholder="field.placeholder || field.label">
              <template #prepend>
                <el-icon v-if="form[field.prop] && form[field.prop] !== '#'">
                  <component :is="form[field.prop]" />
                </el-icon>
                <span v-else>#</span>
              </template>
            </el-input>
            <el-popover trigger="click" width="360">
              <template #reference>
                <el-button>选择图标</el-button>
              </template>
              <el-scrollbar max-height="320px">
                <el-space wrap>
                  <el-button
                    v-for="icon in field.icons || defaultIcons"
                    :key="icon"
                    :plain="form[field.prop] !== icon"
                    :type="form[field.prop] === icon ? 'primary' : 'default'"
                    @click="form[field.prop] = icon"
                  >
                    <el-icon><component :is="icon" /></el-icon>
                    <span>{{ icon }}</span>
                  </el-button>
                </el-space>
              </el-scrollbar>
            </el-popover>
          </el-space>
          <el-space v-else-if="field.type === 'imageBase64'" direction="vertical" fill>
            <el-upload
              :auto-upload="false"
              :limit="1"
              accept="image/*"
              :show-file-list="false"
              :on-change="(file) => handleBase64FieldChange(field, file)"
            >
              <el-button>选择图片</el-button>
            </el-upload>
            <el-input v-model="form[field.prop]" type="textarea" :rows="3" :placeholder="field.placeholder || '上传后自动回填 base64，也可手动粘贴 data:image 内容'" />
            <el-avatar v-if="form[field.prop]" shape="square" :src="form[field.prop]" />
          </el-space>
          <el-space v-else-if="field.type === 'imagePreview'" direction="vertical" fill>
            <el-avatar v-if="resolvePreviewUrl(field, form)" shape="square" :src="resolvePreviewUrl(field, form)" />
            <el-input v-model="form[field.prop]" :placeholder="field.placeholder || field.label" />
          </el-space>
          <el-space v-else-if="field.type === 'editableList'" direction="vertical" fill>
            <el-button size="small" type="primary" plain @click="addEditableListRow(field)">新增明细</el-button>
            <el-table :data="form[field.prop] || []" border size="small">
              <el-table-column v-for="child in field.children || []" :key="child.prop" :label="child.label" :min-width="child.minWidth || 130">
                <template #default="{ row }">
                  <el-select v-if="child.type === 'select'" v-model="row[child.prop]" clearable @change="(value) => handleEditableChildChange(field, child, row, value)">
                    <el-option v-for="option in child.options || []" :key="option.value" :label="option.label" :value="option.value" />
                  </el-select>
                  <el-input-number v-else-if="child.type === 'number'" v-model="row[child.prop]" controls-position="right" :min="child.min ?? 0" />
                  <el-input v-else-if="child.type === 'textarea'" v-model="row[child.prop]" type="textarea" :rows="child.rows || 2" />
                  <el-input v-else v-model="row[child.prop]" />
                </template>
              </el-table-column>
              <el-table-column label="操作" width="80">
                <template #default="{ $index }">
                  <el-button link type="danger" @click="removeEditableListRow(field, $index)">删除</el-button>
                </template>
              </el-table-column>
            </el-table>
          </el-space>
          <el-input v-else-if="field.type === 'textarea'" v-model="form[field.prop]" type="textarea" :rows="field.rows || 4" :placeholder="field.placeholder || field.label" />
          <el-input v-else v-model="form[field.prop]" :type="field.inputType || 'text'" :placeholder="field.placeholder || field.label" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="formVisible = false">取消</el-button>
        <el-button type="primary" @click="submitForm">保存</el-button>
      </template>
    </el-dialog>

    <el-dialog v-model="detailVisible" title="详情" width="720px">
      <el-descriptions :column="2" border>
        <el-descriptions-item v-for="column in visibleColumns" :key="column.prop" :label="column.label">
          {{ displayValue(detail, column) }}
        </el-descriptions-item>
      </el-descriptions>
      <template v-for="table in detailTables" :key="table.key || table.title">
        <el-divider content-position="left">{{ table.title }}</el-divider>
        <el-table :data="resolveDetailTableRows(table)" border size="small">
          <el-table-column v-for="column in table.columns || []" :key="column.prop" :label="column.label" :prop="column.prop" :min-width="column.minWidth" :width="column.width">
            <template #default="{ row }">
              {{ displayValue(row, column) }}
            </template>
          </el-table-column>
        </el-table>
      </template>
    </el-dialog>

    <el-dialog v-model="uploadVisible" title="上传文件" width="520px">
      <el-upload drag multiple :auto-upload="false" :on-change="handleUploadChange">
        <el-icon class="el-icon--upload"><InboxOutlined /></el-icon>
        <div class="el-upload__text">拖入文件或点击选择</div>
      </el-upload>
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
        <el-icon class="el-icon--upload"><InboxOutlined /></el-icon>
        <div class="el-upload__text">拖入 Excel 文件或点击选择</div>
      </el-upload>
      <el-checkbox v-model="importUpdateSupport">更新已存在数据</el-checkbox>
      <template #footer>
        <el-button @click="importVisible = false">关闭</el-button>
        <el-button type="primary" :disabled="!importFile" @click="submitImport">导入</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { computed, onMounted, reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { message, Modal } from 'ant-design-vue'
import { InboxOutlined } from '@ant-design/icons-vue'
import { createResource, deleteResource, getResource, listResource, normalizeRows, normalizeTotal, updateResource, uploadFiles } from '@/api/crud'
import { request } from '@/api/http'
import { loadDictOptions, resolveDictLabel, resolveDictTagType } from '@/utils/dict'
import { hasAnyPermission } from '@/utils/permission'
import { resolveFeedbackMessage } from '@/utils/toast'

const props = defineProps({
  title: { type: String, required: true },
  description: { type: String, default: '' },
  basePath: { type: String, default: '' },
  list: { type: Function, default: null },
  create: { type: Function, default: null },
  update: { type: Function, default: null },
  remove: { type: Function, default: null },
  detail: { type: Function, default: null },
  listPath: { type: String, default: '' },
  listMethod: { type: String, default: 'GET' },
  rowKey: { type: String, required: true },
  columns: { type: Array, default: () => [] },
  searchFields: { type: [Array, Function], default: () => [] },
  formFields: { type: [Array, Function], default: () => [] },
  defaults: { type: Object, default: () => ({}) },
  initialQuery: { type: Object, default: () => ({}) },
  transformRows: { type: Function, default: null },
  treeTable: { type: Boolean, default: false },
  treeProps: { type: Object, default: () => ({ children: 'children', hasChildren: 'hasChildren' }) },
  enableCreate: { type: Boolean, default: true },
  enableEdit: { type: Boolean, default: true },
  enableDelete: { type: Boolean, default: true },
  enableBatchDelete: { type: Boolean, default: true },
  showDetail: { type: Boolean, default: true },
  pagination: { type: Boolean, default: true },
  createPath: { type: String, default: '' },
  updatePath: { type: String, default: '' },
  deletePath: { type: String, default: '' },
  detailPath: { type: String, default: '' },
  detailMethod: { type: String, default: 'GET' },
  detailQuery: { type: Function, default: null },
  detailLoader: { type: Function, default: null },
  createMethod: { type: String, default: 'POST' },
  updateMethod: { type: String, default: 'PUT' },
  deleteMethod: { type: String, default: 'DELETE' },
  uploadField: { type: String, default: '' },
  headerActions: { type: Array, default: () => [] },
  rowActions: { type: Array, default: () => [] },
  detailTables: { type: Array, default: () => [] },
  importAction: { type: Function, default: null },
  permissions: { type: Object, default: () => ({}) },
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
const resolvedFormFields = ref([])
const resolvedSearchFields = ref([])
const treeSelectProps = { value: 'id', label: 'label', children: 'children' }
const defaultIcons = [
  'House',
  'Menu',
  'Grid',
  'Setting',
  'User',
  'UserFilled',
  'Avatar',
  'Lock',
  'Key',
  'Tools',
  'Monitor',
  'Cpu',
  'DataLine',
  'PieChart',
  'Histogram',
  'Document',
  'Tickets',
  'Folder',
  'Files',
  'Bell',
  'Message',
  'Picture',
  'VideoCamera',
  'Microphone',
  'Headset',
  'MapLocation',
  'Location',
  'Guide',
  'Van',
  'Dish',
  'ShoppingCart',
  'Calendar',
  'Clock',
  'Search',
  'Edit',
  'Delete',
  'Plus',
  'InboxOutlined',
  'Download',
  'Refresh'
]

const visibleFormFields = computed(() =>
  resolvedFormFields.value.filter((field) => !field.hidden?.({ form, mode: formMode.value }))
)
const visibleColumns = computed(() =>
  props.columns.filter((column) => !isHiddenColumn(column))
)
const hasListHandler = computed(() => typeof props.list === 'function' || Boolean(props.listPath || props.basePath))
const hasCreateHandler = computed(() => typeof props.create === 'function' || Boolean(props.createPath || props.basePath))
const hasUpdateHandler = computed(() => typeof props.update === 'function' || Boolean(props.updatePath || props.basePath))
const hasDeleteHandler = computed(() => typeof props.remove === 'function' || Boolean(props.deletePath || props.basePath))
const hasDetailHandler = computed(() => typeof props.detail === 'function' || Boolean(props.detailLoader || props.detailPath || props.basePath))
const canCreate = computed(() => props.enableCreate && hasCreateHandler.value && canAction('add'))
const canEdit = computed(() => props.enableEdit && hasUpdateHandler.value && canAction('edit'))
const canDelete = computed(() => props.enableDelete && hasDeleteHandler.value && canAction('remove'))
const canShowDetail = computed(() => props.showDetail && hasDetailHandler.value)
const visibleHeaderActions = computed(() => props.headerActions.filter((action) => canAction(action.permission || action.key, action.permissions)))
const visibleRowActions = computed(() => props.rowActions.filter((action) => canAction(action.permission || action.key, action.permissions)))

function isHiddenColumn(column) {
  if (typeof column.hidden === 'function') {
    return column.hidden({ rows: rows.value, query })
  }
  if (column.hidden) {
    return true
  }
  return column.prop === props.rowKey && String(column.label).toLowerCase() === 'id'
}

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
  if (column.dictOptions) {
    return resolveDictLabel(column.dictOptions, raw)
  }
  if (column.formatter) {
    return column.formatter(raw, row)
  }
  const value = column.map ? column.map[String(raw)] : raw
  return value === undefined || value === null || value === '' ? '-' : value
}

function displayImageValue(row, column) {
  if (column.imageUrl) {
    return column.imageUrl(row)
  }
  return displayValue(row, column)
}

function resolveTagType(column, row) {
  const raw = column.prop.includes('.') ? getByPath(row, column.prop) : row?.[column.prop]
  if (column.dictOptions) {
    return resolveDictTagType(column.dictOptions, raw)
  }
  return column.tagMap?.[String(raw)] || column.tag || 'info'
}

function canAction(action, permissions = null) {
  const values = permissions || props.permissions?.[action]
  return !values || hasAnyPermission(Array.isArray(values) ? values : [values])
}

function showMessage(type, message) {
  if (!message) {
    return
  }
  const fn = messageMap[type] || messageMap.info
  fn(message)
}

async function hydrateDictColumns() {
  const dictColumns = props.columns.filter((column) => column.dictType && !column.dictOptions)
  if (!dictColumns.length) return
  await Promise.all(dictColumns.map(async (column) => {
    column.dictOptions = await loadDictOptions(column.dictType)
    column.tag = column.tag || 'info'
  }))
}

async function resolveSearchFields() {
  resolvedSearchFields.value = await Promise.all(
    (props.searchFields || []).map(async (field) => {
      if (typeof field.options === 'function') {
        return {
          ...field,
          options: await field.options()
        }
      }
      return field
    })
  )
}

async function loadRows() {
  if (!hasListHandler.value) {
    rows.value = []
    total.value = 0
    return
  }
  loading.value = true
  errorMessage.value = ''
  try {
    const params = {
      ...(props.pagination ? { pageNum: pageNum.value, pageSize: pageSize.value } : {}),
      ...query
    }
    const payload = typeof props.list === 'function'
      ? await props.list(params)
      : props.listPath
      ? await request(props.listPath, { method: props.listMethod, query: params })
      : await listResource(props.basePath, params, props.listMethod)
    const normalizedRows = normalizeRows(payload)
    rows.value = props.transformRows ? props.transformRows(normalizedRows) : normalizedRows
    total.value = props.pagination ? normalizeTotal(payload) : rows.value.length
  } catch (error) {
    const message = error?.payload?.msg || error?.message || '加载失败'
    errorMessage.value = message
    showMessage('error', message)
  } finally {
    loading.value = false
  }
}

function handleSearch() {
  pageNum.value = 1
  loadRows()
}

function resetSearch() {
  resetObject(query, props.initialQuery)
  handleSearch()
}

async function resolveFormFields(context = {}) {
  const fields = typeof props.formFields === 'function' ? await props.formFields(context) : props.formFields
  resolvedFormFields.value = fields || []
  applyFieldDefaults(form, resolvedFormFields.value)
}

function applyFieldDefaults(target, fields) {
  fields.forEach((field) => {
    if (field.defaultValue === undefined) {
      return
    }
    const current = target[field.prop]
    const emptyArray = Array.isArray(current) && current.length === 0
    if (current === undefined || current === null || current === '' || emptyArray) {
      target[field.prop] = typeof field.defaultValue === 'function' ? field.defaultValue() : field.defaultValue
    }
  })
}

async function openCreate() {
  formMode.value = 'create'
  resetObject(form, { ...props.defaults })
  await resolveFormFields({ mode: 'create', row: null, response: null, form: { ...form } })
  formVisible.value = true
}

async function openEdit(row) {
  formMode.value = 'edit'
  errorMessage.value = ''
  try {
    const response = await loadDetail(row)
    const detailData = props.transformDetail ? await props.transformDetail(response, row) : (response?.data || response || row)
    resetObject(form, { ...props.defaults, ...detailData })
    await resolveFormFields({ mode: 'edit', row, response, form: { ...form } })
    formVisible.value = true
  } catch (error) {
    resetObject(form, { ...props.defaults, ...row })
    await resolveFormFields({ mode: 'edit', row, response: null, form: { ...form } })
    formVisible.value = true
  }
}

async function openDetail(row) {
  detail.value = row
  if (hasDetailHandler.value) {
    try {
      const response = await loadDetail(row)
      detail.value = props.transformDetail ? await props.transformDetail(response, row) : (response?.data || response || row)
    } catch {
      detail.value = row
    }
  }
  detailVisible.value = true
}

async function loadDetail(row) {
  if (props.detail) {
    return props.detail(row)
  }
  if (props.detailLoader) {
    return props.detailLoader(row)
  }
  if (props.detailPath) {
    return request(props.detailPath, {
      method: props.detailMethod,
      query: props.detailQuery ? props.detailQuery(row) : { [props.rowKey]: row[props.rowKey] }
    })
  }
  return getResource(props.basePath, row[props.rowKey])
}

async function submitForm() {
  try {
    const normalizedPayload = normalizeSubmitPayload({ ...form }, resolvedFormFields.value)
    const payload = props.beforeSubmit ? props.beforeSubmit(normalizedPayload, formMode.value) : normalizedPayload
    if (formMode.value === 'create') {
      const response = props.create
        ? await props.create(payload)
        : await createResource(props.createPath || props.basePath, payload, props.createMethod)
      successMessage.value = resolveFeedbackMessage(response, '新增成功')
      showMessage('success', successMessage.value)
    } else {
      const response = props.update
        ? await props.update(payload)
        : await updateResource(props.updatePath || props.basePath, payload, props.updateMethod)
      successMessage.value = resolveFeedbackMessage(response, '保存成功')
      showMessage('success', successMessage.value)
    }
    formVisible.value = false
    await loadRows()
  } catch (error) {
    const message = error?.payload?.msg || error?.message || '保存失败'
    errorMessage.value = message
    showMessage('error', message)
  }
}

function normalizeSubmitPayload(payload, fields) {
  fields.forEach((field) => {
    if (!field.joinArray || !Array.isArray(payload[field.prop])) {
      return
    }
    payload[field.prop] = payload[field.prop].join(field.joinDelimiter || ',')
  })
  return payload
}

function handleBase64FieldChange(field, file) {
  const raw = file.raw
  if (!raw) {
    return
  }
  const reader = new FileReader()
  reader.onload = () => {
    form[field.prop] = reader.result
    if (field.nameProp && !form[field.nameProp]) {
      form[field.nameProp] = raw.name
    }
  }
  reader.readAsDataURL(raw)
}

function resolvePreviewUrl(field, source) {
  if (field.url) {
    return typeof field.url === 'function' ? field.url(source) : field.url
  }
  return source[field.prop]
}

function handleFieldChange(field, value) {
  field.onChange?.(value, { form, field })
}

function addEditableListRow(field) {
  if (!Array.isArray(form[field.prop])) {
    form[field.prop] = []
  }
  const row = typeof field.newRow === 'function' ? field.newRow(form[field.prop]) : { ...(field.newRow || {}) }
  form[field.prop].push(row)
}

function removeEditableListRow(field, index) {
  form[field.prop]?.splice(index, 1)
}

function handleEditableChildChange(field, child, row, value) {
  child.onChange?.(value, row, { form, field, child })
}

function resolveDetailTableRows(table) {
  if (typeof table.rows === 'function') {
    return table.rows(detail.value) || []
  }
  return detail.value?.[table.prop] || []
}

async function handleSwitchChange(column, row, value) {
  const previous = getRawValue(row, column)
  row[column.prop] = value
  try {
    await column.action(row, value)
    successMessage.value = column.successMessage || '状态已更新'
    showMessage('success', successMessage.value)
    await loadRows()
  } catch (error) {
    row[column.prop] = previous
    const message = error?.payload?.msg || error?.message || '状态更新失败'
    errorMessage.value = message
    showMessage('error', message)
  }
}

async function runHeaderAction(action) {
  try {
    if (action.confirm) {
      await confirmDialog(action.confirmTitle || '操作确认', action.confirm)
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
    const response = await action.handler?.({ rows: rows.value, selectedRows: selectedRows.value, query: { ...query }, loadRows })
    successMessage.value = resolveFeedbackMessage(response, action.successMessage || successMessage.value)
    if (successMessage.value) {
      showMessage('success', successMessage.value)
    }
    if (action.reload !== false) {
      await loadRows()
    }
  } catch (error) {
    if (error !== 'cancel') {
      const message = error?.payload?.msg || error?.message || action.errorMessage || '操作失败'
      errorMessage.value = message
      showMessage('error', message)
    }
  }
}

async function runRowAction(action, row) {
  try {
    if (action.promptFields?.length) {
      promptTitle.value = action.promptTitle || action.label
      promptFields.value = await resolvePromptFields(action, row)
      resetObject(promptForm, typeof action.promptDefaults === 'function' ? action.promptDefaults(row) : { ...(action.promptDefaults || {}) })
      applyFieldDefaults(promptForm, promptFields.value)
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
      await confirmDialog(action.confirmTitle || '操作确认', typeof action.confirm === 'function' ? action.confirm(row) : action.confirm)
    }
    const response = await action.handler?.(row, { loadRows })
    successMessage.value = resolveFeedbackMessage(response, action.successMessage || '操作成功')
    showMessage('success', successMessage.value)
    if (action.reload !== false) {
      await loadRows()
    }
  } catch (error) {
    if (error !== 'cancel') {
      const message = error?.payload?.msg || error?.message || action.errorMessage || '操作失败'
      errorMessage.value = message
      showMessage('error', message)
    }
  }
}

async function resolvePromptFields(action, row) {
  const fields = typeof action.promptFields === 'function' ? await action.promptFields(row) : action.promptFields
  return fields || []
}

async function submitPromptAction() {
  try {
    const response = await pendingPromptAction.value?.handler?.(pendingPromptRow.value, { form: { ...promptForm }, loadRows })
    promptVisible.value = false
    successMessage.value = resolveFeedbackMessage(response, pendingPromptAction.value?.successMessage || '操作成功')
    showMessage('success', successMessage.value)
    await loadRows()
  } catch (error) {
    const message = error?.payload?.msg || error?.message || '操作失败'
    errorMessage.value = message
    showMessage('error', message)
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
    await confirmDialog('删除确认', '确认删除所选数据？')
    const response = props.remove
      ? await props.remove(ids)
      : await deleteResource(props.deletePath || props.basePath, ids, props.deleteMethod)
    successMessage.value = resolveFeedbackMessage(response, '删除成功')
    showMessage('success', successMessage.value)
    await loadRows()
  } catch (error) {
    if (error !== 'cancel') {
      const message = error?.payload?.msg || error?.message || '删除失败'
      errorMessage.value = message
      showMessage('error', message)
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
    uploadMessage.value = resolveFeedbackMessage(response, `上传成功：${response.originalFilenames || response.fileNames || ''}`)
    showMessage('success', uploadMessage.value)
    if (props.uploadField && formVisible.value) {
      form[props.uploadField] = String(response.fileNames || '').split(',')[0] || form[props.uploadField]
    }
  } catch (error) {
    uploadMessageType.value = 'error'
    uploadMessage.value = error?.payload?.msg || error?.message || '上传失败'
    showMessage('error', uploadMessage.value)
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
    importMessage.value = resolveFeedbackMessage(response, '导入成功')
    showMessage('success', importMessage.value)
    await loadRows()
  } catch (error) {
    importMessageType.value = 'error'
    importMessage.value = error?.payload?.msg || error?.message || '导入失败'
    showMessage('error', importMessage.value)
  }
}

onMounted(() => {
  resetObject(query, props.initialQuery)
  Promise.all([hydrateDictColumns(), resolveSearchFields()]).finally(loadRows)
})

function confirmDialog(title, content) {
  return new Promise((resolve, reject) => {
    Modal.confirm({
      title,
      content,
      onOk: resolve,
      onCancel: () => reject('cancel')
    })
  })
}

const messageMap = {
  success: message.success,
  error: message.error,
  warning: message.warning,
  info: message.info
}
</script>
