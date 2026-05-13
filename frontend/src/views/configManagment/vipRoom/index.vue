<template>
  <CrudPage v-bind="config" />
</template>

<script setup>
import CrudPage from '@/components/CrudPage.vue'
import { getCrudPage } from '@/views/crudConfigs'

const config = {
  ...getCrudPage('dept'),
  title: '贵宾室配置',
  description: '贵宾室组织节点、房间编码、负责人和状态维护。'
}
</script>
