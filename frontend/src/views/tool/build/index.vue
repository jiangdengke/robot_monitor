<template>
  <el-card class="page-card">
    <template #header>
      <div class="page-header">
        <div>
          <h1>表单构建器</h1>
          <p>拖拽排序、字段属性、选项、校验规则和 Element Plus 代码输出已实时联动。</p>
        </div>
        <div class="header-actions">
          <el-button @click="loadExample">示例表单</el-button>
          <el-button @click="openImport">导入 Schema</el-button>
          <el-button @click="copySchema">复制 Schema</el-button>
          <el-button type="primary" @click="copyVue">复制 Vue 源码</el-button>
          <el-button type="danger" plain @click="clearFields">清空</el-button>
        </div>
      </div>
    </template>

    <div class="builder-layout">
      <el-card shadow="never">
        <template #header><h2>控件库</h2></template>
        <div class="palette">
          <el-button v-for="item in palette" :key="item.type" @click="addField(item)">{{ item.label }}</el-button>
        </div>

        <el-divider />

        <h2>字段列表</h2>
        <div class="field-list">
          <div
            v-for="(field, index) in fields"
            :key="field.id"
            class="field-item"
            :class="{ active: field.id === activeId }"
            draggable="true"
            @dragstart="dragIndex = index"
            @dragover.prevent
            @drop="dropField(index)"
            @click="activeId = field.id"
          >
            <strong>{{ index + 1 }}. {{ field.label }}</strong>
            <span>{{ field.prop }} · {{ field.type }} · {{ field.span }}/24</span>
          </div>
        </div>
      </el-card>

      <el-card shadow="never">
        <template #header><h2>属性面板</h2></template>
        <el-form v-if="activeField" label-position="top">
          <el-row :gutter="12">
            <el-col :span="12">
              <el-form-item label="字段标签"><el-input v-model="activeField.label" /></el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="字段名"><el-input v-model="activeField.prop" /></el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="组件类型">
                <el-select v-model="activeField.type">
                  <el-option v-for="item in palette" :key="item.type" :label="item.label" :value="item.type" />
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="栅格宽度">
                <el-slider v-model="activeField.span" :min="6" :max="24" :step="2" />
              </el-form-item>
            </el-col>
            <el-col :span="24">
              <el-form-item label="提示文案"><el-input v-model="activeField.placeholder" /></el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="默认值"><el-input v-model="activeField.defaultValue" /></el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="字典类型"><el-input v-model="activeField.dictType" placeholder="可选，如 sys_normal_disable" /></el-form-item>
            </el-col>
            <el-col :span="24" v-if="hasOptions(activeField)">
              <el-form-item label="选项，逗号分隔"><el-input v-model="activeField.optionsText" /></el-form-item>
            </el-col>
            <el-col :span="12" v-if="activeField.type === 'number'">
              <el-form-item label="小数位"><el-input-number v-model="activeField.precision" :min="0" :max="4" /></el-form-item>
            </el-col>
            <el-col :span="12" v-if="activeField.type === 'textarea'">
              <el-form-item label="行数"><el-input-number v-model="activeField.rows" :min="2" :max="8" /></el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="必填"><el-switch v-model="activeField.required" /></el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="禁用"><el-switch v-model="activeField.disabled" /></el-form-item>
            </el-col>
          </el-row>
          <div class="action-row">
            <el-button @click="duplicateActive">复制字段</el-button>
            <el-button type="danger" @click="removeActive">删除字段</el-button>
          </div>
        </el-form>
        <el-empty v-else description="选择字段后编辑属性" />
      </el-card>

      <el-card shadow="never">
        <template #header><h2>Element Plus 预览</h2></template>
        <el-form :model="preview" label-position="top">
          <el-row :gutter="12">
            <el-col v-for="field in fields" :key="field.id" :span="field.span">
              <el-form-item :label="field.label" :required="field.required">
                <el-select v-if="field.type === 'select'" v-model="preview[field.prop]" :disabled="field.disabled" :placeholder="field.placeholder">
                  <el-option v-for="option in options(field)" :key="option" :label="option" :value="option" />
                </el-select>
                <el-radio-group v-else-if="field.type === 'radio'" v-model="preview[field.prop]" :disabled="field.disabled">
                  <el-radio-button v-for="option in options(field)" :key="option" :label="option" :value="option" />
                </el-radio-group>
                <el-checkbox-group v-else-if="field.type === 'checkbox'" v-model="preview[field.prop]" :disabled="field.disabled">
                  <el-checkbox v-for="option in options(field)" :key="option" :label="option" :value="option" />
                </el-checkbox-group>
                <el-switch v-else-if="field.type === 'switch'" v-model="preview[field.prop]" :disabled="field.disabled" />
                <el-input-number v-else-if="field.type === 'number'" v-model="preview[field.prop]" :precision="field.precision" :disabled="field.disabled" controls-position="right" />
                <el-date-picker v-else-if="field.type === 'date'" v-model="preview[field.prop]" :disabled="field.disabled" type="date" :placeholder="field.placeholder" />
                <el-upload v-else-if="field.type === 'upload'" action="#" :auto-upload="false" :disabled="field.disabled">
                  <el-button :disabled="field.disabled">选择文件</el-button>
                </el-upload>
                <el-input v-else-if="field.type === 'textarea'" v-model="preview[field.prop]" :disabled="field.disabled" type="textarea" :rows="field.rows" :placeholder="field.placeholder" />
                <el-input v-else v-model="preview[field.prop]" :disabled="field.disabled" :placeholder="field.placeholder" />
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </el-card>
    </div>

    <el-row :gutter="16" class="output-row">
      <el-col :span="12">
        <el-card shadow="never" class="schema-panel">
          <template #header><h2>Schema 预览</h2></template>
          <pre>{{ schemaText }}</pre>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card shadow="never" class="schema-panel">
          <template #header><h2>Vue 源码预览</h2></template>
          <pre>{{ generatedVue }}</pre>
        </el-card>
      </el-col>
    </el-row>

    <el-dialog v-model="importVisible" title="导入 Schema" width="720px">
      <el-input v-model="importText" type="textarea" :rows="14" placeholder="粘贴表单 Schema JSON" />
      <template #footer>
        <el-button @click="importVisible = false">取消</el-button>
        <el-button type="primary" @click="importSchema">导入</el-button>
      </template>
    </el-dialog>
  </el-card>
