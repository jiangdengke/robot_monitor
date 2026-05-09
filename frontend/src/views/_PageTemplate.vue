<template>
  <el-card class="page-card">
    <template #header>
      <div class="page-header">
        <div>
          <h1>{{ title }}</h1>
          <p>{{ description }}</p>
        </div>
      </div>
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

<style scoped>
.page-card { padding: 24px; }
.page-header h1 { margin: 0; font-size: 28px; }
.page-header p { margin: 8px 0 0; color: var(--text-soft); }
</style>
