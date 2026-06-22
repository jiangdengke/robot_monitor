<template>
  <CrudPage v-bind="config" />
</template>

<script setup>
import { computed } from 'vue'
import CrudPage from '@/components/CrudPage.vue'
import { getCrudPage } from '@/views/crudConfigs'
import { mergePlatformPageConfig } from '@/views/crudConfigs/platformPage'

const fallbackConfig = {
  ...getCrudPage('passenger'),
  title: '在舱记录',
  description: '旅客姓名、航班、卡号、准入类型和进出舱时间记录。'
}

const config = computed(() => mergePlatformPageConfig('passenger', fallbackConfig))
</script>
