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
    title: '餐食管理',
    path: '/foodManagment',
    icon: 'food',
    children: [
      ['菜品管理', '/foodManagment/food', 'food'],
      ['菜单计划', '/foodManagment/foodPlan', 'calendar'],
      ['每日菜单', '/foodManagment/menuPlan', 'menuPlan']
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
      ['餐桌管理', '/foodManagment/foodTable', 'table'],
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

export const menuCatalog = catalog.map((group) => ({
  ...group,
  children: group.children.map(([title, path, icon]) => ({ title, path, icon }))
}))

const titleByPath = new Map()
const iconByPath = new Map()
const orderByPath = new Map()
const parentByPath = new Map()

menuCatalog.forEach((group, groupIndex) => {
  const groupPath = normalizePath(group.path)
  titleByPath.set(groupPath, group.title)
  iconByPath.set(groupPath, group.icon)
  orderByPath.set(groupPath, groupIndex * 1000)
  group.children.forEach((item, childIndex) => {
    const itemPath = normalizePath(item.path)
    titleByPath.set(itemPath, item.title)
    iconByPath.set(itemPath, item.icon)
    orderByPath.set(itemPath, groupIndex * 1000 + childIndex + 1)
    parentByPath.set(itemPath, groupPath)
  })
})

const auxiliaryRoutes = [
  ['首页', '/', 'dashboard'],
  ['个人中心', '/profile', 'user'],
  ['个人资料', '/profile/userInfo', 'user'],
  ['修改密码', '/profile/resetPwd', 'lock'],
  ['头像上传', '/profile/userAvatar', 'user'],
  ['桌台模型', '/numberModel', 'table'],
  ['点餐订单', '/foodManagment/foodMenu', 'shopping'],
  ['投诉记录', '/configManagment/complaintRecord', 'message'],
  ['视频资源', '/configManagment/vedio', 'video-camera'],
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
  ['/config/table', '/foodManagment/foodTable'],
  ['/config/task', '/taskManagment/taskList'],
  ['/flight/passenger', '/statAnalysis/inLoungeList'],
  ['/flight/goingStat', '/statAnalysis/goingStat'],
  ['/flight/outGoing', '/viewManagment/outGoing'],
  ['/food/foodConfig', '/foodManagment/food'],
  ['/food/dailyMenu', '/foodManagment/menuPlan'],
  ['/food/foodOrder', '/foodManagment/foodMenu'],
  ['/food/foodTable', '/foodManagment/foodTable'],
  ['/food/foodPlan', '/foodManagment/foodPlan']
]

const aliasByPath = new Map(canonicalAliases.map(([alias, canonical]) => [normalizePath(alias), normalizePath(canonical)]))

auxiliaryRoutes.forEach(([title, path, icon], index) => {
  titleByPath.set(path, title)
  iconByPath.set(path, icon)
  orderByPath.set(path, 9000 + index)
})

export function normalizePath(path = '') {
  const normalized = `/${path}`.replace(/\/+/g, '/')
  return normalized.length > 1 ? normalized.replace(/\/$/, '') : normalized
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
  return menuCatalog.map((group) => ({
    title: group.title,
    path: group.path,
    icon: group.icon,
    children: group.children.map((item) => ({ ...item }))
  }))
}
