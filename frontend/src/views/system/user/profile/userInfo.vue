<template>
  <a-card :bordered="false" class="page-card">
    <template #title>
      <div class="page-title">
        <div class="headline">个人资料</div>
        <div class="desc">本页对接新的 `/me` 与 `PUT /me`。</div>
      </div>
    </template>
    <div class="profile-grid">
      <InfoCard
        title="当前账号"
        :lines="[
          `账号：${profile.username || '-'}`,
          `昵称：${profile.nickname || '-'}`,
          `邮箱：${profile.email || '-'}`,
          `手机号：${profile.phone || '-'}`
        ]"
      />
      <a-form layout="vertical" @finish="handleSave">
        <a-form-item label="昵称">
          <a-input v-model:value="form.nickname" />
        </a-form-item>
        <a-form-item label="邮箱">
          <a-input v-model:value="form.email" />
        </a-form-item>
        <a-form-item label="手机号">
          <a-input v-model:value="form.phone" />
        </a-form-item>
        <a-form-item label="性别">
          <a-select v-model:value="form.sex" :options="sexOptions" />
        </a-form-item>
        <a-button html-type="submit" type="primary">保存资料</a-button>
      </a-form>
    </div>
  </a-card>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import InfoCard from '@/components/InfoCard.vue'
import { getProfile, updateProfile } from '@/api/system'
import { toastError, toastSuccess } from '@/utils/toast'

const profile = ref({})
const form = ref({ nickname: '', email: '', phone: '', sex: '2' })

const sexOptions = [
  { label: '男', value: '0' },
  { label: '女', value: '1' },
  { label: '未知', value: '2' }
]

async function loadProfile() {
  try {
    profile.value = await getProfile()
    form.value = {
      nickname: profile.value.nickname || '',
      email: profile.value.email || '',
      phone: profile.value.phone || '',
      sex: profile.value.sex || '2'
    }
  } catch (error) {
    toastError(error?.payload?.msg || error?.message || '加载失败')
  }
}

async function handleSave() {
  try {
    await updateProfile({ ...form.value })
    toastSuccess('保存成功')
    await loadProfile()
  } catch (error) {
    toastError(error?.payload?.msg || error?.message || '保存失败')
  }
}

onMounted(loadProfile)
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

.profile-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(320px, 1fr));
  gap: 16px;
}
</style>
