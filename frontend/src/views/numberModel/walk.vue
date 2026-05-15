<template>
  <el-card shadow="never">
    <template #header>
      <el-text tag="b">桌台路径</el-text>
    </template>
    <el-space wrap>
      <el-button
        v-for="table in tables"
        :key="table.id"
        :type="selected?.id === table.id ? 'primary' : 'default'"
        :plain="selected?.id !== table.id"
        @click="selected = table"
      >
        {{ table.tableNo }}
      </el-button>
    </el-space>
    <el-descriptions v-if="selected" :column="2" border>
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
