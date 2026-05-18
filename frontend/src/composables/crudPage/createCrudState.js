import { reactive, ref } from 'vue'
import { defaultIcons } from './constants'

export function createCrudState() {
  return {
    rows: ref([]),
    total: ref(0),
    pageNum: ref(1),
    pageSize: ref(20),
    loading: ref(false),
    selectedRows: ref([]),
    errorMessage: ref(''),
    successMessage: ref(''),
    formVisible: ref(false),
    detailVisible: ref(false),
    uploadVisible: ref(false),
    uploadMessage: ref(''),
    uploadMessageType: ref('success'),
    pendingFiles: ref([]),
    promptVisible: ref(false),
    promptTitle: ref(''),
    promptFields: ref([]),
    promptForm: reactive({}),
    pendingPromptAction: ref(null),
    pendingPromptRow: ref(null),
    importVisible: ref(false),
    importFile: ref(null),
    importUpdateSupport: ref(false),
    importMessage: ref(''),
    importMessageType: ref('success'),
    formMode: ref('create'),
    detail: ref({}),
    form: reactive({}),
    query: reactive({}),
    resolvedFormFields: ref([]),
    resolvedSearchFields: ref([]),
    treeSelectProps: { value: 'id', label: 'label', children: 'children' },
    defaultIcons
  }
}
