<template>
  <el-card shadow="never">
    <template #header>
      <el-row justify="space-between" align="middle">
        <el-text tag="b">实时消息</el-text>
        <el-tag type="success">{{ activeRoom || '全部房间' }}</el-tag>
      </el-row>
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
