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

    <el-form ref="queryRef" class="filter-form" :model="query" inline @submit.prevent="handleSearch">
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
      <el-form-item label="准入类型" prop="accessType">
        <el-select v-model="query.accessType" clearable placeholder="请选择准入类型">
          <el-option v-for="option in inTypeOptions" :key="option.value" :label="option.label" :value="option.value" />
        </el-select>
      </el-form-item>
      <el-form-item label="在舱状态" prop="status">
        <el-select v-model="query.status" clearable placeholder="请选择状态">
          <el-option label="在舱" value="IN" />
          <el-option label="已出舱" value="OUT" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" native-type="submit">搜索</el-button>
        <el-button @click="resetSearch">重置</el-button>
      </el-form-item>
    </el-form>

    <el-table v-loading="loading" :data="rows" border stripe max-height="70vh">
      <el-table-column label="序号" type="index" width="70" align="center" />
      <el-table-column prop="passengerName" label="旅客姓名" min-width="130" show-overflow-tooltip />
      <el-table-column prop="roomCode" label="贵宾室编码" min-width="130" />
      <el-table-column prop="flightNo" label="航班号" min-width="110" />
      <el-table-column prop="flightDate" label="航班日期" min-width="120" />
      <el-table-column prop="cardProvider" label="发卡方" min-width="110" show-overflow-tooltip />
      <el-table-column prop="cardNo" label="卡号" min-width="140" show-overflow-tooltip />
      <el-table-column prop="accessType" label="准入类型" min-width="120">
        <template #default="{ row }">{{ inTypeMap[String(row.accessType)] || row.accessType || '-' }}</template>
      </el-table-column>
      <el-table-column prop="accessStatus" label="在舱状态" width="110">
        <template #default="{ row }">
          <el-tag :type="row.accessStatus === 'IN' ? 'success' : 'info'">{{ row.accessStatus === 'IN' ? '在舱' : '已出舱' }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="checkInAt" label="入舱时间" min-width="170" />
      <el-table-column prop="checkOutAt" label="出舱时间" min-width="170" />
      <el-table-column prop="originalImageUrl" label="最后照片" width="100">
        <template #default="{ row }">
          <el-avatar v-if="row.originalImageUrl" shape="square" :src="row.originalImageUrl" />
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

  </el-card>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import { getRoomList, listPassengerStatisticsByInType } from '@/api/system'
import { toastError } from '@/utils/toast'

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
  flightDate: '',
  cardNo: '',
  accessType: '',
  status: ''
})

const inTypeOptions = [
  { label: '身份证验证', value: 'ID_CARD' },
  { label: '扫码准入', value: 'QRCODE' },
  { label: '人脸识别', value: 'FACE' },
  { label: '人工登记', value: 'MANUAL' }
]
const inTypeMap = Object.fromEntries(inTypeOptions.map((item) => [item.value, item.label]))

function roomLabel(room) {
  return room?.deptName && room?.roomCode ? `${room.deptName}(${room.roomCode})` : room?.roomCode || '-'
}

async function loadRooms() {
  try {
    const response = await getRoomList()
    rooms.value = response.rows || response.data || []
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
    toastError(errorMessage.value)
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
  query.flightDate = ''
  loadRows()
}

onMounted(() => {
  loadRooms()
  loadRows()
})
</script>
