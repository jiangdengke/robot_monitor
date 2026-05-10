<template>
  <el-card shadow="never">
    <template #header>
      <el-space direction="vertical" alignment="flex-start">
        <el-text tag="b" size="large">路由重定向</el-text>
        <el-text type="info">当前单体后台前端已内置基础路由守卫，后续如需标签页重定向可在本页扩展。</el-text>
      </el-space>
    </template>
    <el-alert title="该页面已作为真实源码功能页保留。" type="info" :closable="false" />
  </el-card>
</template>
