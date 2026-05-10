<template>
  <el-card class="page-card">
    <template #header>
      <div class="page-header">
        <div>
          <h1>用户授权角色</h1>
          <p>读取 `/system/user/authRole/{userId}` 并提交角色授权。</p>
        </div>
        <el-button type="primary" @click="loadRows">刷新</el-button>
      </div>
    </template>

    <el-form inline class="toolbar">
      <el-form-item label="用户 ID">
        <el-input-number v-model="userId" :min="1" controls-position="right" />
      </el-form-item>
      <el-form-item>
        <el-button type="success" :disabled="selectedRoles.length === 0" @click="saveRoles">保存授权</el-button>
      </el-form-item>
    </el-form>

    <el-table ref="tableRef" :data="rows" row-key="roleId" border @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" :selectable="() => true" reserve-selection />
      <el-table-column prop="roleId" label="角色 ID" width="100" />
      <el-table-column prop="roleName" label="角色名称" min-width="140" />
      <el-table-column prop="roleKey" label="权限标识" min-width="160" />
      <el-table-column label="已分配" width="100">
        <template #default="{ row }">
          <el-tag :type="row.flag ? 'success' : 'info'">{{ row.flag ? '是' : '否' }}</el-tag>
        </template>
      </el-table-column>
    </el-table>

    <el-alert v-if="message" class="message-alert" :title="message" type="success" :closable="false" />
    <el-alert v-if="errorMessage" class="message-alert" :title="errorMessage" type="error" :closable="false" />
  </el-card>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import { useRoute } from 'vue-router'
import { getUserRoleAuth, updateUserRoleAuth } from '@/api/system'

const route = useRoute()
const rows = ref([])
const userId = ref(Number(route.params.userId || 1))
const selectedRoles = ref([])
const errorMessage = ref('')
const message = ref('')
const tableRef = ref()

async function loadRows() {
  errorMessage.value = ''
  message.value = ''
  try {
    const response = await getUserRoleAuth(userId.value)
    rows.value = response.roles || []
    selectedRoles.value = rows.value.filter((item) => item.flag).map((item) => item.roleId)
    requestAnimationFrame(() => {
      try {
        tableRef.value?.clearSelection()
        rows.value.forEach((row) => {
          if (row.flag) {
            tableRef.value?.toggleRowSelection(row, true)
          }
        })
      } catch (error) {
        errorMessage.value = error?.message || '表格选择状态同步失败'
      }
    })
  } catch (error) {
    errorMessage.value = error?.payload?.msg || error?.message || '加载失败'
  }
}

function handleSelectionChange(selection) {
  selectedRoles.value = selection.map((item) => item.roleId)
}

async function saveRoles() {
  try {
    await updateUserRoleAuth(userId.value, selectedRoles.value)
    message.value = '授权成功'
    await loadRows()
  } catch (error) {
    errorMessage.value = error?.payload?.msg || error?.message || '授权失败'
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
.message-alert { margin-top: 16px; }
</style>
