<template>
  <a-card :bordered="false" class="info-card has-hover">
    <template #title>
      <div class="info-card-title">
        <span>{{ title }}</span>
      </div>
    </template>
    <ul class="info-lines">
      <li v-for="line in lines" :key="line" class="info-line">
        <span class="info-bullet" />
        <span>{{ line }}</span>
      </li>
    </ul>
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
  border-radius: var(--radius-lg);
}

.info-card-title {
  font-weight: 600;
  color: var(--text-strong);
}

.info-lines {
  list-style: none;
  padding: 0;
  margin: 0;
  display: grid;
  gap: 10px;
}

.info-line {
  display: flex;
  align-items: flex-start;
  gap: 10px;
  color: var(--text-default);
  line-height: 1.7;
  font-size: 13px;
}

.info-bullet {
  width: 6px;
  height: 6px;
  border-radius: 50%;
  background: var(--brand-primary);
  margin-top: 8px;
  flex: 0 0 auto;
  box-shadow: 0 0 0 3px rgb(47 84 235 / 12%);
}
</style>
