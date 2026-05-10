<template>
  <el-card shadow="never">
    <template #header>
      <el-text tag="b">{{ title }}</el-text>
    </template>
    <el-space direction="vertical" alignment="flex-start">
      <el-text v-for="line in lines" :key="line">{{ line }}</el-text>
    </el-space>
  </el-card>
</template>

<script setup>
defineProps({
  title: { type: String, required: true },
  lines: { type: Array, default: () => [] }
})
</script>
