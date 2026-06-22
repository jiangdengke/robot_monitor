<template>
  <CrudPage v-bind="config" />
</template>

<script setup>
import { computed } from 'vue'
import CrudPage from '@/components/CrudPage.vue'
import { getCrudPage } from '@/views/crudConfigs'
import { mergePlatformPageConfig } from '@/views/crudConfigs/platformPage'

const fallbackConfig = getCrudPage('robot')
const config = computed(() => mergePlatformPageConfig('robot', fallbackConfig))
</script>
