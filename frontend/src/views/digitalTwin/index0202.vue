<template>
  <el-card shadow="never">
    <template #header>
      <el-row justify="space-between" align="middle">
        <el-space direction="vertical" alignment="flex-start">
          <el-text tag="b" size="large">数字孪生运营看板</el-text>
          <el-text type="info">备用分屏看板视图，聚合贵宾室容量、机器人、旅客预警和巡检异常。</el-text>
        </el-space>
        <el-space wrap>
          <el-select v-model="activeRoomCode" clearable placeholder="全部贵宾室" @change="loadDashboard">
            <el-option v-for="room in rooms" :key="room.roomCode || room.deptId" :label="room.deptName || room.roomCode" :value="room.roomCode" />
          </el-select>
          <el-button type="primary" :loading="loading" @click="loadDashboard">刷新</el-button>
        </el-space>
      </el-row>
    </template>

    <el-row :gutter="16">
      <el-col v-for="metric in metrics" :key="metric.label" :xs="24" :sm="12" :md="6">
        <el-card shadow="hover">
          <el-statistic :title="metric.label" :value="metric.value" />
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="16">
      <el-col :xs="24" :lg="14">
      <el-card shadow="never">
        <template #header><el-text tag="b">区域容量排行</el-text></template>
        <el-table v-loading="loading" :data="capacityRows" border>
          <el-table-column prop="regionName" label="区域" min-width="140" />
          <el-table-column prop="roomCode" label="贵宾室" min-width="120" />
          <el-table-column label="容量" width="180">
            <template #default="{ row }">
              <el-progress :percentage="capacityPercent(row)" :status="capacityPercent(row) >= 90 ? 'exception' : undefined" />
            </template>
          </el-table-column>
          <el-table-column label="人数" width="120">
            <template #default="{ row }">{{ row.curCapacity || 0 }}/{{ row.maxCapacity || 0 }}</template>
          </el-table-column>
        </el-table>
      </el-card>
      </el-col>

      <el-col :xs="24" :lg="10">
      <el-card shadow="never">
        <template #header><el-text tag="b">机器人状态</el-text></template>
        <el-timeline>
          <el-timeline-item v-for="robot in robots" :key="robot.robotId || robot.id" :timestamp="robot.roomCode || activeRoomCode || '全部'">
            <el-text tag="b">{{ robot.robotName || robot.robotId }}</el-text>
            <el-text type="info">{{ robot.workingState || '空闲' }} · 区域 {{ robot.regionId || '-' }} · {{ pointText(robot) || '无坐标' }}</el-text>
          </el-timeline-item>
        </el-timeline>
        <el-empty v-if="!robots.length && !loading" description="暂无机器人数据" />
      </el-card>
      </el-col>

      <el-col :xs="24">
      <el-card shadow="never">
        <template #header><el-text tag="b">预警和巡检消息</el-text></template>
        <el-table v-loading="loading" :data="eventRows" border>
          <el-table-column prop="type" label="类型" width="110">
            <template #default="{ row }"><el-tag :type="row.tag">{{ row.type }}</el-tag></template>
          </el-table-column>
          <el-table-column prop="target" label="对象" min-width="140" />
          <el-table-column prop="content" label="内容" min-width="260" />
          <el-table-column prop="roomCode" label="贵宾室" width="130" />
          <el-table-column label="坐标" width="180">
            <template #default="{ row }">{{ row.coordinate || '-' }}</template>
          </el-table-column>
        </el-table>
      </el-card>
      </el-col>
    </el-row>
  </el-card>
</template>

<script setup>
import { computed, onMounted, ref } from 'vue'
import { getDigitalTwinAll, getRoomList } from '@/api/system'
import { request } from '@/api/http'

const loading = ref(false)
const activeRoomCode = ref('')
const rooms = ref([])
const regions = ref([])
const robots = ref([])
const passengers = ref([])
const inspections = ref([])

const metrics = computed(() => [
  { label: '贵宾室', value: rooms.value.length, type: 'blue' },
  { label: '区域点位', value: regions.value.length, type: 'green' },
  { label: '机器人', value: robots.value.length, type: 'orange' },
  { label: '旅客预警', value: warningRows.value.length, type: 'red' }
])

const capacityRows = computed(() => [...regions.value].sort((a, b) => capacityPercent(b) - capacityPercent(a)).slice(0, 10))

const warningRows = computed(() => passengers.value.flatMap((passenger) => (passenger.warningLogList || []).map((warning) => ({
  type: '旅客预警',
  tag: warning.isSuccess === '1' ? 'success' : 'warning',
  target: passenger.userName || passenger.cardNo || passenger.id,
  content: warning.warningInfo || warning.warningType || '预警',
  roomCode: passenger.roomCode || activeRoomCode.value,
  coordinate: pointText(passenger)
}))))

const inspectionRows = computed(() => inspections.value.map((item) => ({
  type: '巡检异常',
  tag: 'danger',
  target: item.robotId || item.inspTaskId,
  content: item.abnormalInfo || item.abnormal || '异常',
  roomCode: item.roomCode || activeRoomCode.value,
  coordinate: pointText(item)
})))

const eventRows = computed(() => [...warningRows.value, ...inspectionRows.value])

async function loadDashboard() {
  loading.value = true
  try {
    const [roomResponse, regionResponse, twinResponse] = await Promise.all([
      getRoomList(),
      request('/DigitalTwin/selectRegionList', { query: { roomCode: activeRoomCode.value } }),
      getDigitalTwinAll({ roomCode: activeRoomCode.value })
    ])
    rooms.value = roomResponse.data || []
    regions.value = regionResponse.data || []
    const data = twinResponse.data || {}
    robots.value = data.robotList || []
    passengers.value = data.passengerList || []
    inspections.value = data.inspectionList || []
  } finally {
    loading.value = false
  }
}

function capacityPercent(row) {
  const max = Number(row.maxCapacity || 0)
  if (!max) return 0
  return Math.min(100, Math.round((Number(row.curCapacity || 0) / max) * 100))
}

function pointText(item) {
  return item.coordinate || item.cameraCoordinates || item.oriCoordinate || item.position || item.coordinates || ''
}

onMounted(loadDashboard)
</script>
