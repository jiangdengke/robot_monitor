<template>
  <el-card class="page-card">
    <template #header>
      <div class="page-header">
        <div>
          <h1>角色授权用户</h1>
          <p>读取 `/system/role/authUser/allocatedList` 与 `/system/role/authUser/unallocatedList`。</p>
        </div>
        <el-button type="primary" @click="loadRows">刷新</el-button>
      </div>
    </template>

    <el-form inline class="toolbar">
      <el-form-item label="角色 ID">
        <el-input-number v-model="roleId" :min="1" controls-position="right" />
      </el-form-item>
      <el-form-item>
        <el-button type="success" :disabled="selectedUnallocated.length === 0" @click="assignSelected">
          分配选中用户
        </el-button>
      </el-form-item>
      <el-form-item>
        <el-button type="danger" :disabled="selectedAllocated.length === 0" @click="cancelSelected">
          取消已分配
        </el-button>
      </el-form-item>
    </el-form>

    <div class="panel-grid">
      <el-card shadow="never" class="sub-panel">
        <template #header>
          <h2>已分配用户</h2>
        </template>
        <el-table :data="allocatedRows" border @selection-change="handleAllocatedChange">
          <el-table-column type="selection" width="55" />
          <el-table-column prop="userId" label="用户 ID" width="100" />
          <el-table-column prop="userName" label="账号" min-width="120" />
          <el-table-column prop="nickName" label="昵称" min-width="120" />
          <el-table-column prop="phonenumber" label="手机号" min-width="140" />
        </el-table>
      </el-card>

      <el-card shadow="never" class="sub-panel">
        <template #header>
          <h2>未分配用户</h2>
        </template>
        <el-table :data="unallocatedRows" border @selection-change="handleUnallocatedChange">
          <el-table-column type="selection" width="55" />
          <el-table-column prop="userId" label="用户 ID" width="100" />
          <el-table-column prop="userName" label="账号" min-width="120" />
          <el-table-column prop="nickName" label="昵称" min-width="120" />
          <el-table-column prop="phonenumber" label="手机号" min-width="140" />
        </el-table>
      </el-card>
    </div>

    <el-alert v-if="message" class="message-alert" :title="message" type="success" :closable="false" />
    <el-alert v-if="errorMessage" class="message-alert" :title="errorMessage" type="error" :closable="false" />
  </el-card>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import { assignRoleUsers, cancelRoleUsers, listAllocatedUsers, listUnallocatedUsers } from '@/api/system'

const allocatedRows = ref([])
const unallocatedRows = ref([])
const roleId = ref(1)
const selectedAllocated = ref([])
const selectedUnallocated = ref([])
const errorMessage = ref('')
const message = ref('')

async function loadRows() {
  errorMessage.value = ''
  message.value = ''
  try {
    const [allocated, unallocated] = await Promise.all([
      listAllocatedUsers({ roleId: roleId.value, pageNum: 1, pageSize: 20 }),
      listUnallocatedUsers({ roleId: roleId.value, pageNum: 1, pageSize: 20 })
    ])
    allocatedRows.value = allocated.rows || []
    unallocatedRows.value = unallocated.rows || []
    selectedAllocated.value = []
    selectedUnallocated.value = []
  } catch (error) {
    errorMessage.value = error?.payload?.msg || error?.message || '加载失败'
  }
}

function handleAllocatedChange(rows) {
  selectedAllocated.value = rows.map((row) => row.userId)
}

function handleUnallocatedChange(rows) {
  selectedUnallocated.value = rows.map((row) => row.userId)
}

async function assignSelected() {
  try {
    await assignRoleUsers(roleId.value, selectedUnallocated.value)
    message.value = '分配成功'
    await loadRows()
  } catch (error) {
    errorMessage.value = error?.payload?.msg || error?.message || '分配失败'
  }
}

async function cancelSelected() {
  try {
    await cancelRoleUsers(roleId.value, selectedAllocated.value)
    message.value = '取消成功'
    await loadRows()
  } catch (error) {
    errorMessage.value = error?.payload?.msg || error?.message || '取消失败'
  }
}

onMounted(loadRows)
</script>

<style scoped>
.page-card { padding: 24px; }
.page-header { display: flex; justify-content: space-between; align-items: center; gap: 16px; margin-bottom: 18px; }
.page-header h1 { margin: 0; font-size: 28px; }
.page-header p { margin: 8px 0 0; color: var(--text-soft); }
.toolbar { margin-bottom: 16px; }
.panel-grid { display: grid; grid-template-columns: 1fr 1fr; gap: 16px; }
.sub-panel h2 { margin: 0; font-size: 18px; }
.message-alert { margin-top: 16px; }
@media (max-width: 960px) { .panel-grid { grid-template-columns: 1fr; } }
</style>
