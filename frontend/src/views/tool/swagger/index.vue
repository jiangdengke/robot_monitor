<template>
  <el-card shadow="never" class="swagger-page">
    <template #header>
      <el-row justify="space-between" align="middle">
        <el-space direction="vertical" alignment="flex-start">
          <el-text tag="b" size="large">API 文档</el-text>
          <el-text type="info">后端 OpenAPI 文档。</el-text>
        </el-space>
        <el-button tag="a" href="/api/swagger-ui.html" target="_blank" rel="noreferrer" type="primary">
          新窗口打开
        </el-button>
      </el-row>
    </template>
    <iframe class="swagger-frame" src="/api/swagger-ui.html" title="API 文档" />
  </el-card>
</template>

<style scoped>
.swagger-page {
  min-height: calc(100vh - 120px);
}

.swagger-frame {
  width: 100%;
  min-height: calc(100vh - 220px);
  border: 1px solid var(--border-soft);
  border-radius: var(--radius-sm);
  background: #fff;
}
</style>
