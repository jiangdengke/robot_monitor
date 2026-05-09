<template>
  <CrudPage v-bind="config" />
</template>

<script setup>
import CrudPage from '@/components/CrudPage.vue'
import { getCrudPage } from '@/views/crudConfigs'

const config = {
  ...getCrudPage('flightInfo'),
  title: '航班信息',
  description: '航班计划、状态、登机口、行李转盘和临时航班维护。'
}
</script>
