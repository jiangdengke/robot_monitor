<template>
  <el-card class="page-card">
    <template #header>
      <div class="page-header">
        <div>
          <h1>数字孪生总览</h1>
          <p>地图、区域、机器人、旅客和预警数据已在前端完成联动。</p>
        </div>
        <el-button type="primary" @click="loadDashboard">刷新</el-button>
      </div>
    </template>

    <el-row :gutter="16" class="dashboard-grid">
      <el-col v-for="metric in metrics" :key="metric.label" :xs="24" :sm="12" :md="8" :lg="4">
        <el-card shadow="hover" class="metric-card">
          <strong>{{ metric.value }}</strong>
          <span>{{ metric.label }}</span>
        </el-card>
      </el-col>
    </el-row>

    <div class="panel-grid">
      <CanvasImg
        :room-name="activeRoomName"
        :regions="filteredRegions"
        :robots="filteredRobots"
        :passengers="filteredPassengers"
        :selected-region="selectedRegion"
        @select-region="selectRegion"
        @select-robot="selectRobot"
        @select-passenger="selectPassenger"
      />
      <MessageInfo :active-room="activeRoomName" :messages="messages" />
    </div>

    <el-tabs v-model="tab" class="tab-panel">
      <el-tab-pane label="房间/区域" name="area">
        <el-select v-model="activeRoomCode" clearable placeholder="选择贵宾室" class="room-select">
          <el-option v-for="room in roomList" :key="room.roomCode || room.deptId" :label="room.deptName || room.roomCode" :value="room.roomCode" />
        </el-select>
        <el-table :data="filteredRegions" border>
          <el-table-column prop="regionName" label="区域名称" min-width="140" />
          <el-table-column prop="roomCode" label="房间编码" min-width="120" />
          <el-table-column prop="maxCapacity" label="容量" width="90" />
          <el-table-column prop="cameraCoordinates" label="坐标" min-width="180" />
        </el-table>
      </el-tab-pane>
      <el-tab-pane label="机器人" name="robot">
        <el-table :data="filteredRobots" border>
          <el-table-column prop="robotId" label="机器人编号" min-width="130" />
          <el-table-column prop="robotName" label="名称" min-width="140" />
          <el-table-column prop="robotIp" label="IP" min-width="140" />
          <el-table-column prop="workingState" label="工作状态" min-width="120" />
          <el-table-column prop="batteryState" label="电量" width="90" />
        </el-table>
      </el-tab-pane>
      <el-tab-pane label="旅客" name="passenger">
        <el-table :data="filteredPassengers" border>
          <el-table-column prop="userName" label="旅客" min-width="120" />
          <el-table-column prop="flightNo" label="航班" min-width="120" />
          <el-table-column prop="regionId" label="区域" width="100" />
          <el-table-column prop="createTime" label="时间" min-width="170" />
        </el-table>
      </el-tab-pane>
    </el-tabs>

    <el-drawer v-model="drawerVisible" title="联动详情" size="420px">
      <el-descriptions :column="1" border>
        <el-descriptions-item v-for="item in selectedDetail" :key="item.label" :label="item.label">
          {{ item.value || '-' }}
        </el-descriptions-item>
      </el-descriptions>
    </el-drawer>

    <el-alert v-if="errorMessage" class="hint" :title="errorMessage" type="warning" :closable="false" show-icon />
  </el-card>
</template>

<script setup>
import { computed, onMounted, ref } from 'vue'
import MessageInfo from './MessageInfo.vue'
import CanvasImg from './canvasImg.vue'
import { getRoomList, listConfigRobots, listPassengerWarningLogs, listPassengers } from '@/api/system'
import { request } from '@/api/http'

const tab = ref('area')
const activeRoomCode = ref('')
const roomList = ref([])
const robots = ref([])
const regions = ref([])
const passengers = ref([])
const warnings = ref([])
const selectedRegion = ref(null)
const drawerVisible = ref(false)
const selectedDetail = ref([])
const errorMessage = ref('')

