<template>
  <el-card shadow="never">
    <template #header>
      <el-row justify="space-between" align="middle">
        <el-space direction="vertical" alignment="flex-start">
          <el-text tag="b" size="large">缓存监控</el-text>
          <el-text type="info">Redis 摘要、缓存名称、缓存键和值内容联动管理。</el-text>
        </el-space>
        <el-space wrap>
          <el-button @click="loadAll">刷新</el-button>
          <el-button type="danger" @click="clearAll">清理全部</el-button>
        </el-space>
      </el-row>
    </template>

    <el-row :gutter="16">
      <el-col :xs="24" :md="12">
        <el-card shadow="never">
          <template #header><el-text tag="b">基础信息</el-text></template>
          <el-descriptions :column="1" border>
            <el-descriptions-item label="Key 数量">{{ cacheInfo.dbSize || '-' }}</el-descriptions-item>
            <el-descriptions-item label="Redis 版本">{{ cacheInfo.info?.redis_version || '-' }}</el-descriptions-item>
            <el-descriptions-item label="运行模式">{{ cacheInfo.info?.redis_mode || '-' }}</el-descriptions-item>
          </el-descriptions>
        </el-card>
      </el-col>
      <el-col :xs="24" :md="12">
        <el-card shadow="never">
          <template #header><el-text tag="b">命令统计</el-text></template>
          <el-descriptions :column="1" border>
            <el-descriptions-item v-for="item in cacheInfo.commandStats || []" :key="item.name" :label="item.name">
              {{ item.value }}
            </el-descriptions-item>
          </el-descriptions>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="16">
      <el-col :lg="8" :md="24">
        <el-card shadow="never">
          <template #header>
            <el-row justify="space-between" align="middle">
              <el-text tag="b">缓存列表</el-text>
              <el-button link type="primary" @click="loadNames">刷新</el-button>
            </el-row>
          </template>
          <el-table
            v-loading="namesLoading"
            :data="cacheNames"
            height="420"
            highlight-current-row
            border
            @row-click="selectName"
          >
            <el-table-column type="index" label="序号" width="70" />
            <el-table-column prop="cacheName" label="缓存名称" min-width="150" :formatter="formatCacheName" show-overflow-tooltip />
            <el-table-column prop="remark" label="备注" min-width="140" show-overflow-tooltip />
            <el-table-column label="操作" width="80">
              <template #default="{ row }">
                <el-button link type="danger" @click.stop="clearName(row)">清理</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-col>

      <el-col :lg="8" :md="24">
        <el-card shadow="never">
          <template #header>
            <el-row justify="space-between" align="middle">
              <el-text tag="b">键名列表</el-text>
              <el-button link type="primary" @click="refreshSelectedName">刷新</el-button>
            </el-row>
          </template>
          <el-table
            v-loading="keysLoading"
            :data="cacheKeys"
            height="420"
            highlight-current-row
            border
            @row-click="selectKey"
          >
            <el-table-column type="index" label="序号" width="70" />
            <el-table-column label="缓存键名" min-width="220" :formatter="formatCacheKey" show-overflow-tooltip />
            <el-table-column label="操作" width="80">
              <template #default="{ row }">
                <el-button link type="danger" @click.stop="clearKey(row)">清理</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-col>

      <el-col :lg="8" :md="24">
        <el-card shadow="never">
          <template #header>
            <el-row justify="space-between" align="middle">
              <el-text tag="b">缓存内容</el-text>
              <el-button link type="primary" @click="refreshSelectedKey">刷新</el-button>
            </el-row>
          </template>
          <el-form label-position="top" :model="cacheValue">
            <el-form-item label="缓存名称">
              <el-input v-model="cacheValue.cacheName" readonly />
            </el-form-item>
            <el-form-item label="缓存键名">
              <el-input v-model="cacheValue.cacheKey" readonly />
            </el-form-item>
            <el-form-item label="缓存内容">
              <el-input v-model="cacheValue.cacheValue" type="textarea" :rows="10" readonly />
            </el-form-item>
          </el-form>
        </el-card>
      </el-col>
    </el-row>

  </el-card>
</template>

<script setup>
import { Modal } from 'ant-design-vue'
import { onMounted, ref } from 'vue'
import { clearCacheAll, clearCacheKey, clearCacheName, getCacheInfo, getCacheValue, listCacheKeys, listCacheNames } from '@/api/system'
import { toastError, toastSuccess, toastWarning } from '@/utils/toast'

