<template>
  <el-card shadow="never" class="mini-card">
    <template #header>
      <h2>桌台查找</h2>
    </template>
    <el-input v-model="keyword" placeholder="输入桌号、区域或坐标" clearable />
    <el-table :data="matched" border class="match-table">
      <el-table-column prop="tableNo" label="桌号" width="120" />
      <el-table-column prop="regionName" label="区域" min-width="140" />
      <el-table-column prop="cameraCoordinates" label="坐标" min-width="180" />
      <el-table-column prop="status" label="状态" width="100" />
    </el-table>
  </el-card>
</template>

<script setup>
import { computed, ref } from 'vue'

const props = defineProps({
  tables: { type: Array, default: () => [] }
})

const keyword = ref('')
const matched = computed(() => {
  const key = keyword.value.trim().toLowerCase()
  if (!key) return props.tables
  return props.tables.filter((item) =>
    [item.tableNo, item.regionName, item.cameraCoordinates, item.status].some((value) => String(value || '').toLowerCase().includes(key))
  )
})
</script>

<style scoped>
.mini-card h2 { margin: 0 0 10px; font-size: 16px; }
.match-table { margin-top: 12px; }
</style>
