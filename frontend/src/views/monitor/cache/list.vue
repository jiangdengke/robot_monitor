<template>
  <el-card class="page-card">
    <template #header>
      <div class="page-header">
        <div>
          <h1>缓存详情</h1>
          <p>按缓存名称查看 Redis key、缓存值和清理操作，和缓存监控主页面共用真实接口。</p>
        </div>
        <div class="header-actions">
          <el-button @click="loadNames">刷新缓存名</el-button>
          <el-button type="danger" plain @click="clearAll">清空全部</el-button>
        </div>
      </div>
    </template>

    <div class="cache-layout">
      <el-card shadow="never">
        <template #header>
          <div class="panel-header">
            <h2>缓存名称</h2>
            <el-tag>{{ cacheNames.length }}</el-tag>
          </div>
        </template>
        <el-input v-model.trim="nameFilter" clearable placeholder="过滤缓存名" />
        <el-table
          class="compact-table"
          :data="filteredNames"
          highlight-current-row
          row-key="cacheName"
          @current-change="selectName"
        >
          <el-table-column prop="cacheName" label="名称" min-width="150" />
          <el-table-column prop="remark" label="说明" min-width="140" />
          <el-table-column label="操作" width="82">
            <template #default="{ row }">
              <el-button link type="danger" @click.stop="clearName(row.cacheName)">清理</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-card>

      <el-card shadow="never">
        <template #header>
          <div class="panel-header">
            <h2>缓存键</h2>
            <el-tag type="success">{{ cacheKeys.length }}</el-tag>
          </div>
        </template>
        <el-input v-model.trim="keyFilter" clearable placeholder="过滤 key" />
        <el-table
          class="compact-table"
          :data="filteredKeys"
          highlight-current-row
          row-key="cacheKey"
          @current-change="selectKey"
        >
          <el-table-column prop="cacheKey" label="Key" min-width="240" show-overflow-tooltip />
          <el-table-column label="操作" width="82">
            <template #default="{ row }">
              <el-button link type="danger" @click.stop="clearKey(row.cacheKey)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-card>

      <el-card shadow="never">
        <template #header>
          <div class="panel-header">
            <h2>缓存值</h2>
            <el-button size="small" :disabled="!currentKey" @click="reloadValue">刷新值</el-button>
          </div>
        </template>
        <el-descriptions v-if="cacheValue" :column="1" border>
          <el-descriptions-item label="缓存名称">{{ cacheValue.cacheName }}</el-descriptions-item>
          <el-descriptions-item label="缓存键">{{ cacheValue.cacheKey }}</el-descriptions-item>
        </el-descriptions>
        <pre v-if="cacheValue" class="value-box">{{ formatValue(cacheValue.cacheValue) }}</pre>
        <el-empty v-else description="选择缓存 key 后查看缓存值" />
      </el-card>
    </div>
  </el-card>
</template>

<script setup>
import { ElMessage, ElMessageBox } from 'element-plus'
import { computed, onMounted, ref } from 'vue'
import {
  clearCacheAll,
  clearCacheKey,
  clearCacheName,
  getCacheValue,
  listCacheKeys,
  listCacheNames
} from '@/api/system'

const cacheNames = ref([])
const cacheKeys = ref([])
const cacheValue = ref(null)
const currentName = ref('')
const currentKey = ref('')
const nameFilter = ref('')
const keyFilter = ref('')

const filteredNames = computed(() => {
  const keyword = nameFilter.value.toLowerCase()
  if (!keyword) return cacheNames.value
  return cacheNames.value.filter((item) => `${item.cacheName} ${item.remark || ''}`.toLowerCase().includes(keyword))
})

const filteredKeys = computed(() => {
  const keyword = keyFilter.value.toLowerCase()
  if (!keyword) return cacheKeys.value
  return cacheKeys.value.filter((item) => item.cacheKey.toLowerCase().includes(keyword))
})

async function loadNames() {
  const response = await listCacheNames()
  cacheNames.value = response.data || []
  if (!currentName.value && cacheNames.value.length) {
    await selectName(cacheNames.value[0])
  }
}

async function selectName(row) {
  if (!row?.cacheName) return
  currentName.value = row.cacheName
  currentKey.value = ''
  cacheValue.value = null
  const response = await listCacheKeys(row.cacheName)
  cacheKeys.value = (response.data || []).map((item) => (typeof item === 'string' ? { cacheKey: item } : item))
}

async function selectKey(row) {
  if (!row?.cacheKey || !currentName.value) return
  currentKey.value = row.cacheKey
  await reloadValue()
}

async function reloadValue() {
  if (!currentName.value || !currentKey.value) return
  const response = await getCacheValue(currentName.value, currentKey.value)
  cacheValue.value = response.data || response
}

async function clearName(cacheName) {
  await ElMessageBox.confirm(`确认清理缓存 "${cacheName}" 下的全部 key？`, '清理确认', { type: 'warning' })
  await clearCacheName(cacheName)
  ElMessage.success('缓存名称已清理')
  if (currentName.value === cacheName) {
    cacheKeys.value = []
    cacheValue.value = null
    currentKey.value = ''
  }
  await loadNames()
}

async function clearKey(cacheKey) {
  await ElMessageBox.confirm(`确认删除缓存 key "${cacheKey}"？`, '删除确认', { type: 'warning' })
  await clearCacheKey(cacheKey)
  ElMessage.success('缓存 key 已删除')
  cacheKeys.value = cacheKeys.value.filter((item) => item.cacheKey !== cacheKey)
  if (currentKey.value === cacheKey) {
    currentKey.value = ''
    cacheValue.value = null
  }
}

async function clearAll() {
  await ElMessageBox.confirm('确认清空全部缓存？', '清理确认', { type: 'warning' })
  await clearCacheAll()
  ElMessage.success('全部缓存已清理')
  cacheKeys.value = []
  cacheValue.value = null
  currentName.value = ''
  currentKey.value = ''
  await loadNames()
}

function formatValue(value) {
  if (typeof value !== 'string') return JSON.stringify(value, null, 2)
  try {
    return JSON.stringify(JSON.parse(value), null, 2)
  } catch {
    return value
  }
}

onMounted(loadNames)
</script>

<style scoped>
.page-card { padding: 24px; }
.page-header,
.panel-header { display: flex; justify-content: space-between; align-items: center; gap: 16px; }
.page-header h1 { margin: 0; font-size: 28px; }
.page-header p { margin: 8px 0 0; color: var(--text-soft); }
.header-actions { display: flex; flex-wrap: wrap; gap: 8px; }
.cache-layout { display: grid; grid-template-columns: .9fr 1.15fr 1.25fr; gap: 16px; margin-top: 18px; }
h2 { margin: 0; font-size: 16px; }
.compact-table { margin-top: 12px; }
.value-box {
  margin: 16px 0 0;
  min-height: 320px;
  max-height: 560px;
  overflow: auto;
  padding: 16px;
  border: 1px solid var(--line);
  border-radius: 12px;
  background: #101828;
  color: #d1e7ff;
  white-space: pre-wrap;
}
@media (max-width: 1180px) {
  .cache-layout { grid-template-columns: 1fr; }
}
</style>
