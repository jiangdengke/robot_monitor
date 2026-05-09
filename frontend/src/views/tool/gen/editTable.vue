<template>
  <el-card shadow="never" class="mini-card">
    <template #header>
      <div class="card-header">
        <h2>编辑生成表</h2>
        <div class="actions">
          <el-button @click="resetDraft">重置</el-button>
          <el-button type="primary" @click="save">保存</el-button>
        </div>
      </div>
    </template>

    <el-tabs v-model="activeTab">
      <el-tab-pane label="基础信息" name="basic">
        <basic-info-form :model="draft" />
      </el-tab-pane>
      <el-tab-pane label="生成信息" name="gen">
        <gen-info-form :model="draft" />
      </el-tab-pane>
      <el-tab-pane label="字段信息" name="columns">
        <el-table :data="draft.columns" border row-key="columnName">
          <el-table-column label="排序" width="82">
            <template #default="{ $index }">
              <el-button-group>
                <el-button size="small" :disabled="$index === 0" @click="moveColumn($index, -1)">上</el-button>
                <el-button size="small" :disabled="$index === draft.columns.length - 1" @click="moveColumn($index, 1)">下</el-button>
              </el-button-group>
            </template>
          </el-table-column>
          <el-table-column prop="columnName" label="字段" min-width="150" />
          <el-table-column label="说明" min-width="160">
            <template #default="{ row }"><el-input v-model.trim="row.columnComment" /></template>
          </el-table-column>
          <el-table-column label="Java 字段" min-width="140">
            <template #default="{ row }"><el-input v-model.trim="row.javaField" /></template>
          </el-table-column>
          <el-table-column label="必填" width="82">
            <template #default="{ row }"><el-switch v-model="row.isRequired" active-value="1" inactive-value="0" /></template>
          </el-table-column>
          <el-table-column label="列表" width="82">
            <template #default="{ row }"><el-switch v-model="row.isList" active-value="1" inactive-value="0" /></template>
          </el-table-column>
          <el-table-column label="查询" width="82">
            <template #default="{ row }"><el-switch v-model="row.isQuery" active-value="1" inactive-value="0" /></template>
          </el-table-column>
          <el-table-column label="控件" min-width="140">
            <template #default="{ row }">
              <el-select v-model="row.htmlType">
                <el-option label="输入框" value="input" />
                <el-option label="文本域" value="textarea" />
                <el-option label="下拉框" value="select" />
                <el-option label="日期" value="datetime" />
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="字典类型" min-width="160">
            <template #default="{ row }"><el-input v-model.trim="row.dictType" placeholder="sys_normal_disable" /></template>
          </el-table-column>
        </el-table>
      </el-tab-pane>
    </el-tabs>
  </el-card>
</template>

<script setup>
import { ElMessage } from 'element-plus'
import { reactive, ref, watch } from 'vue'
import BasicInfoForm from './basicInfoForm.vue'
import GenInfoForm from './genInfoForm.vue'

const props = defineProps({
  table: {
    type: Object,
    default: () => ({})
  }
})
const emit = defineEmits(['save'])

const activeTab = ref('basic')
const draft = reactive({ columns: [] })

watch(
  () => props.table,
  resetDraft,
  { immediate: true, deep: true }
)

function resetDraft() {
  const source = props.table || {}
  Object.keys(draft).forEach((key) => delete draft[key])
  Object.assign(draft, JSON.parse(JSON.stringify({
    tplCategory: 'crud',
    genType: '0',
    genPath: '/',
    columns: [],
    ...source,
    columns: source.columns || source.rows || []
  })))
}

function moveColumn(index, direction) {
  const target = index + direction
  if (target < 0 || target >= draft.columns.length) return
  const moved = draft.columns.splice(index, 1)[0]
  draft.columns.splice(target, 0, moved)
  draft.columns.forEach((item, itemIndex) => {
    item.sort = itemIndex + 1
  })
}

function save() {
  emit('save', JSON.parse(JSON.stringify(draft)))
  ElMessage.success('生成表配置已提交')
}
</script>

<style scoped>
.card-header,
.actions { display: flex; align-items: center; justify-content: space-between; gap: 8px; }
h2 { margin: 0; font-size: 16px; }
</style>
