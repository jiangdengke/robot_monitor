<template>
  <el-card class="page-card">
    <template #header>
      <div class="page-header">
        <div>
          <h1>修改密码</h1>
          <p>本页已接 `PUT /system/user/profile/updatePwd`。</p>
        </div>
      </div>
    </template>
    <el-form label-position="top" class="form-grid" @submit.prevent="handleSubmit">
      <el-form-item label="旧密码">
        <el-input v-model="oldPassword" type="password" show-password placeholder="旧密码" />
      </el-form-item>
      <el-form-item label="新密码">
        <el-input v-model="newPassword" type="password" show-password placeholder="新密码" />
      </el-form-item>
      <el-button type="primary" native-type="submit">提交修改</el-button>
      <el-alert v-if="message" class="message-alert" :title="message" :type="message.includes('成功') ? 'success' : 'error'" :closable="false" />
    </el-form>
  </el-card>
</template>

<script setup>
import { ref } from 'vue'
import { updatePassword } from '@/api/system'

const oldPassword = ref('')
const newPassword = ref('')
const message = ref('')

async function handleSubmit() {
  try {
    await updatePassword(oldPassword.value, newPassword.value)
    message.value = '密码修改成功'
    oldPassword.value = ''
    newPassword.value = ''
  } catch (error) {
    message.value = error?.payload?.msg || error?.message || '修改失败'
  }
}
</script>

<style scoped>
.page-card { padding: 24px; }
.page-header h1 { margin: 0; font-size: 28px; }
.page-header p { margin: 8px 0 0; color: var(--text-soft); }
.form-grid { margin-top: 18px; }
.message-alert { margin-top: 8px; }
</style>
