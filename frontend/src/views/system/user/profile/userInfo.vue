<template>
  <el-card class="page-card">
    <template #header>
      <div class="page-header">
        <div>
          <h1>个人资料</h1>
          <p>本页已接 `GET /system/user/profile` 和 `PUT /system/user/profile`。</p>
        </div>
        <el-button type="primary" @click="loadProfile">刷新</el-button>
      </div>
    </template>
    <div class="info-grid">
      <InfoCard title="账号信息" :lines="[`账号：${profile.userName || '-'}`, `昵称：${profile.nickName || '-'}`, `邮箱：${profile.email || '-'}`]" />
      <InfoCard title="联系信息" :lines="[`手机号：${profile.phonenumber || '-'}`, `性别：${profile.sex || '-'}`, `部门：${profile.dept?.deptName || '-'}`]" />
    </div>
    <el-form label-position="top" class="form-grid" @submit.prevent="handleSave">
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
      <el-alert v-if="message" class="message-alert" :title="message" :type="message.includes('成功') ? 'success' : 'error'" :closable="false" />
    </el-form>
  </el-card>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import InfoCard from '@/components/InfoCard.vue'
import { getProfile, updateProfile } from '@/api/system'

const profile = ref({})
const form = ref({ nickName: '', email: '', phonenumber: '', sex: '2' })
const message = ref('')

async function loadProfile() {
  const response = await getProfile()
  profile.value = response.data || {}
  form.value = {
    nickName: profile.value.nickName || '',
    email: profile.value.email || '',
    phonenumber: profile.value.phonenumber || '',
    sex: profile.value.sex || '2'
  }
}

async function handleSave() {
  try {
    await updateProfile({ ...form.value })
    message.value = '保存成功'
    await loadProfile()
  } catch (error) {
    message.value = error?.payload?.msg || error?.message || '保存失败'
  }
}

onMounted(loadProfile)
</script>

<style scoped>
.page-card { padding: 24px; }
.page-header { display: flex; justify-content: space-between; align-items: center; gap: 16px; margin-bottom: 18px; }
.page-header h1 { margin: 0; font-size: 28px; }
.page-header p { margin: 8px 0 0; color: var(--text-soft); }
.info-grid { display: grid; grid-template-columns: repeat(2, minmax(0, 1fr)); gap: 16px; }
.form-grid { margin-top: 18px; }
.message-alert { margin-top: 8px; }
</style>
