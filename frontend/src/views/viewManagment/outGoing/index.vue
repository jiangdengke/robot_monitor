<template>
  <CrudPage v-bind="config" />
</template>

<script setup>
import { computed } from 'vue'
import CrudPage from '@/components/CrudPage.vue'
import { getCrudPage } from '@/views/crudConfigs'
import { mergePlatformPageConfig } from '@/views/crudConfigs/platformPage'

const fallbackConfig = {
  ...getCrudPage('outgoingPassenger'),
  title: '准出记录',
  description: '旅客准出、离开贵宾室和航班记录。'
}

const config = computed(() => mergePlatformPageConfig('outgoingPassenger', fallbackConfig))
</script>
