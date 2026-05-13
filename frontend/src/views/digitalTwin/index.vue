<template>
  <el-card shadow="never">
    <template #header>
      <el-row justify="space-between" align="middle">
        <el-space direction="vertical" alignment="flex-start">
          <el-text tag="b" size="large">数字孪生总览</el-text>
          <el-text type="info">区域、机器人、旅客、巡检和桌台数据实时联动，硬件动作在本地以任务方式提交。</el-text>
        </el-space>
        <el-space wrap>
          <el-select v-model="activeRoomCode" clearable placeholder="选择贵宾室" @change="loadDashboard">
            <el-option
              v-for="room in roomList"
              :key="room.roomCode || room.deptId"
              :label="room.deptName || room.roomCode"
              :value="room.roomCode"
            />
          </el-select>
          <el-button :loading="loading" type="primary" @click="loadDashboard">刷新</el-button>
        </el-space>
      </el-row>
    </template>

    <el-row :gutter="16">
      <el-col v-for="metric in metrics" :key="metric.label" :xs="24" :sm="12" :md="8" :lg="4">
        <el-card shadow="hover">
          <el-statistic :title="metric.label" :value="metric.value" />
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="16">
      <el-col :xs="24" :lg="16">
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
      </el-col>
      <el-col :xs="24" :lg="8">
      <MessageInfo :active-room="activeRoomName" :messages="messages" />
      </el-col>
    </el-row>

    <el-tabs v-model="tab">
      <el-tab-pane label="ALL" name="all">
        <el-row :gutter="16">
          <el-col :xs="24" :lg="12">
            <el-table v-loading="loading" :data="filteredRegions" border>
              <el-table-column prop="regionName" label="区域名称" min-width="140" />
              <el-table-column prop="areaName" label="功能区" min-width="130" />
              <el-table-column prop="roomCode" label="房间编码" min-width="120" />
              <el-table-column prop="curCapacity" label="当前人数" width="100" />
              <el-table-column prop="maxCapacity" label="容量" width="90" />
              <el-table-column label="坐标" min-width="180">
                <template #default="{ row }">{{ pointText(row) }}</template>
              </el-table-column>
              <el-table-column label="操作" width="150" fixed="right">
                <template #default="{ row }">
                  <el-button size="small" type="primary" :loading="actionLoading" @click="guideRegion(row)">机器人引导</el-button>
                </template>
              </el-table-column>
            </el-table>
          </el-col>
          <el-col :xs="24" :lg="12">
            <el-table v-loading="loading" :data="filteredRobots" border>
              <el-table-column prop="robotId" label="机器人编号" min-width="130" />
              <el-table-column prop="robotName" label="名称" min-width="140" />
              <el-table-column prop="regionId" label="区域" width="100" />
              <el-table-column label="坐标" min-width="170">
                <template #default="{ row }">{{ pointText(row) }}</template>
              </el-table-column>
              <el-table-column prop="workingState" label="工作状态" min-width="120" />
              <el-table-column label="操作" width="140" fixed="right">
                <template #default="{ row }">
                  <el-button size="small" :loading="actionLoading" @click="interruptRobot(row)">停止任务</el-button>
                </template>
              </el-table-column>
            </el-table>
          </el-col>
        </el-row>
      </el-tab-pane>

      <el-tab-pane label="在舱旅客" name="passenger">
        <el-table v-loading="loading" :data="filteredPassengers" border>
          <el-table-column prop="userName" label="旅客姓名" min-width="120" />
          <el-table-column prop="flightNo" label="航班号" min-width="120" />
          <el-table-column prop="estmTakeOffTime" label="预计起飞时间" min-width="140" />
          <el-table-column prop="latestOffStatus" label="旅客状态" min-width="120" />
          <el-table-column prop="regionId" label="位置" width="100" />
          <el-table-column label="提醒内容" min-width="220">
            <template #default="{ row }">
              <el-space v-if="row.warningLogList?.length" wrap>
                <el-tag
                  v-for="warning in row.warningLogList"
                  :key="warning.id"
                  size="small"
                  :type="warning.isSuccess === '1' ? 'success' : 'warning'"
                >
                  {{ warning.warningInfo || warning.warningType || '预警' }}
                </el-tag>
              </el-space>
              <span v-else>-</span>
            </template>
          </el-table-column>
          <el-table-column label="操作" width="210" fixed="right">
            <template #default="{ row }">
              <el-space>
                <el-button size="small" :disabled="!firstWarning(row)" :loading="actionLoading" @click="manualNotice(row)">人工提醒</el-button>
                <el-button size="small" type="primary" :disabled="!firstWarning(row)" :loading="actionLoading" @click="robotNotice(row)">机器人提醒</el-button>
              </el-space>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>

      <el-tab-pane label="巡检预警" name="inspection">
        <el-table v-loading="loading" :data="inspectionList" border>
          <el-table-column prop="areaName" label="位置" min-width="120" />
          <el-table-column prop="abnormalInfo" label="提醒内容" min-width="220" />
          <el-table-column prop="abnormal" label="状态" min-width="100" />
          <el-table-column label="位置坐标" min-width="170">
            <template #default="{ row }">{{ pointText(row) }}</template>
          </el-table-column>
          <el-table-column label="操作" width="120" fixed="right">
            <template #default="{ row }">
              <el-button size="small" type="primary" :loading="actionLoading" @click="handleInspection(row)">处理</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>

      <el-tab-pane label="翻台提醒" name="tableChange">
        <el-table v-loading="loading" :data="tableList" border>
          <el-table-column prop="tableNo" label="餐桌编号" min-width="120" />
          <el-table-column prop="status" label="状态" min-width="100" />
          <el-table-column prop="cameraCoordinates" label="位置" min-width="170" />
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
  </el-card>
