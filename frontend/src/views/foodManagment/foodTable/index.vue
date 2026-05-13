<template>
  <CrudPage v-bind="config" />
</template>

<script setup>
import { computed } from 'vue'
import { useRoute } from 'vue-router'
import CrudPage from '@/components/CrudPage.vue'
import { getCrudPage } from '@/views/crudConfigs'

const route = useRoute()
const baseConfig = getCrudPage('table')
const config = computed(() => {
  if (route.path.startsWith('/food/foodTable') || route.path.startsWith('/foodManagment/foodTable')) {
    return {
      ...baseConfig,
      title: '桌台视图',
      description: '休息室点餐桌台、区域、坐标和占用状态视图。',
      enableCreate: false,
      enableEdit: false,
      enableDelete: false,
      enableBatchDelete: false
    }
  }
  return {
    ...baseConfig,
    title: '桌台配置',
    description: '桌台、区域、坐标和启用状态配置。'
  }
})
</script>