</template>

<script setup>
import { ElMessage, ElMessageBox } from 'element-plus'
import { computed, reactive, ref, watch } from 'vue'

const palette = [
  { type: 'input', label: '输入框' },
  { type: 'textarea', label: '多行文本' },
  { type: 'select', label: '下拉选择' },
  { type: 'radio', label: '单选框' },
  { type: 'checkbox', label: '复选框' },
  { type: 'number', label: '数字输入' },
  { type: 'date', label: '日期' },
  { type: 'switch', label: '开关' },
  { type: 'upload', label: '上传' }
]

const fields = ref([])
const activeId = ref(null)
const dragIndex = ref(-1)
const preview = reactive({})
const importVisible = ref(false)
const importText = ref('')
const activeField = computed(() => fields.value.find((item) => item.id === activeId.value))
const schemaText = computed(() => JSON.stringify(fields.value, null, 2))
const generatedVue = computed(() => buildVueCode(fields.value))

watch(fields, syncPreview, { deep: true, immediate: true })

function createField(type = 'input', label = '字段') {
  const index = fields.value.length + 1
  return {
    id: Date.now() + Math.floor(Math.random() * 1000),
    label,
    prop: `field${index}`,
    type,
    span: 12,
    placeholder: type === 'select' ? '请选择' : '请输入',
    defaultValue: type === 'checkbox' ? [] : '',
    required: false,
    disabled: false,
    optionsText: '正常,停用',
    dictType: '',
    precision: 0,
    rows: 3
  }
}

