<template>
  <a-card :bordered="false" class="page-card">
    <template #title>
      <div class="page-title">
        <div class="headline">修改密码</div>
        <div class="desc">本页对接 `PUT /me/password`。</div>
      </div>
    </template>
    <a-form layout="vertical" @finish="handleSubmit">
      <a-form-item label="旧密码">
        <a-input-password v-model:value="oldPassword" />
      </a-form-item>
      <a-form-item label="新密码">
        <a-input-password v-model:value="newPassword" />
      </a-form-item>
      <a-button html-type="submit" type="primary">提交修改</a-button>
    </a-form>
  </a-card>
</template>

<script setup>
import { ref } from 'vue'
import { updatePassword } from '@/api/system'
import { toastError, toastSuccess } from '@/utils/toast'

const oldPassword = ref('')
const newPassword = ref('')

async function handleSubmit() {
  try {
    await updatePassword(oldPassword.value, newPassword.value)
    toastSuccess('密码修改成功')
    oldPassword.value = ''
    newPassword.value = ''
  } catch (error) {
    toastError(error?.payload?.msg || error?.message || '修改失败')
  }
}
</script>

<style scoped>
.page-title {
  display: grid;
  gap: 4px;
}

.headline {
  font-size: 18px;
  font-weight: 700;
}

.desc {
  color: #6b7280;
  font-size: 13px;
}
</style>
