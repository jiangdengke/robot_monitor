import {
  listInLounge,
  listOutgoing
} from '@/api/system'
import {
  accessTypeMap,
  accessTypeOptions,
  formatDateTime,
  loadConfigOptions,
  passengerStatusMap,
  passengerStatusTagMap
} from './shared'

function createPassengerSearchFields() {
  return async () => {
    const options = await loadConfigOptions()
    return [
      { prop: 'roomCode', label: '贵宾室', type: 'select', options: options.loungeRooms },
      { prop: 'flightDate', label: '航班日期', type: 'date' },
      { prop: 'cardNo', label: '卡号' },
      { prop: 'accessType', label: '准入类型', type: 'select', options: accessTypeOptions }
    ]
  }
}

const passengerColumns = [
  { prop: 'id', label: 'ID', width: 80 },
  { prop: 'passengerName', label: '旅客姓名', minWidth: 130 },
  { prop: 'deptName', label: '贵宾室', minWidth: 160 },
  { prop: 'flightNo', label: '航班号', minWidth: 120 },
  { prop: 'flightDate', label: '航班日期', minWidth: 120 },
  { prop: 'cardProvider', label: '发卡方', minWidth: 120 },
  { prop: 'cardNo', label: '卡号', minWidth: 150 },
  { prop: 'accessType', label: '准入类型', minWidth: 120, map: accessTypeMap },
  { prop: 'checkInAt', label: '入舱时间', minWidth: 170, formatter: formatDateTime },
  { prop: 'checkOutAt', label: '出舱时间', minWidth: 170, formatter: formatDateTime },
  { prop: 'regionName', label: '区域', minWidth: 140 },
  { prop: 'accessStatus', label: '在舱状态', width: 110, map: passengerStatusMap, tag: 'info', tagMap: passengerStatusTagMap }
]

export const statisticsCrudPages = {
  passenger: {
    title: '在舱记录',
    rowKey: 'id',
    list: listInLounge,
    searchFields: createPassengerSearchFields(),
    columns: passengerColumns
  },
  outgoingPassenger: {
    title: '准出记录',
    rowKey: 'id',
    list: listOutgoing,
    searchFields: createPassengerSearchFields(),
    columns: passengerColumns
  }
}
