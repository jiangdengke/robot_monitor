import {
  listAreas,
  listAudios,
  listDevices,
  listImages,
  listLounges,
  listRegions,
  listRobots
} from '@/api/system'

export const enableOptions = [
  { label: '启用', value: 1 },
  { label: '停用', value: 0 }
]

export const showOptions = [
  { label: '展示', value: '1' },
  { label: '隐藏', value: '0' }
]

export const guideOptions = [
  { label: '支持', value: '1' },
  { label: '不支持', value: '0' }
]

export const languageOptions = [
  { label: '中文', value: 'CN' },
  { label: '英文', value: 'EN' },
  { label: '俄文', value: 'RU' },
  { label: '日文', value: 'JP' }
]

export const deviceTypeOptions = [
  { label: '摄像头', value: 'CAMERA' },
  { label: '门禁', value: 'GATE' },
  { label: '其他', value: 'OTHER' }
]

export const passengerStatusMap = {
  IN: '在舱',
  OUT: '已出舱'
}

export const passengerStatusTagMap = {
  IN: 'success',
  OUT: 'info'
}

export const accessTypeMap = {
  FACE: '人脸识别',
  QRCODE: '扫码准入',
  ID_CARD: '身份证验证',
  MANUAL: '人工登记'
}

export const accessTypeOptions = [
  { label: '人脸识别', value: 'FACE' },
  { label: '扫码准入', value: 'QRCODE' },
  { label: '身份证验证', value: 'ID_CARD' },
  { label: '人工登记', value: 'MANUAL' }
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

function optionsFromRows(rows = [], valueKey = 'id', labelKey = 'name') {
  return rows.map((row) => ({ value: row[valueKey], label: row[labelKey] }))
}

export async function loadConfigOptions() {
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
    loungeRooms: loungeRows.map((item) => ({ value: item.roomCode, label: `${item.deptName} (${item.roomCode})`, raw: item })),
    regions: optionsFromRows(regions.rows || [], 'id', 'regionName'),
    areas: optionsFromRows(areas.rows || [], 'id', 'areaName'),
    images: optionsFromRows(images.rows || [], 'id', 'imgName'),
    audios: optionsFromRows(audios.rows || [], 'id', 'audioKey'),
    robots: optionsFromRows(robots.rows || [], 'id', 'robotName'),
    devices: optionsFromRows(devices.rows || [], 'id', 'deviceName')
  }
}
