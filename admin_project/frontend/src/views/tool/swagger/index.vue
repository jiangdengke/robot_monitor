<template>
  <section class="page-card card">
    <header class="page-header">
      <div>
        <h1>Swagger 文档</h1>
        <p>后端已保留 Swagger 入口，可直接跳转访问。</p>
      </div>
      <a class="link-button" href="http://127.0.0.1:7075/api/swagger-ui.html" target="_blank" rel="noreferrer">打开 Swagger</a>
    </header>
  </section>
</template>

<style scoped>
.page-card { padding: 24px; }
.page-header { display: flex; justify-content: space-between; align-items: center; gap: 16px; }
.page-header h1 { margin: 0; font-size: 28px; }
.page-header p { margin: 8px 0 0; color: var(--text-soft); }
.link-button { height: 40px; padding: 0 16px; display: inline-flex; align-items: center; border-radius: 10px; background: var(--brand); color: #fff; }
</style>
