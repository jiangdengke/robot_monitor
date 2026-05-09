<template>
  <el-dialog v-model="visible" title="选择图标" width="680px">
    <el-input v-model.trim="keyword" clearable placeholder="搜索图标名称" />
    <div class="icon-grid">
      <button
        v-for="item in filteredIcons"
        :key="item"
        class="icon-item"
        type="button"
        @click="selectIcon(item)"
      >
        <span class="icon-preview">{{ item.slice(0, 1).toUpperCase() }}</span>
        <span>{{ item }}</span>
      </button>
    </div>
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

function selectIcon(item) {
  emit('select', item)
  visible.value = false
}
</script>

<style scoped>
.icon-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(112px, 1fr));
  gap: 10px;
  margin-top: 14px;
}
.icon-item {
  display: grid;
  place-items: center;
  gap: 6px;
  padding: 12px 8px;
  border: 1px solid var(--line);
  border-radius: 12px;
  background: #fff;
  color: var(--text);
  cursor: pointer;
}
.icon-item:hover {
  border-color: var(--brand);
  background: #eaf4ff;
}
.icon-preview {
  display: inline-grid;
  width: 32px;
  height: 32px;
  place-items: center;
  border-radius: 10px;
  background: var(--brand);
  color: #fff;
  font-weight: 700;
}
</style>
