<template>
  <el-card class="page-card cache-page">
    <template #header>
      <div class="page-header">
        <div>
          <h1>缓存监控</h1>
          <p>Redis 摘要、缓存名称、缓存键和值内容联动管理。</p>
        </div>
        <div class="header-actions">
          <el-button @click="loadAll">刷新</el-button>
          <el-button type="danger" @click="clearAll">清理全部</el-button>
        </div>
      </div>
    </template>

    <div class="summary-grid">
      <el-card shadow="never" class="info-panel">
        <template #header><h2>基础信息</h2></template>
        <p>Key 数量：{{ cacheInfo.dbSize || '-' }}</p>
        <p>Redis 版本：{{ cacheInfo.info?.redis_version || '-' }}</p>
        <p>运行模式：{{ cacheInfo.info?.redis_mode || '-' }}</p>
      </el-card>
      <el-card shadow="never" class="info-panel">
        <template #header><h2>命令统计</h2></template>
        <p v-for="item in cacheInfo.commandStats || []" :key="item.name">
          {{ item.name }}：{{ item.value }}
        </p>
      </el-card>
    </div>

    <el-row class="cache-grid" :gutter="16">
      <el-col :lg="8" :md="24">
        <el-card shadow="never" class="cache-panel">
          <template #header>
            <div class="panel-header">
              <span>缓存列表</span>
              <el-button link type="primary" @click="loadNames">刷新</el-button>
            </div>
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
        <el-card shadow="never" class="cache-panel">
          <template #header>
            <div class="panel-header">
              <span>键名列表</span>
              <el-button link type="primary" :disabled="!selectedName" @click="loadKeys(selectedName)">刷新</el-button>
            </div>
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
        <el-card shadow="never" class="cache-panel">
          <template #header>
            <div class="panel-header">
              <span>缓存内容</span>
              <el-button link type="primary" :disabled="!selectedKey" @click="selectKey(selectedKey)">刷新</el-button>
            </div>
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

    <el-alert v-if="errorMessage" class="message-alert" :title="errorMessage" type="error" :closable="false" />
    <el-alert v-if="successMessage" class="message-alert" :title="successMessage" type="success" :closable="false" />
  </el-card>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import { ElMessageBox } from 'element-plus'
import { clearCacheAll, clearCacheKey, clearCacheName, getCacheInfo, getCacheValue, listCacheKeys, listCacheNames } from '@/api/system'

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
  } finally {
    keysLoading.value = false
  }
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
  }
}

async function clearName(row) {
  try {
    await ElMessageBox.confirm(`确认清理缓存名称"${row.cacheName}"？`, '清理确认', { type: 'warning' })
    await clearCacheName(row.cacheName)
    successMessage.value = '缓存名称已清理'
    await loadKeys(row.cacheName)
  } catch (error) {
    if (error !== 'cancel') {
      errorMessage.value = error?.payload?.msg || error?.message || '缓存名称清理失败'
    }
  }
}

async function clearKey(row) {
  const cacheKey = row?.cacheKey || row
  try {
    await ElMessageBox.confirm(`确认清理缓存键"${cacheKey}"？`, '清理确认', { type: 'warning' })
    await clearCacheKey(cacheKey)
    successMessage.value = '缓存键已清理'
    await loadKeys(selectedName.value)
  } catch (error) {
    if (error !== 'cancel') {
      errorMessage.value = error?.payload?.msg || error?.message || '缓存键清理失败'
    }
  }
}

async function clearAll() {
  try {
    await ElMessageBox.confirm('确认清理全部缓存？', '清理确认', { type: 'warning' })
    await clearCacheAll()
    successMessage.value = '全部缓存已清理'
    cacheKeys.value = []
    cacheValue.value = {}
    await loadInfo()
  } catch (error) {
    if (error !== 'cancel') {
      errorMessage.value = error?.payload?.msg || error?.message || '全部缓存清理失败'
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
</script>

<style scoped>
.page-card { padding: 24px; }
.page-header { display: flex; justify-content: space-between; align-items: center; gap: 16px; margin-bottom: 18px; }
.page-header h1 { margin: 0; font-size: 28px; }
.page-header p { margin: 8px 0 0; color: var(--text-soft); }
.header-actions,
.panel-header { display: flex; align-items: center; justify-content: space-between; gap: 10px; }
.summary-grid { display: grid; grid-template-columns: repeat(2, minmax(0, 1fr)); gap: 16px; }
.info-panel h2 { margin: 0; font-size: 16px; }
.info-panel p { margin: 8px 0 0; color: var(--text-soft); }
.cache-grid { margin-top: 16px; }
.cache-panel { min-height: 500px; }
.message-alert { margin-top: 16px; }
@media (max-width: 960px) {
  .summary-grid { grid-template-columns: 1fr; }
  .cache-panel { margin-bottom: 16px; }
}
</style>
