<template>
  <el-card shadow="never">
    <template #header>
      <el-space direction="vertical" alignment="flex-start">
        <el-text tag="b" size="large">修改密码</el-text>
        <el-text type="info">本页已接 `PUT /system/user/profile/updatePwd`。</el-text>
      </el-space>
    </template>
    <el-form label-position="top" @submit.prevent="handleSubmit">
      <el-form-item label="旧密码">
        <el-input v-model="oldPassword" type="password" show-password placeholder="旧密码" />
      </el-form-item>
      <el-form-item label="新密码">
        <el-input v-model="newPassword" type="password" show-password placeholder="新密码" />
      </el-form-item>
      <el-button type="primary" native-type="submit">提交修改</el-button>
      <el-alert v-if="message" :title="message" :type="message.includes('成功') ? 'success' : 'error'" :closable="false" />
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
