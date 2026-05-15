<template>
  <a-card :bordered="false">
    <template #title>
      <div class="data-page-title">
        <div>
          <div class="headline">{{ title }}</div>
          <div class="desc">{{ description }}</div>
        </div>
        <a-button type="primary" @click="$emit('refresh')">刷新</a-button>
      </div>
    </template>

    <div class="data-page-body">
      <div v-if="cards?.length" class="card-grid">
        <a-card v-for="card in cards" :key="card.title" :title="card.title" :bordered="false">
          <div class="info-lines">
            <div v-for="line in card.lines" :key="line" class="info-line">{{ line }}</div>
          </div>
        </a-card>
      </div>

      <a-table
        v-if="columns?.length"
        :columns="tableColumns"
        :data-source="rows"
        :pagination="false"
        :row-key="rowKey || 'id'"
        size="middle"
      />
    </div>
  </a-card>
</template>

<script setup>
import { computed, watch } from 'vue'
import { toastError } from '@/utils/toast'

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

watch(
  () => props.errorMessage,
  (message) => {
    if (message) toastError(message)
  }
)

const tableColumns = computed(() =>
  (props.columns || []).map((column) => ({
    title: column.label,
    key: column.key,
    dataIndex: column.key.includes('.') ? undefined : column.key,
    customRender: ({ record }) => renderCell(record, column)
  }))
)

function getByPath(target, path) {
  return path.split('.').reduce((current, segment) => current?.[segment], target)
}

function renderCell(row, column) {
  if (typeof column.render === 'function') return column.render(row)
  const value = column.key.includes('.') ? getByPath(row, column.key) : row[column.key]
  return value === undefined || value === null || value === '' ? '-' : value
}
</script>

<style scoped>
.data-page-title {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 16px;
}

.headline {
  font-size: 18px;
  font-weight: 700;
}

.desc {
  margin-top: 4px;
  color: #6b7280;
  font-size: 13px;
}

.data-page-body {
  display: grid;
  gap: 16px;
}

.card-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(240px, 1fr));
  gap: 16px;
}

.info-lines {
  display: grid;
  gap: 8px;
}

.info-line {
  color: #4b5563;
  line-height: 1.6;
}
</style>
