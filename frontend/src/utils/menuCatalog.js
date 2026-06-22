const catalog = [
  {
    title: '首页',
    path: '/',
    icon: 'dashboard',
    children: []
  },
  {
    title: '统计分析',
    path: '/statAnalysis',
    icon: 'chart',
    children: [
      ['在舱记录', '/statAnalysis/inLoungeList', 'people'],
      ['准出记录', '/viewManagment/outGoing', 'walk'],
      ['问询统计', '/statAnalysis/inquiry', 'message'],
      ['引导统计', '/statAnalysis/guide', 'robot'],
      ['准入记录', '/statAnalysis/goingStat', 'chart']
    ]
  },
  {
    title: '日志管理',
    path: '/logs',
    icon: 'log',
    children: [
      ['操作日志', '/monitor/operlog', 'log'],
      ['登录日志', '/monitor/logininfor', 'logininfor']
    ]
  },
  {
    title: '基础配置',
    path: '/configManagment',
    icon: 'edit',
    children: [
      ['贵宾室', '/configManagment/vipRoom', 'list'],
      ['功能区', '/configManagment/areaManagment', 'tree-table'],
      ['区域', '/configManagment/vipRoomRegion', 'table'],
      ['摄像头', '/configManagment/monitorDevice', 'camera'],
      ['机器人', '/configManagment/robot', 'robot'],
      ['机器人音频', '/configManagment/robotAudio', 'sound'],
      ['音频', '/configManagment/audio', 'chart'],
      ['图片', '/configManagment/photo', 'image'],
      ['任务列表', '/taskManagment/taskList', 'list']
    ]
  },
  {
    title: '系统管理',
    path: '/system',
    icon: 'system',
    children: [
      ['用户管理', '/system/user', 'user']
    ]
  }
]

const fallbackMenuCatalog = catalog.map((group) => ({
  ...group,
  children: group.children.map(([title, path, icon]) => ({ title, path, icon }))
}))

export const menuCatalog = fallbackMenuCatalog
let activeMenuCatalog = cloneMenus(fallbackMenuCatalog)

const titleByPath = new Map()
const iconByPath = new Map()
const orderByPath = new Map()
const parentByPath = new Map()

const auxiliaryRoutes = [
  ['首页', '/', 'dashboard'],
  ['个人中心', '/profile', 'user'],
  ['个人资料', '/profile/userInfo', 'user'],
  ['修改密码', '/profile/resetPwd', 'lock'],
  ['头像上传', '/profile/userAvatar', 'user'],
  ['投诉记录', '/configManagment/complaintRecord', 'message'],
  ['视频资源', '/configManagment/vedio', 'video-camera'],
  ['数字孪生', '/digitalTwin', 'dashboard'],
  ['数字孪生模型', '/digitalTwin/v15', 'dashboard'],
  ['监控大屏', '/digitalTwin/screen', 'dashboard'],
  ['API 文档', '/tool/swagger', 'swagger'],
  ['问询统计', '/statAnalysis/inquiry', 'message'],
  ['引导统计', '/statAnalysis/guide', 'robot']
]

const canonicalAliases = [
  ['/config/robot', '/configManagment/robot'],
  ['/config/photo', '/configManagment/photo'],
  ['/config/audio', '/configManagment/audio'],
  ['/config/robotAudio', '/configManagment/robotAudio'],
  ['/config/monitorDevice', '/configManagment/monitorDevice'],
  ['/config/vipRoom', '/configManagment/vipRoom'],
  ['/config/region', '/configManagment/vipRoomRegion'],
  ['/config/areaManagment', '/configManagment/areaManagment'],
  ['/config/vedio', '/configManagment/vedio'],
  ['/config/complaintRecord', '/configManagment/complaintRecord'],
  ['/config/task', '/taskManagment/taskList'],
  ['/flight/passenger', '/statAnalysis/inLoungeList'],
  ['/flight/goingStat', '/statAnalysis/goingStat'],
  ['/flight/outGoing', '/viewManagment/outGoing']
]

const aliasByPath = new Map(canonicalAliases.map(([alias, canonical]) => [normalizePath(alias), normalizePath(canonical)]))

export function normalizePath(path = '') {
  const normalized = `/${path}`.replace(/\/+/g, '/')
  return normalized.length > 1 ? normalized.replace(/\/$/, '') : normalized
}

function cloneMenus(menus = []) {
  return menus.map((group) => ({
    ...group,
    path: normalizePath(group.path),
    children: (group.children || []).map((item) => ({
      ...item,
      path: normalizePath(item.path)
    }))
  }))
}

