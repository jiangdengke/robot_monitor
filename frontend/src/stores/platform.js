import { reactive } from 'vue'
import { getPlatformBootstrap } from '@/api/system'
import { buildActiveMenus, buildFallbackMenus, configureMenus, resetMenus } from '@/utils/menuCatalog'

const DEFAULT_PROJECT_CODE = 'robot-project'
const DEFAULT_PROJECT_NAME = '通用机器人二开项目'
const DEFAULT_CUSTOMER_NAME = '未配置客户'
const DEFAULT_SYSTEM_TITLE = '机器人二开管理系统'
const DEFAULT_BRAND_TITLE = '机器人管理平台'
const DEFAULT_LOGO_URL = '/favicon.ico'
const DEFAULT_TEMPLATE_CODE = 'generic-robot'
const DEFAULT_TEMPLATE_NAME = '通用机器人项目'

const DEFAULT_MODULES = {
  dashboard: true,
  statistics: false,
  logs: true,
  config: true,
  system: true,
  digitalTwin: false,
  knowledge: false,
  robotControl: true
}

const DEFAULT_TERMS = {
  space: '空间',
  area: '功能区',
  region: '区域',
  point: '点位',
  task: '任务',
  visitor: '访客',
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
  templateCode: DEFAULT_TEMPLATE_CODE,
  templateName: DEFAULT_TEMPLATE_NAME,
  homePath: '/',
  themeColor: '#2f54eb',
  modules: { ...DEFAULT_MODULES },
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
  platformState.templateCode = DEFAULT_TEMPLATE_CODE
  platformState.templateName = DEFAULT_TEMPLATE_NAME
  platformState.homePath = '/'
  platformState.themeColor = '#2f54eb'
  platformState.modules = { ...DEFAULT_MODULES }
  platformState.terms = { ...DEFAULT_TERMS }
  platformState.menus = buildActiveMenus()
  platformState.pages = {}
}

function applyPlatformBootstrap(bootstrap = {}) {
  const menus = Array.isArray(bootstrap.menus) && bootstrap.menus.length ? bootstrap.menus : buildFallbackMenus()
  const modules = { ...DEFAULT_MODULES, ...(bootstrap.modules || {}) }
  configureMenus(menus, modules)
  platformState.project = {
    code: bootstrap.projectCode || DEFAULT_PROJECT_CODE,
    name: bootstrap.projectName || DEFAULT_PROJECT_NAME,
    customerName: bootstrap.customerName || DEFAULT_CUSTOMER_NAME
  }
  platformState.systemTitle = bootstrap.systemTitle || DEFAULT_SYSTEM_TITLE
  platformState.brandTitle = bootstrap.brandTitle || DEFAULT_BRAND_TITLE
  platformState.logoUrl = bootstrap.logoUrl || DEFAULT_LOGO_URL
  platformState.templateCode = bootstrap.templateCode || DEFAULT_TEMPLATE_CODE
  platformState.templateName = bootstrap.templateName || DEFAULT_TEMPLATE_NAME
  platformState.homePath = bootstrap.homePath || '/'
  platformState.themeColor = bootstrap.themeColor || '#2f54eb'
  platformState.modules = modules
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

function isPlatformModuleEnabled(module) {
  return !module || platformState.modules?.[module] !== false
}

export { getBusinessTerm, getPlatformPageConfig, getPlatformTitle, initializePlatform, isPlatformModuleEnabled, platformState }
