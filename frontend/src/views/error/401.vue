<template>
  <div class="error-shell">
    <div class="error-card">
      <div class="error-illustration error-401">
        <div class="badge">401</div>
        <div class="orbit orbit-a" />
        <div class="orbit orbit-b" />
      </div>
      <div class="error-meta">
        <span class="eyebrow-chip">ACCESS DENIED</span>
        <h1>没有访问该资源的权限</h1>
        <p>您当前的账号未被授予访问此页面的权限。请联系管理员开通对应权限，或切换其它账号后重试。</p>
        <div class="error-actions">
          <a-button type="primary" @click="$router.push('/')">回到首页</a-button>
          <a-button @click="$router.push('/login')">切换账号</a-button>
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
  display: grid;
  place-items: center;
  overflow: hidden;
}

.error-401 {
  background:
    radial-gradient(circle at 30% 30%, rgb(255 173 20 / 24%), transparent 55%),
    radial-gradient(circle at 70% 70%, rgb(255 77 79 / 22%), transparent 55%),
    linear-gradient(135deg, #2b1c0e, #4a2410);
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
