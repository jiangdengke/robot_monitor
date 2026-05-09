<template>
  <CrudPage v-bind="config" />
</template>

<script setup>
import { computed } from 'vue'
import { useRoute } from 'vue-router'
import CrudPage from '@/components/CrudPage.vue'
import { getCrudPage } from '@/views/crudConfigs'

const route = useRoute()
const config = computed(() => ({
  ...getCrudPage('dictData'),
  initialQuery: {
    dictType: route.query.dictType || ''
  }
}))
</script>
