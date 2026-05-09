<template>
  <article class="info-panel">
    <h2>{{ title }}</h2>
    <p v-for="line in lines" :key="line">{{ line }}</p>
  </article>
</template>

<script setup>
defineProps({
  title: { type: String, required: true },
  lines: { type: Array, default: () => [] }
})
</script>

<style scoped>
.info-panel { padding: 18px; border-radius: 12px; border: 1px solid var(--line); background: var(--panel-alt); }
.info-panel h2 { margin: 0 0 10px; font-size: 16px; }
.info-panel p { margin: 8px 0 0; color: var(--text-soft); }
</style>
