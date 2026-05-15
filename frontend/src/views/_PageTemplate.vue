<template>
  <a-card :bordered="false">
    <template #title>
      <div class="page-title">
        <div class="headline">{{ title }}</div>
        <div class="desc">{{ description }}</div>
      </div>
    </template>
    <slot />
  </a-card>
</template>

<script setup>
defineProps({
  title: { type: String, default: '管理页面' },
  description: { type: String, default: '可复用页面容器。' }
})
</script>

<style scoped>
.headline {
  font-size: 18px;
  font-weight: 700;
}

.desc {
  margin-top: 4px;
  color: #6b7280;
  font-size: 13px;
}
</style>
