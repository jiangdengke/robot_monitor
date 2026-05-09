<template>
  <el-dialog v-model="visible" title="树节点配置" width="720px">
    <el-form :model="draft" label-position="top">
      <el-row :gutter="12">
        <el-col :span="8">
          <el-form-item label="节点编码字段">
            <el-input v-model.trim="draft.treeCode" placeholder="id" />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="父节点字段">
            <el-input v-model.trim="draft.treeParentCode" placeholder="parentId" />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="节点名称字段">
            <el-input v-model.trim="draft.treeName" placeholder="name" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-alert
        type="info"
        show-icon
        :closable="false"
        title="树表生成会按节点编码、父节点和节点名称字段输出 el-tree / el-tree-select 代码。"
      />
      <el-tree class="preview-tree" :data="previewTree" default-expand-all node-key="id" />
    </el-form>
    <template #footer>
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="save">保存配置</el-button>
    </template>
  </el-dialog>
</template>

<script setup>
import { reactive, watch } from 'vue'

const visible = defineModel({ type: Boolean, default: false })
const props = defineProps({
  model: {
    type: Object,
    default: () => ({})
  }
})
const emit = defineEmits(['save'])

const draft = reactive({
  treeCode: 'id',
  treeParentCode: 'parentId',
  treeName: 'name'
})

const previewTree = [
  {
    id: 1,
    label: '贵宾室',
    children: [
      { id: 11, label: '休息区' },
      { id: 12, label: '餐饮区' }
    ]
  }
]

watch(
  () => props.model,
  (value) => {
    draft.treeCode = value?.treeCode || 'id'
    draft.treeParentCode = value?.treeParentCode || 'parentId'
    draft.treeName = value?.treeName || 'name'
  },
  { immediate: true, deep: true }
)

function save() {
  emit('save', { ...draft })
  visible.value = false
}
</script>

<style scoped>
.preview-tree {
  margin-top: 14px;
  padding: 14px;
  border: 1px solid var(--line);
  border-radius: 12px;
  background: var(--panel-alt);
}
</style>
