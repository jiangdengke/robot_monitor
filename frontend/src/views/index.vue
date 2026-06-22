<template>
  <div class="welcome-page">
    <section class="hero-card">
      <div class="hero-noise" />
      <div class="hero-content">
        <div class="hero-meta">
          <span class="eyebrow-chip">{{ heroEyebrow }}</span>
          <h1>{{ greeting }}，{{ displayName }}</h1>
          <p>{{ heroDescription }}</p>
          <div class="hero-actions">
            <a-button type="primary" size="large" @click="$router.push('/system/user')">
              <template #icon><TeamOutlined /></template>
              用户中心
            </a-button>
            <a-button v-if="isPlatformModuleEnabled('digitalTwin')" size="large" ghost @click="$router.push('/digitalTwin')">
              <template #icon><DeploymentUnitOutlined /></template>
              数字孪生看板
            </a-button>
          </div>
        </div>
        <div class="hero-stats">
          <div class="hero-stat" v-for="item in heroStats" :key="item.label">
            <div class="hero-stat-icon" :style="{ background: item.bg }">
              <component :is="item.icon" />
            </div>
            <div class="hero-stat-body">
              <div class="hero-stat-label">{{ item.label }}</div>
              <div class="hero-stat-value">{{ item.value }}</div>
              <div class="hero-stat-note">{{ item.note }}</div>
            </div>
          </div>
        </div>
      </div>
    </section>

    <section class="grid-row">
      <a-card class="info-card has-hover" :bordered="false">
        <template #title>
          <div class="card-title">
            <CloudServerOutlined class="card-title-icon" />
            <span>后端状态</span>
            <a-tag color="green" class="status-tag">运行中</a-tag>
          </div>
        </template>
        <ul class="bullet-list">
          <li>
            <CheckCircleFilled class="bullet-icon ok" />
            <div>
              <div class="bullet-title">单体 Spring Boot 后端</div>
              <div class="bullet-desc">已具备运行入口，端口 <code>8080</code></div>
            </div>
          </li>
          <li>
            <CheckCircleFilled class="bullet-icon ok" />
            <div>
              <div class="bullet-title">API 文档</div>
              <div class="bullet-desc">以 <code>backend/API_SUMMARY.md</code> 为准</div>
            </div>
          </li>
          <li>
            <ClockCircleFilled class="bullet-icon pending" />
            <div>
              <div class="bullet-title">数据库 Schema</div>
              <div class="bullet-desc">变更见 <code>backend/src/main/resources/db/schema.sql</code></div>
            </div>
          </li>
        </ul>
      </a-card>

      <a-card class="info-card has-hover" :bordered="false">
        <template #title>
          <div class="card-title">
            <ExperimentOutlined class="card-title-icon" />
            <span>前端状态</span>
            <a-tag color="blue" class="status-tag">迁移中</a-tag>
          </div>
        </template>
        <ul class="bullet-list">
          <li>
            <CheckCircleFilled class="bullet-icon ok" />
            <div>
              <div class="bullet-title">UI 组件库</div>
              <div class="bullet-desc">已切换至 Ant Design Vue 4.x</div>
            </div>
          </li>
          <li>
            <CheckCircleFilled class="bullet-icon ok" />
            <div>
              <div class="bullet-title">主题与设计令牌</div>
              <div class="bullet-desc">统一蓝青配色，圆角与阴影体系已收敛</div>
            </div>
          </li>
          <li>
            <ClockCircleFilled class="bullet-icon pending" />
            <div>
              <div class="bullet-title">业务页面</div>
              <div class="bullet-desc">逐步对接新版单体后端 API</div>
            </div>
          </li>
        </ul>
      </a-card>

      <a-card class="info-card has-hover quick-card" :bordered="false">
        <template #title>
          <div class="card-title">
            <ThunderboltFilled class="card-title-icon accent" />
            <span>快捷入口</span>
          </div>
        </template>
        <div class="quick-grid">
          <div
            v-for="item in quickLinks"
            :key="item.path"
            class="quick-tile"
            :style="{ '--tile-color': item.color }"
            @click="$router.push(item.path)"
          >
            <component :is="item.icon" class="quick-tile-icon" />
            <div class="quick-tile-title">{{ item.title }}</div>
            <div class="quick-tile-desc">{{ item.desc }}</div>
          </div>
        </div>
      </a-card>
    </section>
  </div>
</template>

