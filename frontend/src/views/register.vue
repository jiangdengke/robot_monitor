<template>
  <div class="auth-shell">
    <el-card class="auth-card">
      <div class="auth-copy">
        <span class="eyebrow">Account Register</span>
        <h1>注册账号</h1>
        <p>注册开关由系统参数 `sys.account.registerUser` 控制；开启后会直接调用本地后端 `/register`。</p>
      </div>

      <el-form ref="formRef" :model="form" :rules="rules" label-position="top" class="auth-form" @submit.prevent="submit">
        <el-form-item label="用户名" prop="username">
          <el-input v-model.trim="form.username" autocomplete="username" placeholder="请输入 2-20 位账号" />
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="form.password" type="password" show-password autocomplete="new-password" placeholder="请输入 5-20 位密码" />
        </el-form-item>
        <el-form-item label="确认密码" prop="confirmPassword">
          <el-input v-model="form.confirmPassword" type="password" show-password autocomplete="new-password" placeholder="请再次输入密码" />
        </el-form-item>
        <el-form-item v-if="captchaOnOff" label="验证码" prop="code">
          <div class="captcha-row">
            <el-input v-model.trim="form.code" placeholder="请输入验证码" />
            <button class="captcha-image" type="button" @click="loadCaptcha">
              <img v-if="captchaImage" :src="captchaImage" alt="验证码" />
              <span v-else>刷新验证码</span>
            </button>
          </div>
        </el-form-item>

        <el-button class="submit" type="primary" :loading="submitting" native-type="submit">
          {{ submitting ? '提交中...' : '提交注册' }}
        </el-button>
        <el-button class="submit" plain @click="router.push('/login')">返回登录</el-button>

        <el-alert v-if="message" :title="message" :type="messageType" :closable="false" />
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { getCaptchaImage, registerAccount } from '@/api/system'

const router = useRouter()
const formRef = ref()
const submitting = ref(false)
const captchaOnOff = ref(false)
const captchaImage = ref('')
const message = ref('')
const messageType = ref('success')

const form = reactive({
  username: '',
  password: '',
  confirmPassword: '',
  code: '',
  uuid: ''
})

const rules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 2, max: 20, message: '账号长度必须在 2 到 20 个字符之间', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 5, max: 20, message: '密码长度必须在 5 到 20 个字符之间', trigger: 'blur' }
  ],
  confirmPassword: [
    { required: true, message: '请确认密码', trigger: 'blur' },
    {
      validator: (_, value, callback) => {
        if (value !== form.password) {
          callback(new Error('两次输入的密码不一致'))
          return
        }
        callback()
      },
      trigger: 'blur'
    }
  ],
  code: [{ required: true, message: '请输入验证码', trigger: 'blur' }]
}

async function loadCaptcha() {
  try {
    const response = await getCaptchaImage()
    captchaOnOff.value = response.captchaOnOff !== false
    form.uuid = response.uuid || ''
    captchaImage.value = response.img ? `data:image/gif;base64,${response.img}` : ''
  } catch (error) {
    captchaOnOff.value = false
    showMessage(error?.payload?.msg || error?.message || '验证码加载失败，已按关闭验证码处理', 'warning')
  }
}

async function submit() {
  message.value = ''
  await formRef.value?.validate()
  submitting.value = true
  try {
    await registerAccount({
      username: form.username,
      password: form.password,
      code: form.code,
      uuid: form.uuid
    })
    showMessage('注册成功，请返回登录', 'success')
  } catch (error) {
    showMessage(error?.payload?.msg || error?.message || '注册失败', 'error')
    if (captchaOnOff.value) {
      await loadCaptcha()
      form.code = ''
    }
  } finally {
    submitting.value = false
  }
}

function showMessage(text, type = 'success') {
  message.value = text
  messageType.value = type
}

onMounted(loadCaptcha)
</script>

<style scoped>
.auth-shell {
  min-height: 100vh;
  display: grid;
  place-items: center;
  padding: 24px;
  background:
    radial-gradient(circle at top left, rgba(50, 129, 214, .34), transparent 32%),
    linear-gradient(135deg, #071c34, #0d4e83);
}
.auth-card { width: min(560px, 100%); padding: 34px; }
.eyebrow {
  color: var(--brand);
  font-size: 12px;
  font-weight: 700;
  letter-spacing: .12em;
  text-transform: uppercase;
}
.auth-copy h1 { margin: 10px 0 0; font-size: 30px; }
.auth-copy p { margin: 10px 0 0; color: var(--text-soft); line-height: 1.7; }
.auth-form { display: grid; gap: 2px; margin-top: 22px; }
.captcha-row { display: grid; grid-template-columns: 1fr 128px; gap: 10px; width: 100%; }
.captcha-image {
  display: grid;
  place-items: center;
  min-height: 40px;
  border: 1px solid var(--line);
  border-radius: 8px;
  background: #fff;
  color: var(--text-soft);
  cursor: pointer;
}
.captcha-image img { max-width: 112px; max-height: 36px; }
.submit { width: 100%; margin-left: 0; }
</style>
