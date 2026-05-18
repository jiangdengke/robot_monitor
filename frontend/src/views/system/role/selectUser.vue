<template>
  <el-card shadow="never">
    <template #header>
      <el-row justify="space-between" align="middle">
        <el-space direction="vertical" alignment="flex-start">
          <el-text tag="b" size="large">选择用户</el-text>
          <el-text type="info">读取未分配用户列表，并把勾选用户分配到指定角色。</el-text>
        </el-space>
        <el-button type="primary" @click="loadRows">刷新</el-button>
      </el-row>
    </template>

    <el-form class="filter-form" inline @submit.prevent="loadRows">
      <el-form-item label="角色">
        <el-input-number v-model="roleId" :min="1" controls-position="right" />
      </el-form-item>
      <el-form-item label="账号">
        <el-input v-model.trim="query.userName" clearable placeholder="按账号筛选" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" native-type="submit">查询</el-button>
        <el-button type="success" @click="assignSelected">确认分配</el-button>
        <el-button @click="router.push(`/system/role-auth/user/${roleId}`)">返回</el-button>
      </el-form-item>
    </el-form>

    <el-table :data="rows" border @selection-change="selectedUserIds = $event.map((row) => row.userId)">
      <el-table-column type="selection" width="55" />
      <el-table-column prop="userName" label="账号" min-width="120" />
      <el-table-column prop="nickName" label="昵称" min-width="120" />
      <el-table-column prop="phonenumber" label="手机号" min-width="140" />
    </el-table>

    <el-pagination
      background
      layout="total, prev, pager, next"
      :current-page="pageNum"
      :page-size="pageSize"
      :total="total"
      @current-change="pageNum = $event; loadRows()"
    />

  </el-card>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { assignRoleUsers, listUnallocatedUsers } from '@/api/system'
import { toastError, toastSuccess, toastWarning } from '@/utils/toast'

const route = useRoute()
const router = useRouter()
const rows = ref([])
const roleId = ref(Number(route.params.roleId || 1))
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
    toastError(errorMessage.value)
  }
}

async function assignSelected() {
  if (!selectedUserIds.value.length) {
    toastWarning('请先选择要分配的用户')
    return
  }
  try {
    await assignRoleUsers(roleId.value, selectedUserIds.value)
    message.value = '用户分配成功'
    toastSuccess(message.value)
    await loadRows()
  } catch (error) {
    errorMessage.value = error?.payload?.msg || error?.message || '分配失败'
    toastError(errorMessage.value)
  }
}

onMounted(loadRows)
</script>