<script setup>
import { computed } from 'vue'
import {
  ApiOutlined,
  AppstoreOutlined,
  BellOutlined,
  CheckCircleFilled,
  ClockCircleFilled,
  CloudServerOutlined,
  DashboardOutlined,
  DeploymentUnitOutlined,
  ExperimentOutlined,
  FundProjectionScreenOutlined,
  RobotOutlined,
  ScheduleOutlined,
  TeamOutlined,
  ThunderboltFilled
} from '@ant-design/icons-vue'
import { sessionState } from '@/stores/session'
import { getBusinessTerm, isPlatformModuleEnabled, platformState } from '@/stores/platform'

const displayName = computed(() => sessionState.user?.nickName || sessionState.user?.userName || '管理员')
const projectName = computed(() => platformState.project?.name || platformState.systemTitle || '机器人二开项目')
const spaceTerm = computed(() => getBusinessTerm('space', '空间'))
const areaTerm = computed(() => getBusinessTerm('area', '区域'))
const taskTerm = computed(() => getBusinessTerm('task', '任务'))
const visitorTerm = computed(() => getBusinessTerm('visitor', '人员'))
const robotTerm = computed(() => getBusinessTerm('robot', '机器人'))
const heroEyebrow = computed(() => `${platformState.brandTitle || '机器人管理平台'} · 2026`)
const heroDescription = computed(() => `${projectName.value}正在运行，首页会按当前项目配置展示已启用模块和快捷入口。`)

const greeting = computed(() => {
  const hour = new Date().getHours()
  if (hour < 6) return '凌晨好'
  if (hour < 12) return '早上好'
  if (hour < 14) return '中午好'
  if (hour < 18) return '下午好'
  return '晚上好'
})

const heroStats = computed(() => [
  { label: `在线${robotTerm.value}`, value: '--', note: '等待项目数据接入', icon: RobotOutlined, bg: 'rgba(47, 84, 235, 0.18)' },
  { label: `活跃${areaTerm.value}`, value: '--', note: `覆盖当前${spaceTerm.value}`, icon: AppstoreOutlined, bg: 'rgba(19, 194, 194, 0.18)' },
  { label: `今日${taskTerm.value}`, value: '--', note: '按项目任务统计', icon: ScheduleOutlined, bg: 'rgba(82, 196, 26, 0.18)' },
  { label: '待处理事件', value: '--', note: '按项目规则统计', icon: BellOutlined, bg: 'rgba(255, 173, 20, 0.22)' }
])

const quickLinks = computed(() => [
  { title: '数字孪生', desc: `${areaTerm.value}、${robotTerm.value}实时联动`, path: '/digitalTwin', icon: DeploymentUnitOutlined, color: '#2f54eb', module: 'digitalTwin' },
  { title: `${robotTerm.value}管理`, desc: '设备状态与任务配置', path: '/configManagment/robot', icon: RobotOutlined, color: '#13c2c2', module: 'config' },
  { title: `${taskTerm.value}配置`, desc: '调度与执行配置', path: '/taskManagment/taskList', icon: ScheduleOutlined, color: '#722ed1', module: 'config' },
  { title: '运营报表', desc: `${visitorTerm.value}与业务统计`, path: '/statAnalysis/inLoungeList', icon: DashboardOutlined, color: '#fa8c16', module: 'statistics' },
  { title: '监控大屏', desc: '面向值班的总览', path: '/digitalTwin/screen', icon: FundProjectionScreenOutlined, color: '#f5222d', module: 'digitalTwin' },
  { title: 'API 文档', desc: '后端接口说明', path: '/tool/swagger', icon: ApiOutlined, color: '#3f8600', module: 'system' }
].filter((item) => isPlatformModuleEnabled(item.module)))
</script>

<style scoped>
.welcome-page {
  display: grid;
  gap: 20px;
}

.hero-card {
  position: relative;
  border-radius: var(--radius-xl);
  padding: 32px 36px;
  color: #f8fbff;
  background:
    radial-gradient(circle at 0% 0%, rgb(82 196 250 / 26%), transparent 55%),
    radial-gradient(circle at 100% 100%, rgb(19 194 194 / 24%), transparent 55%),
    linear-gradient(135deg, #0f1d3a 0%, #142a55 50%, #1d3b7a 100%);
  overflow: hidden;
  box-shadow: 0 24px 48px rgb(15 23 42 / 18%);
}

.hero-noise {
  position: absolute;
  inset: 0;
  pointer-events: none;
  background-image:
    repeating-linear-gradient(135deg, rgb(255 255 255 / 4%) 0 1px, transparent 1px 28px),
    repeating-linear-gradient(45deg, rgb(255 255 255 / 3%) 0 1px, transparent 1px 36px);
  opacity: 0.55;
}

.hero-content {
  position: relative;
  z-index: 1;
  display: grid;
  gap: 28px;
  grid-template-columns: 1fr;
}

.hero-meta h1 {
  margin: 14px 0 10px;
  font-size: 28px;
  font-weight: 700;
  letter-spacing: 0.02em;
}

.hero-meta p {
  margin: 0 0 18px;
  max-width: 640px;
  color: rgb(207 217 248 / 82%);
  line-height: 1.7;
}

.eyebrow-chip {
  background: rgb(255 255 255 / 12%);
  border-color: rgb(255 255 255 / 18%);
  color: #fff;
}

.hero-actions {
  display: flex;
  gap: 12px;
  flex-wrap: wrap;
}

.hero-stats {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(180px, 1fr));
  gap: 14px;
}

