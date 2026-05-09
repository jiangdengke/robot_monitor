<template>
  <section class="page-card card">
    <header class="page-header">
      <div>
        <h1>缓存详情</h1>
        <p>当前后端未提供 key 明细列表接口，本页保留为缓存明细功能的源码入口。</p>
      </div>
    </header>
    <div class="info-panel">
      可在后端补 `/monitor/cache/keys` 一类接口后，将这里扩展成真实 key 列表和详情页。
    </div>
  </section>
</template>

<style scoped>
.page-card { padding: 24px; }
.page-header h1 { margin: 0; font-size: 28px; }
.page-header p { margin: 8px 0 0; color: var(--text-soft); }
.info-panel { margin-top: 18px; padding: 18px; border: 1px solid var(--line); border-radius: 12px; background: var(--panel-alt); color: var(--text-soft); }
</style>
