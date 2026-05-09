<template>
  <el-card class="page-card">
    <template #header>
      <div class="page-header">
        <div>
          <h1>个人中心</h1>
          <p>直接读取 `/system/user/profile` 接口。</p>
        </div>
        <el-button type="primary" @click="loadProfile">刷新</el-button>
      </div>
    </template>
    <div class="info-grid">
      <el-card shadow="never" class="info-panel">
        <template #header><h2>基础信息</h2></template>
        <p>账号：{{ profile.userName || '-' }}</p>
        <p>昵称：{{ profile.nickName || '-' }}</p>
        <p>邮箱：{{ profile.email || '-' }}</p>
        <p>手机号：{{ profile.phonenumber || '-' }}</p>
      </el-card>
      <el-card shadow="never" class="info-panel">
        <template #header><h2>组织信息</h2></template>
        <p>角色：{{ roleGroup || '-' }}</p>
        <p>岗位：{{ postGroup || '-' }}</p>
        <p>部门：{{ profile.dept?.deptName || '-' }}</p>
      </el-card>
    </div>
    <el-alert v-if="errorMessage" class="message-alert" :title="errorMessage" type="error" :closable="false" />
  </el-card>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import { getProfile } from '@/api/system'

const profile = ref({})
const roleGroup = ref('')
const postGroup = ref('')
const errorMessage = ref('')

async function loadProfile() {
  errorMessage.value = ''
  try {
    const response = await getProfile()
    profile.value = response.data || {}
    roleGroup.value = response.roleGroup || ''
    postGroup.value = response.postGroup || ''
  } catch (error) {
    errorMessage.value = error?.payload?.msg || error?.message || '加载失败'
  }
}

onMounted(loadProfile)
</script>

<style scoped>
.page-card { padding: 24px; }
.page-header { display: flex; justify-content: space-between; align-items: center; gap: 16px; margin-bottom: 18px; }
.page-header h1 { margin: 0; font-size: 28px; }
.page-header p { margin: 8px 0 0; color: var(--text-soft); }
.info-grid { display: grid; grid-template-columns: repeat(2, minmax(0, 1fr)); gap: 16px; }
.info-panel h2 { margin: 0; font-size: 16px; }
.info-panel p { margin: 8px 0 0; color: var(--text-soft); }
.message-alert { margin-top: 16px; }
</style>
