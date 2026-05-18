import {
  createAudio,
  createImage,
  deleteAudio,
  deleteImage,
  listAudios,
  listImages,
  listRobotAudios,
  updateAudio,
  updateImage
} from '@/api/system'
import {
  enableOptions,
  languageOptions,
  loadConfigOptions
} from '../shared'

export const mediaCrudPages = {
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
  }
}
