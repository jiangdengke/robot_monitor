import {
  listAreas,
  listDevices,
  listPoints,
  listRobots,
  listSites
} from '@/api/system'

export const enableOptions = [
  { label: '启用', value: 1 },
  { label: '停用', value: 0 }
]

export const showOptions = [
  { label: '展示', value: '1' },
  { label: '隐藏', value: '0' }
]

export const deviceTypeOptions = [
  { label: '摄像头', value: 'CAMERA' },
  { label: '门禁', value: 'GATE' },
  { label: '其他', value: 'OTHER' }
]

export function formatDateTime(value) {
  if (!value) {
    return '-'
  }
  return String(value)
    .replace('T', ' ')
    .replace(/\.\d+/, '')
    .replace(/([+-]\d{2}:?\d{2}|Z)$/, '')
    .slice(0, 19)
}

export function deleteSelectedResources(deleteResource, ids) {
  const resourceIds = Array.isArray(ids) ? ids : [ids]
  return Promise.all(resourceIds.map((resourceId) => deleteResource(resourceId)))
}

export function filterOptionsBySite(options, siteId) {
  if (siteId === undefined || siteId === null || siteId === '') {
    return []
  }
  return options.filter((option) => option.raw?.siteId === siteId)
}

export function updateSiteScopedField(siteId, context, dependentProp, options) {
  context.form[dependentProp] = null
  const dependentField = context.fields.find((field) => field.prop === dependentProp)
  if (dependentField) {
    dependentField.options = filterOptionsBySite(options, siteId)
  }
}

function optionsFromRows(rows = [], valueKey = 'id', labelKey = 'name') {
  return rows.map((row) => ({ value: row[valueKey], label: row[labelKey], raw: row }))
}

export async function loadSiteOptions() {
  const response = await listSites()
  return (response.rows || []).map((site) => ({
    value: site.id,
    label: `${site.siteName} (${site.siteCode})`,
    raw: site
  }))
}

export async function loadAreaOptions() {
  const response = await listAreas()
  return optionsFromRows(response.rows || [], 'id', 'areaName')
}

export async function loadPointOptions() {
  const response = await listPoints()
  return optionsFromRows(response.rows || [], 'id', 'pointName')
}

export async function loadRobotOptions() {
  const response = await listRobots()
  return optionsFromRows(response.rows || [], 'id', 'robotName')
}

export async function loadDeviceOptions() {
  const response = await listDevices()
  return optionsFromRows(response.rows || [], 'id', 'deviceName')
}
