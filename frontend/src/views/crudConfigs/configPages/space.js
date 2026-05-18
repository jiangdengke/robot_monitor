import {
  createArea,
  createLounge,
  createRegion,
  deleteArea,
  deleteLounge,
  deleteRegion,
  listAreas,
  listLounges,
  listRegions,
  updateArea,
  updateLounge,
  updateRegion
} from '@/api/system'
import {
  enableOptions,
  guideOptions,
  loadConfigOptions,
  showOptions
} from '../shared'

export const spaceCrudPages = {
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
  }
}
