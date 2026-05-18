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
    { prop: 'deptName', label: '贵宾室', minWidth: 160 },
    { prop: 'robotName', label: '机器人', minWidth: 150 },
    { prop: 'passengerName', label: '旅客', minWidth: 120 },
    { prop: 'regionName', label: '区域', minWidth: 120 },
    { prop: 'resultStatus', label: '结果状态', minWidth: 140 },
    { prop: 'coordinate', label: '目标坐标', minWidth: 220 },
    { prop: 'createdAt', label: '创建时间', minWidth: 180 }
  ],
  formFields: []
}
</script>
