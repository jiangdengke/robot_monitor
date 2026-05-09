<template>
  <el-card shadow="never" class="mini-card">
    <template #header>
      <div class="card-header">
        <h2>创建生成表</h2>
        <el-button type="primary" @click="submit">创建</el-button>
      </div>
    </template>

    <el-form ref="formRef" :model="form" :rules="rules" label-position="top">
      <el-row :gutter="12">
        <el-col :span="12">
          <el-form-item label="表名称" prop="tableName">
            <el-input v-model.trim="form.tableName" placeholder="例如 config_robot" @blur="deriveNames" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="表描述" prop="tableComment">
            <el-input v-model.trim="form.tableComment" placeholder="例如 机器人配置" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="实体类名" prop="className">
            <el-input v-model.trim="form.className" placeholder="ConfigRobot" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="业务名" prop="businessName">
            <el-input v-model.trim="form.businessName" placeholder="robot" />
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="字段定义">
            <el-table :data="form.columns" border row-key="columnName">
              <el-table-column label="字段名" min-width="160">
                <template #default="{ row }"><el-input v-model.trim="row.columnName" /></template>
              </el-table-column>
              <el-table-column label="说明" min-width="160">
                <template #default="{ row }"><el-input v-model.trim="row.columnComment" /></template>
              </el-table-column>
              <el-table-column label="Java 类型" width="150">
                <template #default="{ row }">
                  <el-select v-model="row.javaType">
                    <el-option label="String" value="String" />
                    <el-option label="Long" value="Long" />
                    <el-option label="Integer" value="Integer" />
                    <el-option label="LocalDateTime" value="LocalDateTime" />
                  </el-select>
                </template>
              </el-table-column>
              <el-table-column label="操作" width="88">
                <template #default="{ $index }">
                  <el-button link type="danger" :disabled="form.columns.length <= 1" @click="removeColumn($index)">删除</el-button>
                </template>
              </el-table-column>
            </el-table>
            <el-button class="add-btn" @click="addColumn">新增字段</el-button>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
  </el-card>
</template>

<script setup>
import { ElMessage } from 'element-plus'
import { reactive, ref } from 'vue'

const emit = defineEmits(['submit'])

const formRef = ref()
const form = reactive({
  tableName: '',
  tableComment: '',
  className: '',
  businessName: '',
  columns: [
    { columnName: 'id', columnComment: '主键', javaType: 'Long' },
    { columnName: 'name', columnComment: '名称', javaType: 'String' }
  ]
})

const rules = {
  tableName: [{ required: true, message: '请输入表名称', trigger: 'blur' }],
  tableComment: [{ required: true, message: '请输入表描述', trigger: 'blur' }],
  className: [{ required: true, message: '请输入实体类名', trigger: 'blur' }]
}

function deriveNames() {
  if (!form.tableName) return
  form.className ||= form.tableName.split('_').map((item) => item.charAt(0).toUpperCase() + item.slice(1)).join('')
  form.businessName ||= form.tableName.split('_').at(-1)
}

function addColumn() {
  form.columns.push({ columnName: '', columnComment: '', javaType: 'String' })
}

function removeColumn(index) {
  form.columns.splice(index, 1)
}

async function submit() {
  await formRef.value?.validate()
  const invalid = form.columns.some((item) => !item.columnName)
  if (invalid) {
    ElMessage.warning('字段名不能为空')
    return
  }
  emit('submit', JSON.parse(JSON.stringify(form)))
  ElMessage.success('创建表单已提交')
}
</script>

<style scoped>
.card-header { display: flex; align-items: center; justify-content: space-between; gap: 12px; }
h2 { margin: 0; font-size: 16px; }
.add-btn { margin-top: 10px; }
</style>