function addField(item) {
  const field = createField(item.type, item.label)
  fields.value.push(field)
  activeId.value = field.id
}

function duplicateActive() {
  if (!activeField.value) return
  const clone = JSON.parse(JSON.stringify(activeField.value))
  clone.id = Date.now()
  clone.prop = `${clone.prop}Copy`
  clone.label = `${clone.label}副本`
  fields.value.push(clone)
  activeId.value = clone.id
}

async function removeActive() {
  if (!activeField.value) return
  await ElMessageBox.confirm(`确认删除字段 "${activeField.value.label}"？`, '删除确认', { type: 'warning' })
  fields.value = fields.value.filter((item) => item.id !== activeId.value)
  activeId.value = fields.value[0]?.id || null
}

function dropField(targetIndex) {
  if (dragIndex.value < 0 || dragIndex.value === targetIndex) return
  const moved = fields.value.splice(dragIndex.value, 1)[0]
  fields.value.splice(targetIndex, 0, moved)
  dragIndex.value = -1
}

function options(field) {
  return String(field.optionsText || '').split(',').map((item) => item.trim()).filter(Boolean)
}

function hasOptions(field) {
  return ['select', 'radio', 'checkbox'].includes(field.type)
}

function syncPreview() {
  for (const field of fields.value) {
    if (!(field.prop in preview)) {
      preview[field.prop] = defaultPreviewValue(field)
    }
  }
}

function defaultPreviewValue(field) {
  if (field.type === 'checkbox') return Array.isArray(field.defaultValue) ? field.defaultValue : []
  if (field.type === 'switch') return field.defaultValue === true || field.defaultValue === 'true'
  if (field.type === 'number') return Number(field.defaultValue || 0)
  return field.defaultValue || ''
}

function loadExample() {
  fields.value = [
    { ...createField('input', '机器人名称'), id: 1, prop: 'robotName', placeholder: '请输入机器人名称', required: true },
    { ...createField('select', '机器人类型'), id: 2, prop: 'robotType', optionsText: '多功能机器人,迎宾机器人,配送机器人', required: true },
    { ...createField('number', '电量阈值'), id: 3, prop: 'batteryLimit', defaultValue: 20, precision: 0 },
    { ...createField('radio', '启用状态'), id: 4, prop: 'enable', optionsText: '启用,禁用', defaultValue: '启用' },
    { ...createField('textarea', '备注'), id: 5, prop: 'remark', span: 24, rows: 4 }
  ]
  activeId.value = fields.value[0].id
  ElMessage.success('已加载示例表单')
}

function clearFields() {
  fields.value = []
  activeId.value = null
}

function openImport() {
  importText.value = schemaText.value
  importVisible.value = true
}

function importSchema() {
  try {
    const parsed = JSON.parse(importText.value)
    if (!Array.isArray(parsed)) {
      throw new Error('Schema 必须是数组')
    }
    fields.value = parsed.map((item, index) => ({ ...createField(item.type || 'input', item.label || `字段${index + 1}`), ...item, id: item.id || Date.now() + index }))
    activeId.value = fields.value[0]?.id || null
    importVisible.value = false
    ElMessage.success('Schema 已导入')
  } catch (error) {
    ElMessage.error(error.message || 'Schema 解析失败')
  }
}

async function copySchema() {
  await navigator.clipboard?.writeText(schemaText.value)
  ElMessage.success('Schema 已复制')
}

async function copyVue() {
  await navigator.clipboard?.writeText(generatedVue.value)
  ElMessage.success('Vue 源码已复制')
}

