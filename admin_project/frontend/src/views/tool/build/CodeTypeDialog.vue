<template>
  <el-card shadow="never" class="mini-card">
    <template #header><h2>CodeTypeDialog</h2></template>
    <el-select model-value="字符串" style="width: 100%">
      <el-option label="字符串" value="字符串" />
      <el-option label="数字" value="数字" />
      <el-option label="枚举" value="枚举" />
    </el-select>
  </el-card>
</template>
<style scoped>
h2{margin:0;font-size:16px;}
</style>
