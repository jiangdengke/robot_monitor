<template>
  <section class="mini-card">
    <h2>IconsDialog</h2>
    <div class="icon-row">
      <span>user</span>
      <span>table</span>
      <span>tool</span>
    </div>
  </section>
</template>
<style scoped>
.mini-card{padding:16px;border:1px solid var(--line);border-radius:12px;background:var(--panel-alt);}
h2{margin:0 0 10px;font-size:16px;}
.icon-row{display:flex;gap:8px;flex-wrap:wrap;color:var(--text-soft);}
.icon-row span{padding:8px 10px;border:1px solid var(--line);border-radius:10px;background:#fff;}
</style>