</template>

<script setup>
import { computed, onMounted, ref } from 'vue'
import MessageInfo from './MessageInfo.vue'
import CanvasImg from './canvasImg.vue'
import { getDigitalTwinAll, getRoomList } from '@/api/system'
import { request } from '@/api/http'
import { showToast } from '@/utils/toast'

const tab = ref('all')
const activeRoomCode = ref('')
const roomList = ref([])
const regions = ref([])
const robots = ref([])
const passengers = ref([])
const inspectionList = ref([])
const tableList = ref([])
const selectedRegion = ref(null)
const drawerVisible = ref(false)
const selectedDetail = ref([])
const loading = ref(false)
const actionLoading = ref(false)
const actionMessage = ref('')
const actionType = ref('success')

const activeRoomName = computed(() => roomList.value.find((room) => room.roomCode === activeRoomCode.value)?.deptName || activeRoomCode.value || '全部贵宾室')
const filteredRegions = computed(() => activeRoomCode.value ? regions.value.filter((item) => item.roomCode === activeRoomCode.value) : regions.value)
const filteredRobots = computed(() => {
  const roomRegions = new Set(filteredRegions.value.map((item) => String(item.id)))
  return robots.value.filter((item) => !activeRoomCode.value || item.roomCode === activeRoomCode.value || roomRegions.has(String(item.regionId || '')))
})
const filteredPassengers = computed(() => {
  const list = activeRoomCode.value ? passengers.value.filter((item) => item.roomCode === activeRoomCode.value) : passengers.value
  if (!selectedRegion.value) return list
  return list.filter((item) => String(item.regionId || '') === String(selectedRegion.value.id || ''))
})
const warningList = computed(() => passengers.value.flatMap((item) => (item.warningLogList || []).map((warning) => ({ ...warning, passengerName: item.userName }))))
const pendingWarningCount = computed(() => warningList.value.filter((item) => item.isSuccess !== '1').length)
const metrics = computed(() => [
  { label: '房间数', value: roomList.value.length },
  { label: '区域点位', value: filteredRegions.value.length },
  { label: '机器人', value: filteredRobots.value.length },
  { label: '旅客', value: filteredPassengers.value.length },
  { label: '待处理预警', value: pendingWarningCount.value },
  { label: '巡检异常', value: inspectionList.value.length }
])
const messages = computed(() => [
  ...warningList.value.slice(0, 5).map((item) => ({
    id: `w-${item.id}`,
    time: '',
    type: item.isSuccess === '1' ? 'success' : 'warning',
    content: `${item.passengerName || '旅客'}：${item.warningInfo || item.warningType || '预警'}`
  })),
  ...inspectionList.value.slice(0, 4).map((item) => ({
    id: `i-${item.inspTaskId}-${item.point}`,
    time: '',
    type: 'danger',
    content: `${item.robotId || '机器人'} 巡检 ${item.areaName || item.point || ''}：${item.abnormalInfo || item.abnormal || '异常'}`
  })),
  ...filteredRobots.value.slice(0, 3).map((item) => ({
    id: `r-${item.robotId}`,
    time: '',
    type: 'primary',
    content: `${item.robotName || item.robotId} 当前区域：${item.regionId || '-'}`
  }))
])