function normalizeMenu(menu, index, baseSort = 0) {
  const children = (menu.children || [])
    .filter((item) => item.enabled !== false)
    .map((item, childIndex) => normalizeMenu(item, childIndex, (menu.sort ?? baseSort + index) * 1000))
  return {
    title: menu.title,
    path: normalizePath(menu.path),
    icon: menu.icon,
    module: menu.module,
    permission: menu.permission,
    pluginPage: menu.pluginPage,
    sort: menu.sort ?? baseSort + index,
    children
  }
}

function rebuildMenuLookups(menus) {
  titleByPath.clear()
  iconByPath.clear()
  orderByPath.clear()
  parentByPath.clear()

  menus.forEach((group, groupIndex) => {
    const groupPath = normalizePath(group.path)
    titleByPath.set(groupPath, group.title)
    iconByPath.set(groupPath, group.icon)
    orderByPath.set(groupPath, group.sort ?? groupIndex * 1000)
    ;(group.children || []).forEach((item, childIndex) => {
      const itemPath = normalizePath(item.path)
      titleByPath.set(itemPath, item.title)
      iconByPath.set(itemPath, item.icon)
      orderByPath.set(itemPath, item.sort ?? groupIndex * 1000 + childIndex + 1)
      parentByPath.set(itemPath, groupPath)
    })
  })

  auxiliaryRoutes.forEach(([title, path, icon], index) => {
    const normalized = normalizePath(path)
    titleByPath.set(normalized, title)
    iconByPath.set(normalized, icon)
    orderByPath.set(normalized, 9000 + index)
  })
}

export function configureMenus(menus = []) {
  const normalizedMenus = menus
    .filter((group) => group.enabled !== false)
    .map((group, groupIndex) => normalizeMenu(group, groupIndex, groupIndex * 1000))
  activeMenuCatalog = normalizedMenus.length ? normalizedMenus : cloneMenus(fallbackMenuCatalog)
  rebuildMenuLookups(activeMenuCatalog)
}

export function resetMenus() {
  activeMenuCatalog = cloneMenus(fallbackMenuCatalog)
  rebuildMenuLookups(activeMenuCatalog)
}

export function joinMenuPath(parentPath = '', childPath = '') {
  if (!childPath) {
    return normalizePath(parentPath)
  }
  if (/^(https?:)?\/\//.test(childPath)) {
    return childPath
  }
  if (childPath.startsWith('/')) {
    return normalizePath(childPath)
  }
  return normalizePath(`${parentPath}/${childPath}`)
}

export function getCanonicalMenuPath(path = '') {
  const normalized = normalizePath(path)
  if (aliasByPath.has(normalized)) {
    return aliasByPath.get(normalized)
  }
  const prefix = [...aliasByPath.keys()]
    .filter((item) => normalized.startsWith(`${item}/`))
    .sort((a, b) => b.length - a.length)[0]
  if (!prefix) {
    return normalized
  }
  return normalizePath(`${aliasByPath.get(prefix)}${normalized.slice(prefix.length)}`)
}

export function getMenuTitle(path, fallback = '') {
  const normalized = getCanonicalMenuPath(path)
  if (titleByPath.has(normalized)) {
    return titleByPath.get(normalized)
  }
  const prefix = [...titleByPath.keys()]
    .filter((item) => normalized.startsWith(`${item}/`))
    .sort((a, b) => b.length - a.length)[0]
  return prefix ? titleByPath.get(prefix) : fallback
}

export function getMenuIcon(path, fallback = '') {
  const normalized = getCanonicalMenuPath(path)
  return iconByPath.get(normalized) || fallback
}

export function getMenuOrder(path) {
  const normalized = getCanonicalMenuPath(path)
  return orderByPath.get(normalized) ?? 999999
}

export function getParentPath(path) {
  return parentByPath.get(getCanonicalMenuPath(path)) || ''
}

export function sortByMenuOrder(items, getPath = (item) => item.path) {
  return [...items].sort((left, right) => {
    const leftOrder = getMenuOrder(getPath(left))
    const rightOrder = getMenuOrder(getPath(right))
    if (leftOrder !== rightOrder) {
      return leftOrder - rightOrder
    }
    return String(getPath(left)).localeCompare(String(getPath(right)))
  })
}

export function buildFallbackMenus() {
  return cloneMenus(fallbackMenuCatalog)
}

export function buildActiveMenus() {
  return cloneMenus(activeMenuCatalog)
}

resetMenus()
