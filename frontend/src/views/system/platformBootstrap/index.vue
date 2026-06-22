<template>
  <a-card title="平台启动配置" :bordered="false">
    <template #extra>
      <a-space>
        <a-button @click="loadConfigs">刷新</a-button>
        <a-button @click="fillFromBootstrap">使用当前启动配置</a-button>
        <a-button type="primary" :loading="saving" @click="saveConfig">保存配置</a-button>
      </a-space>
    </template>

    <a-alert
      class="mb16"
      type="info"
      show-icon
      message="启用的数据库配置会优先作为 /platform/bootstrap 返回；未配置或解析失败时自动回退模板文件。"
    />

    <a-table
      class="mb16"
      size="small"
      row-key="id"
      :columns="columns"
      :data-source="configs"
      :pagination="false"
      :loading="loading"
      :custom-row="rowEvents"
    />

    <a-form layout="vertical">
      <a-form-item label="配置标识">
        <a-input v-model:value="form.configKey" placeholder="default" />
      </a-form-item>
      <a-form-item label="启用状态">
        <a-switch v-model:checked="form.enabled" checked-children="启用" un-checked-children="停用" />
      </a-form-item>
      <a-form-item label="备注">
        <a-input v-model:value="form.remark" placeholder="说明这个配置用于哪个项目或环境" />
      </a-form-item>
      <a-form-item label="Bootstrap JSON">
        <a-textarea
          v-model:value="form.configJson"
          :rows="18"
          placeholder="粘贴完整 bootstrap JSON"
        />
      </a-form-item>
    </a-form>
  </a-card>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import {
  getPlatformBootstrap,
  listPlatformBootstrapConfigs,
  savePlatformBootstrapConfig
} from '@/api/system'
import { toastError, toastSuccess } from '@/utils/toast'

const columns = [
  { title: 'ID', dataIndex: 'id', width: 80 },
  { title: '配置标识', dataIndex: 'configKey', width: 160 },
  { title: '启用', dataIndex: 'enabled', width: 100, customRender: ({ text }) => (text ? '启用' : '停用') },
  { title: '备注', dataIndex: 'remark' }
]

const configs = ref([])
const loading = ref(false)
const saving = ref(false)
const form = reactive({
  configKey: 'default',
  enabled: true,
  remark: '',
  configJson: ''
})

async function loadConfigs() {
  loading.value = true
  try {
    const response = await listPlatformBootstrapConfigs()
    configs.value = response.rows || []
    if (!form.configJson && configs.value.length) {
      selectConfig(configs.value[0])
    }
  } catch (error) {
    toastError(error?.message || '加载平台配置失败')
  } finally {
    loading.value = false
  }
}

function selectConfig(row) {
  form.configKey = row.configKey || 'default'
  form.enabled = row.enabled !== false
  form.remark = row.remark || ''
  form.configJson = row.configJson || ''
}

function rowEvents(row) {
  return {
    onClick: () => selectConfig(row)
  }
}

async function fillFromBootstrap() {
  try {
    const bootstrap = await getPlatformBootstrap()
    form.configJson = JSON.stringify(bootstrap, null, 2)
    toastSuccess('已填入当前启动配置')
  } catch (error) {
    toastError(error?.message || '读取当前启动配置失败')
  }
}

async function saveConfig() {
  try {
    JSON.parse(form.configJson)
  } catch {
    toastError('Bootstrap JSON 格式不正确')
    return
  }
  saving.value = true
  try {
    await savePlatformBootstrapConfig({ ...form })
    toastSuccess('平台启动配置已保存')
    await loadConfigs()
  } catch (error) {
    toastError(error?.message || '保存平台配置失败')
  } finally {
    saving.value = false
  }
}

onMounted(loadConfigs)
</script>

<style scoped>
.mb16 {
  margin-bottom: 16px;
}
</style>
