<template>
  <el-card shadow="never">
    <template #header>
      <el-row justify="space-between" align="middle">
        <el-space direction="vertical" alignment="flex-start">
          <el-text tag="b" size="large">个人中心</el-text>
          <el-text type="info">直接读取 `/system/user/profile` 接口。</el-text>
        </el-space>
        <el-button type="primary" @click="loadProfile">刷新</el-button>
      </el-row>
    </template>
    <el-row :gutter="16">
      <el-col :xs="24" :md="12">
        <el-card shadow="never">
          <template #header><el-text tag="b">基础信息</el-text></template>
          <el-descriptions :column="1" border>
            <el-descriptions-item label="账号">{{ profile.userName || '-' }}</el-descriptions-item>
            <el-descriptions-item label="昵称">{{ profile.nickName || '-' }}</el-descriptions-item>
            <el-descriptions-item label="邮箱">{{ profile.email || '-' }}</el-descriptions-item>
            <el-descriptions-item label="手机号">{{ profile.phonenumber || '-' }}</el-descriptions-item>
          </el-descriptions>
        </el-card>
      </el-col>
      <el-col :xs="24" :md="12">
        <el-card shadow="never">
          <template #header><el-text tag="b">组织信息</el-text></template>
          <el-descriptions :column="1" border>
            <el-descriptions-item label="角色">{{ roleGroup || '-' }}</el-descriptions-item>
            <el-descriptions-item label="岗位">{{ postGroup || '-' }}</el-descriptions-item>
            <el-descriptions-item label="部门">{{ profile.dept?.deptName || '-' }}</el-descriptions-item>
          </el-descriptions>
        </el-card>
      </el-col>
    </el-row>
  </el-card>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import { getProfile } from '@/api/system'
import { toastError } from '@/utils/toast'

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
    toastError(errorMessage.value)
  }
}

onMounted(loadProfile)
</script>
