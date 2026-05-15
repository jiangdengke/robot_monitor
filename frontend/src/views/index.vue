<template>
  <a-card :bordered="false" class="page-card">
    <template #title>
      <div class="page-title">
        <div class="headline">后台首页</div>
        <div class="desc">当前首页展示本地单体后台状态和恢复进度。</div>
      </div>
    </template>

    <div class="card-grid">
      <a-card title="后端状态" :bordered="false">
        <div class="info-lines">
          <div class="info-line">单体 Spring Boot 后端已具备运行入口。</div>
          <div class="info-line">当前 API 以 `moban_java/API_SUMMARY.md` 为准。</div>
        </div>
      </a-card>
      <a-card title="前端状态" :bordered="false">
        <div class="info-lines">
          <div class="info-line">前端正在迁移到 Ant Design Vue。</div>
          <div class="info-line">接口层已开始对接新的单体后端。</div>
        </div>
      </a-card>
    </div>
  </a-card>
</template>

<style scoped>
.page-title {
  display: grid;
  gap: 4px;
}

.headline {
  font-size: 18px;
  font-weight: 700;
}

.desc {
  color: #6b7280;
  font-size: 13px;
}

.card-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(260px, 1fr));
  gap: 16px;
}

.info-lines {
  display: grid;
  gap: 8px;
}

.info-line {
  color: #4b5563;
  line-height: 1.6;
}
</style>
