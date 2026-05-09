<template>
  <el-dialog v-model="visible" title="选择代码类型" width="620px">
    <el-input v-model.trim="keyword" clearable placeholder="搜索类型，如 String、Long、LocalDateTime" />
    <el-table class="type-table" :data="filteredTypes" border highlight-current-row @row-dblclick="selectType">
      <el-table-column prop="name" label="类型" width="170" />
      <el-table-column prop="javaType" label="Java 类型" width="180" />
      <el-table-column prop="description" label="说明" />
      <el-table-column label="操作" width="90">
        <template #default="{ row }">
          <el-button link type="primary" @click="selectType(row)">选择</el-button>
        </template>
      </el-table-column>
    </el-table>
    <template #footer>
      <el-button @click="visible = false">关闭</el-button>
    </template>
  </el-dialog>
</template>

<script setup>
import { computed, ref } from 'vue'

const visible = defineModel({ type: Boolean, default: false })
const emit = defineEmits(['select'])
const keyword = ref('')

const codeTypes = [
  { name: '字符串', javaType: 'String', description: '文本、编码、描述字段' },
  { name: '长整型', javaType: 'Long', description: '主键、外键、计数字段' },
  { name: '整型', javaType: 'Integer', description: '状态、排序、数量字段' },
  { name: '小数', javaType: 'BigDecimal', description: '金额、价格、精度数值' },
  { name: '布尔', javaType: 'Boolean', description: '开关类字段' },
  { name: '日期时间', javaType: 'LocalDateTime', description: '创建时间、更新时间' },
  { name: '日期', javaType: 'LocalDate', description: '业务日期' }
]

const filteredTypes = computed(() => {
  const value = keyword.value.toLowerCase()
  if (!value) return codeTypes
  return codeTypes.filter((item) => `${item.name} ${item.javaType} ${item.description}`.toLowerCase().includes(value))
})

function selectType(row) {
  emit('select', row)
  visible.value = false
}
</script>

<style scoped>
.type-table { margin-top: 12px; }
</style>
