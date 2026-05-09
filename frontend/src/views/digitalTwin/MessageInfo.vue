<template>
  <el-card shadow="never" class="message-panel">
    <template #header>
      <div class="panel-header">
        <h2>实时消息</h2>
        <el-tag type="success">{{ activeRoom || '全部房间' }}</el-tag>
      </div>
    </template>
    <el-timeline>
      <el-timeline-item v-for="item in messages" :key="item.id" :timestamp="item.time" :type="item.type">
        {{ item.content }}
      </el-timeline-item>
    </el-timeline>
    <el-empty v-if="!messages.length" description="暂无联动消息" />
  </el-card>
</template>

<script setup>
defineProps({
  activeRoom: { type: String, default: '' },
  messages: { type: Array, default: () => [] }
})
</script>

<style scoped>
.message-panel { min-height: 100%; }
.panel-header { display: flex; align-items: center; justify-content: space-between; gap: 12px; }
.panel-header h2 { margin: 0; font-size: 16px; }
</style>
