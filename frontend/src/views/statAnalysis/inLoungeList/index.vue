<template>
  <CrudPage v-bind="config" />
</template>

<script setup>
import CrudPage from '@/components/CrudPage.vue'
import { getCrudPage } from '@/views/crudConfigs'

const config = {
  ...getCrudPage('passenger'),
  title: '在厅旅客列表',
  description: '当前贵宾室在厅旅客、航班和区域记录。'
}
</script>
