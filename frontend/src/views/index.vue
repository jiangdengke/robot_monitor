<template>
  <section class="page-card card">
    <header class="page-header">
      <div>
        <h1>后台首页</h1>
        <p>当前首页展示本地单体后台状态和恢复进度。</p>
      </div>
    </header>
    <div class="info-grid">
      <article class="info-panel">
        <h2>后端状态</h2>
        <p>单体 Spring Boot 后端已本地启动。</p>
        <p>接口前缀：`/api`</p>
      </article>
      <article class="info-panel">
        <h2>前端状态</h2>
        <p>页面已从静态包重建为可维护的 `.vue` 源码，并接入本地后台接口。</p>
      </article>
    </div>
  </section>
</template>

<style scoped>
.page-card { padding: 24px; }
.page-header h1 { margin: 0; font-size: 28px; }
.page-header p { margin: 8px 0 0; color: var(--text-soft); }
.info-grid { display: grid; grid-template-columns: repeat(2, minmax(0, 1fr)); gap: 16px; margin-top: 18px; }
.info-panel { padding: 18px; border-radius: 12px; border: 1px solid var(--line); background: var(--panel-alt); }
.info-panel h2 { margin: 0 0 10px; font-size: 16px; }
.info-panel p { margin: 8px 0 0; color: var(--text-soft); }
</style>
