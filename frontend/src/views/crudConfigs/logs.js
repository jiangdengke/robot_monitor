import {
  clearLoginLogs,
  clearOperationLogs,
  listLoginLogs,
  listOperationLogs
} from '@/api/system'

export const logCrudPages = {
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
