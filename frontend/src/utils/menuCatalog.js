const menuCatalog = [
  {
    title: '首页',
    path: '/',
    icon: 'dashboard',
    children: []
  },
  {
    title: '机器人管理',
    path: '/robot-management',
    icon: 'robot',
    children: [
      { title: '机器人', path: '/config/robot' },
      { title: '设备', path: '/config/device' },
      { title: '任务', path: '/config/task' }
    ]
  },
  {
    title: '空间管理',
    path: '/space-management',
    icon: 'config',
    children: [
      { title: '场地', path: '/config/site' },
      { title: '区域', path: '/config/area' },
      { title: '点位', path: '/config/point' }
    ]
  },
  {
    title: '日志管理',
    path: '/logs',
    icon: 'log',
    children: [
      { title: '操作日志', path: '/monitor/operlog', icon: 'log' },
      { title: '登录日志', path: '/monitor/logininfor', icon: 'logininfor' }
    ]
  },
  {
    title: '系统管理',
    path: '/system',
    icon: 'system',
    children: [
      { title: '用户管理', path: '/system/user' }
    ]
  }
]

const titleByPath = new Map()
const orderByPath = new Map()
const parentByPath = new Map()

const auxiliaryRoutes = [
  ['个人中心', '/profile'],
  ['个人资料', '/profile/userInfo'],
  ['修改密码', '/profile/resetPwd'],
  ['头像上传', '/profile/userAvatar'],
  ['页面跳转', '/redirect']
]

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

function rebuildMenuLookups(menus) {
  titleByPath.clear()
  orderByPath.clear()
  parentByPath.clear()

  menus.forEach((group, groupIndex) => {
    const groupPath = normalizePath(group.path)
    titleByPath.set(groupPath, group.title)
    orderByPath.set(groupPath, groupIndex * 1000)
    ;(group.children || []).forEach((item, childIndex) => {
      const itemPath = normalizePath(item.path)
      titleByPath.set(itemPath, item.title)
      orderByPath.set(itemPath, groupIndex * 1000 + childIndex + 1)
      parentByPath.set(itemPath, groupPath)
    })
  })

  auxiliaryRoutes.forEach(([title, path], index) => {
    const normalized = normalizePath(path)
    titleByPath.set(normalized, title)
    orderByPath.set(normalized, 9000 + index)
  })
}

export function getCanonicalMenuPath(path = '') {
  return normalizePath(path)
}

export function getMenuTitle(path, fallback = '') {
  const normalized = normalizePath(path)
  if (titleByPath.has(normalized)) {
    return titleByPath.get(normalized)
  }
  const prefix = [...titleByPath.keys()]
    .filter((item) => normalized.startsWith(`${item}/`))
    .sort((leftPath, rightPath) => rightPath.length - leftPath.length)[0]
  return prefix ? titleByPath.get(prefix) : fallback
}

export function getMenuOrder(path) {
  return orderByPath.get(normalizePath(path)) ?? 999999
}

export function getParentPath(path) {
  return parentByPath.get(normalizePath(path)) || ''
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

export function buildMenus() {
  return cloneMenus(menuCatalog)
}

rebuildMenuLookups(menuCatalog)
