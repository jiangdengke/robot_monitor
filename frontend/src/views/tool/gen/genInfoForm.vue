<template>
  <el-card shadow="never" class="mini-card">
    <template #header><h2>生成信息</h2></template>

    <el-form :model="model" label-position="top">
      <el-row :gutter="12">
        <el-col :span="12">
          <el-form-item label="包路径">
            <el-input v-model.trim="model.packageName" placeholder="com.robotmonitor.system" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="模块名">
            <el-input v-model.trim="model.moduleName" placeholder="system" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="业务名">
            <el-input v-model.trim="model.businessName" placeholder="user" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="功能名">
            <el-input v-model.trim="model.functionName" placeholder="用户管理" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="生成模板">
            <el-select v-model="model.tplCategory" style="width: 100%">
              <el-option label="单表 CRUD" value="crud" />
              <el-option label="树表" value="tree" />
              <el-option label="主子表" value="sub" />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="生成方式">
            <el-radio-group v-model="model.genType">
              <el-radio-button label="0" value="0">zip 下载</el-radio-button>
              <el-radio-button label="1" value="1">自定义路径</el-radio-button>
            </el-radio-group>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="生成路径">
            <el-input v-model.trim="model.genPath" :disabled="model.genType !== '1'" placeholder="/" />
          </el-form-item>
        </el-col>
        <el-col v-if="model.tplCategory === 'tree'" :span="12">
          <el-form-item label="树编码字段">
            <el-input v-model.trim="model.treeCode" placeholder="id" />
          </el-form-item>
        </el-col>
        <el-col v-if="model.tplCategory === 'tree'" :span="12">
          <el-form-item label="树父编码字段">
            <el-input v-model.trim="model.treeParentCode" placeholder="parent_id" />
          </el-form-item>
        </el-col>
        <el-col v-if="model.tplCategory === 'tree'" :span="12">
          <el-form-item label="树名称字段">
            <el-input v-model.trim="model.treeName" placeholder="name" />
          </el-form-item>
        </el-col>
        <el-col v-if="model.tplCategory === 'sub'" :span="12">
          <el-form-item label="关联子表">
            <el-input v-model.trim="model.subTableName" placeholder="子表名称" />
          </el-form-item>
        </el-col>
        <el-col v-if="model.tplCategory === 'sub'" :span="12">
          <el-form-item label="子表外键">
            <el-input v-model.trim="model.subTableFkName" placeholder="外键字段" />
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
  }
})
</script>

<style scoped>
h2 { margin: 0; font-size: 16px; }
</style>
