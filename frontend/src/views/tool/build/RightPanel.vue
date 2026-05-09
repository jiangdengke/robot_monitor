<template>
  <el-card shadow="never" class="mini-card">
    <template #header>
      <div class="card-header">
        <h2>属性面板</h2>
        <el-button v-if="model" type="danger" link @click="$emit('remove')">删除字段</el-button>
      </div>
    </template>

    <el-form v-if="model" label-position="top">
      <el-row :gutter="12">
        <el-col :span="12">
          <el-form-item label="字段标签">
            <el-input v-model.trim="model.label" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="字段名">
            <el-input v-model.trim="model.prop" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="组件类型">
            <el-select v-model="model.type" @change="normalizeByType">
              <el-option v-for="item in palette" :key="item.type" :label="item.label" :value="item.type" />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="栅格宽度">
            <el-slider v-model="model.span" :min="6" :max="24" :step="2" />
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="提示文案">
            <el-input v-model.trim="model.placeholder" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="默认值">
            <el-input v-if="model.type !== 'switch'" v-model="model.defaultValue" />
            <el-switch v-else v-model="model.defaultValue" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="字段图标">
            <el-input v-model.trim="model.icon" placeholder="选择或输入 icon">
              <template #append>
                <el-button @click="$emit('pick-icon')">选择</el-button>
              </template>
            </el-input>
          </el-form-item>
        </el-col>
        <el-col v-if="hasOptions" :span="24">
          <el-form-item label="选项，逗号分隔">
            <el-input v-model.trim="model.optionsText" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="字典类型">
            <el-input v-model.trim="model.dictType" placeholder="sys_normal_disable" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="代码类型">
            <el-input v-model.trim="model.codeType" placeholder="String">
              <template #append>
                <el-button @click="$emit('pick-code-type')">选择</el-button>
              </template>
            </el-input>
          </el-form-item>
        </el-col>
        <el-col v-if="model.type === 'number'" :span="12">
          <el-form-item label="小数位">
            <el-input-number v-model="model.precision" :min="0" :max="4" />
          </el-form-item>
        </el-col>
        <el-col v-if="model.type === 'textarea'" :span="12">
          <el-form-item label="行数">
            <el-input-number v-model="model.rows" :min="2" :max="8" />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="必填"><el-switch v-model="model.required" /></el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="禁用"><el-switch v-model="model.disabled" /></el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="隐藏"><el-switch v-model="model.hidden" /></el-form-item>
        </el-col>
      </el-row>
      <div class="action-row">
        <el-button @click="$emit('duplicate')">复制字段</el-button>
        <el-button @click="$emit('pick-tree-node')">树节点配置</el-button>
      </div>
    </el-form>
    <el-empty v-else description="选择字段后编辑属性" />
  </el-card>
</template>

<script setup>
import { computed } from 'vue'

const props = defineProps({
  model: {
    type: Object,
    default: null
  },
  palette: {
    type: Array,
    default: () => []
  }
})

defineEmits(['remove', 'duplicate', 'pick-icon', 'pick-code-type', 'pick-tree-node'])

const hasOptions = computed(() => ['select', 'radio', 'checkbox'].includes(props.model?.type))

function normalizeByType() {
  if (!props.model) return
  if (hasOptions.value && !props.model.optionsText) {
    props.model.optionsText = '正常,停用'
  }
  if (props.model.type === 'textarea' && !props.model.rows) {
    props.model.rows = 3
  }
  if (props.model.type === 'number' && props.model.precision == null) {
    props.model.precision = 0
  }
}
</script>

<style scoped>
.card-header,
.action-row { display: flex; align-items: center; justify-content: space-between; gap: 8px; }
h2 { margin: 0; font-size: 16px; }
</style>
