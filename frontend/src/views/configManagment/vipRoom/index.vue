<template>
  <CrudPage v-bind="config" />
</template>

<script setup>
import { computed } from 'vue'
import CrudPage from '@/components/CrudPage.vue'
import { getCrudPage } from '@/views/crudConfigs'
import { mergePlatformPageConfig } from '@/views/crudConfigs/platformPage'

const fallbackConfig = {
  ...getCrudPage('dept'),
  title: '贵宾室配置',
  description: '贵宾室组织节点、房间编码、负责人和状态维护。'
}

const config = computed(() => mergePlatformPageConfig('dept', fallbackConfig))
</script>
