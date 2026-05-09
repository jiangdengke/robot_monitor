<template>
  <el-card class="page-card">
    <template #header>
      <div class="page-header">
        <div>
          <h1>数字孪生运营看板</h1>
          <p>备用分屏看板视图，聚合贵宾室容量、机器人、旅客预警和巡检异常。</p>
        </div>
        <div class="header-actions">
          <el-select v-model="activeRoomCode" clearable placeholder="全部贵宾室" class="room-select" @change="loadDashboard">
            <el-option v-for="room in rooms" :key="room.roomCode || room.deptId" :label="room.deptName || room.roomCode" :value="room.roomCode" />
          </el-select>
          <el-button type="primary" :loading="loading" @click="loadDashboard">刷新</el-button>
        </div>
      </div>
    </template>

    <el-row :gutter="16">
      <el-col v-for="metric in metrics" :key="metric.label" :xs="24" :sm="12" :md="6">
        <el-card shadow="hover" class="metric-card" :class="metric.type">
          <strong>{{ metric.value }}</strong>
          <span>{{ metric.label }}</span>
        </el-card>
      </el-col>
    </el-row>

    <div class="board-grid">
      <el-card shadow="never">
        <template #header><h2>区域容量排行</h2></template>
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

      <el-card shadow="never">
        <template #header><h2>机器人状态</h2></template>
        <el-timeline>
          <el-timeline-item v-for="robot in robots" :key="robot.robotId || robot.id" :timestamp="robot.roomCode || activeRoomCode || '全部'">
            <strong>{{ robot.robotName || robot.robotId }}</strong>
            <p>{{ robot.workingState || '空闲' }} · 区域 {{ robot.regionId || '-' }} · {{ pointText(robot) || '无坐标' }}</p>
          </el-timeline-item>
        </el-timeline>
        <el-empty v-if="!robots.length && !loading" description="暂无机器人数据" />
      </el-card>

      <el-card shadow="never" class="wide-card">
        <template #header><h2>预警和巡检消息</h2></template>
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
    </div>
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

<style scoped>
.page-card { padding: 24px; }
.page-header { display: flex; justify-content: space-between; align-items: center; gap: 16px; }
.page-header h1 { margin: 0; font-size: 28px; }
.page-header p { margin: 8px 0 0; color: var(--text-soft); }
.header-actions { display: flex; align-items: center; gap: 10px; }
.room-select { width: 220px; }
.metric-card { display: grid; gap: 6px; border-left: 5px solid #2f80ed; }
.metric-card strong { font-size: 28px; }
.metric-card span { color: var(--text-soft); }
.metric-card.green { border-left-color: #27ae60; }
.metric-card.orange { border-left-color: #f2994a; }
.metric-card.red { border-left-color: #eb5757; }
.board-grid { display: grid; grid-template-columns: 1.15fr .85fr; gap: 16px; margin-top: 18px; }
.wide-card { grid-column: 1 / -1; }
h2 { margin: 0; font-size: 16px; }
@media (max-width: 1100px) {
  .page-header { align-items: flex-start; flex-direction: column; }
  .board-grid { grid-template-columns: 1fr; }
}
</style>
