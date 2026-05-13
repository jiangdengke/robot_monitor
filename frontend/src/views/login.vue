<template>
  <el-container>
    <el-main>
      <el-card shadow="never">
        <template #header>
          <el-space direction="vertical" alignment="flex-start">
            <el-text type="primary">Air China Lounge Ops</el-text>
            <el-text tag="b" size="large">国航智慧贵宾室管理系统</el-text>
            <el-text type="info">当前前端已切换为可维护的 `.vue` 源码页面，直接对接本地单体 Spring Boot 后端。</el-text>
          </el-space>
        </template>
        <el-form label-position="top">
          <el-form-item label="账号">
            <el-input v-model="form.username" autocomplete="username" placeholder="请输入账号" />
          </el-form-item>
          <el-form-item label="密码">
            <el-input v-model="form.password" type="password" autocomplete="current-password" placeholder="请输入密码" show-password />
          </el-form-item>
        </el-form>

        <el-divider />
        <el-button :loading="submitting" type="primary" @click="handleSubmit">
          {{ submitting ? '登录中...' : '登录系统' }}
        </el-button>
      </el-card>
    </el-main>
  </el-container>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { hydrateSession, login } from '@/stores/session'
import { toastError, toastInfo } from '@/utils/toast'

const router = useRouter()
const submitting = ref(false)
const errorMessage = ref('')

const form = reactive({
  username: 'admin',
  password: 'admin123'
})

async function handleSubmit() {
  errorMessage.value = ''
  submitting.value = true
  try {
    await login({
      username: form.username,
      password: form.password
    })
    await hydrateSession()
    await router.push('/system/user')
  } catch (error) {
    errorMessage.value = error?.payload?.msg || error?.message || '登录失败'
    toastError(errorMessage.value)
  } finally {
    submitting.value = false
  }
}

toastInfo('当前默认账号：admin；当前默认密码：admin123')
</script>
