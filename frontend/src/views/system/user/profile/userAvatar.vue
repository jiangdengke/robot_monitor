<template>
  <el-card class="page-card">
    <template #header>
      <div class="page-header">
        <div>
          <h1>头像设置</h1>
          <p>头像上传与资料更新都已经走真实接口。</p>
        </div>
      </div>
    </template>
    <div class="info-grid">
      <el-card shadow="never" class="info-panel">
        <template #header>
          <h2>上传头像</h2>
        </template>
        <el-upload
          class="avatar-uploader"
          :auto-upload="false"
          :show-file-list="true"
          :limit="1"
          accept="image/*"
          :on-change="handleFileChange"
        >
          <template #trigger>
            <el-button>选择图片</el-button>
          </template>
          <el-button type="primary" class="upload-button" :disabled="!file" @click="handleUpload">上传</el-button>
        </el-upload>
        <el-alert v-if="message" class="message-alert" :title="message" :type="messageType" :closable="false" />
      </el-card>
      <el-card shadow="never" class="info-panel">
        <template #header>
          <h2>上传结果</h2>
        </template>
        <p>文件名：{{ uploadedInfo.originalFilenames || '-' }}</p>
        <p>访问地址：{{ uploadedInfo.urls || '-' }}</p>
        <p>头像字段：{{ avatarPath || '-' }}</p>
      </el-card>
    </div>
  </el-card>
</template>

<script setup>
import { ref } from 'vue'
import { upload } from '@/api/http'
import { updateAvatar } from '@/api/system'

const file = ref(null)
const message = ref('')
const uploadedInfo = ref({})
const avatarPath = ref('')
const messageType = ref('success')

function handleFileChange(uploadFile) {
  file.value = uploadFile.raw || null
}

async function handleUpload() {
  if (!file.value) return
  const formData = new FormData()
  formData.append('files', file.value)
  try {
    const response = await upload('/common/uploads', formData)
    uploadedInfo.value = response
    const firstFile = String(response.fileNames || '').split(',')[0]
    if (firstFile) {
      await updateAvatar(firstFile)
      avatarPath.value = firstFile
      messageType.value = 'success'
      message.value = '上传并更新头像成功'
    } else {
      messageType.value = 'warning'
      message.value = '上传成功，但未拿到头像路径'
    }
  } catch (error) {
    messageType.value = 'error'
    message.value = error?.payload?.msg || error?.message || '上传失败'
  }
}
</script>

<style scoped>
.page-card { padding: 24px; }
.page-header h1 { margin: 0; font-size: 28px; }
.page-header p { margin: 8px 0 0; color: var(--text-soft); }
.info-grid { display: grid; grid-template-columns: 1fr 1fr; gap: 16px; margin-top: 18px; }
.info-panel h2 { margin: 0 0 10px; font-size: 16px; }
.info-panel p { margin: 8px 0 0; color: var(--text-soft); }
.avatar-uploader { display: flex; flex-wrap: wrap; gap: 12px; align-items: center; }
.upload-button { margin-left: 8px; }
.message-alert { margin-top: 16px; }
</style>
