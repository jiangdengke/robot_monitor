<template>
  <el-card shadow="never">
    <template #header>
      <el-row justify="space-between" align="middle">
        <el-space direction="vertical" alignment="flex-start">
          <el-text tag="b" size="large">通行统计</el-text>
          <el-text type="info">按贵宾室、航班日期、卡号、准入类型和在舱状态查询旅客通行记录。</el-text>
        </el-space>
        <el-button @click="loadRows">刷新</el-button>
      </el-row>
    </template>

    <el-form ref="queryRef" :model="query" inline @submit.prevent="handleSearch">
      <el-form-item label="贵宾室编码" prop="roomCode">
        <el-select v-model="query.roomCode" clearable filterable placeholder="请选择贵宾室编码">
          <el-option v-for="room in rooms" :key="room.roomCode" :label="roomLabel(room)" :value="room.roomCode" />
        </el-select>
      </el-form-item>
      <el-form-item label="航班日期" prop="flightDate">
        <el-date-picker v-model="query.flightDate" type="date" value-format="YYYY-MM-DD" placeholder="请选择航班日期" />
      </el-form-item>
      <el-form-item label="卡号" prop="cardNo">
        <el-input v-model.trim="query.cardNo" clearable placeholder="请输入卡号" />
      </el-form-item>
      <el-form-item label="准入类型" prop="inType">
        <el-select v-model="query.inType" clearable placeholder="请选择准入类型">
          <el-option v-for="option in inTypeOptions" :key="option.value" :label="option.label" :value="option.value" />
        </el-select>
      </el-form-item>
      <el-form-item label="在舱状态" prop="status">
        <el-select v-model="query.status" clearable placeholder="请选择状态">
          <el-option label="在舱" value="1" />
          <el-option label="不在" value="0" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" native-type="submit">搜索</el-button>
        <el-button @click="resetSearch">重置</el-button>
      </el-form-item>
    </el-form>

    <el-table v-loading="loading" :data="rows" border stripe max-height="70vh">
      <el-table-column label="序号" type="index" width="70" align="center" />
      <el-table-column prop="userName" label="旅客姓名" min-width="130" show-overflow-tooltip />
      <el-table-column prop="roomCode" label="贵宾室编码" min-width="130" />
      <el-table-column prop="flightNo" label="航班号" min-width="110" />
      <el-table-column prop="flightDate" label="航班日期" min-width="120" />
      <el-table-column prop="cardService" label="发卡方" min-width="110" show-overflow-tooltip />
      <el-table-column prop="cardNo" label="卡号" min-width="140" show-overflow-tooltip />
      <el-table-column prop="inTypeText" label="准入类型" min-width="120">
        <template #default="{ row }">{{ row.inTypeText || inTypeMap[String(row.inType)] || '-' }}</template>
      </el-table-column>
      <el-table-column prop="status" label="在舱状态" width="110">
        <template #default="{ row }">
          <el-tag :type="String(row.status) === '1' ? 'success' : 'info'">{{ String(row.status) === '1' ? '在舱' : '不在' }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="getInTime" label="入舱时间" min-width="170" />
      <el-table-column prop="getOutTime" label="出舱时间" min-width="170" />
      <el-table-column prop="origImageUrl" label="最后照片" width="100">
        <template #default="{ row }">
          <el-avatar v-if="row.origImageUrl" shape="square" :src="row.origImageUrl" />
          <span v-else>-</span>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination
      v-if="total > 0"
      background
      layout="total, sizes, prev, pager, next, jumper"
      :current-page="query.pageNum"
      :page-size="query.pageSize"
      :page-sizes="[10, 20, 50, 100]"
      :total="total"
      @current-change="query.pageNum = $event; loadRows()"
      @size-change="query.pageSize = $event; query.pageNum = 1; loadRows()"
    />

    <el-alert v-if="errorMessage" :title="errorMessage" type="error" :closable="false" />
  </el-card>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import { getRoomList, listPassengerStatisticsByInType } from '@/api/system'

const today = new Date().toISOString().slice(0, 10)
const queryRef = ref(null)
const rooms = ref([])
const rows = ref([])
const total = ref(0)
const loading = ref(false)
const errorMessage = ref('')
const query = reactive({
  pageNum: 1,
  pageSize: 10,
  roomCode: '',
  flightDate: today,
  cardNo: '',
  inType: '',
  status: ''
})

const inTypeOptions = [
  { label: '身份证验证', value: '1' },
  { label: '扫码准入', value: '2' },
  { label: '人脸识别', value: '3' }
]
const inTypeMap = Object.fromEntries(inTypeOptions.map((item) => [item.value, item.label]))

function roomLabel(room) {
  return room?.deptName && room?.roomCode ? `${room.deptName}(${room.roomCode})` : room?.roomCode || '-'
}

async function loadRooms() {
  try {
    const response = await getRoomList()
    rooms.value = response.data || []
  } catch {
    rooms.value = []
  }
}

async function loadRows() {
  loading.value = true
  errorMessage.value = ''
  try {
    const response = await listPassengerStatisticsByInType({ ...query })
    rows.value = response.rows || response.data || []
    total.value = Number(response.total || rows.value.length || 0)
  } catch (error) {
    errorMessage.value = error?.payload?.msg || error?.message || '加载失败'
  } finally {
    loading.value = false
  }
}

function handleSearch() {
  query.pageNum = 1
  loadRows()
}

function resetSearch() {
  queryRef.value?.resetFields()
  query.pageNum = 1
  query.pageSize = 10
  query.flightDate = today
  loadRows()
}

onMounted(() => {
  loadRooms()
  loadRows()
})
</script>
