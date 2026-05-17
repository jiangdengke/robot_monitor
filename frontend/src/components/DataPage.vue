<template>
  <a-card :bordered="false" class="data-page-card">
    <template #title>
      <div class="data-page-title">
        <div class="title-text">
          <div class="headline">{{ title }}</div>
          <div class="desc">{{ description }}</div>
        </div>
        <a-button type="primary" @click="$emit('refresh')">
          <template #icon><ReloadOutlined /></template>
          刷新
        </a-button>
      </div>
    </template>

    <div class="data-page-body">
      <div v-if="cards?.length" class="card-grid">
        <a-card
          v-for="card in cards"
          :key="card.title"
          :bordered="false"
          class="metric-card has-hover"
        >
          <template #title>
            <div class="metric-title">
              <span class="metric-dot" />
              <span>{{ card.title }}</span>
            </div>
          </template>
          <ul class="metric-lines">
            <li v-for="line in card.lines" :key="line">{{ line }}</li>
          </ul>
        </a-card>
      </div>

      <a-table
        v-if="columns?.length"
        :columns="tableColumns"
        :data-source="rows"
        :pagination="false"
        :row-key="rowKey || 'id'"
        size="middle"
        class="data-page-table"
      />
    </div>
  </a-card>
</template>

<script setup>
import { computed, watch } from 'vue'
import { ReloadOutlined } from '@ant-design/icons-vue'
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
.data-page-card {
  border-radius: var(--radius-lg);
}

.data-page-title {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 16px;
}

.headline {
  font-size: 18px;
  font-weight: 700;
  color: var(--text-strong);
  letter-spacing: 0.02em;
}

.desc {
  margin-top: 4px;
  color: var(--text-muted);
  font-size: 13px;
}

.data-page-body {
  display: grid;
  gap: 18px;
}

.card-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(240px, 1fr));
  gap: 14px;
}

.metric-card {
  border-radius: var(--radius-md);
  background: var(--surface-muted);
  border: 1px solid var(--border-soft);
}

.metric-title {
  display: inline-flex;
  align-items: center;
  gap: 8px;
  color: var(--text-strong);
  font-weight: 600;
}

.metric-dot {
  width: 8px;
  height: 8px;
  border-radius: 50%;
  background: var(--brand-primary);
  box-shadow: 0 0 0 4px rgb(47 84 235 / 14%);
}

.metric-lines {
  list-style: none;
  padding: 0;
  margin: 0;
  display: grid;
  gap: 6px;
  font-size: 13px;
  color: var(--text-default);
}

.metric-lines li {
  line-height: 1.7;
  position: relative;
  padding-left: 14px;
}

.metric-lines li::before {
  content: '';
  position: absolute;
  left: 0;
  top: 10px;
  width: 6px;
  height: 2px;
  border-radius: 2px;
  background: var(--text-faint);
}
</style>
