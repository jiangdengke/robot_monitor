<template>
  <CrudPage v-bind="config" />
</template>

<script setup>
import CrudPage from '@/components/CrudPage.vue'
import { listInquiry, listRobots } from '@/api/system'

async function robotOptions() {
  const response = await listRobots()
  return (response.rows || []).map((robot) => ({
    value: robot.id,
    label: robot.robotName ? `${robot.robotName} (${robot.robotId})` : robot.robotId
  }))
}

const config = {
  title: '问询统计',
  description: '问询记录按机器人、旅客、问询内容和机器人回复展示。',
  rowKey: 'id',
  list: listInquiry,
  enableCreate: false,
  enableEdit: false,
  enableDelete: false,
  enableBatchDelete: false,
  showDetail: false,
  searchFields: [
    { prop: 'robotId', label: '机器人', type: 'select', options: robotOptions }
  ],
  columns: [
    { prop: 'deptName', label: '贵宾室', minWidth: 160 },
    { prop: 'robotName', label: '机器人', minWidth: 150 },
    { prop: 'passengerName', label: '旅客', minWidth: 120 },
    { prop: 'topic', label: '问询内容', minWidth: 220 },
    { prop: 'robotResponse', label: '机器人回复', minWidth: 280 },
    { prop: 'channel', label: '渠道', minWidth: 120 },
    { prop: 'createdAt', label: '创建时间', minWidth: 180 }
  ],
  formFields: []
}
</script>
