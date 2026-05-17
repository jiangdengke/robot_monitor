<template>
  <div class="login-page">
    <div class="login-shell">
      <aside class="brand-panel">
        <div class="brand-noise" />
        <div class="brand-top">
          <div class="brand-mark">
            <img src="/legacy-dist/favicon-old.ico" alt="国航" />
          </div>
          <div class="brand-text">
            <span class="brand-eyebrow">Air China · Lounge OS</span>
            <span class="brand-name">智慧贵宾室管理系统</span>
          </div>
        </div>

        <div class="brand-headline">
          <h1>统一调度</h1>
          <h1>更聪明的贵宾室运营</h1>
          <p>区域、机器人、旅客、巡检数据实时联动，一处操作，全室协同。</p>
        </div>

        <ul class="brand-points">
          <li><span class="dot" />数字孪生看板，全流程可视化</li>
          <li><span class="dot" />机器人引导与提醒一键派发</li>
          <li><span class="dot" />巡检预警与运营报表自动汇总</li>
        </ul>

        <div class="brand-footer">
          <span>© Air China Lounge Operations</span>
          <span>v2026.05</span>
        </div>
      </aside>

      <section class="form-panel">
        <div class="form-card">
          <div class="form-heading">
            <span class="eyebrow-chip">SIGN IN</span>
            <h2>欢迎回来</h2>
            <p>使用您的运营账号登录，进入管理后台。</p>
          </div>

          <a-form :model="form" layout="vertical" class="login-form" @finish="handleSubmit">
            <a-form-item label="账号" name="username">
              <a-input
                v-model:value="form.username"
                size="large"
                autocomplete="username"
                placeholder="请输入账号"
              >
                <template #prefix><UserOutlined class="input-icon" /></template>
              </a-input>
            </a-form-item>
            <a-form-item label="密码" name="password">
              <a-input-password
                v-model:value="form.password"
                size="large"
                autocomplete="current-password"
                placeholder="请输入密码"
              >
                <template #prefix><LockOutlined class="input-icon" /></template>
              </a-input-password>
            </a-form-item>

            <div class="login-meta">
              <a-checkbox v-model:checked="remember">7 天内自动登录</a-checkbox>
              <a class="forgot" @click.prevent>忘记密码？</a>
            </div>

            <a-button
              html-type="submit"
              type="primary"
              size="large"
              block
              :loading="submitting"
              class="submit-btn"
              @click="handleSubmit"
            >
              {{ submitting ? '登录中...' : '登录系统' }}
            </a-button>

            <div class="hint-card">
              <InfoCircleOutlined />
              <span>默认演示账号 <code>admin</code> / <code>admin123</code></span>
            </div>
          </a-form>
        </div>
      </section>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { InfoCircleOutlined, LockOutlined, UserOutlined } from '@ant-design/icons-vue'
import { hydrateSession, login } from '@/stores/session'
import { toastError } from '@/utils/toast'

const router = useRouter()
const submitting = ref(false)
const errorMessage = ref('')
const remember = ref(true)

const form = reactive({
  username: 'admin',
  password: 'admin123'
})

