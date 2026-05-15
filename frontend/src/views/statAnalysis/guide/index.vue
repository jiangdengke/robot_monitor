<template>
  <CrudPage v-bind="config" />
</template>

<script setup>
import CrudPage from '@/components/CrudPage.vue'
import { listGuide } from '@/api/system'

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
  columns: [
    { prop: 'id', label: 'ID', width: 80 },
    { prop: 'loungeId', label: '贵宾室 ID', width: 120 },
    { prop: 'robotId', label: '机器人 ID', width: 120 },
    { prop: 'passengerId', label: '旅客 ID', width: 120 },
    { prop: 'regionId', label: '区域 ID', width: 120 },
    { prop: 'resultStatus', label: '结果状态', minWidth: 140 },
    { prop: 'coordinate', label: '目标坐标', minWidth: 220 },
    { prop: 'createdAt', label: '创建时间', minWidth: 180 }
  ],
  formFields: []
}
</script>
