import { reactive } from 'vue'
import { getPlatformBootstrap } from '@/api/system'
import { buildActiveMenus, buildFallbackMenus, configureMenus, resetMenus } from '@/utils/menuCatalog'

const DEFAULT_SYSTEM_TITLE = '国航智慧贵宾室管理系统'
const DEFAULT_BRAND_TITLE = '智慧贵宾室'
const DEFAULT_LOGO_URL = '/favicon.ico'

const DEFAULT_TERMS = {
  space: '贵宾室',
  area: '功能区',
  region: '区域',
  point: '点位',
  task: '任务',
  visitor: '旅客',
  event: '业务事件',
  robot: '机器人'
}

const platformState = reactive({
  loaded: false,
  failed: false,
  project: null,
  systemTitle: DEFAULT_SYSTEM_TITLE,
  brandTitle: DEFAULT_BRAND_TITLE,
  logoUrl: DEFAULT_LOGO_URL,
  templateCode: 'lounge-greeting',
  templateName: '休息室迎宾',
  homePath: '/',
  themeColor: '#2f54eb',
  modules: {},
  terms: { ...DEFAULT_TERMS },
  menus: buildFallbackMenus(),
  pages: {}
})

function applyFallbackBootstrap() {
  resetMenus()
  platformState.project = null
  platformState.systemTitle = DEFAULT_SYSTEM_TITLE
  platformState.brandTitle = DEFAULT_BRAND_TITLE
  platformState.logoUrl = DEFAULT_LOGO_URL
  platformState.templateCode = 'lounge-greeting'
  platformState.templateName = '休息室迎宾'
  platformState.homePath = '/'
  platformState.themeColor = '#2f54eb'
  platformState.modules = {}
  platformState.terms = { ...DEFAULT_TERMS }
  platformState.menus = buildActiveMenus()
  platformState.pages = {}
}

function applyPlatformBootstrap(bootstrap = {}) {
  const menus = Array.isArray(bootstrap.menus) && bootstrap.menus.length ? bootstrap.menus : buildFallbackMenus()
  configureMenus(menus)
  platformState.project = {
    code: bootstrap.projectCode || 'air-china-lounge',
    name: bootstrap.projectName || '国航休息室迎宾',
    customerName: bootstrap.customerName || '中国国际航空'
  }
  platformState.systemTitle = bootstrap.systemTitle || DEFAULT_SYSTEM_TITLE
  platformState.brandTitle = bootstrap.brandTitle || DEFAULT_BRAND_TITLE
  platformState.logoUrl = bootstrap.logoUrl || DEFAULT_LOGO_URL
  platformState.templateCode = bootstrap.templateCode || 'lounge-greeting'
  platformState.templateName = bootstrap.templateName || '休息室迎宾'
  platformState.homePath = bootstrap.homePath || '/'
  platformState.themeColor = bootstrap.themeColor || '#2f54eb'
  platformState.modules = bootstrap.modules || {}
  platformState.terms = { ...DEFAULT_TERMS, ...(bootstrap.terms || {}) }
  platformState.menus = buildActiveMenus()
  platformState.pages = bootstrap.pages || {}
}

async function initializePlatform() {
  try {
    const bootstrap = await getPlatformBootstrap()
    applyPlatformBootstrap(bootstrap)
    platformState.failed = false
    return true
  } catch {
    applyFallbackBootstrap()
    platformState.failed = true
    return false
  } finally {
    platformState.loaded = true
  }
}

function getPlatformTitle(routeTitle = '') {
  const systemTitle = platformState.systemTitle || DEFAULT_SYSTEM_TITLE
  return routeTitle ? `${routeTitle} - ${systemTitle}` : systemTitle
}

function getBusinessTerm(key, fallback = '') {
  return platformState.terms[key] || fallback
}

function getPlatformPageConfig(pageCode) {
  return platformState.pages?.[pageCode] || null
}

export { getBusinessTerm, getPlatformPageConfig, getPlatformTitle, initializePlatform, platformState }
