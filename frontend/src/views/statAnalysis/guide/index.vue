<template>
  <CrudPage v-bind="config" />
</template>

<script setup>
import CrudPage from '@/components/CrudPage.vue'
import { listGuide, listRobots } from '@/api/system'

async function robotOptions() {
  const response = await listRobots()
  return (response.rows || []).map((robot) => ({
    value: robot.id,
    label: robot.robotName ? `${robot.robotName} (${robot.robotId})` : robot.robotId
  }))
}

const resultStatusOptions = [
  { label: '成功', value: 'SUCCESS' },
  { label: '已创建', value: 'CREATED' },
  { label: '失败', value: 'FAILED' },
  { label: '待处理', value: 'PENDING' }
]
const resultStatusMap = Object.fromEntries(resultStatusOptions.map((item) => [item.value, item.label]))
const resultStatusTagMap = {
  SUCCESS: 'success',
  CREATED: 'info',
  FAILED: 'danger',
  PENDING: 'warning'
}

const config = {
  title: '引导统计',
  description: '引导任务记录按机器人、旅客、区域和执行结果展示。',
  rowKey: 'id',
  list: listGuide,
  enableCreate: false,
  enableEdit: false,
  enableDelete: false,
  enableBatchDelete: false,
  showDetail: false,
  searchFields: [
    { prop: 'robotId', label: '机器人', type: 'select', options: robotOptions },
    {
      prop: 'resultStatus',
      label: '结果状态',
      type: 'select',
      options: resultStatusOptions
    }
  ],
  columns: [
    { prop: 'deptName', label: '贵宾室', minWidth: 160 },
    { prop: 'robotName', label: '机器人', minWidth: 150 },
    { prop: 'passengerName', label: '旅客', minWidth: 120 },
    { prop: 'regionName', label: '区域', minWidth: 120 },
    { prop: 'resultStatus', label: '结果状态', minWidth: 140, map: resultStatusMap, tag: 'info', tagMap: resultStatusTagMap },
    { prop: 'coordinate', label: '目标坐标', minWidth: 220 },
    { prop: 'createdAt', label: '创建时间', minWidth: 180 }
  ],
  formFields: []
}
</script>
