<template>
  <el-card shadow="never">
    <template #header>
      <el-row justify="space-between" align="middle">
        <el-space direction="vertical" alignment="flex-start">
          <el-text tag="b" size="large">引导日志</el-text>
          <el-text type="info">查询 `/ai/log/infoList`，展示机器人、贵宾室、区域和坐标引导记录。</el-text>
        </el-space>
        <el-space wrap>
          <el-button type="primary" :loading="loading" @click="loadRows">刷新</el-button>
          <el-button v-if="hasPermission('ai:log:export')" @click="exportRows">导出</el-button>
        </el-space>
      </el-row>
    </template>

    <el-form :model="query" inline @submit.prevent="loadRows">
      <el-form-item label="机器人">
        <el-input v-model.trim="query.robotName" clearable placeholder="机器人名称" />
      </el-form-item>
      <el-form-item label="贵宾室">
        <el-select v-model="query.roomCode" clearable filterable placeholder="选择贵宾室">
          <el-option
            v-for="room in roomList"
            :key="room.roomCode || room.deptId"
            :label="room.deptName || room.roomCode"
            :value="room.roomCode"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="时间">
        <el-date-picker
          v-model="range"
          type="datetimerange"
          value-format="YYYY-MM-DD HH:mm:ss"
          start-placeholder="开始时间"
          end-placeholder="结束时间"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" native-type="submit">查询</el-button>
        <el-button @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="16">
      <el-col :xs="24" :sm="8">
        <el-card shadow="never">
          <el-statistic title="引导记录" :value="total" />
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="8">
        <el-card shadow="never">
          <el-statistic title="机器人数量" :value="robotCount" />
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="8">
        <el-card shadow="never">
          <el-statistic title="贵宾室数量" :value="roomCount" />
        </el-card>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="rows" border>
      <el-table-column prop="robotId" label="机器人编号" min-width="130" />
      <el-table-column prop="robotName" label="机器人名称" min-width="150" />
      <el-table-column prop="deptName" label="贵宾室" min-width="160" />
      <el-table-column prop="roomCode" label="房间编码" min-width="120" />
      <el-table-column prop="regionName" label="区域" min-width="140" />
      <el-table-column prop="coordinate" label="坐标" min-width="180" />
      <el-table-column prop="createTime" label="引导时间" min-width="170" />
    </el-table>

    <el-alert v-if="errorMessage" :title="errorMessage" type="error" :closable="false" />
  </el-card>
</template>

<script setup>
import { computed, onMounted, reactive, ref } from 'vue'
import { exportSystemResource, getRoomList } from '@/api/system'
import { request } from '@/api/http'
import { hasPermission } from '@/utils/permission'

const rows = ref([])
const roomList = ref([])
const total = ref(0)
const loading = ref(false)
const errorMessage = ref('')
const range = ref([])
const query = reactive({ robotName: '', roomCode: '' })
const robotCount = computed(() => new Set(rows.value.map((item) => item.robotId).filter(Boolean)).size)
const roomCount = computed(() => new Set(rows.value.map((item) => item.roomCode).filter(Boolean)).size)

async function loadRows() {
  loading.value = true
  errorMessage.value = ''
  try {
    const response = await request('/ai/log/infoList', {
      query: {
        pageNum: 1,
        pageSize: 100,
        robotName: query.robotName,
        roomCode: query.roomCode,
        startTime: range.value?.[0],
        endTime: range.value?.[1]
      }
    })
    rows.value = response.rows || []
    total.value = response.total || rows.value.length
  } catch (error) {
    errorMessage.value = error?.payload?.msg || error?.message || '引导日志加载失败'
  } finally {
    loading.value = false
  }
}

function resetQuery() {
  query.robotName = ''
  query.roomCode = ''
  range.value = []
  loadRows()
}

function exportRows() {
  exportSystemResource('/ai/log/export', {
    robotName: query.robotName,
    roomCode: query.roomCode,
    startTime: range.value?.[0],
    endTime: range.value?.[1]
  }, '引导日志.xlsx')
}

onMounted(async () => {
  const roomResponse = await getRoomList()
  roomList.value = roomResponse.data || []
  await loadRows()
})
</script>
