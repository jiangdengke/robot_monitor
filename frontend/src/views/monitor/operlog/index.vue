<template>
  <CrudPage v-bind="config" />
</template>

<script setup>
import { computed } from 'vue'
import CrudPage from '@/components/CrudPage.vue'
import { getCrudPage } from '@/views/crudConfigs'
import { mergePlatformPageConfig } from '@/views/crudConfigs/platformPage'

const fallbackConfig = getCrudPage('operlog')
const config = computed(() => mergePlatformPageConfig('monitorOperlog', fallbackConfig))
</script>