.hero-stat {
  display: flex;
  align-items: center;
  gap: 14px;
  padding: 14px 16px;
  border-radius: 14px;
  background: rgb(255 255 255 / 8%);
  border: 1px solid rgb(255 255 255 / 10%);
  backdrop-filter: blur(6px);
}

.hero-stat-icon {
  width: 42px;
  height: 42px;
  border-radius: 12px;
  display: grid;
  place-items: center;
  font-size: 20px;
  color: #fff;
}

.hero-stat-label {
  font-size: 12px;
  color: rgb(207 217 248 / 76%);
  letter-spacing: 0.06em;
}

.hero-stat-value {
  font-size: 22px;
  font-weight: 700;
  margin-top: 2px;
}

.hero-stat-note {
  font-size: 11px;
  color: rgb(207 217 248 / 64%);
  margin-top: 2px;
}

.grid-row {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
  gap: 16px;
}

.info-card {
  border-radius: var(--radius-lg);
  background: var(--surface-card);
  border: 1px solid var(--border-soft);
  height: 100%;
}

.card-title {
  display: flex;
  align-items: center;
  gap: 10px;
}

.card-title-icon {
  color: var(--brand-primary);
  font-size: 16px;
  background: rgb(47 84 235 / 10%);
  padding: 6px;
  border-radius: 8px;
}

.card-title-icon.accent {
  color: #fa8c16;
  background: rgb(250 140 22 / 12%);
}

.status-tag {
  margin-left: 4px;
}

.bullet-list {
  list-style: none;
  padding: 0;
  margin: 0;
  display: grid;
  gap: 14px;
}

.bullet-list li {
  display: flex;
  align-items: flex-start;
  gap: 12px;
}

.bullet-icon {
  font-size: 16px;
  margin-top: 2px;
}

.bullet-icon.ok {
  color: var(--brand-success);
}

.bullet-icon.pending {
  color: var(--brand-warning);
}

.bullet-title {
  font-weight: 600;
  color: var(--text-strong);
}

.bullet-desc {
  margin-top: 2px;
  font-size: 12px;
  color: var(--text-muted);
}

.bullet-desc code {
  background: var(--surface-muted);
  padding: 1px 6px;
  border-radius: 4px;
  font-family: var(--font-mono);
  color: var(--text-strong);
  font-size: 11px;
}

.quick-card :deep(.ant-card-body) {
  padding: 16px;
}

.quick-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 10px;
}

.quick-tile {
  --tile-color: var(--brand-primary);

  position: relative;
  padding: 14px;
  border-radius: 12px;
  background: var(--surface-muted);
  border: 1px solid var(--border-soft);
  cursor: pointer;
  transition: all 0.18s ease;
  overflow: hidden;
}

.quick-tile:hover {
  border-color: var(--tile-color);
  background: #fff;
  transform: translateY(-2px);
  box-shadow: 0 12px 28px rgb(15 23 42 / 8%);
}

.quick-tile::after {
  content: '';
  position: absolute;
  inset: 0 0 auto auto;
  width: 80px;
  height: 80px;
  background: var(--tile-color);
  opacity: 0.08;
  border-radius: 50%;
  transform: translate(30%, -40%);
}

.quick-tile-icon {
  font-size: 18px;
  color: var(--tile-color);
  background: color-mix(in srgb, var(--tile-color) 12%, transparent);
  padding: 8px;
  border-radius: 10px;
}

.quick-tile-title {
  margin-top: 10px;
  font-weight: 600;
  color: var(--text-strong);
  font-size: 14px;
}

.quick-tile-desc {
  margin-top: 2px;
  font-size: 12px;
  color: var(--text-muted);
}

@media (min-width: 1080px) {
  .hero-content {
    grid-template-columns: 1.1fr 1fr;
    align-items: center;
  }
}
</style>
