import {
  changeRoleStatus,
  changeJobStatus,
  changeUserStatus,
  checkoutPassenger,
  cleanJobLog,
  cleanLogininfor,
  cleanOperLog,
  downloadUserImportTemplate,
  exportSystemResource,
  forceLogoutOnlineUser,
  addDeviceRegion,
  deleteDeviceRegion,
  getDeviceRegion,
  getDeptExcludeList,
  getDeptTree,
  getFoodConfig,
  getFoodDaily,
  getFoodOrder,
  getFoodPlan,
  getMenuTree,
  getRoleDeptTree,
  getRoleMenuTree,
  getUserCreateOptions,
  importTemporaryFlights,
  importUsers,
  listConfigAudios,
  listConfigAreas,
  listConfigDevices,
  listConfigImages,
  listConfigRegions,
  listConfigRobots,
  listConfigTables,
  listDeviceRegions,
  listFoodConfigs,
  refreshConfigCache,
  refreshDictCache,
  cancelFoodOrder,
  finishFoodOrder,
  receiveFoodOrder,
  resetUserPassword,
  runConfigTask,
  runJob,
  unlockLogininfor,
  updateDeviceRegion,
  updateRoleDataScope
} from '@/api/system'

const statusOptions = [
  { label: '正常', value: '0' },
  { label: '停用', value: '1' }
]

const jobStatusOptions = [
  { label: '正常', value: '0' },
  { label: '暂停', value: '1' }
]

const successFailOptions = [
  { label: '成功', value: '0' },
  { label: '失败', value: '1' }
]

const jobGroupOptions = [
  { label: '默认', value: 'DEFAULT' },
  { label: '系统', value: 'SYSTEM' }
]

const businessTypeOptions = [
  { label: '其它', value: 0 },
  { label: '新增', value: 1 },
  { label: '修改', value: 2 },
  { label: '删除', value: 3 },
  { label: '授权', value: 4 },
  { label: '导出', value: 5 },
  { label: '导入', value: 6 },
  { label: '强退', value: 7 },
  { label: '生成代码', value: 8 },
  { label: '清空数据', value: 9 }
]

const businessTypeMap = {
  0: '其它',
  1: '新增',
  2: '修改',
  3: '删除',
  4: '授权',
  5: '导出',
  6: '导入',
  7: '强退',
  8: '生成代码',
  9: '清空数据'
}

const successFailMap = { 0: '成功', 1: '失败' }

const yesNoOptions = [
  { label: '是', value: 'Y' },
  { label: '否', value: 'N' }
]

const enableOptions = [
  { label: '启用', value: 1 },
  { label: '停用', value: 0 }
]

const showOptions = [
  { label: '展示', value: '1' },
  { label: '隐藏', value: '0' }
]

const guideOptions = [
  { label: '支持', value: '1' },
  { label: '不支持', value: '0' }
]

const languageOptions = [
  { label: '中文', value: 'CN' },
  { label: '英文', value: 'EN' },
  { label: '俄文', value: 'RU' }
]

const executeTypeOptions = [
  { label: '立即执行', value: 'immediately' },
  { label: '每天', value: 'day' },
  { label: '每周', value: 'week' },
  { label: '每月', value: 'month' }
]

const taskSubtypeOptions = [
  { label: '指令任务', value: '0' },
  { label: '语音任务', value: '1' },
  { label: '视频流任务', value: '2' },
  { label: 'HTTP 任务', value: '3' }
]

const deviceTypeOptions = [
  { label: '摄像头', value: 'camera' },
  { label: '门禁', value: 'gate' },
  { label: '其他', value: 'other' }
]

const foodTypeOptions = [
  { label: '冷菜', value: '冷菜' },
  { label: '热菜', value: '热菜' },
  { label: '甜品', value: '甜品' },
  { label: '饮料', value: '饮料' }
]

const dailyMenuStatusOptions = [
  { label: '上架', value: '1' },
  { label: '下架', value: '0' }
]

const orderStatusOptions = [
  { label: '已取消', value: '0' },
  { label: '已下单', value: '1' },
  { label: '已接单', value: '2' },
  { label: '已完成', value: '3' }
]

const orderStatusMap = { 0: '已取消', 1: '已下单', 2: '已接单', 3: '已完成' }

const passengerStatusOptions = [
  { label: '在厅', value: '1' },
  { label: '已出厅', value: '0' }
]

const passengerStatusMap = { 1: '在厅', 0: '已出厅' }

const warningSuccessOptions = [
  { label: '未处理', value: '-1' },
  { label: '提醒中', value: '99' },
  { label: '成功', value: '1' },
  { label: '失败', value: '0' }
]

const warningSuccessMap = { '-1': '未处理', 99: '提醒中', 1: '成功', 0: '失败' }

const noticeTypeOptions = [
  { label: '人工提醒', value: '1' },
  { label: '机器人提醒', value: '2' }
]

const messageStatusOptions = [
  { label: '创建', value: '0' },
  { label: '已读', value: '1' },
  { label: '已处理', value: '2' },
  { label: '无需处理', value: '3' }
]

const messageStatusMap = { 0: '创建', 1: '已读', 2: '已处理', 3: '无需处理' }

const flightOffStatusOptions = [
  { label: '计划中', value: 'SCH' },
  { label: '预计起飞', value: 'ETD' },
  { label: '关舱门', value: 'CLD' },
  { label: '滑出', value: 'OUT' },
  { label: '起飞', value: 'OFF' }
]

const flightOnStatusOptions = [
  { label: '计划中', value: 'SCH' },
  { label: '预计到达', value: 'ETA' },
  { label: '落地', value: 'ON' },
  { label: '滑入', value: 'IN' },
  { label: '开舱门', value: 'OPN' }
]

const statusColumn = (prop = 'status') => ({
  prop,
  label: '状态',
  width: 100,
  dictType: 'sys_normal_disable',
  tag: 'info',
  tagMap: { 0: 'success', 1: 'danger' },
  map: { 0: '正常', 1: '停用' }
})

const switchStatusColumn = (action) => ({
  prop: 'status',
  label: '状态',
  width: 100,
  switch: true,
  action
})

function normalizeDateTimeValue(value) {
  const text = String(value || '')
  if (/^\d{14}$/.test(text)) {
    return `${text.slice(0, 4)}-${text.slice(4, 6)}-${text.slice(6, 8)} ${text.slice(8, 10)}:${text.slice(10, 12)}:${text.slice(12, 14)}`
  }
  if (/^\d{8}$/.test(text)) {
    return `${text.slice(0, 4)}-${text.slice(4, 6)}-${text.slice(6, 8)}`
  }
  return value
}

const switchJobStatusColumn = (action) => ({
  prop: 'status',
  label: '状态',
  width: 100,
  switch: true,
  activeValue: '0',
  inactiveValue: '1',
  action,
  successMessage: '任务状态已更新'
})

const dataScopeOptions = [
  { label: '全部数据权限', value: '1' },
  { label: '自定数据权限', value: '2' },
  { label: '本部门数据权限', value: '3' },
  { label: '本部门及以下数据权限', value: '4' },
  { label: '仅本人数据权限', value: '5' }
]

function buildTree(rows, idKey, parentKey) {
  const cloned = (rows || []).map((row) => ({ ...row, children: [] }))
  const map = new Map(cloned.map((row) => [String(row[idKey]), row]))
  const roots = []
  cloned.forEach((row) => {
    const parent = map.get(String(row[parentKey]))
    if (parent && parent !== row) {
      parent.children.push(row)
    } else {
      roots.push(row)
    }
  })
  return roots.map((row) => trimEmptyChildren(row))
}

function trimEmptyChildren(row) {
  if (row.children?.length) {
    row.children = row.children.map((child) => trimEmptyChildren(child))
  } else {
    delete row.children
  }
  return row
}

function optionsFromRows(rows, valueKey, labelKey) {
  return (rows || []).map((row) => ({ value: row[valueKey], label: row[labelKey] }))
}

function roomOptions(rows) {
  return (rows || [])
    .filter((row) => row.roomCode)
    .map((row) => ({ value: row.roomCode, label: `${row.deptName || row.roomCode} (${row.roomCode})` }))
}

function resourceOptions(rows, valueKey, labelKeys) {
  return (rows || []).map((row) => ({
    value: row[valueKey],
    label: labelKeys.map((key) => row[key]).filter(Boolean).join(' / ') || String(row[valueKey])
  }))
}

function parseIds(value) {
  return value ? String(value).split(',').filter(Boolean).map((item) => (/^\d+$/.test(item) ? Number(item) : item)) : []
}

function defaultAreaDetails() {
  return languageOptions.map((item) => ({
    languageType: item.value,
    areaName: '',
    label: '',
    remark: '',
    arrText: ''
  }))
}

async function loadConfigOptions() {
  const [roomResponse, regionResponse, areaResponse, imageResponse, audioResponse, robotResponse, deviceResponse, tableResponse] = await Promise.all([
    getRoomList(),
    listConfigRegions({ pageNum: 1, pageSize: 500 }),
    listConfigAreas({ pageNum: 1, pageSize: 500 }),
    listConfigImages({ pageNum: 1, pageSize: 500 }),
    listConfigAudios({ pageNum: 1, pageSize: 500 }),
    listConfigRobots({ pageNum: 1, pageSize: 500 }),
    listConfigDevices({ pageNum: 1, pageSize: 500 }),
    listConfigTables({ pageNum: 1, pageSize: 500 })
  ])
  return {
    rooms: roomOptions(roomResponse.data || []),
    regions: resourceOptions(regionResponse.rows || [], 'id', ['regionName', 'roomCode']),
    areas: resourceOptions(areaResponse.rows || [], 'id', ['areaName', 'roomCode']),
    images: resourceOptions(imageResponse.rows || [], 'id', ['imgName', 'roomCode']),
    audios: resourceOptions(audioResponse.rows || [], 'audioKey', ['audioKey', 'textInfo']),
    robots: resourceOptions(robotResponse.rows || [], 'id', ['robotName', 'robotId']),
    devices: resourceOptions(deviceResponse.rows || [], 'id', ['deviceName', 'deepGlintDeviceId']),
    tables: resourceOptions(tableResponse.rows || [], 'id', ['tableNo', 'roomCode'])
  }
}

async function loadFoodOptions() {
  const [configOptions, foodResponse] = await Promise.all([
    loadConfigOptions(),
    listFoodConfigs({ pageNum: 1, pageSize: 500 })
  ])
  const foods = foodResponse.rows || foodResponse.data || []
  return {
    ...configOptions,
    foods,
    foodOptions: foods.map((food) => ({
      value: food.foodId,
      label: `${food.name || food.foodId} / ${food.dicTypeCode || '-'} / ¥${food.price ?? 0}`,
      raw: food
    }))
  }
}

