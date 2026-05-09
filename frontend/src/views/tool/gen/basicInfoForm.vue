<template>
  <el-card shadow="never" class="mini-card">
    <template #header>
      <div class="card-header">
        <h2>基础信息</h2>
        <el-tag v-if="model.tableName" type="info">{{ model.tableName }}</el-tag>
      </div>
    </template>

    <el-form :model="model" label-position="top">
      <el-row :gutter="12">
        <el-col :span="12">
          <el-form-item label="表名称" required>
            <el-input v-model.trim="model.tableName" :disabled="readonly" placeholder="例如 config_robot" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="表描述">
            <el-input v-model.trim="model.tableComment" placeholder="例如 机器人配置" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="实体类名称" required>
            <el-input v-model.trim="model.className" placeholder="例如 ConfigRobot" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="功能名称">
            <el-input v-model.trim="model.functionName" placeholder="例如 机器人配置" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="作者">
            <el-input v-model.trim="model.functionAuthor" placeholder="system" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="备注">
            <el-input v-model.trim="model.remark" placeholder="生成说明" />
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
  </el-card>
</template>

<script setup>
defineProps({
  model: {
    type: Object,
    default: () => ({})
  },
  readonly: {
    type: Boolean,
    default: false
  }
})
</script>

<style scoped>
.card-header { display: flex; align-items: center; justify-content: space-between; gap: 12px; }
h2 { margin: 0; font-size: 16px; }
</style>
