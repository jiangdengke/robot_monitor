<template>
  <el-card shadow="never" class="mini-card">
    <template #header><h2>生成配置</h2></template>
    <el-select model-value="crud" style="width: 100%">
      <el-option label="crud" value="crud" />
      <el-option label="tree" value="tree" />
      <el-option label="sub" value="sub" />
    </el-select>
  </el-card>
</template>
<style scoped>
h2{margin:0;font-size:16px;}
</style>
