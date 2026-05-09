<template>
  <CrudPage v-bind="config" />
</template>

<script setup>
import CrudPage from '@/components/CrudPage.vue'
import { getCrudPage } from '@/views/crudConfigs'

const config = {
  ...getCrudPage('passenger'),
  title: '旅客移动统计',
  description: '按旅客、航班和区域查看移动记录。'
}
</script>
