import {
  clearLoginLogs,
  clearOperationLogs,
  createArea,
  createAudio,
  createComplaint,
  createDevice,
  createImage,
  createLounge,
  createRegion,
  createRobot,
  createTask,
  createUser,
  deleteArea,
  deleteAudio,
  deleteComplaint,
  deleteDevice,
  deleteImage,
  deleteLounge,
  deleteRegion,
  deleteRobot,
  deleteTask,
  deleteUsers,
  getProfile,
  getUserDetail,
  listAreas,
  listAudios,
  listComplaints,
  listDevices,
  listImages,
  listInLounge,
  listInquiry,
  listKnowledge,
  listLoginLogs,
  listLounges,
  listOperationLogs,
  listOutgoing,
  listRegions,
  listRobots,
  listRobotAudios,
  listTasks,
  listUsers,
  runTask,
  saveDeviceRegionBinding,
  updateArea,
  updateAudio,
  updateComplaint,
  updateDevice,
  updateImage,
  updateLounge,
  updateProfile,
  updateRegion,
  updateRobot,
  updateTask,
  updateUser
} from '@/api/system'

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
  { label: '俄文', value: 'RU' },
  { label: '日文', value: 'JP' }
]

const deviceTypeOptions = [
  { label: '摄像头', value: 'CAMERA' },
  { label: '门禁', value: 'GATE' },
  { label: '其他', value: 'OTHER' }
]

const passengerStatusMap = {
  IN: '在厅',
  OUT: '已出厅'
}

function optionsFromRows(rows = [], valueKey = 'id', labelKey = 'name') {
  return rows.map((row) => ({ value: row[valueKey], label: row[labelKey] }))
}

async function loadConfigOptions() {
  const [lounges, regions, areas, images, audios, robots, devices] = await Promise.all([
    listLounges(),
    listRegions(),
    listAreas(),
    listImages(),
    listAudios(),
    listRobots(),
    listDevices()
  ])

  const loungeRows = lounges.rows || []
  return {
    lounges: loungeRows.map((item) => ({ value: item.id, label: `${item.deptName} (${item.roomCode})`, raw: item })),
    regions: optionsFromRows(regions.rows || [], 'id', 'regionName'),
    areas: optionsFromRows(areas.rows || [], 'id', 'areaName'),
    images: optionsFromRows(images.rows || [], 'id', 'imgName'),
    audios: optionsFromRows(audios.rows || [], 'id', 'audioKey'),
    robots: optionsFromRows(robots.rows || [], 'id', 'robotName'),
    devices: optionsFromRows(devices.rows || [], 'id', 'deviceName')
  }
}

