<template>
  <el-card class="page-card">
    <template #header>
      <div class="page-header">
        <div>
          <h1>表单构建器</h1>
          <p>字段拖拽排序、字段属性编辑和 Element Plus 预览已完成实时联动。</p>
        </div>
        <div class="header-actions">
          <el-button @click="addField">新增字段</el-button>
          <el-button type="primary" @click="copySchema">复制 Schema</el-button>
        </div>
      </div>
    </template>

    <div class="builder-layout">
      <el-card shadow="never">
        <template #header><h2>字段列表</h2></template>
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
            <strong>{{ field.label }}</strong>
            <span>{{ field.prop }} · {{ field.type }}</span>
          </div>
        </div>
      </el-card>

      <el-card shadow="never">
        <template #header><h2>属性面板</h2></template>
        <el-form v-if="activeField" label-position="top">
          <el-form-item label="字段标签">
            <el-input v-model="activeField.label" />
          </el-form-item>
          <el-form-item label="字段名">
            <el-input v-model="activeField.prop" />
          </el-form-item>
          <el-form-item label="组件类型">
            <el-select v-model="activeField.type">
              <el-option label="输入框" value="input" />
              <el-option label="多行文本" value="textarea" />
              <el-option label="下拉选择" value="select" />
              <el-option label="数字输入" value="number" />
              <el-option label="日期" value="date" />
            </el-select>
          </el-form-item>
          <el-form-item label="提示文案">
            <el-input v-model="activeField.placeholder" />
          </el-form-item>
          <el-form-item label="选项，逗号分隔" v-if="activeField.type === 'select'">
            <el-input v-model="activeField.optionsText" />
          </el-form-item>
          <el-button type="danger" @click="removeActive">删除字段</el-button>
        </el-form>
        <el-empty v-else description="选择字段后编辑属性" />
      </el-card>

      <el-card shadow="never">
        <template #header><h2>表单预览</h2></template>
        <el-form label-position="top">
          <el-form-item v-for="field in fields" :key="field.id" :label="field.label">
            <el-select v-if="field.type === 'select'" v-model="preview[field.prop]" :placeholder="field.placeholder">
              <el-option v-for="option in options(field)" :key="option" :label="option" :value="option" />
            </el-select>
            <el-input-number v-else-if="field.type === 'number'" v-model="preview[field.prop]" controls-position="right" />
            <el-date-picker v-else-if="field.type === 'date'" v-model="preview[field.prop]" type="date" :placeholder="field.placeholder" />
            <el-input v-else-if="field.type === 'textarea'" v-model="preview[field.prop]" type="textarea" :rows="3" :placeholder="field.placeholder" />
            <el-input v-else v-model="preview[field.prop]" :placeholder="field.placeholder" />
          </el-form-item>
        </el-form>
      </el-card>
    </div>

    <el-card shadow="never" class="schema-panel">
      <template #header><h2>Schema 预览</h2></template>
      <pre>{{ schemaText }}</pre>
    </el-card>
  </el-card>
</template>

<script setup>
import { computed, reactive, ref } from 'vue'

const fields = ref([
  { id: 1, label: '标题', prop: 'title', type: 'input', placeholder: '请输入标题' },
  { id: 2, label: '状态', prop: 'status', type: 'select', placeholder: '请选择状态', optionsText: '正常,停用' },
  { id: 3, label: '备注', prop: 'remark', type: 'textarea', placeholder: '请输入备注' }
])
const activeId = ref(1)
const dragIndex = ref(-1)
const preview = reactive({})
const activeField = computed(() => fields.value.find((item) => item.id === activeId.value))
const schemaText = computed(() => JSON.stringify(fields.value, null, 2))

function addField() {
  const id = Date.now()
  fields.value.push({ id, label: `字段${fields.value.length + 1}`, prop: `field${fields.value.length + 1}`, type: 'input', placeholder: '请输入' })
  activeId.value = id
}

function removeActive() {
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

async function copySchema() {
  await navigator.clipboard?.writeText(schemaText.value)
}
</script>

<style scoped>
.page-card { padding: 24px; }
.page-header { display: flex; justify-content: space-between; align-items: center; gap: 16px; }
.page-header h1 { margin: 0; font-size: 28px; }
.page-header p { margin: 8px 0 0; color: var(--text-soft); }
.header-actions { display: flex; gap: 8px; }
.builder-layout { display: grid; grid-template-columns: .8fr .9fr 1.2fr; gap: 16px; margin-top: 18px; }
h2 { margin: 0; font-size: 16px; }
.field-list { display: grid; gap: 10px; }
.field-item { display: grid; gap: 4px; padding: 12px; border: 1px solid var(--line); border-radius: 12px; background: #fff; cursor: grab; }
.field-item.active { border-color: var(--brand); background: #eaf4ff; }
.field-item span { color: var(--text-soft); font-size: 12px; }
.schema-panel { margin-top: 16px; }
.schema-panel pre { margin: 0; white-space: pre-wrap; color: var(--text-soft); }
@media (max-width: 1180px) { .builder-layout { grid-template-columns: 1fr; } }
</style>
