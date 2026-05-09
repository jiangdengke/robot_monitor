<template>
  <el-card shadow="never" class="mini-card">
    <template #header>
      <h2>桌台路径</h2>
    </template>
    <div class="path-line">
      <button
        v-for="table in tables"
        :key="table.id"
        :class="{ active: selected?.id === table.id }"
        @click="selected = table"
      >
        {{ table.tableNo }}
      </button>
    </div>
    <el-descriptions v-if="selected" :column="2" border class="path-detail">
      <el-descriptions-item label="桌号">{{ selected.tableNo }}</el-descriptions-item>
      <el-descriptions-item label="区域">{{ selected.regionName || '-' }}</el-descriptions-item>
      <el-descriptions-item label="坐标">{{ selected.cameraCoordinates || '-' }}</el-descriptions-item>
      <el-descriptions-item label="状态">{{ selected.status || '-' }}</el-descriptions-item>
    </el-descriptions>
    <el-empty v-else description="选择一个桌台查看路径信息" />
  </el-card>
</template>

<script setup>
import { ref, watch } from 'vue'

const props = defineProps({
  tables: { type: Array, default: () => [] }
})

const selected = ref(null)

watch(
  () => props.tables,
  (tables) => {
    if (tables.length && !selected.value) selected.value = tables[0]
  },
  { immediate: true }
)
</script>

<style scoped>
.mini-card h2 { margin: 0; font-size: 16px; }
.path-line { display: flex; flex-wrap: wrap; gap: 10px; margin-top: 12px; padding: 12px; border-radius: 999px; background: linear-gradient(90deg, #e8f2ff, #f1f8ee); }
.path-line button { border: 0; border-radius: 999px; padding: 8px 14px; background: #fff; color: var(--text-soft); cursor: pointer; }
.path-line button.active { background: var(--brand); color: #fff; }
.path-detail { margin-top: 16px; }
</style>
