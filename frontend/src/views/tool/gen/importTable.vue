<template>
  <el-card shadow="never">
    <template #header>
      <el-row justify="space-between" align="middle">
        <el-text tag="b">导入表</el-text>
        <el-space wrap>
          <el-button @click="loadRows">刷新</el-button>
          <el-button type="primary" :disabled="!selected.length" @click="submit">导入选中</el-button>
        </el-space>
      </el-row>
    </template>

    <el-form inline @submit.prevent="loadRows">
      <el-form-item label="表名">
        <el-input v-model.trim="query.tableName" clearable placeholder="支持模糊查询" />
      </el-form-item>
      <el-form-item label="说明">
        <el-input v-model.trim="query.tableComment" clearable placeholder="支持模糊查询" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" native-type="submit">查询</el-button>
        <el-button @click="reset">重置</el-button>
      </el-form-item>
    </el-form>

    <el-table v-loading="loading" :data="rows" border row-key="tableName" @selection-change="selected = $event">
      <el-table-column type="selection" width="46" />
      <el-table-column prop="tableName" label="表名" min-width="180" />
      <el-table-column prop="tableComment" label="说明" min-width="240" />
      <el-table-column prop="createTime" label="创建时间" width="180" />
      <el-table-column prop="updateTime" label="更新时间" width="180" />
    </el-table>
  </el-card>
</template>

<script setup>
import { ElMessage } from 'element-plus'
import { onMounted, reactive, ref } from 'vue'
import { request } from '@/api/http'

const emit = defineEmits(['imported'])
const rows = ref([])
const selected = ref([])
const loading = ref(false)
const query = reactive({ tableName: '', tableComment: '' })

async function loadRows() {
  loading.value = true
  try {
    const response = await request('/tool/gen/db/list', { query: { pageNum: 1, pageSize: 100, ...query } })
    rows.value = response.rows || []
  } finally {
    loading.value = false
  }
}

function reset() {
  query.tableName = ''
  query.tableComment = ''
  loadRows()
}

async function submit() {
  const tables = selected.value.map((item) => item.tableName).join(',')
  if (!tables) return
  await request('/tool/gen/importTable', {
    method: 'POST',
    query: { tables }
  })
  ElMessage.success('表已导入代码生成')
  emit('imported', selected.value)
  await loadRows()
}

onMounted(loadRows)
</script>
