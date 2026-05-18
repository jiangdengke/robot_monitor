import {
  createComplaint,
  deleteComplaint,
  listComplaints,
  updateComplaint
} from '@/api/system'
import { loadConfigOptions } from '../shared'

export const complaintCrudPages = {
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
  }
}
