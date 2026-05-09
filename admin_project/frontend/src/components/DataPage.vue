<template>
  <el-card class="page-card">
    <template #header>
      <div class="page-header">
        <div>
          <h1>{{ title }}</h1>
          <p>{{ description }}</p>
        </div>
        <el-button type="primary" @click="$emit('refresh')">刷新</el-button>
      </div>
    </template>
    <div v-if="cards?.length" class="info-grid">
      <el-card v-for="card in cards" :key="card.title" shadow="never" class="info-panel">
        <h2>{{ card.title }}</h2>
        <p v-for="line in card.lines" :key="line">{{ line }}</p>
      </el-card>
    </div>
    <el-table v-if="columns?.length" :data="rows" border>
      <el-table-column
        v-for="column in columns"
        :key="column.key"
        :label="column.label"
        :prop="column.key.includes('.') ? undefined : column.key"
      >
        <template #default="{ row }">
          {{ renderCell(row, column) }}
        </template>
      </el-table-column>
    </el-table>
    <el-alert v-if="errorMessage" class="error-alert" :title="errorMessage" type="error" :closable="false" />
  </el-card>
</template>

<script setup>
const props = defineProps({
  title: { type: String, required: true },
  description: { type: String, required: true },
  columns: { type: Array, default: () => [] },
  rows: { type: Array, default: () => [] },
  rowKey: { type: String, default: '' },
  cards: { type: Array, default: () => [] },
  errorMessage: { type: String, default: '' }
})

defineEmits(['refresh'])

function getByPath(target, path) {
  return path.split('.').reduce((current, segment) => current?.[segment], target)
}

function renderCell(row, column) {
  if (typeof column.render === 'function') {
    return column.render(row)
  }
  const value = column.key.includes('.') ? getByPath(row, column.key) : row[column.key]
  return value === undefined || value === null || value === '' ? '-' : value
}
</script>

<style scoped>
.page-card { padding: 24px; }
.page-header { display: flex; justify-content: space-between; align-items: center; gap: 16px; margin-bottom: 18px; }
.page-header h1 { margin: 0; font-size: 28px; }
.page-header p { margin: 8px 0 0; color: var(--text-soft); }
.info-grid { display: grid; grid-template-columns: repeat(3, minmax(0, 1fr)); gap: 16px; margin-bottom: 18px; }
.info-panel { padding: 18px; border-radius: 12px; border: 1px solid var(--line); background: var(--panel-alt); }
.info-panel h2 { margin: 0 0 10px; font-size: 16px; }
.info-panel p { margin: 8px 0 0; color: var(--text-soft); }
.error-alert { margin-top: 16px; }
@media (max-width: 960px) { .info-grid { grid-template-columns: 1fr; } }
</style>