export const crudPages = {
  user: {
    title: '用户管理',
    rowKey: 'id',
    list: listUsers,
    detail: (row) => getUserDetail(row.id),
    create: createUser,
    update: (payload) => updateUser(payload.id, payload),
    remove: deleteUsers,
    columns: [
      { prop: 'id', label: 'ID', width: 80 },
      { prop: 'username', label: '账号', minWidth: 140 },
      { prop: 'nickname', label: '昵称', minWidth: 140 },
      { prop: 'email', label: '邮箱', minWidth: 180 },
      { prop: 'phone', label: '手机号', minWidth: 140 },
      { prop: 'enable', label: '状态', map: { true: '启用', false: '停用' } }
    ],
    formFields: [
      { prop: 'username', label: '账号' },
      { prop: 'password', label: '密码', inputType: 'password' },
      { prop: 'nickname', label: '昵称' },
      { prop: 'email', label: '邮箱' },
      { prop: 'phone', label: '手机号' },
      { prop: 'sex', label: '性别', type: 'select', options: [{ label: '男', value: '0' }, { label: '女', value: '1' }, { label: '未知', value: '2' }] },
      { prop: 'enable', label: '状态', type: 'select', options: [{ label: '启用', value: true }, { label: '停用', value: false }] },
      { prop: 'remark', label: '备注', type: 'textarea' }
    ],
    defaults: { sex: '2', enable: true }
  },
  dept: {
    title: '贵宾室配置',
    rowKey: 'id',
    list: listLounges,
    create: createLounge,
    update: (payload) => updateLounge(payload.id, payload),
    remove: (ids) => deleteLounge(Array.isArray(ids) ? ids[0] : ids),
    columns: [
      { prop: 'id', label: 'ID', width: 80 },
      { prop: 'deptName', label: '贵宾室名称', minWidth: 180 },
      { prop: 'roomCode', label: '房间编码', minWidth: 140 },
      { prop: 'terminal', label: '航站楼', minWidth: 120 },
      { prop: 'locationDesc', label: '位置', minWidth: 180 }
    ],
    formFields: [
      { prop: 'deptName', label: '贵宾室名称' },
      { prop: 'roomCode', label: '房间编码' },
      { prop: 'terminal', label: '航站楼' },
      { prop: 'locationDesc', label: '位置' },
      { prop: 'enabled', label: '启用状态', type: 'select', options: [{ label: '启用', value: true }, { label: '停用', value: false }] },
      { prop: 'remark', label: '备注', type: 'textarea' }
    ],
    defaults: { enabled: true }
  },
  area: {
    title: '功能区管理',
    rowKey: 'id',
    list: listAreas,
    create: createArea,
    update: (payload) => updateArea(payload.id, payload),
    remove: (ids) => deleteArea(Array.isArray(ids) ? ids[0] : ids),
    columns: [
      { prop: 'id', label: 'ID', width: 80 },
      { prop: 'areaName', label: '功能区', minWidth: 160 },
      { prop: 'deptName', label: '贵宾室', minWidth: 160 },
      { prop: 'coordinate', label: '坐标', minWidth: 200 },
      { prop: 'maxCapacity', label: '容量', width: 100 }
    ],
    formFields: async () => {
      const options = await loadConfigOptions()
      return [
        { prop: 'loungeId', label: '贵宾室', type: 'select', options: options.lounges },
        { prop: 'areaName', label: '功能区名称' },
        { prop: 'coordinate', label: '坐标' },
        { prop: 'maxCapacity', label: '最大容量', type: 'number' },
        { prop: 'isGuide', label: '支持引导', type: 'select', options: guideOptions },
        { prop: 'isShow', label: '是否展示', type: 'select', options: showOptions },
        { prop: 'enable', label: '状态', type: 'select', options: enableOptions },
        { prop: 'remark', label: '备注', type: 'textarea' }
      ]
    },
    defaults: { isGuide: '0', isShow: '1', enable: 1 }
  },
  region: {
    title: '区域管理',
    rowKey: 'id',
    list: listRegions,
    create: createRegion,
    update: (payload) => updateRegion(payload.id, payload),
    remove: (ids) => deleteRegion(Array.isArray(ids) ? ids[0] : ids),
    columns: [
      { prop: 'id', label: 'ID', width: 80 },
      { prop: 'regionName', label: '区域名称', minWidth: 160 },
      { prop: 'deptName', label: '贵宾室', minWidth: 160 },
      { prop: 'areaName', label: '功能区', minWidth: 140 },
      { prop: 'maxCapacity', label: '容量', width: 100 }
    ],
    formFields: async () => {
      const options = await loadConfigOptions()
      return [
        { prop: 'loungeId', label: '贵宾室', type: 'select', options: options.lounges },
        { prop: 'areaId', label: '功能区', type: 'select', options: options.areas },
        { prop: 'regionName', label: '区域名称' },
        { prop: 'coordinate', label: '坐标' },
        { prop: 'maxCapacity', label: '最大容量', type: 'number' },
        { prop: 'isGuide', label: '支持引导', type: 'select', options: guideOptions },
        { prop: 'isShow', label: '是否展示', type: 'select', options: showOptions },
        { prop: 'enable', label: '状态', type: 'select', options: enableOptions },
        { prop: 'remark', label: '备注', type: 'textarea' }
      ]
    },
    defaults: { isGuide: '0', isShow: '1', enable: 1 }
  },
  image: {
    title: '图片管理',
    rowKey: 'id',
    list: listImages,
    create: createImage,
    update: (payload) => updateImage(payload.id, payload),
    remove: (ids) => deleteImage(Array.isArray(ids) ? ids[0] : ids),
    columns: [
      { prop: 'id', label: 'ID', width: 80 },
      { prop: 'imgName', label: '图片名称', minWidth: 180 },
      { prop: 'imgType', label: '类型', minWidth: 120 },
      { prop: 'deptName', label: '贵宾室', minWidth: 160 },
      { prop: 'width', label: '宽', width: 80 },
      { prop: 'height', label: '高', width: 80 }
    ],
    formFields: async () => {
      const options = await loadConfigOptions()
      return [
        { prop: 'loungeId', label: '贵宾室', type: 'select', options: options.lounges },
        { prop: 'imgName', label: '图片名称' },
        { prop: 'imgType', label: '图片类型' },
        { prop: 'img', label: 'Base64 内容', type: 'textarea' },
        { prop: 'width', label: '宽度', type: 'number' },
        { prop: 'height', label: '高度', type: 'number' },
        { prop: 'enable', label: '状态', type: 'select', options: enableOptions },
        { prop: 'remark', label: '备注', type: 'textarea' }
      ]
    },
    defaults: { enable: 1 }
  },
  audio: {
    title: '音频管理',
    rowKey: 'id',
    list: listAudios,
    create: createAudio,
    update: (payload) => updateAudio(payload.id, payload),
    remove: (ids) => deleteAudio(Array.isArray(ids) ? ids[0] : ids),
    columns: [
      { prop: 'id', label: 'ID', width: 80 },
      { prop: 'audioKey', label: '音频 Key', minWidth: 180 },
      { prop: 'audioType', label: '类型', minWidth: 120 },
      { prop: 'languageType', label: '语言', width: 100 },
      { prop: 'textInfo', label: '文本', minWidth: 220 }
    ],
    formFields: async () => {
      const options = await loadConfigOptions()
      return [
        { prop: 'loungeId', label: '贵宾室', type: 'select', options: options.lounges },
        { prop: 'audioKey', label: '音频 Key' },
        { prop: 'audioType', label: '类型' },
        { prop: 'languageType', label: '语言', type: 'select', options: languageOptions },
        { prop: 'textInfo', label: '文本', type: 'textarea' },
        { prop: 'audioValue', label: '音频内容', type: 'textarea' },
        { prop: 'remark', label: '备注', type: 'textarea' }
      ]
    },
    defaults: { languageType: 'CN' }
  },
  robotAudio: {
    title: '机器人音频',
    rowKey: 'id',
    list: listRobotAudios,
    columns: [
      { prop: 'id', label: 'ID', width: 80 },
      { prop: 'audioKey', label: '音频 Key', minWidth: 180 },
      { prop: 'languageType', label: '语言', width: 100 },
      { prop: 'textInfo', label: '文本', minWidth: 220 }
    ],
    formFields: []
  },
  device: {
    title: '设备管理',
    rowKey: 'id',
    list: listDevices,
    create: createDevice,
    update: (payload) => updateDevice(payload.id, payload),
    remove: (ids) => deleteDevice(Array.isArray(ids) ? ids[0] : ids),
    columns: [
      { prop: 'id', label: 'ID', width: 80 },
      { prop: 'deviceName', label: '设备名称', minWidth: 180 },
      { prop: 'deviceType', label: '设备类型', minWidth: 120 },
      { prop: 'deepGlintDeviceId', label: '外部设备 ID', minWidth: 180 }
    ],
    formFields: async () => {
      const options = await loadConfigOptions()
      return [
        { prop: 'loungeId', label: '贵宾室', type: 'select', options: options.lounges },
        { prop: 'deviceName', label: '设备名称' },
        { prop: 'deviceType', label: '设备类型', type: 'select', options: deviceTypeOptions },
        { prop: 'deepGlintDeviceId', label: '外部设备 ID' },
        { prop: 'enable', label: '状态', type: 'select', options: enableOptions },
        { prop: 'remark', label: '备注', type: 'textarea' }
      ]
    },
    defaults: { enable: 1, deviceType: 'CAMERA' }
  },
  robot: {
    title: '机器人配置',
    rowKey: 'id',
    list: listRobots,
    create: createRobot,
    update: (payload) => updateRobot(payload.id, payload),
    remove: (ids) => deleteRobot(Array.isArray(ids) ? ids[0] : ids),
    columns: [
      { prop: 'id', label: 'ID', width: 80 },
      { prop: 'robotId', label: '机器人编号', minWidth: 160 },
      { prop: 'robotName', label: '名称', minWidth: 160 },
      { prop: 'robotIp', label: 'IP', minWidth: 140 },
      { prop: 'batteryState', label: '电量', width: 80 }
    ],
    formFields: async () => {
      const options = await loadConfigOptions()
      return [
        { prop: 'loungeId', label: '贵宾室', type: 'select', options: options.lounges },
        { prop: 'regionId', label: '区域', type: 'select', options: options.regions },
        { prop: 'robotId', label: '机器人编号' },
        { prop: 'robotName', label: '名称' },
        { prop: 'robotIp', label: 'IP' },
        { prop: 'robotType', label: '型号' },
        { prop: 'batteryState', label: '电量', type: 'number' },
        { prop: 'enable', label: '状态', type: 'select', options: enableOptions },
        { prop: 'remark', label: '备注', type: 'textarea' }
      ]
    },
    defaults: { enable: 1, batteryState: 100 }
  },
  task: {
    title: '任务配置',
    rowKey: 'id',
    list: listTasks,
    create: createTask,
    update: (payload) => updateTask(payload.id, payload),
    remove: (ids) => deleteTask(Array.isArray(ids) ? ids[0] : ids),
    columns: [
      { prop: 'id', label: 'ID', width: 80 },
      { prop: 'taskName', label: '任务名称', minWidth: 180 },
      { prop: 'robotName', label: '机器人', minWidth: 160 },
      { prop: 'commandCn', label: '指令', minWidth: 140 },
      { prop: 'priority', label: '优先级', width: 100 }
    ],
    formFields: async () => {
      const options = await loadConfigOptions()
      return [
        { prop: 'loungeId', label: '贵宾室', type: 'select', options: options.lounges },
        { prop: 'robotId', label: '机器人', type: 'select', options: options.robots },
        { prop: 'taskName', label: '任务名称' },
        { prop: 'commandCode', label: '指令编码', type: 'number' },
        { prop: 'commandCn', label: '指令名称' },
        { prop: 'priority', label: '优先级' },
        { prop: 'executeType', label: '执行类型' },
        { prop: 'taskType', label: '任务类型' },
        { prop: 'remark', label: '备注', type: 'textarea' }
      ]
    },
    rowActions: [
      { key: 'run', label: '执行', handler: (row) => runTask(row.id) }
    ]
  },
  complaint: {
    title: '投诉记录',
    rowKey: 'id',
    list: listComplaints,
    create: createComplaint,
    update: (payload) => updateComplaint(payload.id, payload),
    remove: (ids) => deleteComplaint(Array.isArray(ids) ? ids[0] : ids),
    columns: [
      { prop: 'id', label: 'ID', width: 80 },
      { prop: 'userName', label: '姓名', minWidth: 120 },
      { prop: 'deptName', label: '贵宾室', minWidth: 160 },
      { prop: 'cardService', label: '发卡方', minWidth: 140 },
      { prop: 'complaintContent', label: '客诉内容', minWidth: 240 }
    ],
    formFields: async () => {
      const options = await loadConfigOptions()
      return [
        { prop: 'loungeId', label: '贵宾室', type: 'select', options: options.lounges },
        { prop: 'userName', label: '旅客姓名' },
        { prop: 'cardService', label: '发卡方' },
        { prop: 'cardNo', label: '卡号' },
        { prop: 'complaintContent', label: '客诉内容', type: 'textarea' },
        { prop: 'complaintFeedback', label: '处理反馈', type: 'textarea' }
      ]
    }
  },
  passenger: {
    title: '在厅旅客',
    rowKey: 'id',
    list: listInLounge,
    columns: [
      { prop: 'id', label: 'ID', width: 80 },
      { prop: 'passengerName', label: '旅客姓名', minWidth: 160 },
      { prop: 'flightNo', label: '航班号', minWidth: 120 },
      { prop: 'regionName', label: '区域', minWidth: 140 },
      { prop: 'accessStatus', label: '状态', map: passengerStatusMap }
    ]
  },
  outgoingPassenger: {
    title: '出厅旅客',
    rowKey: 'id',
    list: listOutgoing,
    columns: [
      { prop: 'id', label: 'ID', width: 80 },
      { prop: 'passengerName', label: '旅客姓名', minWidth: 160 },
      { prop: 'flightNo', label: '航班号', minWidth: 120 },
      { prop: 'checkOutAt', label: '出厅时间', minWidth: 180 }
    ]
  },
  operlog: {
    title: '操作日志',
    rowKey: 'id',
    list: listOperationLogs,
    columns: [
      { prop: 'id', label: 'ID', width: 80 },
      { prop: 'moduleName', label: '模块', minWidth: 160 },
      { prop: 'operatorName', label: '操作人', minWidth: 120 },
      { prop: 'requestUrl', label: '请求地址', minWidth: 200 }
    ],
    headerActions: [
      { key: 'clean', label: '清空', handler: clearOperationLogs }
    ]
  },
  logininfor: {
    title: '登录日志',
    rowKey: 'id',
    list: listLoginLogs,
    columns: [
      { prop: 'id', label: 'ID', width: 80 },
      { prop: 'username', label: '用户名', minWidth: 140 },
      { prop: 'ipAddress', label: 'IP', minWidth: 140 },
      { prop: 'browser', label: '浏览器', minWidth: 140 },
      { prop: 'message', label: '信息', minWidth: 220 }
    ],
    headerActions: [
      { key: 'clean', label: '清空', handler: clearLoginLogs }
    ]
  }
}

export function getCrudPage(key) {
  return crudPages[key]
}