function syncFoodNames(payload, foodOptions) {
  const ids = parseIds(payload.foodIds)
  const names = ids
    .map((id) => foodOptions.find((option) => String(option.value) === String(id))?.raw?.name)
    .filter(Boolean)
  return {
    ...payload,
    foodIds: ids.join(','),
    foodNames: names.join(',')
  }
}

function toDeptTreeSelect(nodes) {
  return (nodes || []).map((node) => {
    const item = {
      id: node.id ?? node.deptId,
      label: node.label ?? node.deptName
    }
    if (node.children?.length) {
      item.children = toDeptTreeSelect(node.children)
    }
    return item
  })
}

function filterTreeById(nodes, id) {
  if (!id) {
    return nodes || []
  }
  return (nodes || [])
    .filter((node) => String(node.id) !== String(id))
    .map((node) => ({
      ...node,
      children: filterTreeById(node.children || [], id)
    }))
    .map((node) => trimEmptyChildren(node))
}

export const crudPages = {
  user: {
    title: '用户管理',
    description: '用户、部门、角色、岗位等账号基础资料管理。',
    basePath: '/system/user',
    rowKey: 'userId',
    searchFields: [
      { prop: 'userName', label: '账号' },
      { prop: 'nickName', label: '昵称' },
      { prop: 'phonenumber', label: '手机号' },
      { prop: 'status', label: '状态', type: 'select', options: statusOptions }
    ],
    columns: [
      { prop: 'userId', label: 'ID', width: 90 },
      { prop: 'userName', label: '账号', minWidth: 130 },
      { prop: 'nickName', label: '昵称', minWidth: 130 },
      { prop: 'dept.deptName', label: '部门', minWidth: 140 },
      { prop: 'phonenumber', label: '手机号', minWidth: 140 },
      { prop: 'email', label: '邮箱', minWidth: 180 },
      switchStatusColumn((row, status) => changeUserStatus(row.userId, status))
    ],
    formFields: async (context) => {
      const response = context.mode === 'edit' ? context.response : await getUserCreateOptions()
      const roleOptions = optionsFromRows(response?.roles || [], 'roleId', 'roleName')
      const postOptions = optionsFromRows(response?.posts || [], 'postId', 'postName')
      const deptResponse = await getDeptTree()
      return [
        { prop: 'deptId', label: '归属部门', type: 'tree', options: deptResponse.data || [] },
        { prop: 'userName', label: '账号' },
        { prop: 'nickName', label: '昵称' },
        { prop: 'password', label: '密码', inputType: 'password', hidden: ({ mode }) => mode === 'edit' },
        { prop: 'phonenumber', label: '手机号' },
        { prop: 'email', label: '邮箱' },
        { prop: 'sex', label: '性别', type: 'select', options: [{ label: '男', value: '0' }, { label: '女', value: '1' }, { label: '未知', value: '2' }] },
        { prop: 'postIds', label: '岗位', type: 'select', multiple: true, options: postOptions },
        { prop: 'roleIds', label: '角色', type: 'select', multiple: true, options: roleOptions },
        { prop: 'status', label: '状态', type: 'select', options: statusOptions },
        { prop: 'remark', label: '备注', type: 'textarea' }
      ]
    },
    defaults: { status: '0', sex: '2', password: '123456' },
    transformDetail: (response, row) => ({
      ...(response.data || row),
      roleIds: response.roleIds || [],
      postIds: response.postIds || []
    }),
    beforeSubmit: (payload, mode) => {
      if (mode === 'create' && !payload.password) {
        payload.password = '123456'
      }
      if (mode === 'edit') {
        delete payload.password
      }
      return payload
    },
    importAction: importUsers,
    headerActions: [
      { key: 'import', label: '导入', kind: 'import' },
      { key: 'template', label: '下载模板', handler: () => downloadUserImportTemplate(), reload: false },
      { key: 'export', label: '导出', handler: ({ query }) => exportSystemResource('/system/user/export', query, '用户数据.xlsx'), reload: false }
    ],
    rowActions: [
      {
        key: 'resetPwd',
        label: '重置密码',
        promptTitle: '重置密码',
        promptDefaults: { password: '123456' },
        promptFields: [{ prop: 'password', label: '新密码', inputType: 'password' }],
        handler: (row, { form }) => resetUserPassword(row.userId, form.password)
      },
      {
        key: 'authRole',
        label: '分配角色',
        route: (row) => `/system/user-auth/role/${row.userId}`
      }
    ],
    operationWidth: 350
  },
  role: {
    title: '角色管理',
    description: '角色、权限标识、数据范围和角色状态管理。',
    basePath: '/system/role',
    rowKey: 'roleId',
    searchFields: [{ prop: 'roleName', label: '角色名称' }, { prop: 'roleKey', label: '权限标识' }],
    columns: [
      { prop: 'roleId', label: 'ID', width: 90 },
      { prop: 'roleName', label: '角色名称', minWidth: 150 },
      { prop: 'roleKey', label: '权限标识', minWidth: 160 },
      { prop: 'roleSort', label: '排序', width: 90 },
      switchStatusColumn((row, status) => changeRoleStatus(row.roleId, status)),
      { prop: 'remark', label: '备注', minWidth: 180 }
    ],
    formFields: async (context) => {
      const treeResponse = context.mode === 'edit' ? await getRoleMenuTree(context.row.roleId) : await getMenuTree()
      return [
        { prop: 'roleName', label: '角色名称' },
        { prop: 'roleKey', label: '权限标识' },
        { prop: 'roleSort', label: '排序', type: 'number' },
        { prop: 'menuIds', label: '菜单权限', type: 'tree', multiple: true, showCheckbox: true, options: treeResponse.menus || treeResponse.data || [], defaultValue: treeResponse.checkedKeys || [] },
        { prop: 'status', label: '状态', type: 'select', options: statusOptions },
        { prop: 'remark', label: '备注', type: 'textarea' }
      ]
    },
    defaults: { status: '0', roleSort: 1, dataScope: '1', menuCheckStrictly: true, deptCheckStrictly: true },
    headerActions: [
      { key: 'export', label: '导出', handler: ({ query }) => exportSystemResource('/system/role/export', query, '角色数据.xlsx'), reload: false }
    ],
    rowActions: [
      {
        key: 'dataScope',
        label: '数据权限',
        promptTitle: '分配数据权限',
        promptDefaults: (row) => ({ dataScope: row.dataScope || '1', deptIds: row.deptIds || [] }),
        promptFields: async (row) => {
          const response = await getRoleDeptTree(row.roleId)
          const checkedKeys = response.checkedKeys || []
          return [
            { prop: 'dataScope', label: '权限范围', type: 'select', options: dataScopeOptions },
            { prop: 'deptIds', label: '部门权限', type: 'tree', options: response.depts || [], defaultValue: checkedKeys }
          ]
        },
        handler: (row, { form }) => updateRoleDataScope({ ...row, dataScope: form.dataScope, deptIds: form.deptIds || [] })
      },
      {
        key: 'authUser',
        label: '分配用户',
        route: (row) => `/system/role-auth/user/${row.roleId}`
      }
    ],
    operationWidth: 340
  },
  dept: {
    title: '部门管理',
    description: '组织架构、贵宾室房间编码和部门状态维护。',
    basePath: '/system/dept',
    rowKey: 'deptId',
    pagination: false,
    treeTable: true,
    enableBatchDelete: false,
    transformRows: (rows) => buildTree(rows, 'deptId', 'parentId'),
    searchFields: [{ prop: 'deptName', label: '部门名称' }, { prop: 'status', label: '状态', type: 'select', options: statusOptions }],
    columns: [
      { prop: 'deptId', label: 'ID', width: 90 },
      { prop: 'deptName', label: '部门名称', minWidth: 160 },
      { prop: 'parentId', label: '父级', width: 90 },
      { prop: 'leader', label: '负责人', minWidth: 120 },
      { prop: 'phone', label: '电话', minWidth: 140 },
      { prop: 'roomCode', label: '房间编码', minWidth: 130 },
      statusColumn()
    ],
    formFields: async (context) => {
      const response = context.mode === 'edit' && context.row?.deptId
        ? await getDeptExcludeList(context.row.deptId)
        : await getDeptTree()
      const deptTree = response.data && !response.data[0]?.label
        ? toDeptTreeSelect(buildTree(response.data, 'deptId', 'parentId'))
        : response.data || []
      return [
        { prop: 'parentId', label: '上级部门', type: 'tree', options: [{ id: 0, label: '主类目', children: deptTree }] },
        { prop: 'deptName', label: '部门名称' },
        { prop: 'orderNum', label: '排序', type: 'number' },
        { prop: 'leader', label: '负责人' },
        { prop: 'phone', label: '电话' },
        { prop: 'email', label: '邮箱' },
        { prop: 'roomCode', label: '房间编码' },
        { prop: 'status', label: '状态', type: 'select', options: statusOptions }
      ]
    },
    defaults: { parentId: 0, orderNum: 1, status: '0' }
  },
  menu: {
    title: '菜单管理',
    description: '后台菜单、路由路径、组件路径和权限标识维护。',
    basePath: '/system/menu',
    rowKey: 'menuId',
    pagination: false,
    treeTable: true,
    enableBatchDelete: false,
    transformRows: (rows) => buildTree(rows, 'menuId', 'parentId'),
    searchFields: [{ prop: 'menuName', label: '菜单名称' }, { prop: 'status', label: '状态', type: 'select', options: statusOptions }],
    columns: [
      { prop: 'menuId', label: 'ID', width: 90 },
      { prop: 'menuName', label: '菜单名称', minWidth: 150 },
      { prop: 'parentId', label: '父级', width: 90 },
      { prop: 'path', label: '路径', minWidth: 150 },
      { prop: 'component', label: '组件', minWidth: 180 },
      { prop: 'menuType', label: '类型', width: 80 },
      { prop: 'perms', label: '权限', minWidth: 180 }
    ],
    formFields: async (context) => {
      const response = await getMenuTree()
      const menuTree = filterTreeById(response.data || [], context.row?.menuId)
      return [
        { prop: 'parentId', label: '上级菜单', type: 'tree', options: [{ id: 0, label: '主类目', children: menuTree }] },
        { prop: 'menuName', label: '菜单名称' },
        { prop: 'orderNum', label: '排序', type: 'number' },
        { prop: 'path', label: '路由路径' },
        { prop: 'component', label: '组件路径' },
        { prop: 'menuType', label: '菜单类型', type: 'radio', options: [{ label: '目录', value: 'M' }, { label: '菜单', value: 'C' }, { label: '按钮', value: 'F' }] },
        { prop: 'perms', label: '权限标识', hidden: ({ form }) => form.menuType === 'M' },
        { prop: 'icon', label: '图标', type: 'icon', hidden: ({ form }) => form.menuType === 'F' },
        { prop: 'visible', label: '显示状态', type: 'select', options: [{ label: '显示', value: '0' }, { label: '隐藏', value: '1' }] },
        { prop: 'status', label: '菜单状态', type: 'select', options: statusOptions }
      ]
    },
    defaults: { parentId: 0, orderNum: 1, menuType: 'C', icon: '#', visible: '0', status: '0', isFrame: '1', isCache: '0' }
  },
  post: {
    title: '岗位管理',
    description: '岗位编码、岗位名称和岗位排序维护。',
    basePath: '/system/post',
    rowKey: 'postId',
    searchFields: [{ prop: 'postCode', label: '岗位编码' }, { prop: 'postName', label: '岗位名称' }],
    columns: [
      { prop: 'postId', label: 'ID', width: 90 },
      { prop: 'postCode', label: '岗位编码', minWidth: 140 },
      { prop: 'postName', label: '岗位名称', minWidth: 140 },
      { prop: 'postSort', label: '排序', width: 90 },
      statusColumn(),
      { prop: 'remark', label: '备注', minWidth: 180 }
    ],
    formFields: [
      { prop: 'postCode', label: '岗位编码' },
      { prop: 'postName', label: '岗位名称' },
      { prop: 'postSort', label: '排序', type: 'number' },
      { prop: 'status', label: '状态', type: 'select', options: statusOptions },
      { prop: 'remark', label: '备注', type: 'textarea' }
    ],
    defaults: { status: '0', postSort: 1 },
    headerActions: [
      { key: 'export', label: '导出', handler: ({ query }) => exportSystemResource('/system/post/export', query, '岗位数据.xlsx'), reload: false }
    ]
  },
  config: {
    title: '参数设置',
    description: '系统参数键值、内置标记和缓存刷新相关配置。',
    basePath: '/system/config',
    rowKey: 'configId',
    searchFields: [{ prop: 'configName', label: '参数名称' }, { prop: 'configKey', label: '参数键名' }],
    columns: [
      { prop: 'configId', label: 'ID', width: 90 },
      { prop: 'configName', label: '参数名称', minWidth: 160 },
      { prop: 'configKey', label: '参数键名', minWidth: 180 },
      { prop: 'configValue', label: '参数键值', minWidth: 180 },
      { prop: 'configType', label: '内置', width: 90, map: { Y: '是', N: '否' } },
      { prop: 'remark', label: '备注', minWidth: 180 }
    ],
    formFields: [
      { prop: 'configName', label: '参数名称' },
      { prop: 'configKey', label: '参数键名' },
      { prop: 'configValue', label: '参数键值' },
      { prop: 'configType', label: '是否内置', type: 'select', options: yesNoOptions },
      { prop: 'remark', label: '备注', type: 'textarea' }
    ],
    defaults: { configType: 'N' },
    headerActions: [
      { key: 'refresh', label: '刷新缓存', type: 'warning', confirm: '确认刷新参数缓存？', handler: refreshConfigCache, successMessage: '参数缓存已刷新' },
      { key: 'export', label: '导出', handler: ({ query }) => exportSystemResource('/system/config/export', query, '参数数据.xlsx'), reload: false }
    ]
  },
  notice: {
    title: '通知公告',
    description: '公告标题、类型、状态和正文维护。',
    basePath: '/system/notice',
    rowKey: 'noticeId',
    searchFields: [{ prop: 'noticeTitle', label: '标题' }, { prop: 'createBy', label: '创建人' }],
    columns: [
      { prop: 'noticeId', label: 'ID', width: 90 },
      { prop: 'noticeTitle', label: '标题', minWidth: 180 },
      { prop: 'noticeType', label: '类型', width: 90, map: { 1: '通知', 2: '公告' } },
      statusColumn(),
      { prop: 'createBy', label: '创建人', minWidth: 120 },
      { prop: 'createTime', label: '创建时间', minWidth: 170 }
    ],
    formFields: [
      { prop: 'noticeTitle', label: '标题' },
      { prop: 'noticeType', label: '类型', type: 'select', options: [{ label: '通知', value: '1' }, { label: '公告', value: '2' }] },
      { prop: 'status', label: '状态', type: 'select', options: statusOptions },
      { prop: 'noticeContent', label: '内容', type: 'textarea', rows: 8 }
    ],
    defaults: { noticeType: '1', status: '0' },
    operationWidth: 210
  },
  dictType: {
    title: '字典类型',
    description: '字典类型、名称和状态管理。',
    basePath: '/system/dict/type',
    rowKey: 'dictId',
    searchFields: [{ prop: 'dictName', label: '字典名称' }, { prop: 'dictType', label: '字典类型' }],
    columns: [
      { prop: 'dictId', label: 'ID', width: 90 },
      { prop: 'dictName', label: '字典名称', minWidth: 150 },
      { prop: 'dictType', label: '字典类型', minWidth: 180 },
      statusColumn(),
      { prop: 'remark', label: '备注', minWidth: 180 }
    ],
    formFields: [
      { prop: 'dictName', label: '字典名称' },
      { prop: 'dictType', label: '字典类型' },
      { prop: 'status', label: '状态', type: 'select', options: statusOptions },
      { prop: 'remark', label: '备注', type: 'textarea' }
    ],
    defaults: { status: '0' },
    headerActions: [
      { key: 'refresh', label: '刷新缓存', type: 'warning', confirm: '确认刷新字典缓存？', handler: refreshDictCache, successMessage: '字典缓存已刷新' },
      { key: 'export', label: '导出', handler: ({ query }) => exportSystemResource('/system/dict/type/export', query, '字典类型.xlsx'), reload: false }
    ],
    rowActions: [
      { key: 'data', label: '字典数据', route: (row) => `/system/dict-data/index/${row.dictId}?dictType=${encodeURIComponent(row.dictType || '')}` }
    ],
    operationWidth: 300
  },
  dictData: {
    title: '字典数据',
    description: '字典标签、键值、样式和默认值维护。',
    basePath: '/system/dict/data',
    rowKey: 'dictCode',
    searchFields: [{ prop: 'dictType', label: '字典类型' }, { prop: 'dictLabel', label: '字典标签' }],
    columns: [
      { prop: 'dictCode', label: 'ID', width: 90 },
      { prop: 'dictSort', label: '排序', width: 90 },
      { prop: 'dictLabel', label: '标签', minWidth: 150 },
      { prop: 'dictValue', label: '键值', minWidth: 120 },
      { prop: 'dictType', label: '类型', minWidth: 180 },
      { prop: 'cssClass', label: '样式属性', minWidth: 120 },
      { prop: 'listClass', label: '回显样式', minWidth: 120 },
      { prop: 'isDefault', label: '默认', width: 90, map: { Y: '是', N: '否' } },
      statusColumn()
    ],
    formFields: [
      { prop: 'dictType', label: '字典类型' },
      { prop: 'dictLabel', label: '字典标签' },
      { prop: 'dictValue', label: '字典键值' },
      { prop: 'dictSort', label: '排序', type: 'number' },
      { prop: 'cssClass', label: '样式属性' },
      { prop: 'listClass', label: '回显样式', type: 'select', options: [{ label: '默认', value: 'default' }, { label: '主要', value: 'primary' }, { label: '成功', value: 'success' }, { label: '信息', value: 'info' }, { label: '警告', value: 'warning' }, { label: '危险', value: 'danger' }] },
      { prop: 'isDefault', label: '默认值', type: 'select', options: yesNoOptions },
      { prop: 'status', label: '状态', type: 'select', options: statusOptions },
      { prop: 'remark', label: '备注', type: 'textarea' }
    ],
    defaults: { status: '0', isDefault: 'N', dictSort: 1 },
    headerActions: [
      { key: 'export', label: '导出', handler: ({ query }) => exportSystemResource('/system/dict/data/export', query, '字典数据.xlsx'), reload: false }
    ]
  },
  robot: {
    title: '机器人配置',
    description: '机器人编号、名称、IP、区域和运行状态配置。',
    basePath: '/config/robot',
    rowKey: 'id',
    searchFields: [{ prop: 'robotId', label: '机器人编号' }, { prop: 'robotName', label: '机器人名称' }, { prop: 'roomCode', label: '房间编码' }],
    columns: [
      { prop: 'id', label: 'ID', width: 90 },
      { prop: 'robotId', label: '机器人编号', minWidth: 140 },
      { prop: 'robotName', label: '名称', minWidth: 150 },
      { prop: 'robotIp', label: 'IP', minWidth: 140 },
      { prop: 'deptName', label: '贵宾室', minWidth: 150 },
      { prop: 'region.regionName', label: '当前位置', minWidth: 140 },
      { prop: 'batteryState', label: '电量', width: 90 },
      { prop: 'workingState', label: '工作', width: 90, map: { 0: '空闲', 1: '工作中' } },
      { prop: 'enable', label: '启用', width: 90, tag: 'info', tagMap: { 1: 'success', 0: 'danger' }, map: { 1: '启用', 0: '停用' } }
    ],
    formFields: async () => {
      const options = await loadConfigOptions()
      return [
        { prop: 'robotId', label: '机器人编号' },
        { prop: 'robotName', label: '机器人名称' },
        { prop: 'mac', label: 'MAC 地址' },
        { prop: 'robotIp', label: 'IP 地址' },
        { prop: 'roomCode', label: '贵宾室', type: 'select', options: options.rooms },
        { prop: 'regionId', label: '当前位置', type: 'select', options: options.regions },
        { prop: 'robotType', label: '机器人型号' },
        { prop: 'belongedCompany', label: '所属公司' },
        { prop: 'batteryState', label: '电量', type: 'number' },
        { prop: 'network', label: '网络状态', type: 'number' },
        { prop: 'chargingState', label: '充电状态', type: 'select', options: [{ label: '未充电', value: '0' }, { label: '充电中', value: '1' }] },
        { prop: 'workingState', label: '工作状态', type: 'select', options: [{ label: '空闲', value: '0' }, { label: '工作中', value: '1' }] },
        { prop: 'standbyState', label: '待机状态', type: 'select', options: [{ label: '否', value: '0' }, { label: '是', value: '1' }] },
        { prop: 'positioningState', label: '定位状态' },
        { prop: 'enable', label: '启用状态', type: 'select', options: enableOptions },
        { prop: 'imgIds', label: '图片资源', type: 'select', multiple: true, joinArray: true, options: options.images },
        { prop: 'auditKeys', label: '音频资源', type: 'select', multiple: true, joinArray: true, options: options.audios },
        { prop: 'oriCoordinate', label: '初始坐标' },
        { prop: 'adminMode', label: '管理模式' },
        { prop: 'remark', label: '备注', type: 'textarea' }
      ]
    },
    defaults: { enable: 1, batteryState: 100, chargingState: '0', workingState: '0', standbyState: '1', regionId: 0, isDelete: '0' },
    transformDetail: (response, row) => {
      const data = response.data || row
      return {
        ...data,
        imgIds: parseIds(data.imgIds),
        auditKeys: parseIds(data.auditKeys)
      }
    }
  },
  image: {
    title: '图片管理',
    description: '贵宾室图片、地图底图和展示资源管理。',
    basePath: '/config/img',
    rowKey: 'id',
    searchFields: [{ prop: 'imgName', label: '图片名称' }, { prop: 'imgType', label: '图片类别' }, { prop: 'roomCode', label: '房间编码' }],
    columns: [
      { prop: 'id', label: 'ID', width: 90 },
      { prop: 'id', label: '预览', width: 90, image: true, imageUrl: (row) => `/api/rest/image/config/${row.id}` },
      { prop: 'imgName', label: '名称', minWidth: 160 },
      { prop: 'imgType', label: '类别', minWidth: 120 },
      { prop: 'deptName', label: '贵宾室', minWidth: 150 },
      { prop: 'width', label: '宽', width: 90 },
      { prop: 'height', label: '高', width: 90 },
      { prop: 'enable', label: '启用', width: 90, tag: 'info', tagMap: { 1: 'success', 0: 'danger' }, map: { 1: '启用', 0: '停用' } }
    ],
    formFields: async () => {
      const options = await loadConfigOptions()
      return [
        { prop: 'imgName', label: '图片名称' },
        { prop: 'imgType', label: '图片类别', type: 'select', options: [{ label: '地图/区域', value: '1' }, { label: '引导展示', value: '2' }, { label: '餐食图片', value: '3' }, { label: '其他', value: '9' }] },
        { prop: 'roomCode', label: '贵宾室', type: 'select', options: options.rooms },
        { prop: 'enable', label: '启用状态', type: 'select', options: enableOptions },
        { prop: 'width', label: '宽度', type: 'number' },
        { prop: 'height', label: '高度', type: 'number' },
        { prop: 'img', label: '图片文件', type: 'imageBase64', nameProp: 'imgName' },
        { prop: 'remark', label: '备注', type: 'textarea' }
      ]
    },
    defaults: { imgType: '1', enable: 1, width: 0, height: 0, isDelete: '0' }
  },
  audio: {
    title: '机器人语音',
    description: '机器人语音资源、音频 Key 和播放内容管理。',
    basePath: '/config/robotAudio',
    rowKey: 'id',
    searchFields: [{ prop: 'audioKey', label: '音频 Key' }, { prop: 'languageType', label: '语言', type: 'select', options: languageOptions }, { prop: 'roomCode', label: '房间编码' }],
    columns: [
      { prop: 'id', label: 'ID', width: 90 },
      { prop: 'audioKey', label: 'Key', minWidth: 150 },
      { prop: 'languageType', label: '语言', width: 90 },
      { prop: 'textInfo', label: '文字内容', minWidth: 240 },
      { prop: 'audioValue', label: '音频内容', minWidth: 180 },
      { prop: 'deptName', label: '贵宾室', minWidth: 150 }
    ],
    formFields: async () => {
      const options = await loadConfigOptions()
      return [
        { prop: 'audioKey', label: '音频 Key' },
        { prop: 'languageType', label: '语言', type: 'select', options: languageOptions },
        { prop: 'roomCode', label: '贵宾室', type: 'select', options: options.rooms },
        { prop: 'textInfo', label: '文字内容', type: 'textarea', rows: 5 },
        { prop: 'audioValue', label: '音频内容/mock 返回', type: 'textarea', rows: 3 },
        { prop: 'remark', label: '备注', type: 'textarea' }
      ]
    },
    defaults: { languageType: 'CN' }
  },
  region: {
    title: '贵宾室区域',
    description: '贵宾室区域编码、坐标和展示状态管理。',
    basePath: '/config/region',
    rowKey: 'id',
    searchFields: [{ prop: 'regionName', label: '区域名称' }, { prop: 'roomCode', label: '房间编码' }],
    columns: [
      { prop: 'id', label: 'ID', width: 90 },
      { prop: 'regionName', label: '区域名称', minWidth: 160 },
      { prop: 'deptName', label: '贵宾室', minWidth: 150 },
      { prop: 'coordinate', label: '坐标', minWidth: 220 },
      { prop: 'areaName', label: '功能区', minWidth: 140 },
      { prop: 'maxCapacity', label: '容量', width: 90 },
      { prop: 'isGuide', label: '引导', width: 90, map: { 1: '支持', 0: '不支持' } },
      { prop: 'isShow', label: '展示', width: 90, map: { 1: '展示', 0: '隐藏' } }
    ],
    formFields: async () => {
      const options = await loadConfigOptions()
      return [
        { prop: 'regionName', label: '区域名称' },
        { prop: 'roomCode', label: '贵宾室', type: 'select', options: options.rooms },
        { prop: 'areaId', label: '功能区', type: 'select', options: options.areas },
        { prop: 'coordinate', label: '区域坐标' },
        { prop: 'maxCapacity', label: '最大容量', type: 'number' },
        { prop: 'imgIds', label: '图片资源', type: 'select', multiple: true, joinArray: true, options: options.images },
        { prop: 'audioKeys', label: '音频资源', type: 'select', multiple: true, joinArray: true, options: options.audios },
        { prop: 'isGuide', label: '支持引导', type: 'select', options: guideOptions },
        { prop: 'isShow', label: '是否展示', type: 'select', options: showOptions },
        { prop: 'enable', label: '启用状态', type: 'select', options: enableOptions },
        { prop: 'remark', label: '备注', type: 'textarea' }
      ]
    },
    defaults: { isShow: '1', isGuide: '0', enable: 1, maxCapacity: 0 },
    transformDetail: (response, row) => {
      const data = response.data || row
      return {
        ...data,
        imgIds: parseIds(data.imgIds),
        audioKeys: parseIds(data.audioKeys)
      }
    }
  },
  area: {
    title: '功能区管理',
    description: '贵宾室功能区、容量、图片和语音配置。',
    basePath: '/config/area',
    rowKey: 'id',
    searchFields: [{ prop: 'areaName', label: '功能区名称' }, { prop: 'roomCode', label: '房间编码' }],
    columns: [
      { prop: 'id', label: 'ID', width: 90 },
      { prop: 'areaName', label: '功能区', minWidth: 160 },
      { prop: 'deptName', label: '贵宾室', minWidth: 150 },
      { prop: 'coordinate', label: '坐标', minWidth: 180 },
      { prop: 'maxCapacity', label: '容量', width: 90 },
      { prop: 'imgIds', label: '图片', minWidth: 120 },
      { prop: 'isGuide', label: '引导', width: 90, map: { 1: '支持', 0: '不支持' } },
      { prop: 'isShow', label: '展示', width: 90, map: { 1: '展示', 0: '隐藏' } }
    ],
    formFields: async () => {
      const options = await loadConfigOptions()
      return [
        { prop: 'areaName', label: '功能区名称' },
        { prop: 'roomCode', label: '贵宾室', type: 'select', options: options.rooms },
        { prop: 'coordinate', label: '坐标' },
        { prop: 'maxCapacity', label: '最大容量', type: 'number' },
        { prop: 'imgIds', label: '图片资源', type: 'select', multiple: true, joinArray: true, options: options.images },
        { prop: 'isGuide', label: '支持引导', type: 'select', options: guideOptions },
        { prop: 'isShow', label: '是否展示', type: 'select', options: showOptions },
        {
          prop: 'configAreaDetailList',
          label: '多语言明细',
          type: 'editableList',
          defaultValue: defaultAreaDetails,
          newRow: () => ({ languageType: 'CN', areaName: '', label: '', remark: '', arrText: '' }),
          children: [
            { prop: 'languageType', label: '语言', type: 'select', options: languageOptions, minWidth: 110 },
            { prop: 'areaName', label: '功能区名称', minWidth: 150 },
            { prop: 'label', label: '标签', minWidth: 130 },
            { prop: 'remark', label: '播报文本', type: 'textarea', minWidth: 220 },
            { prop: 'arrText', label: '到达文本', type: 'textarea', minWidth: 220 }
          ]
        },
        { prop: 'remark', label: '备注', type: 'textarea' }
      ]
    },
    defaults: { maxCapacity: 0, isShow: '1', isGuide: '0' },
    transformDetail: (response, row) => {
      const data = response.data || row
      return {
        ...data,
        imgIds: parseIds(data.imgIds),
        configAreaDetailList: data.configAreaDetailList?.length ? data.configAreaDetailList : defaultAreaDetails()
      }
    }
  },
  device: {
    title: '监控设备',
    description: '摄像头、监控设备编码、IP 和区域绑定维护。',
    basePath: '/config/device',
    rowKey: 'id',
    searchFields: [{ prop: 'deviceName', label: '设备名称' }, { prop: 'deviceType', label: '设备类型' }, { prop: 'roomCode', label: '房间编码' }],
    columns: [
      { prop: 'id', label: 'ID', width: 90 },
      { prop: 'deviceName', label: '设备名称', minWidth: 160 },
      { prop: 'deviceType', label: '设备类型', minWidth: 120 },
      { prop: 'deepGlintDeviceId', label: '格灵设备 ID', minWidth: 160 },
      { prop: 'deptName', label: '贵宾室', minWidth: 150 },
      { prop: 'enable', label: '启用', width: 90, tag: 'info', tagMap: { 1: 'success', 0: 'danger' }, map: { 1: '启用', 0: '停用' } }
    ],
    formFields: async () => {
      const options = await loadConfigOptions()
      return [
        { prop: 'deviceName', label: '设备名称' },
        { prop: 'deviceType', label: '设备类型', type: 'select', options: deviceTypeOptions },
        { prop: 'deepGlintDeviceId', label: '格灵摄像头 ID' },
        { prop: 'roomCode', label: '贵宾室', type: 'select', options: options.rooms },
        { prop: 'enable', label: '启用状态', type: 'select', options: enableOptions },
        { prop: 'remark', label: '备注', type: 'textarea' }
      ]
    },
    defaults: { enable: 1, isDelete: '0', deviceType: 'camera' },
    rowActions: [
      {
        key: 'bindRegion',
        label: '区域绑定',
        promptTitle: '设备区域绑定',
        promptDefaults: (row) => ({ deviceId: row.id }),
        promptFields: async (row) => {
          const options = await loadConfigOptions()
          const current = await listDeviceRegions(row.id)
          const first = current.rows?.[0] || current.data?.[0] || {}
          return [
            { prop: 'deviceId', label: '设备 ID', type: 'number', defaultValue: row.id },
            { prop: 'regionId', label: '区域', type: 'select', options: options.regions, defaultValue: first.regionId },
            { prop: 'imgId', label: '关联图片', type: 'select', options: options.images, defaultValue: first.imgId },
            { prop: 'coordinate', label: '设备坐标', defaultValue: first.coordinate || '' }
          ]
        },
        handler: async (row, { form }) => {
          const payload = { ...form, deviceId: row.id }
          const current = payload.regionId ? await getDeviceRegion(row.id, payload.regionId) : null
          if (current?.data) {
            return updateDeviceRegion(payload)
          }
          return addDeviceRegion(payload)
        },
        successMessage: '设备区域绑定已保存'
      },
      {
        key: 'clearRegion',
        label: '清空绑定',
        type: 'danger',
        confirm: (row) => `确认清空设备"${row.deviceName}"的区域绑定？`,
        handler: async (row) => {
          const current = await listDeviceRegions(row.id)
          const binds = current.rows || current.data || []
          await Promise.all(binds.map((bind) => deleteDeviceRegion({ deviceId: row.id, regionId: bind.regionId })))
        },
        successMessage: '设备区域绑定已清空'
      }
    ],
    operationWidth: 340
  },
  table: {
    title: '餐桌配置',
    description: '桌号、坐标、区域和翻台状态管理。',
    basePath: '/config/table',
    rowKey: 'id',
    searchFields: [{ prop: 'tableNo', label: '桌号' }, { prop: 'roomCode', label: '房间编码' }],
    columns: [
      { prop: 'id', label: 'ID', width: 90 },
      { prop: 'tableNo', label: '桌号', minWidth: 120 },
      { prop: 'deptName', label: '贵宾室', minWidth: 150 },
      { prop: 'regionName', label: '区域', minWidth: 140 },
      { prop: 'cameraCoordinates', label: '坐标', minWidth: 220 },
      { prop: 'status', label: '占用状态', width: 100, map: { 0: '空闲', 1: '翻台' } },
      { prop: 'isEnable', label: '可用', width: 90, map: { 1: '可用', 0: '停用' } }
    ],
    formFields: async () => {
      const options = await loadConfigOptions()
      return [
        { prop: 'tableNo', label: '桌号' },
        { prop: 'roomCode', label: '贵宾室', type: 'select', options: options.rooms },
        { prop: 'regionId', label: '区域', type: 'select', options: options.regions },
        { prop: 'deviceId', label: '摄像头设备', type: 'select', options: options.devices },
        { prop: 'cameraCoordinates', label: '摄像头坐标' },
        { prop: 'isEnable', label: '是否可用', type: 'select', options: [{ label: '可用', value: '1' }, { label: '停用', value: '0' }] },
        { prop: 'status', label: '占用状态', type: 'select', options: [{ label: '空闲', value: '0' }, { label: '翻台', value: '1' }] }
      ]
    },
    defaults: { status: '0', isEnable: '1' }
  },
  task: {
    title: '任务配置',
    description: '机器人任务、任务状态和执行目标管理。',
    basePath: '/config/task',
    rowKey: 'id',
    searchFields: [{ prop: 'taskName', label: '任务名称' }, { prop: 'robotId', label: '机器人' }],
    columns: [
      { prop: 'id', label: 'ID', width: 90 },
      { prop: 'taskName', label: '任务名称', minWidth: 160 },
      { prop: 'robotId', label: '机器人', minWidth: 130 },
      { prop: 'commandCn', label: '指令', minWidth: 140 },
      { prop: 'executeType', label: '执行类型', minWidth: 120 },
      { prop: 'deptName', label: '贵宾室', minWidth: 150 },
      { prop: 'enable', label: '启用', width: 90, map: { 1: '启用', 0: '停用' } },
      { prop: 'createTime', label: '创建时间', minWidth: 170 }
    ],
    formFields: async () => {
      const options = await loadConfigOptions()
      return [
        { prop: 'taskName', label: '任务名称' },
        { prop: 'robotId', label: '机器人', type: 'select', options: options.robots },
        { prop: 'command', label: '指令编码', type: 'number' },
        { prop: 'commandCn', label: '指令中文' },
        { prop: 'region', label: '执行区域' },
        { prop: 'priority', label: '优先级', type: 'select', options: [{ label: '低', value: '1' }, { label: '中', value: '5' }, { label: '高', value: '9' }] },
        { prop: 'executeType', label: '执行类型', type: 'select', options: executeTypeOptions },
        { prop: 'executeDay', label: '执行日期' },
        { prop: 'executeTime', label: '执行时间', type: 'datetime' },
        { prop: 'roomCode', label: '贵宾室', type: 'select', options: options.rooms },
        { prop: 'imgIds', label: '任务图片', type: 'select', multiple: true, joinArray: true, options: options.images },
        { prop: 'auditIds', label: '任务音频', type: 'select', multiple: true, joinArray: true, options: options.audios },
        { prop: 'taskType', label: '任务类别', type: 'select', options: [{ label: '一次性任务', value: '0' }, { label: '持续任务', value: '1' }] },
        { prop: 'taskSubtype', label: '任务子类型', type: 'select', options: taskSubtypeOptions },
        { prop: 'taskMode', label: '任务模式', type: 'select', options: [{ label: '后台模式', value: '0' }, { label: '前台模式', value: '1' }] },
        { prop: 'directExecution', label: '队列属性', type: 'select', options: [{ label: '排队执行', value: '0' }, { label: '直接执行', value: '1' }] },
        { prop: 'isReturn', label: '返回结果', type: 'select', options: [{ label: '不返回', value: '0' }, { label: '返回', value: '1' }] },
        { prop: 'enable', label: '启用状态', type: 'select', options: enableOptions },
        { prop: 'remark', label: '备注', type: 'textarea' }
      ]
    },
    defaults: { enable: 1, priority: '5', executeType: 'immediately', isReturn: '1', taskType: '0', taskSubtype: '0', taskMode: '0', directExecution: '0', isDelete: '0' },
    transformDetail: (response, row) => {
      const data = response.data || row
      return {
        ...data,
        imgIds: parseIds(data.imgIds),
        auditIds: parseIds(data.auditIds)
      }
    },
    rowActions: [
      {
        key: 'run',
        label: '执行',
        type: 'warning',
        confirm: (row) => `确认执行任务"${row.taskName}"？`,
        handler: (row) => runConfigTask(row.id),
        successMessage: '任务已提交执行'
      }
    ],
    operationWidth: 300
  },
  complaint: {
    title: '投诉记录',
    description: '旅客客诉、发卡方、卡号、客诉内容和处理反馈维护。',
    basePath: '/flight/complaint',
    rowKey: 'id',
    searchFields: [{ prop: 'userName', label: '姓名' }],
    columns: [
      { prop: 'id', label: 'ID', width: 90 },
      { prop: 'userName', label: '姓名', minWidth: 130 },
      { prop: 'roomCode', label: '贵宾室', minWidth: 130 },
      { prop: 'cardService', label: '发卡方', minWidth: 130 },
      { prop: 'cardNo', label: '卡号', minWidth: 140 },
      { prop: 'complaintContent', label: '客诉内容', minWidth: 260 },
      { prop: 'complaintFeedback', label: '客诉反馈', minWidth: 260 },
      { prop: 'createTime', label: '创建时间', minWidth: 170 }
    ],
    formFields: async () => {
      const options = await loadConfigOptions()
      return [
        { prop: 'userName', label: '旅客姓名' },
        { prop: 'roomCode', label: '贵宾室', type: 'select', options: options.rooms },
        { prop: 'cardService', label: '发卡方' },
        { prop: 'cardNo', label: '卡号' },
        { prop: 'complaintContent', label: '客诉内容', type: 'textarea', rows: 3 },
        { prop: 'complaintFeedback', label: '客诉反馈', type: 'textarea', rows: 3 }
      ]
    },
    defaults: { roomCode: 'PEK2DX1' }
  },
  msg: {
    title: '消息日志',
    description: '通知、机器人消息和后台处理消息记录。',
    basePath: '/config/msg',
    rowKey: 'id',
    searchFields: [{ prop: 'title', label: '标题' }, { prop: 'source', label: '来源' }, { prop: 'status', label: '状态', type: 'select', options: messageStatusOptions }],
    columns: [
      { prop: 'id', label: 'ID', width: 90 },
      { prop: 'title', label: '标题', minWidth: 180 },
      { prop: 'source', label: '来源', minWidth: 120 },
      { prop: 'roomCode', label: '房间编码', minWidth: 130 },
      { prop: 'processor', label: '处理人', minWidth: 120 },
      { prop: 'status', label: '状态', width: 110, tag: 'info', tagMap: { 0: 'warning', 1: 'primary', 2: 'success', 3: 'info' }, map: messageStatusMap },
      { prop: 'content', label: '内容', minWidth: 260 },
      { prop: 'createTime', label: '时间', minWidth: 170 }
    ],
    formFields: [
      { prop: 'title', label: '标题' },
      { prop: 'source', label: '来源' },
      { prop: 'roomCode', label: '房间编码' },
      { prop: 'processor', label: '处理人' },
      { prop: 'status', label: '状态', type: 'select', options: messageStatusOptions },
      { prop: 'content', label: '内容', type: 'textarea' }
    ],
    defaults: { status: '0', source: 'admin' }
  },
  foodConfig: {
    title: '菜品管理',
    description: '菜品名称、分类、价格、热量、图片和贵宾室维度管理。',
    basePath: '/food',
    listPath: '/food/selectFoodConfigList',
    listMethod: 'POST',
    createPath: '/food/insertFoodConfig',
    updatePath: '/food/updateFoodConfig',
    deletePath: '/food/deleteFoodConfigByFoodIds',
    deleteMethod: 'POST',
    detailLoader: (row) => getFoodConfig(row.foodId),
    rowKey: 'foodId',
    searchFields: [{ prop: 'name', label: '菜品名称' }, { prop: 'dicTypeCode', label: '分类' }, { prop: 'roomCode', label: '房间编码' }],
    columns: [
      { prop: 'foodId', label: 'ID', width: 90 },
      { prop: 'imgIds', label: '图片', width: 90, image: true, imageUrl: (row) => row.imgUrlList?.[0] || (parseIds(row.imgIds)[0] ? `/api/rest/image/config/${parseIds(row.imgIds)[0]}` : '') },
      { prop: 'name', label: '名称', minWidth: 160 },
      { prop: 'dicTypeCode', label: '分类', minWidth: 120 },
      { prop: 'deptName', label: '贵宾室', minWidth: 150 },
      { prop: 'price', label: '价格', width: 100 },
      { prop: 'calorie', label: '热量', width: 100 },
      { prop: 'remark', label: '备注', minWidth: 180 }
    ],
    formFields: async () => {
      const options = await loadConfigOptions()
      return [
        { prop: 'name', label: '菜品名称' },
        { prop: 'dicTypeCode', label: '分类', type: 'select', options: foodTypeOptions },
        { prop: 'roomCode', label: '贵宾室', type: 'select', options: options.rooms },
        { prop: 'imgIds', label: '菜品图片', type: 'select', multiple: true, joinArray: true, options: options.images },
        { prop: 'price', label: '价格', type: 'number' },
        { prop: 'calorie', label: '热量', type: 'number' },
        { prop: 'remark', label: '备注', type: 'textarea' }
      ]
    },
    defaults: { price: 0, calorie: 0 },
    transformDetail: (response, row) => {
      const data = response.data || row
      return {
        ...data,
        imgIds: parseIds(data.imgIds)
      }
    }
  },
  foodDaily: {
    title: '今日菜单',
    description: '按日期、贵宾室和菜品维护每日上架菜单。',
    basePath: '/food',
    listPath: '/food/selectFoodDailyList',
    listMethod: 'POST',
    createPath: '/food/insertFoodDaily',
    updatePath: '/food/updateFoodDaily',
    updateMethod: 'POST',
    deletePath: '/food/deleteFoodDaily',
    deleteMethod: 'POST',
    detailLoader: (row) => getFoodDaily(row.id),
    rowKey: 'id',
    searchFields: [
      { prop: 'foodDate', label: '日期', type: 'date' },
      { prop: 'roomCode', label: '房间编码' },
      { prop: 'status', label: '状态', type: 'select', options: dailyMenuStatusOptions }
    ],
    columns: [
      { prop: 'id', label: 'ID', width: 90 },
      { prop: 'foodDate', label: '日期', minWidth: 120 },
      { prop: 'foodName', label: '菜品', minWidth: 160 },
      { prop: 'foodType', label: '分类', minWidth: 110 },
      { prop: 'deptName', label: '贵宾室', minWidth: 150 },
      { prop: 'price', label: '价格', width: 90 },
      { prop: 'calorie', label: '热量', width: 90 },
      { prop: 'status', label: '状态', width: 100, tag: 'info', tagMap: { 1: 'success', 0: 'danger' }, map: { 1: '上架', 0: '下架' } }
    ],
    formFields: async () => {
      const options = await loadFoodOptions()
      return [
        { prop: 'foodDate', label: '日期', type: 'date' },
        { prop: 'roomCode', label: '贵宾室', type: 'select', options: options.rooms },
        { prop: 'foodId', label: '菜品', type: 'select', options: options.foodOptions },
        { prop: 'status', label: '状态', type: 'select', options: dailyMenuStatusOptions }
      ]
    },
    defaults: { status: '1' }
  },
  foodPlan: {
    title: '菜单计划',
    description: '贵宾室餐食计划、日期和房间维度管理。',
    basePath: '/food',
    listPath: '/food/selectFoodPlanList',
    listMethod: 'POST',
    createPath: '/food/insertFoodPlan',
    updatePath: '/food/updateFoodPlan',
    updateMethod: 'POST',
    deletePath: '/food/deleteFoodPlan',
    deleteMethod: 'POST',
    detailLoader: (row) => getFoodPlan(row.id),
    rowKey: 'id',
    searchFields: [{ prop: 'roomCode', label: '房间编码' }, { prop: 'startDay', label: '开始日期', type: 'date' }, { prop: 'endDay', label: '结束日期', type: 'date' }],
    columns: [
      { prop: 'id', label: 'ID', width: 90 },
      { prop: 'roomCode', label: '房间编码', minWidth: 130 },
      { prop: 'deptName', label: '贵宾室', minWidth: 160 },
      { prop: 'startDay', label: '开始日期', minWidth: 120 },
      { prop: 'endDay', label: '结束日期', minWidth: 120 },
      { prop: 'foodNames', label: '菜品', minWidth: 260 }
    ],
    formFields: async () => {
      const options = await loadFoodOptions()
      return [
        { prop: 'roomCode', label: '贵宾室', type: 'select', options: options.rooms },
        { prop: 'startDay', label: '开始日期', type: 'date' },
        { prop: 'endDay', label: '结束日期', type: 'date' },
        {
          prop: 'foodIds',
          label: '计划菜品',
          type: 'select',
          multiple: true,
          joinArray: true,
          options: options.foodOptions,
          onChange: (value, { form }) => {
            form.foodNames = (value || [])
              .map((id) => options.foodOptions.find((option) => String(option.value) === String(id))?.raw?.name)
              .filter(Boolean)
              .join(',')
          }
        },
        { prop: 'foodNames', label: '菜品名称', hidden: () => true }
      ]
    },
    transformDetail: (response, row) => {
      const data = response.data || row
      return {
        ...data,
        foodIds: parseIds(data.foodIds)
      }
    },
    beforeSubmit: (payload) => ({
      ...payload,
      foodIds: Array.isArray(payload.foodIds) ? payload.foodIds.join(',') : payload.foodIds,
      foodNames: payload.foodNames || ''
    })
  },
  foodOrder: {
    title: '订单管理',
    description: '点餐订单、桌号、旅客和订单状态管理。',
    basePath: '/food',
    listPath: '/food/queryOrderList',
    listMethod: 'POST',
    createPath: '/food/createOrder',
    createMethod: 'POST',
    deletePath: '/food/deleteOrder',
    deleteMethod: 'POST',
    detailLoader: (row) => getFoodOrder(row.id),
    rowKey: 'id',
    enableCreate: true,
    enableEdit: false,
    searchFields: [{ prop: 'orderCode', label: '订单号' }, { prop: 'status', label: '状态', type: 'select', options: orderStatusOptions }, { prop: 'roomCode', label: '房间编码' }],
    columns: [
      { prop: 'id', label: 'ID', width: 90 },
      { prop: 'orderCode', label: '订单号', minWidth: 160 },
      { prop: 'deskNo', label: '桌号', minWidth: 100 },
      { prop: 'roomCode', label: '房间编码', minWidth: 130 },
      { prop: 'cardNo', label: '会员卡号', minWidth: 130 },
      { prop: 'orderPrice', label: '金额', width: 100 },
      { prop: 'status', label: '状态', width: 100, tag: 'info', tagMap: { 0: 'danger', 1: 'warning', 2: 'primary', 3: 'success' }, map: orderStatusMap },
      { prop: 'createTime', label: '创建时间', minWidth: 170 },
      { prop: 'remark', label: '备注', minWidth: 160 }
    ],
    formFields: async () => {
      const options = await loadFoodOptions()
      return [
        { prop: 'tableId', label: '餐桌', type: 'select', options: options.tables },
        { prop: 'cardNo', label: '会员卡号' },
        {
          prop: 'orderDetailList',
          label: '订单明细',
          type: 'editableList',
          defaultValue: () => [{ foodId: null, num: 1, price: 0, foodName: '' }],
          newRow: () => ({ foodId: null, num: 1, price: 0, foodName: '' }),
          children: [
            {
              prop: 'foodId',
              label: '菜品',
              type: 'select',
              options: options.foodOptions,
              minWidth: 220,
              onChange: (value, row) => {
                const food = options.foodOptions.find((option) => String(option.value) === String(value))?.raw
                row.foodName = food?.name || ''
                row.price = food?.price ?? 0
              }
            },
            { prop: 'foodName', label: '菜品名称', minWidth: 160 },
            { prop: 'num', label: '数量', type: 'number', minWidth: 110 },
            { prop: 'price', label: '单价', type: 'number', minWidth: 120 }
          ]
        },
        { prop: 'remark', label: '备注', type: 'textarea' }
      ]
    },
    beforeSubmit: (payload) => ({
      ...payload,
      orderDetailList: (payload.orderDetailList || []).filter((item) => item.foodId && Number(item.num || 0) > 0)
    }),
    detailTables: [
      {
        title: '订单明细',
        prop: 'orderDetailList',
        columns: [
          { prop: 'foodName', label: '菜品', minWidth: 160 },
          { prop: 'num', label: '数量', width: 90 },
          { prop: 'price', label: '单价', width: 100 }
        ]
      }
    ],
    rowActions: [
      {
        key: 'receive',
        label: '接单',
        type: 'warning',
        disabled: (row) => String(row.status) !== '1',
        handler: (row) => receiveFoodOrder(row.id),
        successMessage: '订单已接单'
      },
      {
        key: 'finish',
        label: '完成',
        type: 'success',
        disabled: (row) => !['1', '2'].includes(String(row.status)),
        handler: (row) => finishFoodOrder(row.id),
        successMessage: '订单已完成'
      },
      {
        key: 'cancel',
        label: '取消',
        type: 'danger',
        disabled: (row) => !['1'].includes(String(row.status)),
        confirm: (row) => `确认取消订单"${row.orderCode}"？`,
        handler: (row) => cancelFoodOrder(row.id),
        successMessage: '订单已取消'
      }
    ],
    operationWidth: 360
  },
  passenger: {
    title: '在厅旅客',
    description: '旅客、航班、区域和入厅状态统计。',
    basePath: '/flight/passenger',
    listPath: '/flight/passenger/inLoungeList',
    rowKey: 'id',
    searchFields: [{ prop: 'userName', label: '旅客姓名' }, { prop: 'flightNo', label: '航班号' }, { prop: 'roomCode', label: '房间编码' }],
    columns: [
      { prop: 'id', label: 'ID', width: 90 },
      { prop: 'userName', label: '旅客', minWidth: 130 },
      { prop: 'flightNo', label: '航班', minWidth: 120 },
      { prop: 'flightDate', label: '航班日期', minWidth: 120 },
      { prop: 'regionId', label: '区域 ID', width: 100 },
      { prop: 'coordinate', label: '坐标', minWidth: 180 },
      { prop: 'createTime', label: '入厅时间', minWidth: 170 },
      { prop: 'updateTime', label: '更新时间', minWidth: 170 }
    ],
    formFields: async () => {
      const options = await loadConfigOptions()
      return [
        { prop: 'userName', label: '旅客姓名' },
        { prop: 'roomCode', label: '贵宾室', type: 'select', options: options.rooms },
        { prop: 'flightNo', label: '航班号' },
        { prop: 'flightDate', label: '航班日期', type: 'date' },
        { prop: 'regionId', label: '区域', type: 'select', options: options.regions },
        { prop: 'inType', label: '入厅类型' },
        { prop: 'status', label: '状态', type: 'select', options: passengerStatusOptions }
      ]
    },
    defaults: { status: '1', isMember: '0' },
    rowActions: [
      {
        key: 'checkout',
        label: '出厅',
        type: 'warning',
        confirm: (row) => `确认将"${row.userName}"标记为出厅？`,
        handler: (row) => checkoutPassenger(row.id),
        successMessage: '旅客已出厅'
      }
    ],
    operationWidth: 300
  },
  passengerAll: {
    title: '旅客信息',
    description: '旅客基础信息、航班、会员和区域记录维护。',
    basePath: '/flight/passenger',
    rowKey: 'id',
    searchFields: [{ prop: 'userName', label: '旅客姓名' }, { prop: 'flightNo', label: '航班号' }, { prop: 'status', label: '状态', type: 'select', options: passengerStatusOptions }],
    columns: [
      { prop: 'id', label: 'ID', width: 90 },
      { prop: 'userName', label: '旅客', minWidth: 130 },
      { prop: 'roomCode', label: '房间编码', minWidth: 130 },
      { prop: 'flightNo', label: '航班', minWidth: 120 },
      { prop: 'flightDate', label: '航班日期', minWidth: 120 },
      { prop: 'orig', label: '始发', width: 90 },
      { prop: 'dest', label: '到达', width: 90 },
      { prop: 'seat', label: '座位', width: 90 },
      { prop: 'status', label: '状态', width: 100, tag: 'info', tagMap: { 1: 'success', 0: 'info' }, map: passengerStatusMap },
      { prop: 'getInTime', label: '入厅时间', minWidth: 170 },
      { prop: 'getOutTime', label: '出厅时间', minWidth: 170 }
    ],
    formFields: async () => {
      const options = await loadConfigOptions()
      return [
        { prop: 'userName', label: '旅客姓名' },
        { prop: 'roomCode', label: '贵宾室', type: 'select', options: options.rooms },
        { prop: 'flightNo', label: '航班号' },
        { prop: 'flightDate', label: '航班日期', type: 'date' },
        { prop: 'flightId', label: '航班 ID' },
        { prop: 'orig', label: '始发地' },
        { prop: 'dest', label: '目的地' },
        { prop: 'cabin', label: '舱位' },
        { prop: 'seat', label: '座位' },
        { prop: 'cardNo', label: '会员卡号' },
        { prop: 'memLevel', label: '会员等级' },
        { prop: 'regionId', label: '区域', type: 'select', options: options.regions },
        { prop: 'coordinate', label: '坐标' },
        { prop: 'status', label: '状态', type: 'select', options: passengerStatusOptions },
        { prop: 'remark', label: '备注', type: 'textarea' }
      ]
    },
    defaults: { status: '1', isMember: '0' }
  },
  outgoingPassenger: {
    title: '出厅旅客',
    description: '旅客出厅、离开贵宾室和航班记录。',
    basePath: '/flight/passenger',
    listPath: '/flight/passenger/outgoingList',
    rowKey: 'userName',
    enableCreate: false,
    enableEdit: false,
    enableDelete: false,
    showDetail: false,
    searchFields: [{ prop: 'userName', label: '旅客姓名' }, { prop: 'flightNo', label: '航班号' }, { prop: 'orig', label: '始发' }, { prop: 'dest', label: '到达' }],
    columns: [
      { prop: 'userName', label: '旅客', minWidth: 130 },
      { prop: 'flightNo', label: '航班', minWidth: 120 },
      { prop: 'flightDate', label: '航班日期', minWidth: 120 },
      { prop: 'oriImageUrl', label: '抓拍图', width: 100, image: true },
      { prop: 'registerImageUrl', label: '注册图', width: 100, image: true },
      { prop: 'getOutTime', label: '出厅时间', minWidth: 170 }
    ],
    formFields: []
  },
  flightInfo: {
    title: '航班信息',
    description: '航班计划、状态、登机口、行李转盘和临时航班维护。',
    basePath: '/flight/flightinfo',
    rowKey: 'flightId',
    importAction: importTemporaryFlights,
    headerActions: [
      { key: 'import', label: '导入临时航班', kind: 'import' },
      { key: 'export', label: '导出', handler: ({ query }) => exportSystemResource('/flight/flightinfo/export', query, '航班信息.xlsx'), reload: false }
    ],
    searchFields: [{ prop: 'flightNo', label: '航班号' }, { prop: 'scheExecDate', label: '航班日期', type: 'date' }, { prop: 'latestOffStatus', label: '起飞状态', type: 'select', options: flightOffStatusOptions }],
    columns: [
      { prop: 'flightId', label: '航班 ID', minWidth: 170 },
      { prop: 'flightNo', label: '航班号', minWidth: 120 },
      { prop: 'scheExecDate', label: '执行日期', minWidth: 120 },
      { prop: 'airlineCd', label: '航司', width: 90 },
      { prop: 'latestOffStatus', label: '起飞状态', minWidth: 110 },
      { prop: 'latestOnStatus', label: '到达状态', minWidth: 110 },
      { prop: 'gateCd', label: '登机口', width: 100 },
      { prop: 'carouselCd', label: '转盘', width: 90 },
      { prop: 'estmTakeOffTime', label: '预计起飞', minWidth: 150 },
      { prop: 'updateTime', label: '更新时间', minWidth: 170 }
    ],
    formFields: [
      { prop: 'flightId', label: '航班 ID' },
      { prop: 'flightNo', label: '航班号' },
      { prop: 'scheExecDate', label: '执行日期', type: 'date' },
      { prop: 'airlineCd', label: '航司' },
      { prop: 'flightAttr', label: '航班属性' },
      { prop: 'craftType', label: '机型' },
      { prop: 'craftNo', label: '机号' },
      { prop: 'latestOffStatus', label: '起飞状态', type: 'select', options: flightOffStatusOptions },
      { prop: 'latestOnStatus', label: '到达状态', type: 'select', options: flightOnStatusOptions },
      { prop: 'station', label: '航站' },
      { prop: 'stationCn', label: '航站中文' },
      { prop: 'scheTakeOffTime', label: '计划起飞', type: 'datetime' },
      { prop: 'estmTakeOffTime', label: '预计起飞', type: 'datetime' },
      { prop: 'gateCd', label: '登机口' },
      { prop: 'gateAttr', label: '登机口属性' },
      { prop: 'carouselCd', label: '行李转盘' },
      { prop: 'carouselClass', label: '转盘等级' },
      { prop: 'carouselAttr', label: '转盘属性' }
    ],
    transformDetail: (response, row) => {
      const detail = { ...(response.data || row) }
      detail.scheExecDate = normalizeDateTimeValue(detail.scheExecDate)
      detail.scheTakeOffTime = normalizeDateTimeValue(detail.scheTakeOffTime)
      detail.estmTakeOffTime = normalizeDateTimeValue(detail.estmTakeOffTime)
      return detail
    },
    defaults: { latestOffStatus: 'SCH', latestOnStatus: 'ON', isDelete: '0', airlineCd: 'CA' }
  },
  passengerWarning: {
    title: '旅客预警日志',
    description: '旅客越界、超时和重点旅客预警记录。',
    basePath: '/flight/passengerWarningLog',
    rowKey: 'id',
    searchFields: [{ prop: 'passengerId', label: '旅客 ID' }, { prop: 'flightId', label: '航班 ID' }, { prop: 'warningType', label: '预警类型' }],
    columns: [
      { prop: 'id', label: 'ID', width: 90 },
      { prop: 'passengerId', label: '旅客 ID', width: 100 },
      { prop: 'flightId', label: '航班 ID', minWidth: 160 },
      { prop: 'warningType', label: '预警类型', minWidth: 130 },
      { prop: 'warningInfo', label: '内容', minWidth: 260 },
      { prop: 'noticeType', label: '提醒方式', width: 110, map: { 1: '人工', 2: '机器人' } },
      { prop: 'isSuccess', label: '结果', width: 110, tag: 'info', tagMap: { '-1': 'info', 99: 'warning', 1: 'success', 0: 'danger' }, map: warningSuccessMap },
      { prop: 'createTime', label: '时间', minWidth: 170 }
    ],
    formFields: [
      { prop: 'passengerId', label: '旅客 ID', type: 'number' },
      { prop: 'flightId', label: '航班 ID' },
      { prop: 'regionId', label: '区域 ID', type: 'number' },
      { prop: 'warningType', label: '预警类型' },
      { prop: 'noticeType', label: '提醒方式', type: 'select', options: noticeTypeOptions },
      { prop: 'isSuccess', label: '结果', type: 'select', options: warningSuccessOptions },
      { prop: 'warningInfo', label: '预警内容', type: 'textarea' }
    ],
    defaults: { noticeType: '1', isSuccess: '-1' }
  },
  online: {
    title: '在线用户',
    description: '查看当前登录会话，按账号或 IP 检索并执行强制下线。',
    basePath: '/monitor/online',
    rowKey: 'tokenId',
    enableCreate: false,
    enableEdit: false,
    enableDelete: false,
    showDetail: false,
    searchFields: [{ prop: 'ipaddr', label: '登录地址' }, { prop: 'userName', label: '用户名称' }],
    columns: [
      { prop: 'tokenId', label: '会话编号', minWidth: 220 },
      { prop: 'userName', label: '用户名称', minWidth: 130 },
      { prop: 'deptName', label: '部门', minWidth: 140 },
      { prop: 'ipaddr', label: '登录地址', minWidth: 140 },
      { prop: 'loginLocation', label: '登录地点', minWidth: 140 },
      { prop: 'browser', label: '浏览器', minWidth: 130 },
      { prop: 'os', label: '操作系统', minWidth: 140 },
      { prop: 'loginTime', label: '登录时间', minWidth: 170 }
    ],
    formFields: [],
    rowActions: [
      {
        key: 'forceLogout',
        label: '强退',
        type: 'danger',
        confirm: (row) => `确认强退用户"${row.userName || row.tokenId}"？`,
        handler: (row) => forceLogoutOnlineUser(row.tokenId),
        successMessage: '强退成功'
      }
    ],
    operationWidth: 120
  },
  logininfor: {
    title: '登录日志',
    description: '登录日志查询、删除、清空、解锁和导出。',
    basePath: '/monitor/logininfor',
    rowKey: 'infoId',
    enableCreate: false,
    enableEdit: false,
    searchFields: [
      { prop: 'ipaddr', label: '登录地址' },
      { prop: 'userName', label: '用户名称' },
      { prop: 'status', label: '登录状态', type: 'select', options: successFailOptions }
    ],
    columns: [
      { prop: 'infoId', label: 'ID', width: 90 },
      { prop: 'userName', label: '用户名称', minWidth: 130 },
      { prop: 'ipaddr', label: '登录地址', minWidth: 140 },
      { prop: 'loginLocation', label: '登录地点', minWidth: 140 },
      { prop: 'browser', label: '浏览器', minWidth: 130 },
      { prop: 'os', label: '操作系统', minWidth: 140 },
      { prop: 'status', label: '状态', width: 100, tag: 'info', tagMap: { 0: 'success', 1: 'danger' }, map: successFailMap },
      { prop: 'msg', label: '提示消息', minWidth: 220 },
      { prop: 'loginTime', label: '访问时间', minWidth: 170 }
    ],
    formFields: [],
    headerActions: [
      { key: 'clean', label: '清空', type: 'danger', confirm: '确认清空所有登录日志？', handler: cleanLogininfor, successMessage: '登录日志已清空' },
      { key: 'export', label: '导出', handler: ({ query }) => exportSystemResource('/monitor/logininfor/export', query, '登录日志.xlsx'), reload: false }
    ],
    rowActions: [
      {
        key: 'unlock',
        label: '解锁',
        type: 'warning',
        disabled: (row) => !row.userName,
        confirm: (row) => `确认解锁用户"${row.userName}"？`,
        handler: (row) => unlockLogininfor(row.userName),
        successMessage: '解锁成功'
      }
    ],
    operationWidth: 210
  },
  operlog: {
    title: '操作日志',
    description: '后台操作日志查询、删除、清空、导出和明细查看。',
    basePath: '/monitor/operlog',
    rowKey: 'operId',
    enableCreate: false,
    enableEdit: false,
    searchFields: [
      { prop: 'title', label: '系统模块' },
      { prop: 'operName', label: '操作人员' },
      { prop: 'operIp', label: '操作地址' },
      { prop: 'businessType', label: '操作类型', type: 'select', options: businessTypeOptions },
      { prop: 'status', label: '操作状态', type: 'select', options: successFailOptions }
    ],
    columns: [
      { prop: 'operId', label: 'ID', width: 90 },
      { prop: 'title', label: '系统模块', minWidth: 150 },
      { prop: 'businessType', label: '操作类型', minWidth: 110, map: businessTypeMap },
      { prop: 'method', label: '请求方法', minWidth: 220 },
      { prop: 'requestMethod', label: '请求方式', width: 100 },
      { prop: 'operName', label: '操作人员', minWidth: 120 },
      { prop: 'operIp', label: '操作地址', minWidth: 140 },
      { prop: 'operUrl', label: '请求地址', minWidth: 180 },
      { prop: 'operParam', label: '请求参数', minWidth: 220 },
      { prop: 'jsonResult', label: '返回参数', minWidth: 220 },
      { prop: 'status', label: '状态', width: 100, tag: 'info', tagMap: { 0: 'success', 1: 'danger' }, map: successFailMap },
      { prop: 'errorMsg', label: '异常消息', minWidth: 220 },
      { prop: 'operTime', label: '操作时间', minWidth: 170 },
      { prop: 'costTime', label: '消耗时间(ms)', minWidth: 120 }
    ],
    formFields: [],
    headerActions: [
      { key: 'clean', label: '清空', type: 'danger', confirm: '确认清空所有操作日志？', handler: cleanOperLog, successMessage: '操作日志已清空' },
      { key: 'export', label: '导出', handler: ({ query }) => exportSystemResource('/monitor/operlog/export', query, '操作日志.xlsx'), reload: false }
    ],
    operationWidth: 170
  },
  job: {
    title: '定时任务',
    description: 'Quartz 任务名称、调用目标、Cron 表达式、运行状态和手动执行。',
    basePath: '/monitor/job',
    rowKey: 'jobId',
    searchFields: [
      { prop: 'jobName', label: '任务名称' },
      { prop: 'jobGroup', label: '任务组名', type: 'select', options: jobGroupOptions },
      { prop: 'status', label: '任务状态', type: 'select', options: jobStatusOptions }
    ],
    columns: [
      { prop: 'jobId', label: 'ID', width: 90 },
      { prop: 'jobName', label: '任务名称', minWidth: 160 },
      { prop: 'jobGroup', label: '任务组名', minWidth: 120 },
      { prop: 'invokeTarget', label: '调用目标字符串', minWidth: 240 },
      { prop: 'cronExpression', label: 'Cron 表达式', minWidth: 160 },
      { prop: 'misfirePolicy', label: '执行策略', minWidth: 120, map: { 1: '立即执行', 2: '执行一次', 3: '放弃执行' } },
      { prop: 'concurrent', label: '并发执行', minWidth: 110, map: { 0: '允许', 1: '禁止' } },
      switchJobStatusColumn((row, status) => changeJobStatus(row.jobId, status))
    ],
    formFields: [
      { prop: 'jobName', label: '任务名称' },
      { prop: 'jobGroup', label: '任务组名', type: 'select', options: jobGroupOptions },
      { prop: 'invokeTarget', label: '调用目标字符串' },
      { prop: 'cronExpression', label: 'Cron 表达式' },
      { prop: 'misfirePolicy', label: '执行策略', type: 'select', options: [{ label: '立即执行', value: '1' }, { label: '执行一次', value: '2' }, { label: '放弃执行', value: '3' }] },
      { prop: 'concurrent', label: '是否并发', type: 'select', options: [{ label: '允许', value: '0' }, { label: '禁止', value: '1' }] },
      { prop: 'status', label: '状态', type: 'select', options: jobStatusOptions },
      { prop: 'remark', label: '备注', type: 'textarea' }
    ],
    defaults: { status: '1', jobGroup: 'DEFAULT', misfirePolicy: '3', concurrent: '1' },
    headerActions: [
      { key: 'export', label: '导出', handler: ({ query }) => exportSystemResource('/monitor/job/export', query, '定时任务.xlsx'), reload: false },
      { key: 'log', label: '调度日志', route: '/monitor/job/log', reload: false }
    ],
    rowActions: [
      {
        key: 'run',
        label: '执行一次',
        type: 'warning',
        confirm: (row) => `确认立即执行一次任务"${row.jobName}"？`,
        handler: (row) => runJob(row),
        successMessage: '任务执行请求已提交'
      },
      {
        key: 'log',
        label: '日志',
        route: (row) => `/monitor/job/log/${row.jobId}?jobName=${encodeURIComponent(row.jobName || '')}&jobGroup=${encodeURIComponent(row.jobGroup || '')}`
      }
    ],
    operationWidth: 360
  },
  jobLog: {
    title: '调度日志',
    description: '定时任务执行日志、异常信息、执行状态和耗时统计。',
    basePath: '/monitor/jobLog',
    rowKey: 'jobLogId',
    enableCreate: false,
    enableEdit: false,
    searchFields: [
      { prop: 'jobName', label: '任务名称' },
      { prop: 'jobGroup', label: '任务组名', type: 'select', options: jobGroupOptions },
      { prop: 'status', label: '执行状态', type: 'select', options: successFailOptions }
    ],
    columns: [
      { prop: 'jobLogId', label: 'ID', width: 90 },
      { prop: 'jobName', label: '任务名称', minWidth: 150 },
      { prop: 'jobGroup', label: '任务组名', minWidth: 120 },
      { prop: 'invokeTarget', label: '调用目标字符串', minWidth: 240 },
      { prop: 'jobMessage', label: '日志信息', minWidth: 260 },
      { prop: 'status', label: '状态', width: 100, tag: 'info', tagMap: { 0: 'success', 1: 'danger' }, map: successFailMap },
      { prop: 'exceptionInfo', label: '异常信息', minWidth: 260 },
      { prop: 'createTime', label: '执行时间', minWidth: 170 }
    ],
    formFields: [],
    headerActions: [
      { key: 'clean', label: '清空', type: 'danger', confirm: '确认清空所有调度日志？', handler: cleanJobLog, successMessage: '调度日志已清空' },
      { key: 'export', label: '导出', handler: ({ query }) => exportSystemResource('/monitor/jobLog/export', query, '调度日志.xlsx'), reload: false },
      { key: 'close', label: '关闭', route: '/monitor/job', reload: false }
    ],
    operationWidth: 170
  }
}

