<template>
  <div class="error-shell">
    <div class="error-card">
      <div class="error-illustration">
        <div class="badge badge-404">404</div>
        <div class="orbit orbit-a" />
        <div class="orbit orbit-b" />
      </div>
      <div class="error-meta">
        <span class="eyebrow-chip">PAGE NOT FOUND</span>
        <h1>这条路径暂时找不到对应内容</h1>
        <p>该地址可能已被迁移、删除，或者路由还在配置中。请检查链接是否正确，或返回首页继续操作。</p>
        <div class="error-actions">
          <a-button type="primary" @click="$router.push('/')">回到首页</a-button>
          <a-button @click="$router.back()">返回上一页</a-button>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.error-shell {
  min-height: calc(100vh - 60px - 53px);
  display: grid;
  place-items: center;
  padding: 24px;
}

.error-card {
  width: min(100%, 880px);
  display: grid;
  grid-template-columns: 280px 1fr;
  gap: 36px;
  background: var(--surface-card);
  border-radius: var(--radius-xl);
  padding: 44px;
  border: 1px solid var(--border-soft);
  box-shadow: var(--shadow-md);
}

.error-illustration {
  position: relative;
  height: 240px;
  border-radius: var(--radius-lg);
  background:
    radial-gradient(circle at 30% 30%, rgb(47 84 235 / 22%), transparent 60%),
    radial-gradient(circle at 70% 70%, rgb(19 194 194 / 22%), transparent 55%),
    linear-gradient(135deg, #0f1d3a, #1d3b7a);
  display: grid;
  place-items: center;
  overflow: hidden;
}

.badge {
  font-size: 88px;
  font-weight: 800;
  color: #fff;
  letter-spacing: 0.04em;
  text-shadow: 0 8px 24px rgb(0 0 0 / 30%);
  z-index: 2;
}

.orbit {
  position: absolute;
  border-radius: 50%;
  border: 1px solid rgb(255 255 255 / 14%);
}

.orbit-a {
  width: 220px;
  height: 220px;
}

.orbit-b {
  width: 320px;
  height: 320px;
  border-style: dashed;
  border-color: rgb(255 255 255 / 12%);
}

.error-meta {
  display: grid;
  align-content: center;
  gap: 14px;
}

.error-meta h1 {
  margin: 0;
  font-size: 26px;
  font-weight: 700;
  color: var(--text-strong);
  line-height: 1.3;
}

.error-meta p {
  margin: 0;
  color: var(--text-muted);
  line-height: 1.7;
}

.error-actions {
  display: flex;
  gap: 12px;
  margin-top: 8px;
}

@media (max-width: 720px) {
  .error-card {
    grid-template-columns: 1fr;
    padding: 28px;
  }

  .error-illustration {
    height: 180px;
  }

  .badge {
    font-size: 64px;
  }
}
</style>
