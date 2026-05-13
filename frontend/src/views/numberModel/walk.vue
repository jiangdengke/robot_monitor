<template>
  <el-card shadow="never">
    <template #header>
      <el-text tag="b">桌台路径</el-text>
    </template>
    <el-space wrap>
      <el-button
        v-for="table in tables"
        :key="table.id"
        :type="selected?.id === table.id ? 'primary' : 'default'"
        :plain="selected?.id !== table.id"
        @click="selected = table"
      >
        {{ table.tableNo }}
      </el-button>
    </el-space>
    <el-descriptions v-if="selected" :column="2" border>
      <el-descriptions-item label="桌号">{{ selected.tableNo }}</el-descriptions-item>
      <el-descriptions-item label="区域">{{ selected.regionName || '-' }}</el-descriptions-item>
      <el-descriptions-item label="坐标">{{ selected.cameraCoordinates || '-' }}</el-descriptions-item>
      <el-descriptions-item label="状态">{{ selected.status || '-' }}</el-descriptions-item>
    </el-descriptions>
    <el-form v-if="selected" label-position="top">
      <el-form-item label="机器人编号">
        <el-input v-model="robotId" placeholder="robotId" />
      </el-form-item>
      <el-button type="primary" :loading="submitting" @click="submitWalk">提交行走路径</el-button>
    </el-form>
    <el-empty v-else description="选择一个桌台查看路径信息" />
  </el-card>
</template>

<script setup>
import { ref, watch } from 'vue'
import { request } from '@/api/http'
import { showToast } from '@/utils/toast'

const props = defineProps({
  tables: { type: Array, default: () => [] }
})

const selected = ref(null)
const robotId = ref('')
const submitting = ref(false)
const message = ref('')
const messageType = ref('success')

watch(
  () => props.tables,
  (tables) => {
    if (tables.length && !selected.value) selected.value = tables[0]
  },
  { immediate: true }
)

async function submitWalk() {
  if (!selected.value) return
  submitting.value = true
  message.value = ''
  try {
    await request('/rest/robot/move', {
      method: 'POST',
      body: JSON.stringify({
        robotId: robotId.value,
        tableId: selected.value.id,
        tableNo: selected.value.tableNo,
        target: selected.value.cameraCoordinates,
        action: 'walk'
      })
    })
    messageType.value = 'success'
    message.value = '行走路径已提交'
    showToast(messageType.value, message.value)
  } catch (error) {
    messageType.value = 'error'
    message.value = error?.payload?.msg || error?.message || '行走路径提交失败'
    showToast(messageType.value, message.value)
  } finally {
    submitting.value = false
  }
}
</script>