async function handleSubmit() {
  if (submitting.value) {
    return
  }
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
</script>

<style scoped>
.login-page {
  min-height: 100vh;
  display: grid;
  place-items: center;
  padding: 32px;
  background:
    radial-gradient(circle at top left, rgb(47 84 235 / 16%), transparent 42%),
    radial-gradient(circle at bottom right, rgb(19 194 194 / 14%), transparent 50%),
    linear-gradient(135deg, #f3f5fa 0%, #eef2fb 100%);
}

.login-shell {
  display: grid;
  grid-template-columns: 1.05fr 1fr;
  width: min(100%, 1080px);
  min-height: 600px;
  background: var(--surface-card);
  border-radius: 24px;
  box-shadow: 0 30px 80px rgb(15 23 42 / 14%);
  overflow: hidden;
  border: 1px solid var(--border-soft);
}

.brand-panel {
  position: relative;
  padding: 48px 44px;
  color: #f8fbff;
  background:
    radial-gradient(circle at 0% 0%, rgb(82 196 250 / 32%), transparent 55%),
    radial-gradient(circle at 100% 100%, rgb(19 194 194 / 28%), transparent 55%),
    linear-gradient(160deg, #0f1d3a 0%, #142a55 50%, #1d3b7a 100%);
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  overflow: hidden;
}

.brand-noise {
  position: absolute;
  inset: 0;
  background-image:
    repeating-linear-gradient(135deg, rgb(255 255 255 / 4%) 0 1px, transparent 1px 24px),
    repeating-linear-gradient(45deg, rgb(255 255 255 / 3%) 0 1px, transparent 1px 36px);
  pointer-events: none;
  opacity: 0.6;
}

.brand-top,
.brand-headline,
.brand-points,
.brand-footer {
  position: relative;
  z-index: 1;
}

.brand-top {
  display: flex;
  align-items: center;
  gap: 14px;
}

.brand-mark {
  width: 44px;
  height: 44px;
  border-radius: 12px;
  background: linear-gradient(135deg, rgb(255 255 255 / 18%), rgb(47 84 235 / 36%));
  border: 1px solid rgb(255 255 255 / 16%);
  display: flex;
  align-items: center;
  justify-content: center;
}

.brand-mark img {
  width: 28px;
  height: 28px;
}

.brand-text {
  display: grid;
  line-height: 1.2;
}

.brand-eyebrow {
  font-size: 11px;
  text-transform: uppercase;
  letter-spacing: 0.18em;
  color: rgb(207 217 248 / 70%);
}

.brand-name {
  font-size: 17px;
  font-weight: 700;
  margin-top: 4px;
}

.brand-headline h1 {
  margin: 0;
  font-size: 34px;
  font-weight: 700;
  letter-spacing: 0.02em;
  line-height: 1.18;
}

.brand-headline p {
  margin: 16px 0 0;
  max-width: 320px;
  font-size: 14px;
  line-height: 1.7;
  color: rgb(207 217 248 / 80%);
}

.brand-points {
  list-style: none;
  padding: 0;
  margin: 0;
  display: grid;
  gap: 12px;
}

.brand-points li {
  display: flex;
  align-items: center;
  gap: 10px;
  font-size: 13px;
  color: rgb(232 238 255 / 90%);
}

.brand-points .dot {
  width: 8px;
  height: 8px;
  border-radius: 50%;
  background: var(--brand-accent);
  box-shadow: 0 0 12px rgb(19 194 194 / 70%);
}

.brand-footer {
  display: flex;
  justify-content: space-between;
  font-size: 11px;
  letter-spacing: 0.08em;
  color: rgb(207 217 248 / 56%);
  text-transform: uppercase;
}

.form-panel {
  display: grid;
  place-items: center;
  padding: 48px;
}

.form-card {
  width: 100%;
  max-width: 380px;
  display: grid;
  gap: 28px;
}

.form-heading h2 {
  margin: 12px 0 6px;
  font-size: 26px;
  font-weight: 700;
  color: var(--text-strong);
}

.form-heading p {
  margin: 0;
  font-size: 13px;
  color: var(--text-muted);
}

.login-form :deep(.ant-form-item) {
  margin-bottom: 18px;
}

.login-form :deep(.ant-form-item-label > label) {
  font-weight: 600;
  color: var(--text-strong);
  font-size: 13px;
}

.input-icon {
  color: var(--text-faint);
}

.login-meta {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
  font-size: 13px;
}

.forgot {
  color: var(--brand-primary);
  font-weight: 500;
  cursor: pointer;
}

.submit-btn {
  height: 44px;
  font-size: 15px;
  font-weight: 600;
  border-radius: 10px;
  background: linear-gradient(135deg, var(--brand-primary), var(--brand-accent));
  border: none;
  box-shadow: 0 14px 30px rgb(47 84 235 / 28%);
}

.submit-btn:hover {
  background: linear-gradient(135deg, var(--brand-primary-soft), var(--brand-accent));
}

.hint-card {
  margin-top: 18px;
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 12px 14px;
  border-radius: 10px;
  background: rgb(47 84 235 / 6%);
  color: var(--text-muted);
  font-size: 12px;
}

.hint-card code {
  background: rgb(15 23 42 / 6%);
  padding: 1px 6px;
  border-radius: 4px;
  font-family: var(--font-mono);
  color: var(--text-strong);
  margin: 0 2px;
}

@media (max-width: 960px) {
  .login-shell {
    grid-template-columns: 1fr;
    min-height: auto;
  }

  .brand-panel {
    padding: 36px;
    gap: 28px;
  }

  .brand-headline h1 {
    font-size: 26px;
  }

  .form-panel {
    padding: 36px 28px 40px;
  }
}
</style>