const cacheInfo = ref({})
const cacheNames = ref([])
const cacheKeys = ref([])
const cacheValue = ref({})
const selectedName = ref('')
const selectedKey = ref('')
const namesLoading = ref(false)
const keysLoading = ref(false)
const errorMessage = ref('')
const successMessage = ref('')

async function loadInfo() {
  errorMessage.value = ''
  try {
    const response = await getCacheInfo()
    cacheInfo.value = response.data || {}
  } catch (error) {
    errorMessage.value = error?.payload?.msg || error?.message || '加载失败'
    toastError(errorMessage.value)
  }
}

async function loadNames() {
  namesLoading.value = true
  errorMessage.value = ''
  try {
    const response = await listCacheNames()
    cacheNames.value = response.data || []
  } catch (error) {
    errorMessage.value = error?.payload?.msg || error?.message || '缓存名称加载失败'
    toastError(errorMessage.value)
  } finally {
    namesLoading.value = false
  }
}

async function loadKeys(cacheName) {
  if (!cacheName) {
    return
  }
  keysLoading.value = true
  errorMessage.value = ''
  cacheValue.value = {}
  selectedKey.value = ''
  try {
    const response = await listCacheKeys(cacheName)
    cacheKeys.value = response.data || []
  } catch (error) {
    errorMessage.value = error?.payload?.msg || error?.message || '缓存键名加载失败'
    toastError(errorMessage.value)
  } finally {
    keysLoading.value = false
  }
}

async function refreshSelectedName() {
  if (!selectedName.value) {
    toastWarning('请先选择缓存名称')
    return
  }
  await loadKeys(selectedName.value)
}

async function refreshSelectedKey() {
  if (!selectedKey.value) {
    toastWarning('请先选择缓存键名')
    return
  }
  await selectKey(selectedKey.value)
}

async function loadAll() {
  await Promise.all([loadInfo(), loadNames()])
  if (selectedName.value) {
    await loadKeys(selectedName.value)
  }
}

function selectName(row) {
  selectedName.value = row.cacheName
  loadKeys(row.cacheName)
}

async function selectKey(row) {
  const cacheKey = typeof row === 'string' ? row : row?.cacheKey || row
  if (!selectedName.value || !cacheKey) {
    return
  }
  selectedKey.value = cacheKey
  errorMessage.value = ''
  try {
    const response = await getCacheValue(selectedName.value, cacheKey)
    cacheValue.value = response.data || {}
  } catch (error) {
    errorMessage.value = error?.payload?.msg || error?.message || '缓存内容加载失败'
    toastError(errorMessage.value)
  }
}

async function clearName(row) {
  try {
    await confirmDialog('清理确认', `确认清理缓存名称"${row.cacheName}"？`)
    await clearCacheName(row.cacheName)
    successMessage.value = '缓存名称已清理'
    toastSuccess(successMessage.value)
    await loadKeys(row.cacheName)
  } catch (error) {
    if (error !== 'cancel') {
      errorMessage.value = error?.payload?.msg || error?.message || '缓存名称清理失败'
      toastError(errorMessage.value)
    }
  }
}

async function clearKey(row) {
  const cacheKey = row?.cacheKey || row
  try {
    await confirmDialog('清理确认', `确认清理缓存键"${cacheKey}"？`)
    await clearCacheKey(cacheKey)
    successMessage.value = '缓存键已清理'
    toastSuccess(successMessage.value)
    await loadKeys(selectedName.value)
  } catch (error) {
    if (error !== 'cancel') {
      errorMessage.value = error?.payload?.msg || error?.message || '缓存键清理失败'
      toastError(errorMessage.value)
    }
  }
}

async function clearAll() {
  try {
    await confirmDialog('清理确认', '确认清理全部缓存？')
    await clearCacheAll()
    successMessage.value = '全部缓存已清理'
    toastSuccess(successMessage.value)
    cacheKeys.value = []
    cacheValue.value = {}
    await loadInfo()
  } catch (error) {
    if (error !== 'cancel') {
      errorMessage.value = error?.payload?.msg || error?.message || '全部缓存清理失败'
      toastError(errorMessage.value)
    }
  }
}

function formatCacheName(row) {
  return String(row.cacheName || '').replace(':', '')
}

function formatCacheKey(row) {
  const value = row?.cacheKey || row || ''
  return selectedName.value ? String(value).replace(selectedName.value, '') : value
}

onMounted(loadAll)

function confirmDialog(title, content) {
  return new Promise((resolve, reject) => {
    Modal.confirm({
      title,
      content,
      onOk: resolve,
      onCancel: () => reject('cancel')
    })
  })
}
</script>
