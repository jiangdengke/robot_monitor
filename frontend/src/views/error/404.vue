<template>
  <div class="error-shell">
    <section class="error-card card">
      <h1>404</h1>
      <p>页面不存在或路由尚未映射。</p>
    </section>
  </div>
</template>

<style scoped>
.error-shell { min-height: 100vh; display: grid; place-items: center; padding: 24px; }
.error-card { width: min(520px,100%); padding: 32px; text-align: center; }
.error-card h1 { margin: 0; font-size: 56px; }
.error-card p { margin: 12px 0 0; color: var(--text-soft); }
</style>
