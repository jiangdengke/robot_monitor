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
    { prop: 'id', label: 'ID', width: 80 },
    { prop: 'loungeId', label: '贵宾室 ID', width: 120 },
    { prop: 'robotId', label: '机器人 ID', width: 120 },
    { prop: 'passengerId', label: '旅客 ID', width: 120 },
    { prop: 'topic', label: '问询主题', minWidth: 220 },
    { prop: 'channel', label: '渠道', minWidth: 120 },
    { prop: 'createdAt', label: '创建时间', minWidth: 180 }
  ],
  formFields: []
}
</script>