export function getCrudPage(key) {
  const page = crudPages[key]
  if (!page) {
    return page
  }
  const prefix = page.permissionPrefix || permissionPrefixMap[key] || inferPermissionPrefix(page.basePath)
  return {
    ...page,
    permissions: {
      ...buildCrudPermissions(prefix),
      ...(page.permissions || {})
    }
  }
}

const permissionPrefixMap = {
  user: 'system:user',
  role: 'system:role',
  menu: 'system:menu',
  dept: 'system:dept',
  post: 'system:post',
  config: 'system:config',
  notice: 'system:notice',
  dictType: 'system:dict',
  dictData: 'system:dict',
  robot: 'config:robot',
  region: 'config:region',
  image: 'config:photo',
  audio: 'config:audio',
  area: 'config:area',
  device: 'config:device',
  table: 'config:table',
  task: 'config:task',
  complaint: 'flight:complaint',
  msg: 'config:msg',
  foodConfig: 'food:config',
  foodDaily: 'food:daily',
  foodPlan: 'food:plan',
  foodOrder: 'food:order',
  passenger: 'system:passenger',
  passengerAll: 'system:passenger',
  outgoingPassenger: 'flight:outGoing',
  flightInfo: 'flight:info',
  passengerWarning: 'flight:warning',
  online: 'monitor:online',
  logininfor: 'monitor:logininfor',
  operlog: 'monitor:operlog',
  job: 'monitor:job',
  jobLog: 'monitor:job'
}

const actionPermissionMap = {
  add: 'add',
  create: 'add',
  edit: 'edit',
  update: 'edit',
  remove: 'remove',
  delete: 'remove',
  export: 'export',
  import: 'import',
  template: 'import',
  resetPwd: 'resetPwd',
  authRole: 'edit',
  dataScope: 'edit',
  authUser: 'edit',
  bindRegion: 'edit',
  clearRegion: 'edit',
  run: 'edit',
  receive: 'edit',
  finish: 'edit',
  cancel: 'edit',
  checkout: 'edit',
  forceLogout: 'forceLogout',
  unlock: 'remove',
  clean: 'remove',
  log: 'query',
  data: 'query',
  close: null
}

function buildCrudPermissions(prefix) {
  if (!prefix) {
    return {}
  }
  return Object.entries(actionPermissionMap).reduce((permissions, [action, suffix]) => {
    if (suffix) {
      permissions[action] = `${prefix}:${suffix}`
    }
    return permissions
  }, {})
}

function inferPermissionPrefix(basePath = '') {
  const segments = basePath.split('/').filter(Boolean)
  if (segments.length < 2) {
    return ''
  }
  return `${segments[0]}:${segments[1]}`
}
