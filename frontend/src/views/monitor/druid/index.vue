<template>
  <el-card shadow="never">
    <template #header>
      <el-row justify="space-between" align="middle">
        <el-space direction="vertical" alignment="flex-start">
          <el-text tag="b" size="large">Druid 监控</el-text>
          <el-text type="info">后端监控地址可直接跳转到 `/api/druid/`。</el-text>
        </el-space>
        <el-button tag="a" href="/api/druid/" target="_blank" rel="noreferrer" type="primary">
          打开 Druid
        </el-button>
      </el-row>
    </template>
  </el-card>
</template>
