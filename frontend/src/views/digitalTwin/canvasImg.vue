<template>
  <el-card shadow="never">
    <template #header>
      <el-row justify="space-between" align="middle">
        <el-space direction="vertical" alignment="flex-start">
          <el-text tag="b">{{ roomName }}</el-text>
          <el-text type="info">{{ selectedRegion?.regionName || selectedRegion?.areaName || '选择区域查看联动数据' }}</el-text>
        </el-space>
      <el-tag>{{ robots.length }} 台机器人</el-tag>
      </el-row>
    </template>
    <el-tabs>
      <el-tab-pane label="区域">
        <el-table :data="regions" border @row-click="$emit('select-region', $event)">
          <el-table-column prop="regionName" label="区域" min-width="140">
            <template #default="{ row }">{{ row.regionName || row.areaName || `区域${row.id}` }}</template>
          </el-table-column>
          <el-table-column label="容量" width="120">
            <template #default="{ row }">{{ row.curCapacity || 0 }}/{{ row.maxCapacity || 0 }}</template>
          </el-table-column>
          <el-table-column label="坐标" min-width="160">
            <template #default="{ row }">{{ pointText(row) || '-' }}</template>
          </el-table-column>
        </el-table>
      </el-tab-pane>
      <el-tab-pane label="机器人">
        <el-table :data="robots" border @row-click="$emit('select-robot', $event)">
          <el-table-column prop="robotName" label="机器人" min-width="140">
            <template #default="{ row }">{{ row.robotName || row.robotId || 'R' }}</template>
          </el-table-column>
          <el-table-column prop="workingState" label="状态" min-width="120" />
          <el-table-column label="坐标" min-width="160">
            <template #default="{ row }">{{ pointText(row) || '-' }}</template>
          </el-table-column>
        </el-table>
      </el-tab-pane>
      <el-tab-pane label="旅客">
        <el-table :data="passengers" border @row-click="$emit('select-passenger', $event)">
          <el-table-column prop="userName" label="旅客" min-width="120" />
          <el-table-column prop="flightNo" label="航班" min-width="120" />
          <el-table-column prop="regionId" label="区域" width="100" />
          <el-table-column label="坐标" min-width="160">
            <template #default="{ row }">{{ pointText(row) || '-' }}</template>
          </el-table-column>
        </el-table>
      </el-tab-pane>
    </el-tabs>
  </el-card>
</template>

<script setup>
defineProps({
  roomName: { type: String, default: '贵宾室地图' },
  regions: { type: Array, default: () => [] },
  robots: { type: Array, default: () => [] },
  passengers: { type: Array, default: () => [] },
  selectedRegion: { type: Object, default: null }
})

defineEmits(['select-region', 'select-robot', 'select-passenger'])

function pointText(item) {
  return item.coordinate || item.cameraCoordinates || item.oriCoordinate || item.position || item.coordinates || ''
}
</script>
