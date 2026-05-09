<template>
  <el-card class="page-card">
    <template #header>
      <div class="page-header">
        <div>
          <h1>选择用户</h1>
          <p>读取未分配用户列表，并把勾选用户分配到指定角色。</p>
        </div>
        <el-button type="primary" @click="loadRows">刷新</el-button>
      </div>
    </template>

    <el-form inline class="toolbar" @submit.prevent="loadRows">
      <el-form-item label="角色 ID">
        <el-input-number v-model="roleId" :min="1" controls-position="right" />
      </el-form-item>
      <el-form-item label="账号">
        <el-input v-model.trim="query.userName" clearable placeholder="按账号筛选" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" native-type="submit">查询</el-button>
        <el-button type="success" :disabled="!selectedUserIds.length" @click="assignSelected">确认分配</el-button>
      </el-form-item>
    </el-form>

    <el-table :data="rows" border @selection-change="selectedUserIds = $event.map((row) => row.userId)">
      <el-table-column type="selection" width="55" />
      <el-table-column prop="userId" label="用户 ID" width="100" />
      <el-table-column prop="userName" label="账号" min-width="120" />
      <el-table-column prop="nickName" label="昵称" min-width="120" />
      <el-table-column prop="phonenumber" label="手机号" min-width="140" />
    </el-table>

    <el-pagination
      class="pagination"
      background
      layout="total, prev, pager, next"
      :current-page="pageNum"
      :page-size="pageSize"
      :total="total"
      @current-change="pageNum = $event; loadRows()"
    />

    <el-alert v-if="message" class="message-alert" :title="message" type="success" :closable="false" />
    <el-alert v-if="errorMessage" class="message-alert" :title="errorMessage" type="error" :closable="false" />
  </el-card>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import { assignRoleUsers, listUnallocatedUsers } from '@/api/system'

const rows = ref([])
const roleId = ref(1)
const pageNum = ref(1)
const pageSize = ref(20)
const total = ref(0)
const selectedUserIds = ref([])
const query = reactive({ userName: '' })
const errorMessage = ref('')
const message = ref('')

async function loadRows() {
  errorMessage.value = ''
  message.value = ''
  try {
    const response = await listUnallocatedUsers({
      roleId: roleId.value,
      pageNum: pageNum.value,
      pageSize: pageSize.value,
      userName: query.userName
    })
    rows.value = response.rows || []
    total.value = response.total || rows.value.length
    selectedUserIds.value = []
  } catch (error) {
    errorMessage.value = error?.payload?.msg || error?.message || '加载失败'
  }
}

async function assignSelected() {
  try {
    await assignRoleUsers(roleId.value, selectedUserIds.value)
    message.value = '用户分配成功'
    await loadRows()
  } catch (error) {
    errorMessage.value = error?.payload?.msg || error?.message || '分配失败'
  }
}

onMounted(loadRows)
</script>

<style scoped>
.page-card { padding: 24px; }
.page-header { display: flex; justify-content: space-between; align-items: center; gap: 16px; margin-bottom: 18px; }
.page-header h1 { margin: 0; font-size: 28px; }
.page-header p { margin: 8px 0 0; color: var(--text-soft); }
.toolbar { margin-bottom: 12px; }
.pagination { justify-content: flex-end; margin-top: 16px; }
.message-alert { margin-top: 16px; }
</style>
