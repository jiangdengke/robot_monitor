<template>
  <article
    class="drag-item"
    :class="{ active, disabled: field.disabled }"
    draggable="true"
    @dragstart="$emit('dragstart')"
    @dragover.prevent
    @drop="$emit('drop')"
    @click="$emit('select')"
  >
    <div class="drag-main">
      <strong>{{ index + 1 }}. {{ field.label }}</strong>
      <span>{{ field.prop }} · {{ field.type }} · {{ field.span }}/24</span>
    </div>
    <div class="drag-actions">
      <el-tag v-if="field.required" size="small" type="danger">必填</el-tag>
      <el-tag v-if="field.dictType" size="small" type="info">{{ field.dictType }}</el-tag>
      <el-button link type="primary" @click.stop="$emit('duplicate')">复制</el-button>
      <el-button link type="danger" @click.stop="$emit('remove')">删除</el-button>
    </div>
  </article>
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

<style scoped>
.drag-item {
  display: flex;
  justify-content: space-between;
  gap: 12px;
  padding: 12px;
  border: 1px solid var(--line);
  border-radius: 12px;
  background: #fff;
  cursor: grab;
}
.drag-item.active {
  border-color: var(--brand);
  background: #eaf4ff;
}
.drag-item.disabled {
  opacity: .72;
}
.drag-main { display: grid; gap: 4px; min-width: 0; }
.drag-main span { color: var(--text-soft); font-size: 12px; }
.drag-actions { display: flex; flex-wrap: wrap; justify-content: flex-end; align-items: center; gap: 6px; }
</style>
