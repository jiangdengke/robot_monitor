import {
  createUser,
  deleteUsers,
  getUserDetail,
  listUsers,
  updateUser
} from '@/api/system'

export const systemCrudPages = {
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
  }
}
