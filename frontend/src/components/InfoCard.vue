<template>
  <a-card :bordered="false" class="info-card">
    <template #title>{{ title }}</template>
    <div class="info-lines">
      <div v-for="line in lines" :key="line" class="info-line">{{ line }}</div>
    </div>
  </a-card>
</template>

<script setup>
defineProps({
  title: { type: String, required: true },
  lines: { type: Array, default: () => [] }
})
</script>

<style scoped>
.info-card {
  height: 100%;
}

.info-lines {
  display: grid;
  gap: 8px;
}

.info-line {
  color: #4b5563;
  line-height: 1.6;
}
</style>
