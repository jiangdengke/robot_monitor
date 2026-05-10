<template>
  <el-card
    shadow="never"
    draggable="true"
    @dragstart="$emit('dragstart')"
    @dragover.prevent
    @drop="$emit('drop')"
    @click="$emit('select')"
  >
    <el-row justify="space-between" align="middle">
      <el-space direction="vertical" alignment="flex-start">
        <el-text :type="active ? 'primary' : undefined" tag="b">{{ index + 1 }}. {{ field.label }}</el-text>
        <el-text type="info" size="small">{{ field.prop }} · {{ field.type }} · {{ field.span }}/24</el-text>
      </el-space>
      <el-space wrap>
      <el-tag v-if="field.required" size="small" type="danger">必填</el-tag>
      <el-tag v-if="field.dictType" size="small" type="info">{{ field.dictType }}</el-tag>
      <el-tag v-if="field.disabled" size="small" type="warning">禁用</el-tag>
      <el-button link type="primary" @click.stop="$emit('duplicate')">复制</el-button>
      <el-button link type="danger" @click.stop="$emit('remove')">删除</el-button>
      </el-space>
    </el-row>
  </el-card>
</template>

<script setup>
defineProps({
  field: {
    type: Object,
    required: true
  },
  index: {
    type: Number,
    default: 0
  },
  active: {
    type: Boolean,
    default: false
  }
})

defineEmits(['select', 'dragstart', 'drop', 'duplicate', 'remove'])
</script>
