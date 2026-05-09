<template>
  <section class="page-card card">
    <header class="page-header">
      <div>
        <h1>Druid 监控</h1>
        <p>后端监控地址可直接跳转到 `/api/druid/`。</p>
      </div>
      <a class="link-button" href="http://127.0.0.1:7075/api/druid/" target="_blank" rel="noreferrer">打开 Druid</a>
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
