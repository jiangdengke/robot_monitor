import {
  createArea,
  createPoint,
  createSite,
  deleteArea,
  deletePoint,
  deleteSite,
  listAreas,
  listPoints,
  listSites,
  updateArea,
  updatePoint,
  updateSite
} from '@/api/system'
import {
  deleteSelectedResources,
  enableOptions,
  filterOptionsBySite,
  loadAreaOptions,
  loadSiteOptions,
  showOptions,
  updateSiteScopedField
} from '../shared'

export const spaceCrudPages = {
  site: {
    title: '场地管理',
    rowKey: 'id',
    list: listSites,
    create: createSite,
    update: (payload) => updateSite(payload.id, payload),
    remove: (ids) => deleteSelectedResources(deleteSite, ids),
    columns: [
      { prop: 'id', label: 'ID', width: 80 },
      { prop: 'siteName', label: '场地名称', minWidth: 180 },
      { prop: 'siteCode', label: '场地编码', minWidth: 140 },
      { prop: 'locationDesc', label: '位置', minWidth: 180 }
    ],
    formFields: [
      { prop: 'siteName', label: '场地名称' },
      { prop: 'siteCode', label: '场地编码' },
      { prop: 'locationDesc', label: '位置' },
      { prop: 'enabled', label: '启用状态', type: 'select', options: [{ label: '启用', value: true }, { label: '停用', value: false }] },
      { prop: 'remark', label: '备注', type: 'textarea' }
    ],
    defaults: { enabled: true }
  },
  area: {
    title: '区域管理',
    rowKey: 'id',
    list: listAreas,
    create: createArea,
    update: (payload) => updateArea(payload.id, payload),
    remove: (ids) => deleteSelectedResources(deleteArea, ids),
    columns: [
      { prop: 'id', label: 'ID', width: 80 },
      { prop: 'areaName', label: '区域名称', minWidth: 160 },
      { prop: 'siteName', label: '场地', minWidth: 160 },
      { prop: 'coordinate', label: '坐标', minWidth: 200 },
      { prop: 'maxCapacity', label: '容量', width: 100 }
    ],
    formFields: async () => [
      { prop: 'siteId', label: '场地', type: 'select', options: await loadSiteOptions() },
      { prop: 'areaName', label: '区域名称' },
      { prop: 'coordinate', label: '坐标' },
      { prop: 'maxCapacity', label: '最大容量', type: 'number' },
      { prop: 'isShow', label: '是否展示', type: 'select', options: showOptions },
      { prop: 'enable', label: '状态', type: 'select', options: enableOptions },
      { prop: 'remark', label: '备注', type: 'textarea' }
    ],
    defaults: { isShow: '1', enable: 1 }
  },
  point: {
    title: '点位管理',
    rowKey: 'id',
    list: listPoints,
    create: createPoint,
    update: (payload) => updatePoint(payload.id, payload),
    remove: (ids) => deleteSelectedResources(deletePoint, ids),
    columns: [
      { prop: 'id', label: 'ID', width: 80 },
      { prop: 'pointName', label: '点位名称', minWidth: 160 },
      { prop: 'siteName', label: '场地', minWidth: 160 },
      { prop: 'areaName', label: '区域', minWidth: 140 },
      { prop: 'maxCapacity', label: '容量', width: 100 }
    ],
    formFields: async ({ form = {} } = {}) => {
      const [siteOptions, areaOptions] = await Promise.all([
        loadSiteOptions(),
        loadAreaOptions()
      ])
      const areaField = {
        prop: 'areaId',
        label: '区域',
        type: 'select',
        options: filterOptionsBySite(areaOptions, form.siteId)
      }
      const siteField = {
        prop: 'siteId',
        label: '场地',
        type: 'select',
        options: siteOptions,
        onChange: (siteId, context) => updateSiteScopedField(siteId, context, 'areaId', areaOptions)
      }
      return [
        siteField,
        areaField,
        { prop: 'pointName', label: '点位名称' },
        { prop: 'coordinate', label: '坐标' },
        { prop: 'maxCapacity', label: '最大容量', type: 'number' },
        { prop: 'isShow', label: '是否展示', type: 'select', options: showOptions },
        { prop: 'enable', label: '状态', type: 'select', options: enableOptions },
        { prop: 'remark', label: '备注', type: 'textarea' }
      ]
    },
    defaults: { isShow: '1', enable: 1 }
  }
}