async function loadDashboard() {
  loading.value = true
  actionMessage.value = ''
  try {
    const [roomResponse, regionResponse, twinResponse] = await Promise.all([
      getRoomList(),
      request('/DigitalTwin/selectRegionList', { query: { roomCode: activeRoomCode.value } }),
      getDigitalTwinAll({ roomCode: activeRoomCode.value })
    ])
    roomList.value = roomResponse.data || []
    if (!activeRoomCode.value && roomList.value.length) {
      activeRoomCode.value = roomList.value[0].roomCode || ''
      return loadDashboard()
    }
    const twin = twinResponse.data || {}
    regions.value = regionResponse.data || []
    robots.value = twin.robotList || []
    passengers.value = twin.passengerList || []
    inspectionList.value = twin.inspectionList || []
    tableList.value = twin.tableList || []
  } catch (error) {
    showAction(error?.payload?.msg || error?.message || '数字孪生数据加载失败', 'error')
  } finally {
    loading.value = false
  }
}

function selectRegion(region) {
  selectedRegion.value = selectedRegion.value?.id === region.id ? null : region
  selectedDetail.value = [
    { label: '区域名称', value: region.regionName || region.areaName },
    { label: '功能区', value: region.areaName },
    { label: '房间编码', value: region.roomCode },
    { label: '人数', value: `${region.curCapacity || 0}/${region.maxCapacity || 0}` },
    { label: '坐标', value: pointText(region) }
  ]
  drawerVisible.value = true
}

function selectRobot(robot) {
  selectedDetail.value = [
    { label: '机器人编号', value: robot.robotId },
    { label: '名称', value: robot.robotName },
    { label: '区域', value: robot.regionId },
    { label: '坐标', value: pointText(robot) }
  ]
  drawerVisible.value = true
}

function selectPassenger(passenger) {
  selectedDetail.value = [
    { label: '旅客', value: passenger.userName },
    { label: '航班', value: passenger.flightNo },
    { label: '会员等级', value: passenger.memLevel },
    { label: '区域', value: passenger.regionId },
    { label: '坐标', value: pointText(passenger) }
  ]
  drawerVisible.value = true
}

async function guideRegion(region) {
  const robot = filteredRobots.value[0] || robots.value[0]
  if (!robot) {
    showAction('没有可用机器人，无法提交引导任务', 'warning')
    return
  }
  await runAction('区域引导任务已提交', () => request('/DigitalTwin/guide', {
    query: {
      robotId: robot.robotId,
      areaId: region.id,
      languageType: 'CN'
    }
  }))
}

async function interruptRobot(robot) {
  await runAction('机器人任务已停止', () => request('/DigitalTwin/interruptGuideTask', {
    query: { robotId: robot.robotId }
  }))
}

async function manualNotice(passenger) {
  const warning = firstWarning(passenger)
  if (!warning) return
  await runAction('人工提醒已完成', () => request('/DigitalTwin/manualNotice', {
    method: 'POST',
    query: {
      warningId: warning.id,
      passengerId: passenger.id,
      coordinate: pointText(passenger),
      warningInfo: warning.warningInfo,
      roomCode: passenger.roomCode || activeRoomCode.value
    }
  }))
}

async function robotNotice(passenger) {
  const warning = firstWarning(passenger)
  if (!warning) return
  await runAction('机器人提醒任务已提交', () => request('/DigitalTwin/notifyCustomer', {
    query: {
      warningId: warning.id,
      passengerId: passenger.id,
      coordinate: pointText(passenger),
      warningInfo: warning.warningInfo || warning.warningType,
      roomCode: passenger.roomCode || activeRoomCode.value
    }
  }))
}

async function handleInspection(row) {
  await runAction('巡检异常已处理', () => request('/DigitalTwin/handleInspection', {
    method: 'POST',
    query: { id: row.id || row.inspTaskId }
  }))
}

async function runAction(successText, executor) {
  actionLoading.value = true
  actionMessage.value = ''
  try {
    await executor()
    await loadDashboard()
    showAction(successText, 'success')
  } catch (error) {
    showAction(error?.payload?.msg || error?.message || '操作失败', 'error')
  } finally {
    actionLoading.value = false
  }
}

function firstWarning(passenger) {
  const list = passenger.warningLogList || []
  return list.find((item) => item.isSuccess !== '1') || list[0]
}

function pointText(item) {
  return item.coordinate || item.cameraCoordinates || item.oriCoordinate || item.position || item.coordinates || ''
}

function showAction(message, type = 'success') {
  actionMessage.value = message
  actionType.value = type
  showToast(type, message)
}

onMounted(loadDashboard)
</script>
