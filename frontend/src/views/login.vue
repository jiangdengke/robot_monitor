<template>
  <div class="login-shell">
    <section class="login-panel card">
      <div class="login-copy">
        <div class="eyebrow">Air China Lounge Ops</div>
        <h1>国航智慧贵宾室管理系统</h1>
        <p>当前前端已切换为可维护的 `.vue` 源码页面，直接对接本地单体 Spring Boot 后端。</p>
      </div>

      <form class="login-form" @submit.prevent="handleSubmit">
        <el-form label-position="top">
          <el-form-item label="账号">
            <el-input v-model="form.username" autocomplete="username" placeholder="请输入账号" />
          </el-form-item>
          <el-form-item label="密码">
            <el-input v-model="form.password" type="password" autocomplete="current-password" placeholder="请输入密码" show-password />
          </el-form-item>
        </el-form>

        <div class="tips">
          <span>当前默认账号：`admin`</span>
          <span>当前默认密码：`admin123`</span>
        </div>

        <el-button class="submit" :loading="submitting" type="primary" native-type="submit">
          {{ submitting ? '登录中...' : '登录系统' }}
        </el-button>

        <el-alert v-if="errorMessage" :title="errorMessage" type="error" :closable="false" />
      </form>
    </section>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { hydrateSession, login } from '@/stores/session'

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
  } finally {
    submitting.value = false
  }
}
</script>

<style scoped>
.login-shell {
  min-height: 100vh;
  display: grid;
  place-items: center;
  padding: 24px;
  background:
    linear-gradient(135deg, rgba(5, 37, 69, 0.94), rgba(8, 75, 132, 0.9)),
    url('/legacy-dist/static/jpg/login-background-Yn3y1TP_.jpg') center/cover no-repeat;
}

.login-panel {
  width: min(980px, 100%);
  display: grid;
  grid-template-columns: 1.1fr 0.9fr;
  overflow: hidden;
}

.login-copy {
  padding: 44px;
  color: #eef6ff;
  background:
    linear-gradient(180deg, rgba(6, 31, 57, 0.92), rgba(8, 52, 93, 0.82));
}

.eyebrow {
  font-size: 12px;
  letter-spacing: 0.12em;
  text-transform: uppercase;
  opacity: 0.75;
}

.login-copy h1 {
  margin: 18px 0 14px;
  font-size: 42px;
  line-height: 1.12;
}

.login-copy p {
  margin: 0;
  font-size: 15px;
  line-height: 1.8;
  color: rgba(238, 246, 255, 0.82);
}

.login-form {
  display: grid;
  gap: 18px;
  padding: 44px;
  background: rgba(255, 255, 255, 0.94);
}

.tips {
  display: grid;
  gap: 6px;
  padding: 12px 14px;
  border-radius: 12px;
  background: #f5f9fd;
  color: var(--text-soft);
  font-size: 13px;
}

.submit {
  width: 100%;
}

@media (max-width: 900px) {
  .login-panel {
    grid-template-columns: 1fr;
  }

  .login-copy,
  .login-form {
    padding: 28px;
  }

  .login-copy h1 {
    font-size: 32px;
  }
}
</style>
