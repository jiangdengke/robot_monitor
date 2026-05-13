<template>
  <el-card shadow="never">
    <template #header>
      <el-row justify="space-between" align="middle">
        <el-space direction="vertical" alignment="flex-start">
          <el-text tag="b" size="large">角色授权用户</el-text>
          <el-text type="info">读取 `/system/role/authUser/allocatedList` 与 `/system/role/authUser/unallocatedList`。</el-text>
        </el-space>
        <el-space wrap>
          <el-button type="primary" @click="router.push(`/system/role-auth/selectUser/${roleId}`)">添加用户</el-button>
          <el-button @click="loadRows">刷新</el-button>
        </el-space>
      </el-row>
    </template>

    <el-form inline>
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

    <el-row :gutter="16">
      <el-col :xs="24" :md="12">
      <el-card shadow="never">
        <template #header>
          <el-text tag="b">已分配用户</el-text>
        </template>
        <el-table :data="allocatedRows" border @selection-change="handleAllocatedChange">
          <el-table-column type="selection" width="55" />
          <el-table-column prop="userId" label="用户 ID" width="100" />
          <el-table-column prop="userName" label="账号" min-width="120" />
          <el-table-column prop="nickName" label="昵称" min-width="120" />
          <el-table-column prop="phonenumber" label="手机号" min-width="140" />
        </el-table>
      </el-card>
      </el-col>

      <el-col :xs="24" :md="12">
      <el-card shadow="never">
        <template #header>
          <el-text tag="b">未分配用户</el-text>
        </template>
        <el-table :data="unallocatedRows" border @selection-change="handleUnallocatedChange">
          <el-table-column type="selection" width="55" />
          <el-table-column prop="userId" label="用户 ID" width="100" />
          <el-table-column prop="userName" label="账号" min-width="120" />
          <el-table-column prop="nickName" label="昵称" min-width="120" />
          <el-table-column prop="phonenumber" label="手机号" min-width="140" />
        </el-table>
      </el-card>
      </el-col>
    </el-row>

  </el-card>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { assignRoleUsers, cancelRoleUsers, listAllocatedUsers, listUnallocatedUsers } from '@/api/system'
import { toastError, toastSuccess } from '@/utils/toast'

const route = useRoute()
const router = useRouter()
const allocatedRows = ref([])
const unallocatedRows = ref([])
const roleId = ref(Number(route.params.roleId || 1))
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
    toastError(errorMessage.value)
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
    toastSuccess(message.value)
    await loadRows()
  } catch (error) {
    errorMessage.value = error?.payload?.msg || error?.message || '分配失败'
    toastError(errorMessage.value)
  }
}

async function cancelSelected() {
  try {
    await cancelRoleUsers(roleId.value, selectedAllocated.value)
    message.value = '取消成功'
    toastSuccess(message.value)
    await loadRows()
  } catch (error) {
    errorMessage.value = error?.payload?.msg || error?.message || '取消失败'
    toastError(errorMessage.value)
  }
}

onMounted(loadRows)
</script>
