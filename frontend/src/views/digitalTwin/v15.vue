<template>
  <el-card shadow="never">
    <template #header>
      <el-row justify="space-between" align="middle">
        <el-space direction="vertical" alignment="flex-start">
          <el-text tag="b" size="large">数字孪生模型视图</el-text>
          <el-text type="info">用于承接 v15 模型/平面点位视图，当前以区域坐标、机器人和旅客轨迹做本地可运行展示。</el-text>
        </el-space>
        <el-space wrap>
          <el-select v-model="activeRoomCode" clearable placeholder="选择贵宾室" @change="loadModel">
            <el-option v-for="room in rooms" :key="room.roomCode || room.deptId" :label="room.deptName || room.roomCode" :value="room.roomCode" />
          </el-select>
          <el-button type="primary" :loading="loading" @click="loadModel">刷新</el-button>
        </el-space>
      </el-row>
    </template>

    <el-row :gutter="16">
      <el-col :xs="24" :lg="16">
        <el-card shadow="never">
          <template #header><el-text tag="b">模型点位</el-text></template>
          <el-tabs>
            <el-tab-pane label="区域">
              <el-table :data="regions" border @row-click="selectItem('区域', $event)">
                <el-table-column prop="regionName" label="区域" min-width="140">
                  <template #default="{ row }">{{ row.regionName || row.areaName || row.id }}</template>
                </el-table-column>
                <el-table-column prop="roomCode" label="贵宾室" min-width="120" />
                <el-table-column label="坐标" min-width="180">
                  <template #default="{ row }">{{ pointText(row) || '-' }}</template>
                </el-table-column>
              </el-table>
            </el-tab-pane>
            <el-tab-pane label="机器人">
              <el-table :data="robots" border @row-click="selectItem('机器人', $event)">
                <el-table-column prop="robotName" label="机器人" min-width="140">
                  <template #default="{ row }">{{ row.robotName || row.robotId }}</template>
                </el-table-column>
                <el-table-column prop="regionId" label="区域" min-width="100" />
                <el-table-column label="坐标" min-width="180">
                  <template #default="{ row }">{{ pointText(row) || '-' }}</template>
                </el-table-column>
              </el-table>
            </el-tab-pane>
            <el-tab-pane label="旅客">
              <el-table :data="passengers" border @row-click="selectItem('旅客', $event)">
                <el-table-column prop="userName" label="旅客" min-width="120">
                  <template #default="{ row }">{{ row.userName || row.cardNo || row.id }}</template>
                </el-table-column>
                <el-table-column prop="flightNo" label="航班" min-width="120" />
                <el-table-column label="坐标" min-width="180">
                  <template #default="{ row }">{{ pointText(row) || '-' }}</template>
                </el-table-column>
              </el-table>
            </el-tab-pane>
          </el-tabs>
        </el-card>
      </el-col>

      <el-col :xs="24" :lg="8">
      <el-card shadow="never">
        <template #header><el-text tag="b">点位详情</el-text></template>
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

function pointText(item) {
  return item.coordinate || item.cameraCoordinates || item.oriCoordinate || item.position || item.coordinates || ''
}

onMounted(loadModel)
</script>
