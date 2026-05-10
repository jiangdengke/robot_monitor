<template>
  <el-card shadow="never">
    <template #header>
      <el-row justify="space-between" align="middle">
        <el-space direction="vertical" alignment="flex-start">
          <el-text tag="b" size="large">{{ title }}</el-text>
          <el-text type="info">{{ description }}</el-text>
        </el-space>
        <el-button type="primary" @click="$emit('refresh')">刷新</el-button>
      </el-row>
    </template>
    <el-space direction="vertical" fill>
      <el-row v-if="cards?.length" :gutter="16">
        <el-col v-for="card in cards" :key="card.title" :xs="24" :md="8">
          <el-card shadow="never">
            <template #header>
              <el-text tag="b">{{ card.title }}</el-text>
            </template>
            <el-space direction="vertical" alignment="flex-start">
              <el-text v-for="line in card.lines" :key="line">{{ line }}</el-text>
            </el-space>
          </el-card>
        </el-col>
      </el-row>
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
      <el-alert v-if="errorMessage" :title="errorMessage" type="error" :closable="false" />
    </el-space>
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
