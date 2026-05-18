<template>
  <CrudPage v-bind="config" />
</template>

<script setup>
import CrudPage from '@/components/CrudPage.vue'
import { listInquiry } from '@/api/system'

const config = {
  title: '问询统计',
  description: '问询记录按主题、渠道、机器人和旅客维度展示。',
  rowKey: 'id',
  list: listInquiry,
  enableCreate: false,
  enableEdit: false,
  enableDelete: false,
  enableBatchDelete: false,
  showDetail: false,
  columns: [
    { prop: 'deptName', label: '贵宾室', minWidth: 160 },
    { prop: 'robotName', label: '机器人', minWidth: 150 },
    { prop: 'passengerName', label: '旅客', minWidth: 120 },
    { prop: 'topic', label: '问询主题', minWidth: 220 },
    { prop: 'channel', label: '渠道', minWidth: 120 },
    { prop: 'createdAt', label: '创建时间', minWidth: 180 }
  ],
  formFields: []
}
</script>
