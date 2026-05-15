<template>
  <a-card :bordered="false" class="page-card">
    <template #title>
      <div class="page-title">
        <div class="headline">个人中心</div>
        <div class="desc">直接读取新的 `/me` 接口。</div>
      </div>
    </template>
    <div class="profile-grid">
      <InfoCard
        title="基础信息"
        :lines="[
          `账号：${profile.username || '-'}`,
          `昵称：${profile.nickname || '-'}`,
          `邮箱：${profile.email || '-'}`,
          `手机号：${profile.phone || '-'}`
        ]"
      />
      <InfoCard
        title="辅助信息"
        :lines="[
          `性别：${profile.sex || '-'}`,
          `状态：${profile.enable ? '启用' : '停用'}`,
          `备注：${profile.remark || '-'}`
        ]"
      />
    </div>
  </a-card>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import InfoCard from '@/components/InfoCard.vue'
import { getProfile } from '@/api/system'
import { toastError } from '@/utils/toast'

const profile = ref({})

async function loadProfile() {
  try {
    profile.value = await getProfile()
  } catch (error) {
    toastError(error?.payload?.msg || error?.message || '加载失败')
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
  grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
  gap: 16px;
}
</style>