const activeRoomName = computed(() => roomList.value.find((room) => room.roomCode === activeRoomCode.value)?.deptName || activeRoomCode.value || '全部贵宾室')
const filteredRegions = computed(() => activeRoomCode.value ? regions.value.filter((item) => item.roomCode === activeRoomCode.value) : regions.value)
const filteredRobots = computed(() => activeRoomCode.value ? robots.value.filter((item) => item.roomCode === activeRoomCode.value || item.region?.roomCode === activeRoomCode.value) : robots.value)
const filteredPassengers = computed(() => {
  if (!selectedRegion.value) return passengers.value
  return passengers.value.filter((item) => String(item.regionId || '') === String(selectedRegion.value.id || ''))
})
const metrics = computed(() => [
  { label: '房间数', value: roomList.value.length },
  { label: '区域点位', value: filteredRegions.value.length },
  { label: '机器人', value: filteredRobots.value.length },
  { label: '旅客', value: filteredPassengers.value.length },
  { label: '预警', value: warnings.value.length }
])
const messages = computed(() => [
  ...warnings.value.slice(0, 4).map((item) => ({ id: `w-${item.id}`, time: item.createTime || '', type: 'warning', content: `${item.userName || '旅客'}：${item.warningContent || item.warningType || '预警'}` })),
  ...filteredRobots.value.slice(0, 3).map((item) => ({ id: `r-${item.id}`, time: item.updateTime || '', type: 'primary', content: `${item.robotName || item.robotId} 当前状态：${item.workingState || '-'}` }))
])

async function loadDashboard() {
  errorMessage.value = ''
  try {
    const [roomResponse, robotResponse, regionResponse, passengerResponse, warningResponse] = await Promise.all([
      getRoomList(),
      listConfigRobots({ pageNum: 1, pageSize: 200 }),
      request('/config/region/list', { query: { pageNum: 1, pageSize: 200 } }),
      listPassengers({ pageNum: 1, pageSize: 200 }),
      listPassengerWarningLogs({ pageNum: 1, pageSize: 50 })
    ])
    roomList.value = roomResponse.data || []
    robots.value = robotResponse.rows || []
    regions.value = regionResponse.rows || []
    passengers.value = passengerResponse.rows || []
    warnings.value = warningResponse.rows || []
    if (!activeRoomCode.value && roomList.value.length) {
      activeRoomCode.value = roomList.value[0].roomCode || ''
    }
  } catch (error) {
    errorMessage.value = error?.payload?.msg || error?.message || '数字孪生数据加载失败'
  }
}

function selectRegion(region) {
  selectedRegion.value = region
  selectedDetail.value = [
    { label: '区域名称', value: region.regionName || region.areaName },
    { label: '房间编码', value: region.roomCode },
    { label: '容量', value: region.maxCapacity },
    { label: '坐标', value: region.cameraCoordinates }
  ]
  drawerVisible.value = true
}

function selectRobot(robot) {
  selectedDetail.value = [
    { label: '机器人编号', value: robot.robotId },
    { label: '名称', value: robot.robotName },
    { label: 'IP', value: robot.robotIp },
    { label: '工作状态', value: robot.workingState },
    { label: '电量', value: robot.batteryState }
  ]
  drawerVisible.value = true
}

function selectPassenger(passenger) {
  selectedDetail.value = [
    { label: '旅客', value: passenger.userName },
    { label: '航班', value: passenger.flightNo },
    { label: '区域', value: passenger.regionId },
    { label: '时间', value: passenger.createTime }
  ]
  drawerVisible.value = true
}

onMounted(loadDashboard)
</script>

<style scoped>
.page-card { padding: 24px; }
.page-header { display: flex; justify-content: space-between; align-items: center; gap: 16px; }
.page-header h1 { margin: 0; font-size: 28px; }
.page-header p { margin: 8px 0 0; color: var(--text-soft); }
.dashboard-grid { margin-top: 18px; }
.metric-card { display: grid; gap: 6px; }
.metric-card strong { font-size: 24px; }
.metric-card span { color: var(--text-soft); font-size: 13px; }
.panel-grid { display: grid; grid-template-columns: minmax(0, 1.4fr) minmax(320px, .6fr); gap: 16px; margin-top: 18px; }
.tab-panel { margin-top: 18px; }
.room-select { width: 260px; margin-bottom: 12px; }
.hint { margin-top: 16px; }
@media (max-width: 1100px) { .panel-grid { grid-template-columns: 1fr; } }
</style>
