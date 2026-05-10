<template>
  <el-card shadow="never">
    <template #header>
      <el-space direction="vertical" alignment="flex-start">
        <el-text tag="b" size="large">{{ title }}</el-text>
        <el-text type="info">{{ description }}</el-text>
      </el-space>
    </template>
    <slot />
  </el-card>
</template>

<script setup>
defineProps({
  title: { type: String, default: '管理页面' },
  description: { type: String, default: '可复用页面容器。' }
})
</script>
