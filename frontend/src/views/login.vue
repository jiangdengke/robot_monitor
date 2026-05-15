<template>
  <div class="login-page">
    <div class="login-panel">
      <a-card :bordered="false">
        <template #title>
          <div class="login-title">
            <div class="eyebrow">Air China Lounge Ops</div>
            <div class="headline">国航智慧贵宾室管理系统</div>
            <div class="desc">前端正在切换为 Ant Design Vue，并对接新的单体后端 API。</div>
          </div>
        </template>

        <a-form layout="vertical" @finish="handleSubmit">
          <a-form-item label="账号" name="username">
            <a-input v-model:value="form.username" autocomplete="username" placeholder="请输入账号" />
          </a-form-item>
          <a-form-item label="密码" name="password">
            <a-input-password v-model:value="form.password" autocomplete="current-password" placeholder="请输入密码" />
          </a-form-item>
          <a-button html-type="submit" type="primary" block :loading="submitting">
            {{ submitting ? '登录中...' : '登录系统' }}
          </a-button>
        </a-form>
      </a-card>
    </div>
  </div>
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

<style scoped>
.login-page {
  min-height: 100vh;
  display: grid;
  place-items: center;
  padding: 24px;
  background:
    radial-gradient(circle at top left, rgb(19 79 92 / 18%), transparent 38%),
    linear-gradient(135deg, #f6f8fb 0%, #eef3f7 100%);
}

.login-panel {
  width: min(100%, 420px);
}

.login-title {
  display: grid;
  gap: 8px;
}

.eyebrow {
  font-size: 12px;
  text-transform: uppercase;
  letter-spacing: 0.12em;
  color: #1677ff;
}

.headline {
  font-size: 24px;
  font-weight: 700;
  color: #111827;
}

.desc {
  font-size: 13px;
  line-height: 1.6;
  color: #6b7280;
}
</style>
