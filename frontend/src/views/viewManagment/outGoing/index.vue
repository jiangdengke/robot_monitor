<template>
  <CrudPage v-bind="config" />
</template>

<script setup>
import CrudPage from '@/components/CrudPage.vue'
import { getCrudPage } from '@/views/crudConfigs'

const config = {
  ...getCrudPage('passenger'),
  title: '出厅旅客',
  description: '旅客出厅、离开贵宾室和航班变更记录。'
}
</script>
