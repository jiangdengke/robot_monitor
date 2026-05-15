<template>
  <el-card shadow="never">
    <template #header>
      <el-text tag="b">桌台动作提交</el-text>
    </template>
    <el-form label-position="top">
      <el-form-item label="桌台">
        <el-select v-model="selectedId" placeholder="选择桌台" filterable>
          <el-option v-for="table in tables" :key="table.id" :label="table.tableNo" :value="table.id" />
        </el-select>
      </el-form-item>
      <el-form-item label="动作">
        <el-radio-group v-model="action">
          <el-radio-button label="free">设为空闲</el-radio-button>
          <el-radio-button label="turnover">设置翻台</el-radio-button>
        </el-radio-group>
      </el-form-item>
      <el-button type="primary" @click="submit">提交动作</el-button>
    </el-form>
  </el-card>
</template>

<script setup>
import { computed, ref, watch } from 'vue'
import { request } from '@/api/http'
import { showToast } from '@/utils/toast'

const props = defineProps({
  tables: { type: Array, default: () => [] }
})

const emit = defineEmits(['refresh'])

const selectedId = ref(null)
const action = ref('free')
const message = ref('')
const messageType = ref('success')
const selectedTable = computed(() => props.tables.find((item) => item.id === selectedId.value))

watch(
  () => props.tables,
  (tables) => {
    if (tables.length && !selectedId.value) {
      selectedId.value = tables[0].id
    }
  },
  { immediate: true }
)

async function submit() {
  if (!selectedTable.value) return
  try {
    await request(`/config/tables/${selectedTable.value.id}/status`, {
      method: 'PUT',
      body: JSON.stringify(action.value === 'turnover' ? 'TURNOVER' : 'IDLE')
    })
    message.value = action.value === 'turnover' ? '已设置为翻台' : '已设置为空闲'
    messageType.value = 'success'
    showToast(messageType.value, message.value)
    emit('refresh')
  } catch (error) {
    messageType.value = 'error'
    message.value = error?.payload?.msg || error?.message || '提交失败'
    showToast(messageType.value, message.value)
  }
}
</script>
