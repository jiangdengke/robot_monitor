<template>
  <el-dialog v-model="visible" title="选择图标" width="680px">
    <el-input v-model.trim="keyword" clearable placeholder="搜索图标名称" />
    <el-table :data="filteredIconRows" border>
      <el-table-column label="图标" width="100">
        <template #default="{ row }">
          <el-avatar>{{ row.name.slice(0, 1).toUpperCase() }}</el-avatar>
        </template>
      </el-table-column>
      <el-table-column prop="name" label="名称" />
      <el-table-column label="操作" width="100">
        <template #default="{ row }">
          <el-button link type="primary" @click="selectIcon(row.name)">选择</el-button>
        </template>
      </el-table-column>
    </el-table>
    <template #footer>
      <el-button @click="visible = false">关闭</el-button>
    </template>
  </el-dialog>
</template>

<script setup>
import { computed, ref } from 'vue'

const visible = defineModel({ type: Boolean, default: false })
const emit = defineEmits(['select'])
const keyword = ref('')

const icons = [
  'user',
  'peoples',
  'tree',
  'dict',
  'edit',
  'message',
  'online',
  'logininfor',
  'log',
  'redis',
  'job',
  'server',
  'druid',
  'robot',
  'map',
  'image',
  'table',
  'sound',
  'tool',
  'monitor',
  'warning',
  'chart',
  'food',
  'shopping',
  'calendar',
  'documentation',
  'code',
  'build',
  'swagger'
]

const filteredIcons = computed(() => {
  const value = keyword.value.toLowerCase()
  if (!value) return icons
  return icons.filter((item) => item.toLowerCase().includes(value))
})
const filteredIconRows = computed(() => filteredIcons.value.map((name) => ({ name })))

function selectIcon(item) {
  emit('select', item)
  visible.value = false
}
</script>
