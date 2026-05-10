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
          <el-radio-button label="guide">机器人引导</el-radio-button>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="机器人编号" v-if="action === 'guide'">
        <el-input v-model="robotId" placeholder="robotId" />
      </el-form-item>
      <el-button type="primary" @click="submit">提交动作</el-button>
    </el-form>
    <el-alert v-if="message" :title="message" :type="messageType" :closable="false" />
  </el-card>
</template>

<script setup>
import { computed, ref, watch } from 'vue'
import { request } from '@/api/http'

const props = defineProps({
  tables: { type: Array, default: () => [] }
})

const emit = defineEmits(['refresh'])

const selectedId = ref(null)
const action = ref('free')
const robotId = ref('')
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
    if (action.value === 'guide') {
      await request('/rest/robot/move', {
        method: 'POST',
        body: JSON.stringify({
          robotId: robotId.value,
          tableId: selectedTable.value.id,
          tableNo: selectedTable.value.tableNo,
          target: selectedTable.value.cameraCoordinates
        })
      })
      message.value = '机器人引导动作已提交'
    } else {
      await request('/rest/table', {
        method: 'POST',
        body: JSON.stringify({
          id: selectedTable.value.id,
          status: action.value === 'turnover' ? '1' : '0'
        })
      })
      message.value = action.value === 'turnover' ? '已设置为翻台' : '已设置为空闲'
    }
    messageType.value = 'success'
    emit('refresh')
  } catch (error) {
    messageType.value = 'error'
    message.value = error?.payload?.msg || error?.message || '提交失败'
  }
}
</script>
