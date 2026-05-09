const statusOptions = [
  { label: '正常', value: '0' },
  { label: '停用', value: '1' }
]

const yesNoOptions = [
  { label: '是', value: 'Y' },
  { label: '否', value: 'N' }
]

const statusColumn = (prop = 'status') => ({
  prop,
  label: '状态',
  width: 100,
  tag: 'info',
  tagMap: { 0: 'success', 1: 'danger' },
  map: { 0: '正常', 1: '停用' }
})

export const crudPages = {
  user: {
    title: '用户管理',
    description: '用户、部门、角色、岗位等账号基础资料管理。',
    basePath: '/system/user',
    rowKey: 'userId',
    searchFields: [
      { prop: 'userName', label: '账号' },
      { prop: 'nickName', label: '昵称' },
      { prop: 'phonenumber', label: '手机号' }
    ],
    columns: [
      { prop: 'userId', label: 'ID', width: 90 },
      { prop: 'userName', label: '账号', minWidth: 130 },
      { prop: 'nickName', label: '昵称', minWidth: 130 },
      { prop: 'dept.deptName', label: '部门', minWidth: 140 },
      { prop: 'phonenumber', label: '手机号', minWidth: 140 },
      { prop: 'email', label: '邮箱', minWidth: 180 },
      statusColumn()
    ],
    formFields: [
      { prop: 'userName', label: '账号' },
      { prop: 'nickName', label: '昵称' },
      { prop: 'phonenumber', label: '手机号' },
      { prop: 'email', label: '邮箱' },
      { prop: 'sex', label: '性别', type: 'select', options: [{ label: '男', value: '0' }, { label: '女', value: '1' }, { label: '未知', value: '2' }] },
      { prop: 'status', label: '状态', type: 'select', options: statusOptions },
      { prop: 'remark', label: '备注', type: 'textarea' }
    ],
    defaults: { status: '0', sex: '2' }
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
      statusColumn(),
      { prop: 'remark', label: '备注', minWidth: 180 }
    ],
    formFields: [
      { prop: 'roleName', label: '角色名称' },
      { prop: 'roleKey', label: '权限标识' },
      { prop: 'roleSort', label: '排序', type: 'number' },
      { prop: 'status', label: '状态', type: 'select', options: statusOptions },
      { prop: 'remark', label: '备注', type: 'textarea' }
    ],
    defaults: { status: '0', roleSort: 1 }
  },
  dept: {
    title: '部门管理',
    description: '组织架构、贵宾室房间编码和部门状态维护。',
    basePath: '/system/dept',
    rowKey: 'deptId',
    pagination: false,
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
    formFields: [
      { prop: 'parentId', label: '父级 ID', type: 'number' },
      { prop: 'deptName', label: '部门名称' },
      { prop: 'orderNum', label: '排序', type: 'number' },
      { prop: 'leader', label: '负责人' },
      { prop: 'phone', label: '电话' },
      { prop: 'email', label: '邮箱' },
      { prop: 'roomCode', label: '房间编码' },
      { prop: 'status', label: '状态', type: 'select', options: statusOptions }
    ],
    defaults: { parentId: 0, orderNum: 1, status: '0' }
  },
  menu: {
    title: '菜单管理',
    description: '后台菜单、路由路径、组件路径和权限标识维护。',
    basePath: '/system/menu',
    rowKey: 'menuId',
    pagination: false,
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
    formFields: [
      { prop: 'parentId', label: '父级 ID', type: 'number' },
      { prop: 'menuName', label: '菜单名称' },
      { prop: 'orderNum', label: '排序', type: 'number' },
      { prop: 'path', label: '路由路径' },
      { prop: 'component', label: '组件路径' },
      { prop: 'menuType', label: '菜单类型', type: 'select', options: [{ label: '目录', value: 'M' }, { label: '菜单', value: 'C' }, { label: '按钮', value: 'F' }] },
      { prop: 'perms', label: '权限标识' },
      { prop: 'icon', label: '图标' }
    ],
    defaults: { parentId: 0, orderNum: 1, menuType: 'C', icon: '#' }
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
    defaults: { status: '0', postSort: 1 }
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
    defaults: { configType: 'N' }
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
      { prop: 'noticeContent', label: '内容', type: 'textarea' }
    ],
    defaults: { noticeType: '1', status: '0' }
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
    defaults: { status: '0' }
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
      { prop: 'isDefault', label: '默认', width: 90 },
      statusColumn()
    ],
    formFields: [
      { prop: 'dictType', label: '字典类型' },
      { prop: 'dictLabel', label: '字典标签' },
      { prop: 'dictValue', label: '字典键值' },
      { prop: 'dictSort', label: '排序', type: 'number' },
      { prop: 'isDefault', label: '默认值', type: 'select', options: yesNoOptions },
      { prop: 'status', label: '状态', type: 'select', options: statusOptions },
      { prop: 'remark', label: '备注', type: 'textarea' }
    ],
    defaults: { status: '0', isDefault: 'N', dictSort: 1 }
  },
  robot: {
    title: '机器人配置',
    description: '机器人编号、名称、IP、区域和运行状态配置。',
    basePath: '/config/robot',
    rowKey: 'id',
    searchFields: [{ prop: 'robotId', label: '机器人编号' }, { prop: 'robotName', label: '机器人名称' }],
    columns: [
      { prop: 'id', label: 'ID', width: 90 },
      { prop: 'robotId', label: '机器人编号', minWidth: 140 },
      { prop: 'robotName', label: '名称', minWidth: 150 },
      { prop: 'robotIp', label: 'IP', minWidth: 140 },
      { prop: 'region.regionName', label: '区域', minWidth: 140 },
      { prop: 'batteryState', label: '电量', width: 90 },
      { prop: 'workingState', label: '工作状态', minWidth: 120 }
    ],
    formFields: [
      { prop: 'robotId', label: '机器人编号' },
      { prop: 'robotName', label: '机器人名称' },
      { prop: 'robotIp', label: 'IP 地址' },
      { prop: 'regionId', label: '区域 ID', type: 'number' },
      { prop: 'workingState', label: '工作状态' },
      { prop: 'batteryState', label: '电量', type: 'number' }
    ]
  },
  image: {
    title: '图片管理',
    description: '贵宾室图片、地图底图和展示资源管理。',
    basePath: '/config/img',
    rowKey: 'id',
    uploadField: 'imgUrl',
    searchFields: [{ prop: 'imgName', label: '图片名称' }, { prop: 'imgKey', label: '图片 Key' }],
    columns: [
      { prop: 'id', label: 'ID', width: 90 },
      { prop: 'imgName', label: '名称', minWidth: 160 },
      { prop: 'imgKey', label: 'Key', minWidth: 160 },
      { prop: 'imgUrl', label: '地址', minWidth: 220 },
      { prop: 'roomCode', label: '房间编码', minWidth: 130 }
    ],
    formFields: [
      { prop: 'imgName', label: '图片名称' },
      { prop: 'imgKey', label: '图片 Key' },
      { prop: 'imgUrl', label: '图片地址' },
      { prop: 'roomCode', label: '房间编码' },
      { prop: 'remark', label: '备注', type: 'textarea' }
    ]
  },
  audio: {
    title: '机器人语音',
    description: '机器人语音资源、音频 Key 和播放内容管理。',
    basePath: '/config/robotAudio',
    rowKey: 'id',
    uploadField: 'audioUrl',
    searchFields: [{ prop: 'audioKey', label: '音频 Key' }, { prop: 'audioName', label: '音频名称' }],
    columns: [
      { prop: 'id', label: 'ID', width: 90 },
      { prop: 'audioName', label: '名称', minWidth: 160 },
      { prop: 'audioKey', label: 'Key', minWidth: 150 },
      { prop: 'audioUrl', label: '地址', minWidth: 220 },
      { prop: 'roomCode', label: '房间编码', minWidth: 130 }
    ],
    formFields: [
      { prop: 'audioName', label: '音频名称' },
      { prop: 'audioKey', label: '音频 Key' },
      { prop: 'audioUrl', label: '音频地址' },
      { prop: 'roomCode', label: '房间编码' },
      { prop: 'remark', label: '备注', type: 'textarea' }
    ]
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
      { prop: 'roomCode', label: '房间编码', minWidth: 130 },
      { prop: 'cameraCoordinates', label: '坐标', minWidth: 220 },
      { prop: 'maxCapacity', label: '容量', width: 90 },
      { prop: 'isShow', label: '展示', width: 90 }
    ],
    formFields: [
      { prop: 'regionName', label: '区域名称' },
      { prop: 'roomCode', label: '房间编码' },
      { prop: 'cameraCoordinates', label: '坐标' },
      { prop: 'maxCapacity', label: '容量', type: 'number' },
      { prop: 'isShow', label: '是否展示', type: 'select', options: yesNoOptions }
    ],
    defaults: { isShow: 'Y', maxCapacity: 0 }
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
      { prop: 'roomCode', label: '房间编码', minWidth: 130 },
      { prop: 'maxCapacity', label: '容量', width: 90 },
      { prop: 'imgIds', label: '图片', minWidth: 120 },
      { prop: 'audioKeys', label: '语音', minWidth: 160 }
    ],
    formFields: [
      { prop: 'areaName', label: '功能区名称' },
      { prop: 'roomCode', label: '房间编码' },
      { prop: 'maxCapacity', label: '容量', type: 'number' },
      { prop: 'imgIds', label: '图片 ID' },
      { prop: 'audioKeys', label: '音频 Key' },
      { prop: 'remark', label: '备注', type: 'textarea' }
    ],
    defaults: { maxCapacity: 0 }
  },
  device: {
    title: '监控设备',
    description: '摄像头、监控设备编码、IP 和区域绑定维护。',
    basePath: '/config/device',
    rowKey: 'id',
    searchFields: [{ prop: 'deviceName', label: '设备名称' }, { prop: 'deviceCode', label: '设备编码' }],
    columns: [
      { prop: 'id', label: 'ID', width: 90 },
      { prop: 'deviceName', label: '设备名称', minWidth: 160 },
      { prop: 'deviceCode', label: '设备编码', minWidth: 150 },
      { prop: 'deviceIp', label: 'IP', minWidth: 140 },
      { prop: 'regionId', label: '区域 ID', width: 100 },
      statusColumn()
    ],
    formFields: [
      { prop: 'deviceName', label: '设备名称' },
      { prop: 'deviceCode', label: '设备编码' },
      { prop: 'deviceIp', label: 'IP 地址' },
      { prop: 'regionId', label: '区域 ID', type: 'number' },
      { prop: 'status', label: '状态', type: 'select', options: statusOptions }
    ],
    defaults: { status: '0' }
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
      { prop: 'regionName', label: '区域', minWidth: 140 },
      { prop: 'cameraCoordinates', label: '坐标', minWidth: 220 },
      { prop: 'status', label: '状态', width: 100 }
    ],
    formFields: [
      { prop: 'tableNo', label: '桌号' },
      { prop: 'roomCode', label: '房间编码' },
      { prop: 'regionId', label: '区域 ID', type: 'number' },
      { prop: 'cameraCoordinates', label: '坐标' },
      { prop: 'status', label: '状态', type: 'select', options: [{ label: '空闲', value: '0' }, { label: '翻台', value: '1' }] }
    ],
    defaults: { status: '0' }
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
      { prop: 'taskStatus', label: '任务状态', minWidth: 120 },
      { prop: 'createTime', label: '创建时间', minWidth: 170 }
    ],
    formFields: [
      { prop: 'taskName', label: '任务名称' },
      { prop: 'robotId', label: '机器人编号' },
      { prop: 'taskStatus', label: '任务状态' },
      { prop: 'remark', label: '备注', type: 'textarea' }
    ]
  },
  msg: {
    title: '消息日志',
    description: '投诉、通知和机器人消息记录。',
    basePath: '/config/msg',
    rowKey: 'id',
    searchFields: [{ prop: 'msgType', label: '消息类型' }, { prop: 'userName', label: '用户' }],
    columns: [
      { prop: 'id', label: 'ID', width: 90 },
      { prop: 'msgType', label: '类型', minWidth: 120 },
      { prop: 'userName', label: '用户', minWidth: 120 },
      { prop: 'content', label: '内容', minWidth: 260 },
      { prop: 'createTime', label: '时间', minWidth: 170 }
    ],
    formFields: [
      { prop: 'msgType', label: '消息类型' },
      { prop: 'userName', label: '用户' },
      { prop: 'content', label: '内容', type: 'textarea' }
    ]
  },
  foodConfig: {
    title: '菜品管理',
    description: '菜品名称、类型、价格和状态管理。',
    basePath: '/food',
    listPath: '/food/selectFoodConfigList',
    listMethod: 'POST',
    createPath: '/food/insertFoodConfig',
    updatePath: '/food/updateFoodConfig',
    deletePath: '/food/deleteFoodConfigByFoodIds',
    deleteMethod: 'POST',
    rowKey: 'foodId',
    searchFields: [{ prop: 'foodName', label: '菜品名称' }, { prop: 'foodType', label: '分类' }],
    columns: [
      { prop: 'foodId', label: 'ID', width: 90 },
      { prop: 'foodName', label: '名称', minWidth: 160 },
      { prop: 'foodType', label: '分类', minWidth: 120 },
      { prop: 'price', label: '价格', width: 100 },
      statusColumn()
    ],
    formFields: [
      { prop: 'foodName', label: '菜品名称' },
      { prop: 'foodType', label: '分类' },
      { prop: 'price', label: '价格', type: 'number' },
      { prop: 'status', label: '状态', type: 'select', options: statusOptions },
      { prop: 'remark', label: '备注', type: 'textarea' }
    ],
    defaults: { status: '0', price: 0 }
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
    rowKey: 'id',
    searchFields: [{ prop: 'roomCode', label: '房间编码' }, { prop: 'foodDate', label: '日期' }],
    columns: [
      { prop: 'id', label: 'ID', width: 90 },
      { prop: 'roomCode', label: '房间编码', minWidth: 130 },
      { prop: 'deptName', label: '贵宾室', minWidth: 160 },
      { prop: 'foodDate', label: '日期', minWidth: 130 },
      { prop: 'remark', label: '备注', minWidth: 180 }
    ],
    formFields: [
      { prop: 'roomCode', label: '房间编码' },
      { prop: 'foodDate', label: '日期' },
      { prop: 'remark', label: '备注', type: 'textarea' }
    ]
  },
  foodOrder: {
    title: '订单管理',
    description: '点餐订单、桌号、旅客和订单状态管理。',
    basePath: '/food',
    listPath: '/food/queryOrderList',
    listMethod: 'POST',
    rowKey: 'foodOrderId',
    enableCreate: false,
    enableEdit: false,
    enableDelete: false,
    searchFields: [{ prop: 'orderCode', label: '订单号' }, { prop: 'status', label: '状态' }],
    columns: [
      { prop: 'foodOrderId', label: 'ID', width: 90 },
      { prop: 'orderCode', label: '订单号', minWidth: 160 },
      { prop: 'tableNo', label: '桌号', minWidth: 100 },
      { prop: 'passengerName', label: '旅客', minWidth: 120 },
      { prop: 'status', label: '状态', width: 100 },
      { prop: 'createTime', label: '创建时间', minWidth: 170 }
    ],
    formFields: []
  },
  passenger: {
    title: '在厅旅客',
    description: '旅客、航班、区域和入厅状态统计。',
    basePath: '/flight/passenger',
    rowKey: 'id',
    searchFields: [{ prop: 'userName', label: '旅客姓名' }, { prop: 'flightNo', label: '航班号' }],
    columns: [
      { prop: 'id', label: 'ID', width: 90 },
      { prop: 'userName', label: '旅客', minWidth: 130 },
      { prop: 'flightNo', label: '航班', minWidth: 120 },
      { prop: 'regionId', label: '区域', width: 100 },
      { prop: 'inType', label: '入厅类型', minWidth: 120 },
      { prop: 'createTime', label: '时间', minWidth: 170 }
    ],
    formFields: [
      { prop: 'userName', label: '旅客姓名' },
      { prop: 'flightNo', label: '航班号' },
      { prop: 'regionId', label: '区域 ID', type: 'number' },
      { prop: 'inType', label: '入厅类型' }
    ]
  },
  passengerWarning: {
    title: '旅客预警日志',
    description: '旅客越界、超时和重点旅客预警记录。',
    basePath: '/flight/passengerWarningLog',
    rowKey: 'id',
    searchFields: [{ prop: 'userName', label: '旅客' }, { prop: 'warningType', label: '预警类型' }],
    columns: [
      { prop: 'id', label: 'ID', width: 90 },
      { prop: 'userName', label: '旅客', minWidth: 130 },
      { prop: 'warningType', label: '预警类型', minWidth: 130 },
      { prop: 'warningContent', label: '内容', minWidth: 260 },
      { prop: 'createTime', label: '时间', minWidth: 170 }
    ],
    formFields: [
      { prop: 'userName', label: '旅客' },
      { prop: 'warningType', label: '预警类型' },
      { prop: 'warningContent', label: '预警内容', type: 'textarea' }
    ]
  },
  job: {
    title: '定时任务',
    description: 'Quartz 任务名称、调用目标、Cron 表达式和运行状态。',
    basePath: '/monitor/job',
    rowKey: 'jobId',
    searchFields: [{ prop: 'jobName', label: '任务名称' }, { prop: 'jobGroup', label: '任务组' }],
    columns: [
      { prop: 'jobId', label: 'ID', width: 90 },
      { prop: 'jobName', label: '任务名称', minWidth: 160 },
      { prop: 'jobGroup', label: '任务组', minWidth: 120 },
      { prop: 'invokeTarget', label: '调用目标', minWidth: 220 },
      { prop: 'cronExpression', label: 'Cron', minWidth: 160 },
      statusColumn()
    ],
    formFields: [
      { prop: 'jobName', label: '任务名称' },
      { prop: 'jobGroup', label: '任务组' },
      { prop: 'invokeTarget', label: '调用目标' },
      { prop: 'cronExpression', label: 'Cron 表达式' },
      { prop: 'status', label: '状态', type: 'select', options: statusOptions },
      { prop: 'remark', label: '备注', type: 'textarea' }
    ],
    defaults: { status: '0' }
  },
  jobLog: {
    title: '调度日志',
    description: '定时任务执行日志、异常信息和耗时统计。',
    basePath: '/monitor/jobLog',
    rowKey: 'jobLogId',
    enableCreate: false,
    enableEdit: false,
    searchFields: [{ prop: 'jobName', label: '任务名称' }, { prop: 'jobGroup', label: '任务组' }],
    columns: [
      { prop: 'jobLogId', label: 'ID', width: 90 },
      { prop: 'jobName', label: '任务名称', minWidth: 150 },
      { prop: 'jobGroup', label: '任务组', minWidth: 120 },
      { prop: 'invokeTarget', label: '调用目标', minWidth: 220 },
      { prop: 'status', label: '状态', width: 100 },
      { prop: 'createTime', label: '时间', minWidth: 170 }
    ],
    formFields: []
  }
}

export function getCrudPage(key) {
  return crudPages[key]
}
