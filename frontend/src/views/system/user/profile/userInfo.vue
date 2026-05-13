<template>
  <el-card shadow="never">
    <template #header>
      <el-row justify="space-between" align="middle">
        <el-space direction="vertical" alignment="flex-start">
          <el-text tag="b" size="large">个人资料</el-text>
          <el-text type="info">本页已接 `GET /system/user/profile` 和 `PUT /system/user/profile`。</el-text>
        </el-space>
        <el-button type="primary" @click="loadProfile">刷新</el-button>
      </el-row>
    </template>
    <el-row :gutter="16">
      <el-col :xs="24" :md="12">
        <InfoCard title="账号信息" :lines="[`账号：${profile.userName || '-'}`, `昵称：${profile.nickName || '-'}`, `邮箱：${profile.email || '-'}`]" />
      </el-col>
      <el-col :xs="24" :md="12">
        <InfoCard title="联系信息" :lines="[`手机号：${profile.phonenumber || '-'}`, `性别：${profile.sex || '-'}`, `部门：${profile.dept?.deptName || '-'}`]" />
      </el-col>
    </el-row>
    <el-divider />
    <el-form label-position="top" @submit.prevent="handleSave">
      <el-form-item label="昵称">
        <el-input v-model="form.nickName" placeholder="昵称" />
      </el-form-item>
      <el-form-item label="邮箱">
        <el-input v-model="form.email" placeholder="邮箱" />
      </el-form-item>
      <el-form-item label="手机号">
        <el-input v-model="form.phonenumber" placeholder="手机号" />
      </el-form-item>
      <el-form-item label="性别">
        <el-select v-model="form.sex">
          <el-option label="男" value="0" />
          <el-option label="女" value="1" />
          <el-option label="未知" value="2" />
        </el-select>
      </el-form-item>
      <el-button type="success" native-type="submit">保存资料</el-button>
    </el-form>
  </el-card>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import InfoCard from '@/components/InfoCard.vue'
import { getProfile, updateProfile } from '@/api/system'
import { toastError, toastSuccess } from '@/utils/toast'

const profile = ref({})
const form = ref({ nickName: '', email: '', phonenumber: '', sex: '2' })
const message = ref('')

async function loadProfile() {
  try {
    const response = await getProfile()
    profile.value = response.data || {}
    form.value = {
      nickName: profile.value.nickName || '',
      email: profile.value.email || '',
      phonenumber: profile.value.phonenumber || '',
      sex: profile.value.sex || '2'
    }
    if (!message.value || !message.value.includes('成功')) {
      message.value = ''
    }
  } catch (error) {
    message.value = error?.payload?.msg || error?.message || '加载失败'
    toastError(message.value)
  }
}

async function handleSave() {
  try {
    await updateProfile({ ...form.value })
    message.value = '保存成功'
    toastSuccess(message.value)
    await loadProfile()
  } catch (error) {
    message.value = error?.payload?.msg || error?.message || '保存失败'
    toastError(message.value)
  }
}

onMounted(loadProfile)
</script>