function buildVueCode(list) {
  const model = list.map((field) => `  ${field.prop}: ${JSON.stringify(defaultPreviewValue(field))}`).join(',\n')
  const rules = list
    .filter((field) => field.required)
    .map((field) => `  ${field.prop}: [{ required: true, message: '${field.placeholder || `请填写${field.label}`}', trigger: 'blur' }]`)
    .join(',\n')
  const items = list.map(renderCodeItem).join('\n')
  return `<template>
  <el-form :model="form" :rules="rules" label-position="top">
    <el-row :gutter="12">
${items}
    </el-row>
  </el-form>
</template>

<script setup>
import { reactive } from 'vue'

const form = reactive({
${model}
})

const rules = {
${rules}
}
<\/script>`
}

function renderCodeItem(field) {
  const disabled = field.disabled ? ' disabled' : ''
  const required = field.required ? ' required' : ''
  const placeholder = field.placeholder ? ` placeholder="${field.placeholder}"` : ''
  const optionNodes = options(field).map((option) => `          <el-option label="${option}" value="${option}" />`).join('\n')
  let control = `<el-input v-model="form.${field.prop}"${placeholder}${disabled} />`
  if (field.type === 'textarea') {
    control = `<el-input v-model="form.${field.prop}" type="textarea" :rows="${field.rows}"${placeholder}${disabled} />`
  } else if (field.type === 'select') {
    control = `<el-select v-model="form.${field.prop}"${placeholder}${disabled}>
${optionNodes}
        </el-select>`
  } else if (field.type === 'number') {
    control = `<el-input-number v-model="form.${field.prop}" :precision="${field.precision}"${disabled} />`
  } else if (field.type === 'date') {
    control = `<el-date-picker v-model="form.${field.prop}" type="date"${placeholder}${disabled} />`
  } else if (field.type === 'switch') {
    control = `<el-switch v-model="form.${field.prop}"${disabled} />`
  } else if (field.type === 'radio') {
    control = `<el-radio-group v-model="form.${field.prop}"${disabled}>
${options(field).map((option) => `            <el-radio-button label="${option}" value="${option}" />`).join('\n')}
          </el-radio-group>`
  } else if (field.type === 'checkbox') {
    control = `<el-checkbox-group v-model="form.${field.prop}"${disabled}>
${options(field).map((option) => `            <el-checkbox label="${option}" value="${option}" />`).join('\n')}
          </el-checkbox-group>`
  } else if (field.type === 'upload') {
    control = `<el-upload action="#" :auto-upload="false"${disabled}>
            <el-button>选择文件</el-button>
          </el-upload>`
  }
  return `      <el-col :span="${field.span}">
        <el-form-item label="${field.label}" prop="${field.prop}"${required}>
          ${control}
        </el-form-item>
      </el-col>`
}

loadExample()
</script>

<style scoped>
.page-card { padding: 24px; }
.page-header { display: flex; justify-content: space-between; align-items: center; gap: 16px; }
.page-header h1 { margin: 0; font-size: 28px; }
.page-header p { margin: 8px 0 0; color: var(--text-soft); }
.header-actions,
.action-row,
.palette { display: flex; flex-wrap: wrap; gap: 8px; }
.builder-layout { display: grid; grid-template-columns: .78fr .95fr 1.25fr; gap: 16px; margin-top: 18px; }
h2 { margin: 0; font-size: 16px; }
.field-list { display: grid; gap: 10px; margin-top: 12px; }
.field-item { display: grid; gap: 4px; padding: 12px; border: 1px solid var(--line); border-radius: 12px; background: #fff; cursor: grab; }
.field-item.active { border-color: var(--brand); background: #eaf4ff; }
.field-item span { color: var(--text-soft); font-size: 12px; }
.output-row { margin-top: 16px; }
.schema-panel pre { margin: 0; max-height: 520px; overflow: auto; white-space: pre-wrap; color: var(--text-soft); }
@media (max-width: 1180px) {
  .builder-layout { grid-template-columns: 1fr; }
  .output-row :deep(.el-col) { max-width: 100%; flex: 0 0 100%; }
}
</style>
