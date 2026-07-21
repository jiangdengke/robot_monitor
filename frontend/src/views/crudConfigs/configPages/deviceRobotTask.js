import {
  createDevice,
  createRobot,
  createTask,
  deleteDevice,
  deleteRobot,
  deleteTask,
  listDevices,
  listRobots,
  listTasks,
  runTask,
  updateDevice,
  updateRobot,
  updateTask
} from '@/api/system'
import {
  deleteSelectedResources,
  deviceTypeOptions,
  enableOptions,
  filterOptionsBySite,
  loadPointOptions,
  loadRobotOptions,
  loadSiteOptions,
  updateSiteScopedField
} from '../shared'

export const deviceRobotTaskCrudPages = {
  device: {
    title: '设备管理',
    rowKey: 'id',
    list: listDevices,
    create: createDevice,
    update: (payload) => updateDevice(payload.id, payload),
    remove: (ids) => deleteSelectedResources(deleteDevice, ids),
    columns: [
      { prop: 'id', label: 'ID', width: 80 },
      { prop: 'deviceName', label: '设备名称', minWidth: 180 },
      { prop: 'siteName', label: '场地', minWidth: 160 },
      { prop: 'deviceType', label: '设备类型', minWidth: 120 },
      { prop: 'deepGlintDeviceId', label: '外部设备 ID', minWidth: 180 }
    ],
    formFields: async () => [
      { prop: 'siteId', label: '场地', type: 'select', options: await loadSiteOptions() },
      { prop: 'deviceName', label: '设备名称' },
      { prop: 'deviceType', label: '设备类型', type: 'select', options: deviceTypeOptions },
      { prop: 'deepGlintDeviceId', label: '外部设备 ID' },
      { prop: 'enable', label: '状态', type: 'select', options: enableOptions },
      { prop: 'remark', label: '备注', type: 'textarea' }
    ],
    defaults: { enable: 1, deviceType: 'CAMERA' }
  },
  robot: {
    title: '机器人配置',
    rowKey: 'id',
    list: listRobots,
    create: createRobot,
    update: (payload) => updateRobot(payload.id, payload),
    remove: (ids) => deleteSelectedResources(deleteRobot, ids),
    columns: [
      { prop: 'id', label: 'ID', width: 80 },
      { prop: 'robotId', label: '机器人编号', minWidth: 160 },
      { prop: 'robotName', label: '名称', minWidth: 160 },
      { prop: 'siteName', label: '场地', minWidth: 160 },
      { prop: 'pointName', label: '点位', minWidth: 140 },
      { prop: 'robotIp', label: 'IP', minWidth: 140 },
      { prop: 'batteryState', label: '电量', width: 80 }
    ],
    formFields: async ({ form = {} } = {}) => {
      const [siteOptions, pointOptions] = await Promise.all([
        loadSiteOptions(),
        loadPointOptions()
      ])
      const pointField = {
        prop: 'pointId',
        label: '点位',
        type: 'select',
        options: filterOptionsBySite(pointOptions, form.siteId)
      }
      const siteField = {
        prop: 'siteId',
        label: '场地',
        type: 'select',
        options: siteOptions,
        onChange: (siteId, context) => updateSiteScopedField(siteId, context, 'pointId', pointOptions)
      }
      return [
        siteField,
        pointField,
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
    remove: (ids) => deleteSelectedResources(deleteTask, ids),
    columns: [
      { prop: 'id', label: 'ID', width: 80 },
      { prop: 'taskName', label: '任务名称', minWidth: 180 },
      { prop: 'robotName', label: '机器人', minWidth: 160 },
      { prop: 'siteName', label: '场地', minWidth: 160 },
      { prop: 'targetPoint', label: '目标点位', minWidth: 140 },
      { prop: 'commandName', label: '指令', minWidth: 140 },
      { prop: 'priority', label: '优先级', width: 100 }
    ],
    formFields: async ({ form = {} } = {}) => {
      const [siteOptions, robotOptions] = await Promise.all([
        loadSiteOptions(),
        loadRobotOptions()
      ])
      const robotField = {
        prop: 'robotId',
        label: '机器人',
        type: 'select',
        options: filterOptionsBySite(robotOptions, form.siteId)
      }
      const siteField = {
        prop: 'siteId',
        label: '场地',
        type: 'select',
        options: siteOptions,
        onChange: (siteId, context) => updateSiteScopedField(siteId, context, 'robotId', robotOptions)
      }
      return [
        siteField,
        robotField,
        { prop: 'taskName', label: '任务名称' },
        { prop: 'commandCode', label: '指令编码', type: 'number' },
        { prop: 'commandName', label: '指令名称' },
        { prop: 'targetPoint', label: '目标点位' },
        { prop: 'priority', label: '优先级' },
        { prop: 'executeType', label: '执行类型' },
        { prop: 'executeAt', label: '执行时间' },
        { prop: 'taskType', label: '任务类型' },
        { prop: 'remark', label: '备注', type: 'textarea' }
      ]
    },
    rowActions: [
      { key: 'run', label: '执行', handler: (row) => runTask(row.id) }
    ]
  }
}
