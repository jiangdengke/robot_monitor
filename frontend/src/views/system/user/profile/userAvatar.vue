<template>
  <el-card shadow="never">
    <template #header>
      <el-space direction="vertical" alignment="flex-start">
        <el-text tag="b" size="large">头像设置</el-text>
        <el-text type="info">头像上传与资料更新都已经走真实接口。</el-text>
      </el-space>
    </template>
    <el-row :gutter="16">
      <el-col :xs="24" :md="12">
      <el-card shadow="never">
        <template #header>
          <el-text tag="b">上传头像</el-text>
        </template>
        <el-upload
          :auto-upload="false"
          :show-file-list="true"
          :limit="1"
          accept="image/*"
          :on-change="handleFileChange"
        >
          <template #trigger>
            <el-button>选择图片</el-button>
          </template>
          <el-button type="primary" :disabled="!file" @click="handleUpload">上传</el-button>
        </el-upload>
      </el-card>
      </el-col>
      <el-col :xs="24" :md="12">
      <el-card shadow="never">
        <template #header>
          <el-text tag="b">上传结果</el-text>
        </template>
        <el-descriptions :column="1" border>
          <el-descriptions-item label="文件名">{{ uploadedInfo.originalFilenames || '-' }}</el-descriptions-item>
          <el-descriptions-item label="访问地址">{{ uploadedInfo.urls || '-' }}</el-descriptions-item>
          <el-descriptions-item label="头像字段">{{ avatarPath || '-' }}</el-descriptions-item>
        </el-descriptions>
      </el-card>
      </el-col>
    </el-row>
  </el-card>
</template>

<script setup>
import { ref } from 'vue'
import { upload } from '@/api/http'
import { updateAvatar } from '@/api/system'
import { showToast } from '@/utils/toast'

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
    showToast(messageType.value, message.value)
  } catch (error) {
    messageType.value = 'error'
    message.value = error?.payload?.msg || error?.message || '上传失败'
    showToast(messageType.value, message.value)
  }
}
</script>
