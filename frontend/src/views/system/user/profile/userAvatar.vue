<template>
  <a-card :bordered="false" class="page-card">
    <template #title>
      <div class="page-title">
        <div class="headline">头像设置</div>
        <div class="desc">头像上传与资料更新都走新的文件与头像接口。</div>
      </div>
    </template>
    <div class="avatar-grid">
      <a-card title="上传头像" :bordered="false">
        <a-upload :before-upload="beforeUpload" :show-upload-list="true" :max-count="1">
          <a-button>选择图片</a-button>
        </a-upload>
        <a-button type="primary" class="upload-btn" @click="handleUpload">上传</a-button>
      </a-card>
      <a-card title="上传结果" :bordered="false">
        <div class="info-lines">
          <div class="info-line">文件路径：{{ uploadedPath || '-' }}</div>
          <div class="info-line">头像字段：{{ avatarPath || '-' }}</div>
        </div>
      </a-card>
    </div>
  </a-card>
</template>

<script setup>
import { ref } from 'vue'
import { updateAvatar, uploadFiles } from '@/api/system'
import { toastError, toastSuccess, toastWarning } from '@/utils/toast'

const file = ref(null)
const uploadedPath = ref('')
const avatarPath = ref('')

function beforeUpload(nextFile) {
  file.value = nextFile
  return false
}

async function handleUpload() {
  if (!file.value) {
    toastWarning('请先选择头像图片')
    return
  }
  try {
    const paths = await uploadFiles([file.value])
    uploadedPath.value = Array.isArray(paths) ? paths[0] : String(paths)
    await updateAvatar(uploadedPath.value)
    avatarPath.value = uploadedPath.value
    toastSuccess('上传并更新头像成功')
  } catch (error) {
    toastError(error?.payload?.msg || error?.message || '上传失败')
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

.avatar-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 16px;
}

.upload-btn {
  margin-top: 12px;
}

.info-lines {
  display: grid;
  gap: 8px;
}

.info-line {
  color: #4b5563;
  line-height: 1.6;
}
</style>
