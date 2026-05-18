<template>
  <CrudPage v-bind="config" />
</template>

<script setup>
import CrudPage from '@/components/CrudPage.vue'
import { getCrudPage } from '@/views/crudConfigs'

const config = {
  ...getCrudPage('outgoingPassenger'),
  title: '准出记录',
  description: '旅客准出、离开贵宾室和航班记录。'
}
</script>
