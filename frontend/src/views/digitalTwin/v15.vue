<template>
  <el-card class="page-card">
    <template #header>
      <div class="page-header">
        <div>
          <h1>数字孪生模型视图</h1>
          <p>用于承接 v15 模型/平面点位视图，当前以区域坐标、机器人和旅客轨迹做本地可运行展示。</p>
        </div>
        <div class="header-actions">
          <el-select v-model="activeRoomCode" clearable placeholder="选择贵宾室" class="room-select" @change="loadModel">
            <el-option v-for="room in rooms" :key="room.roomCode || room.deptId" :label="room.deptName || room.roomCode" :value="room.roomCode" />
          </el-select>
          <el-button type="primary" :loading="loading" @click="loadModel">刷新</el-button>
        </div>
      </div>
    </template>

    <div class="model-layout">
      <section class="model-canvas">
        <div class="canvas-grid"></div>
        <button
          v-for="region in regions"
          :key="`r-${region.id}`"
          type="button"
          class="point region"
          :style="pointStyle(region)"
          @click="selectItem('区域', region)"
        >
          {{ region.regionName || region.areaName || region.id }}
        </button>
        <button
          v-for="robot in robots"
          :key="`robot-${robot.robotId || robot.id}`"
          type="button"
          class="point robot"
          :style="pointStyle(robot, 8)"
          @click="selectItem('机器人', robot)"
        >
          {{ robot.robotName || robot.robotId }}
        </button>
        <button
          v-for="passenger in passengers"
          :key="`p-${passenger.id}`"
          type="button"
          class="point passenger"
          :style="pointStyle(passenger, -8)"
          @click="selectItem('旅客', passenger)"
        >
          {{ passenger.userName || passenger.cardNo || passenger.id }}
        </button>
      </section>

      <el-card shadow="never" class="side-panel">
        <template #header><h2>点位详情</h2></template>
        <el-descriptions v-if="selected" :column="1" border>
          <el-descriptions-item label="类型">{{ selected.type }}</el-descriptions-item>
          <el-descriptions-item label="名称">{{ selected.name }}</el-descriptions-item>
          <el-descriptions-item label="贵宾室">{{ selected.roomCode || activeRoomCode || '-' }}</el-descriptions-item>
          <el-descriptions-item label="区域">{{ selected.regionId || selected.areaName || '-' }}</el-descriptions-item>
          <el-descriptions-item label="坐标">{{ selected.coordinate || '-' }}</el-descriptions-item>
        </el-descriptions>
        <el-empty v-else description="点击模型点位查看详情" />

        <el-divider content-position="left">模型图层</el-divider>
        <el-checkbox-group v-model="visibleLayers">
          <el-checkbox-button label="region" value="region">区域</el-checkbox-button>
          <el-checkbox-button label="robot" value="robot">机器人</el-checkbox-button>
          <el-checkbox-button label="passenger" value="passenger">旅客</el-checkbox-button>
        </el-checkbox-group>
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
const rawRegions = ref([])
const rawRobots = ref([])
const rawPassengers = ref([])
const selected = ref(null)
const visibleLayers = ref(['region', 'robot', 'passenger'])

const regions = computed(() => visibleLayers.value.includes('region') ? rawRegions.value : [])
const robots = computed(() => visibleLayers.value.includes('robot') ? rawRobots.value : [])
const passengers = computed(() => visibleLayers.value.includes('passenger') ? rawPassengers.value : [])

async function loadModel() {
  loading.value = true
  try {
    const [roomResponse, regionResponse, twinResponse] = await Promise.all([
      getRoomList(),
      request('/DigitalTwin/selectRegionList', { query: { roomCode: activeRoomCode.value } }),
      getDigitalTwinAll({ roomCode: activeRoomCode.value })
    ])
    rooms.value = roomResponse.data || []
    rawRegions.value = regionResponse.data || []
    const data = twinResponse.data || {}
    rawRobots.value = data.robotList || []
    rawPassengers.value = data.passengerList || []
  } finally {
    loading.value = false
  }
}

function selectItem(type, item) {
  selected.value = {
    type,
    name: item.regionName || item.areaName || item.robotName || item.robotId || item.userName || item.cardNo || item.id,
    roomCode: item.roomCode,
    regionId: item.regionId,
    areaName: item.areaName,
    coordinate: pointText(item)
  }
}

function pointStyle(item, offset = 0) {
  const { x, y } = parsePoint(item)
  return {
    left: `${Math.max(4, Math.min(94, x + offset / 8))}%`,
    top: `${Math.max(6, Math.min(90, y + offset / 10))}%`
  }
}

function parsePoint(item) {
  const text = pointText(item)
  const values = String(text).match(/-?\d+(\.\d+)?/g)?.map(Number) || []
  if (values.length >= 2) {
    return { x: normalize(values[0]), y: normalize(values[1]) }
  }
  const seed = Number(item.id || item.regionId || String(item.robotId || item.cardNo || '').replace(/\D/g, '') || 1)
  return { x: 12 + ((seed * 17) % 76), y: 12 + ((seed * 23) % 72) }
}

function normalize(value) {
  const abs = Math.abs(value)
  if (abs <= 100) return abs
  return abs % 100
}

function pointText(item) {
  return item.coordinate || item.cameraCoordinates || item.oriCoordinate || item.position || item.coordinates || ''
}

onMounted(loadModel)
</script>

<style scoped>
.page-card { padding: 24px; }
.page-header { display: flex; justify-content: space-between; align-items: center; gap: 16px; }
.page-header h1 { margin: 0; font-size: 28px; }
.page-header p { margin: 8px 0 0; color: var(--text-soft); }
.header-actions { display: flex; align-items: center; gap: 10px; }
.room-select { width: 220px; }
.model-layout { display: grid; grid-template-columns: minmax(0, 1fr) 360px; gap: 16px; margin-top: 18px; }
.model-canvas {
  position: relative;
  min-height: 640px;
  overflow: hidden;
  border: 1px solid rgba(27, 102, 166, .22);
  border-radius: 20px;
  background:
    radial-gradient(circle at 24% 28%, rgba(47, 128, 237, .2), transparent 26%),
    linear-gradient(135deg, #eff7ff, #f8fbff 48%, #eaf4ff);
}
.canvas-grid {
  position: absolute;
  inset: 0;
  background-image:
    linear-gradient(rgba(27, 102, 166, .12) 1px, transparent 1px),
    linear-gradient(90deg, rgba(27, 102, 166, .12) 1px, transparent 1px);
  background-size: 42px 42px;
}
.point {
  position: absolute;
  transform: translate(-50%, -50%);
  max-width: 132px;
  padding: 8px 10px;
  border: 0;
  border-radius: 999px;
  color: #fff;
  font-size: 12px;
  box-shadow: 0 10px 24px rgba(5, 37, 69, .18);
  cursor: pointer;
}
.point.region { background: #2f80ed; }
.point.robot { background: #f2994a; }
.point.passenger { background: #27ae60; }
.side-panel h2 { margin: 0; font-size: 16px; }
@media (max-width: 1100px) {
  .model-layout { grid-template-columns: 1fr; }
}
</style>
