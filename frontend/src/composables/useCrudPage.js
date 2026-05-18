import { computed, onMounted, reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { message, Modal } from 'ant-design-vue'
import { createResource, deleteResource, getResource, listResource, normalizeRows, normalizeTotal, updateResource, uploadFiles } from '@/api/crud'
import { request } from '@/api/http'
import { loadDictOptions, resolveDictLabel, resolveDictTagType } from '@/utils/dict'
import { hasAnyPermission } from '@/utils/permission'
import { resolveFeedbackMessage } from '@/utils/toast'

export function useCrudPage(props) {
  const router = useRouter()
  const rows = ref([])
  const total = ref(0)
  const pageNum = ref(1)
  const pageSize = ref(20)
  const loading = ref(false)
  const selectedRows = ref([])
  const errorMessage = ref('')
  const successMessage = ref('')
  const formVisible = ref(false)
  const detailVisible = ref(false)
  const uploadVisible = ref(false)
  const uploadMessage = ref('')
  const uploadMessageType = ref('success')
  const pendingFiles = ref([])
  const promptVisible = ref(false)
  const promptTitle = ref('')
  const promptFields = ref([])
  const promptForm = reactive({})
  const pendingPromptAction = ref(null)
  const pendingPromptRow = ref(null)
  const importVisible = ref(false)
  const importFile = ref(null)
  const importUpdateSupport = ref(false)
  const importMessage = ref('')
  const importMessageType = ref('success')
  const formMode = ref('create')
  const detail = ref({})
  const form = reactive({})
  const query = reactive({})
  const resolvedFormFields = ref([])
  const resolvedSearchFields = ref([])
  const treeSelectProps = { value: 'id', label: 'label', children: 'children' }
  const defaultIcons = [
    'House',
    'Menu',
    'Grid',
    'Setting',
    'User',
    'UserFilled',
    'Avatar',
    'Lock',
    'Key',
    'Tools',
    'Monitor',
    'Cpu',
    'DataLine',
    'PieChart',
    'Histogram',
    'Document',
    'Tickets',
    'Folder',
    'Files',
    'Bell',
    'Message',
    'Picture',
    'VideoCamera',
    'Microphone',
    'Headset',
    'MapLocation',
    'Location',
    'Guide',
    'Van',
    'Dish',
    'ShoppingCart',
    'Calendar',
    'Clock',
    'Search',
    'Edit',
    'Delete',
    'Plus',
    'InboxOutlined',
    'Download',
    'Refresh'
  ]

  const visibleFormFields = computed(() =>
    resolvedFormFields.value.filter((field) => !field.hidden?.({ form, mode: formMode.value }))
  )
  const visibleColumns = computed(() =>
    props.columns.filter((column) => !isHiddenColumn(column))
  )
  const hasListHandler = computed(() => typeof props.list === 'function' || Boolean(props.listPath || props.basePath))
  const hasCreateHandler = computed(() => typeof props.create === 'function' || Boolean(props.createPath || props.basePath))
  const hasUpdateHandler = computed(() => typeof props.update === 'function' || Boolean(props.updatePath || props.basePath))
  const hasDeleteHandler = computed(() => typeof props.remove === 'function' || Boolean(props.deletePath || props.basePath))
  const hasDetailHandler = computed(() => typeof props.detail === 'function' || Boolean(props.detailLoader || props.detailPath || props.basePath))
  const canCreate = computed(() => props.enableCreate && hasCreateHandler.value && canAction('add'))
  const canEdit = computed(() => props.enableEdit && hasUpdateHandler.value && canAction('edit'))
  const canDelete = computed(() => props.enableDelete && hasDeleteHandler.value && canAction('remove'))
  const canShowDetail = computed(() => props.showDetail && hasDetailHandler.value)
  const visibleHeaderActions = computed(() => props.headerActions.filter((action) => canAction(action.permission || action.key, action.permissions)))
  const visibleRowActions = computed(() => props.rowActions.filter((action) => canAction(action.permission || action.key, action.permissions)))

  function isHiddenColumn(column) {
    if (typeof column.hidden === 'function') {
      return column.hidden({ rows: rows.value, query })
    }
    if (column.hidden) {
      return true
    }
    return column.prop === props.rowKey && String(column.label).toLowerCase() === 'id'
  }

  function resetObject(target, value = {}) {
    Object.keys(target).forEach((key) => delete target[key])
    Object.assign(target, value)
  }

  function getByPath(target, path) {
    return path.split('.').reduce((current, key) => current?.[key], target)
  }

  function getRawValue(row, column) {
    return column.prop.includes('.') ? getByPath(row, column.prop) : row?.[column.prop]
  }

  function displayValue(row, column) {
    const raw = getRawValue(row, column)
    if (column.dictOptions) {
      return resolveDictLabel(column.dictOptions, raw)
    }
    if (column.formatter) {
      return column.formatter(raw, row)
    }
    const value = column.map ? column.map[String(raw)] : translateKnownValue(raw)
    return value === undefined || value === null || value === '' ? '-' : value
  }

  function translateKnownValue(value) {
    if (value === undefined || value === null) {
      return value
    }
    return commonValueMap[String(value)] ?? value
  }

  function displayImageValue(row, column) {
    if (column.imageUrl) {
      return column.imageUrl(row)
    }
    return displayValue(row, column)
  }

  function resolveTagType(column, row) {
    const raw = column.prop.includes('.') ? getByPath(row, column.prop) : row?.[column.prop]
    if (column.dictOptions) {
      return resolveDictTagType(column.dictOptions, raw)
    }
    return column.tagMap?.[String(raw)] || column.tag || 'info'
  }

  function canAction(action, permissions = null) {
    const values = permissions || props.permissions?.[action]
    return !values || hasAnyPermission(Array.isArray(values) ? values : [values])
  }

  function showMessage(type, message) {
    if (!message) {
      return
    }
    const fn = messageMap[type] || messageMap.info
    fn(message)
  }

  async function hydrateDictColumns() {
    const dictColumns = props.columns.filter((column) => column.dictType && !column.dictOptions)
    if (!dictColumns.length) return
    await Promise.all(dictColumns.map(async (column) => {
      column.dictOptions = await loadDictOptions(column.dictType)
      column.tag = column.tag || 'info'
    }))
  }

  async function resolveSearchFields() {
    const fields = typeof props.searchFields === 'function'
      ? await props.searchFields({ query, rows: rows.value })
      : props.searchFields
    resolvedSearchFields.value = await Promise.all(
      (fields || []).map(async (field) => {
        if (typeof field.options === 'function') {
          return {
            ...field,
            options: await field.options()
          }
        }
        return field
      })
    )
  }

  async function loadRows() {
    if (!hasListHandler.value) {
      rows.value = []
      total.value = 0
      return
    }
    loading.value = true
    errorMessage.value = ''
    try {
      const params = {
        ...(props.pagination ? { pageNum: pageNum.value, pageSize: pageSize.value } : {}),
        ...query
      }
      const payload = typeof props.list === 'function'
        ? await props.list(params)
        : props.listPath
        ? await request(props.listPath, { method: props.listMethod, query: params })
        : await listResource(props.basePath, params, props.listMethod)
      const normalizedRows = normalizeRows(payload)
      rows.value = props.transformRows ? props.transformRows(normalizedRows) : normalizedRows
      total.value = props.pagination ? normalizeTotal(payload) : rows.value.length
    } catch (error) {
      const message = error?.payload?.msg || error?.message || '加载失败'
      errorMessage.value = message
      showMessage('error', message)
    } finally {
      loading.value = false
    }
  }

  function handleSearch() {
    pageNum.value = 1
    loadRows()
  }

  function resetSearch() {
    resetObject(query, props.initialQuery)
    handleSearch()
  }

  async function resolveFormFields(context = {}) {
    const fields = typeof props.formFields === 'function' ? await props.formFields(context) : props.formFields
    resolvedFormFields.value = fields || []
    applyFieldDefaults(form, resolvedFormFields.value)
  }

  function applyFieldDefaults(target, fields) {
    fields.forEach((field) => {
      if (field.defaultValue === undefined) {
        return
      }
      const current = target[field.prop]
      const emptyArray = Array.isArray(current) && current.length === 0
      if (current === undefined || current === null || current === '' || emptyArray) {
        target[field.prop] = typeof field.defaultValue === 'function' ? field.defaultValue() : field.defaultValue
      }
    })
  }

  async function openCreate() {
    formMode.value = 'create'
    resetObject(form, { ...props.defaults })
    await resolveFormFields({ mode: 'create', row: null, response: null, form: { ...form } })
    formVisible.value = true
  }

  async function openEdit(row) {
    formMode.value = 'edit'
    errorMessage.value = ''
    try {
      const response = await loadDetail(row)
      const detailData = props.transformDetail ? await props.transformDetail(response, row) : (response?.data || response || row)
      resetObject(form, { ...props.defaults, ...detailData })
      await resolveFormFields({ mode: 'edit', row, response, form: { ...form } })
      formVisible.value = true
    } catch (error) {
      resetObject(form, { ...props.defaults, ...row })
      await resolveFormFields({ mode: 'edit', row, response: null, form: { ...form } })
      formVisible.value = true
    }
  }

  async function openDetail(row) {
    detail.value = row
    if (hasDetailHandler.value) {
      try {
        const response = await loadDetail(row)
        detail.value = props.transformDetail ? await props.transformDetail(response, row) : (response?.data || response || row)
      } catch {
        detail.value = row
      }
    }
    detailVisible.value = true
  }

  async function loadDetail(row) {
    if (props.detail) {
      return props.detail(row)
    }
    if (props.detailLoader) {
      return props.detailLoader(row)
    }
    if (props.detailPath) {
      return request(props.detailPath, {
        method: props.detailMethod,
        query: props.detailQuery ? props.detailQuery(row) : { [props.rowKey]: row[props.rowKey] }
      })
    }
    return getResource(props.basePath, row[props.rowKey])
  }

  async function submitForm() {
    try {
      const normalizedPayload = normalizeSubmitPayload({ ...form }, resolvedFormFields.value)
      const payload = props.beforeSubmit ? props.beforeSubmit(normalizedPayload, formMode.value) : normalizedPayload
      if (formMode.value === 'create') {
        const response = props.create
          ? await props.create(payload)
          : await createResource(props.createPath || props.basePath, payload, props.createMethod)
        successMessage.value = resolveFeedbackMessage(response, '新增成功')
        showMessage('success', successMessage.value)
      } else {
        const response = props.update
          ? await props.update(payload)
          : await updateResource(props.updatePath || props.basePath, payload, props.updateMethod)
        successMessage.value = resolveFeedbackMessage(response, '保存成功')
        showMessage('success', successMessage.value)
      }
      formVisible.value = false
      await loadRows()
    } catch (error) {
      const message = error?.payload?.msg || error?.message || '保存失败'
      errorMessage.value = message
      showMessage('error', message)
    }
  }

  function normalizeSubmitPayload(payload, fields) {
    fields.forEach((field) => {
      if (!field.joinArray || !Array.isArray(payload[field.prop])) {
        return
      }
      payload[field.prop] = payload[field.prop].join(field.joinDelimiter || ',')
    })
    return payload
  }

  function handleBase64FieldChange(field, file) {
    const raw = file.raw
    if (!raw) {
      return
    }
    const reader = new FileReader()
    reader.onload = () => {
      form[field.prop] = reader.result
      if (field.nameProp && !form[field.nameProp]) {
        form[field.nameProp] = raw.name
      }
    }
    reader.readAsDataURL(raw)
  }

  function resolvePreviewUrl(field, source) {
    if (field.url) {
      return typeof field.url === 'function' ? field.url(source) : field.url
    }
    return source[field.prop]
  }

  function handleFieldChange(field, value) {
    field.onChange?.(value, { form, field })
  }

  function addEditableListRow(field) {
    if (!Array.isArray(form[field.prop])) {
      form[field.prop] = []
    }
    const row = typeof field.newRow === 'function' ? field.newRow(form[field.prop]) : { ...(field.newRow || {}) }
    form[field.prop].push(row)
  }

  function removeEditableListRow(field, index) {
    form[field.prop]?.splice(index, 1)
  }

  function handleEditableChildChange(field, child, row, value) {
    child.onChange?.(value, row, { form, field, child })
  }

  function resolveDetailTableRows(table) {
    if (typeof table.rows === 'function') {
      return table.rows(detail.value) || []
    }
    return detail.value?.[table.prop] || []
  }

  async function handleSwitchChange(column, row, value) {
    const previous = getRawValue(row, column)
    row[column.prop] = value
    try {
      await column.action(row, value)
      successMessage.value = column.successMessage || '状态已更新'
      showMessage('success', successMessage.value)
      await loadRows()
    } catch (error) {
      row[column.prop] = previous
      const message = error?.payload?.msg || error?.message || '状态更新失败'
      errorMessage.value = message
      showMessage('error', message)
    }
  }

  async function runHeaderAction(action) {
    try {
      if (action.confirm) {
        await confirmDialog(action.confirmTitle || '操作确认', action.confirm)
      }
      if (action.kind === 'import') {
        importVisible.value = true
        importMessage.value = ''
        importFile.value = null
        return
      }
      if (action.route) {
        await router.push(typeof action.route === 'function' ? action.route({ query: { ...query }, selectedRows: selectedRows.value }) : action.route)
        return
      }
      const response = await action.handler?.({ rows: rows.value, selectedRows: selectedRows.value, query: { ...query }, loadRows })
      successMessage.value = resolveFeedbackMessage(response, action.successMessage || successMessage.value)
      if (successMessage.value) {
        showMessage('success', successMessage.value)
      }
      if (action.reload !== false) {
        await loadRows()
      }
    } catch (error) {
      if (error !== 'cancel') {
        const message = error?.payload?.msg || error?.message || action.errorMessage || '操作失败'
        errorMessage.value = message
        showMessage('error', message)
      }
    }
  }

  async function runRowAction(action, row) {
    try {
      if (action.promptFields?.length) {
        promptTitle.value = action.promptTitle || action.label
        promptFields.value = await resolvePromptFields(action, row)
        resetObject(promptForm, typeof action.promptDefaults === 'function' ? action.promptDefaults(row) : { ...(action.promptDefaults || {}) })
        applyFieldDefaults(promptForm, promptFields.value)
        pendingPromptAction.value = action
        pendingPromptRow.value = row
        promptVisible.value = true
        return
      }
      if (action.route) {
        await router.push(typeof action.route === 'function' ? action.route(row) : action.route)
        return
      }
      if (action.confirm) {
        await confirmDialog(action.confirmTitle || '操作确认', typeof action.confirm === 'function' ? action.confirm(row) : action.confirm)
      }
      const response = await action.handler?.(row, { loadRows })
      successMessage.value = resolveFeedbackMessage(response, action.successMessage || '操作成功')
      showMessage('success', successMessage.value)
      if (action.reload !== false) {
        await loadRows()
      }
    } catch (error) {
      if (error !== 'cancel') {
        const message = error?.payload?.msg || error?.message || action.errorMessage || '操作失败'
        errorMessage.value = message
        showMessage('error', message)
      }
    }
  }

  async function resolvePromptFields(action, row) {
    const fields = typeof action.promptFields === 'function' ? await action.promptFields(row) : action.promptFields
    return fields || []
  }

  async function submitPromptAction() {
    try {
      const response = await pendingPromptAction.value?.handler?.(pendingPromptRow.value, { form: { ...promptForm }, loadRows })
      promptVisible.value = false
      successMessage.value = resolveFeedbackMessage(response, pendingPromptAction.value?.successMessage || '操作成功')
      showMessage('success', successMessage.value)
      await loadRows()
    } catch (error) {
      const message = error?.payload?.msg || error?.message || '操作失败'
      errorMessage.value = message
      showMessage('error', message)
    }
  }

  async function deleteSelected() {
    if (!selectedRows.value.length) {
      showMessage('warning', '请先选择要删除的数据')
      return
    }
    await deleteByIds(selectedRows.value.map((row) => row[props.rowKey]))
  }

  async function deleteOne(row) {
    await deleteByIds(row[props.rowKey])
  }

  async function deleteByIds(ids) {
    try {
      await confirmDialog('删除确认', '确认删除所选数据？')
      const response = props.remove
        ? await props.remove(ids)
        : await deleteResource(props.deletePath || props.basePath, ids, props.deleteMethod)
      successMessage.value = resolveFeedbackMessage(response, '删除成功')
      showMessage('success', successMessage.value)
      await loadRows()
    } catch (error) {
      if (error !== 'cancel') {
        const message = error?.payload?.msg || error?.message || '删除失败'
        errorMessage.value = message
        showMessage('error', message)
      }
    }
  }

  function handleUploadChange(file, fileList) {
    pendingFiles.value = fileList.map((item) => item.raw).filter(Boolean)
  }

  async function submitUpload() {
    if (!pendingFiles.value.length) {
      showMessage('warning', '请先选择要上传的文件')
      return
    }
    try {
      const response = await uploadFiles(pendingFiles.value)
      uploadMessageType.value = 'success'
      uploadMessage.value = resolveFeedbackMessage(response, `上传成功：${response.originalFilenames || response.fileNames || ''}`)
      showMessage('success', uploadMessage.value)
      if (props.uploadField && formVisible.value) {
        form[props.uploadField] = String(response.fileNames || '').split(',')[0] || form[props.uploadField]
      }
    } catch (error) {
      uploadMessageType.value = 'error'
      uploadMessage.value = error?.payload?.msg || error?.message || '上传失败'
      showMessage('error', uploadMessage.value)
    }
  }

  function handleImportChange(file) {
    importFile.value = file.raw
  }

  async function submitImport() {
    if (!props.importAction) {
      showMessage('warning', '当前页面未配置导入接口')
      return
    }
    if (!importFile.value) {
      showMessage('warning', '请先选择要导入的文件')
      return
    }
    try {
      const response = await props.importAction(importFile.value, importUpdateSupport.value)
      importMessageType.value = 'success'
      importMessage.value = resolveFeedbackMessage(response, '导入成功')
      showMessage('success', importMessage.value)
      await loadRows()
    } catch (error) {
      importMessageType.value = 'error'
      importMessage.value = error?.payload?.msg || error?.message || '导入失败'
      showMessage('error', importMessage.value)
    }
  }

  onMounted(() => {
    resetObject(query, props.initialQuery)
    Promise.all([hydrateDictColumns(), resolveSearchFields()]).finally(loadRows)
  })

  function confirmDialog(title, content) {
    return new Promise((resolve, reject) => {
      Modal.confirm({
        title,
        content,
        onOk: resolve,
        onCancel: () => reject('cancel')
      })
    })
  }

  const messageMap = {
    success: message.success,
    error: message.error,
    warning: message.warning,
    info: message.info
  }

  const commonValueMap = {
    SUCCESS: '成功',
    FAILED: '失败',
    ERROR: '异常',
    CREATED: '已创建',
    PENDING: '待处理',
    RUNNING: '运行中',
    FINISHED: '已完成',
    DONE: '已完成',
    CANCELLED: '已取消',
    ROBOT: '机器人',
    MANUAL: '人工',
    AUTO: '自动',
    GUIDE: '引导',
    NOTICE: '提醒',
    BOARDING: '登机提醒',
    GATE_CHANGE: '登机口变更',
    FACE: '人脸识别',
    QRCODE: '扫码准入',
    ID_CARD: '身份证验证',
    IN: '在舱',
    OUT: '已出舱',
    HIGH: '高',
    NORMAL: '普通',
    LOW: '低',
    IMMEDIATELY: '立即执行'
  }

  return {
    rows, total, pageNum, pageSize,
    loading, selectedRows, errorMessage, successMessage,
    formVisible, detailVisible, uploadVisible, uploadMessage,
    uploadMessageType, pendingFiles, promptVisible, promptTitle,
    promptFields, promptForm, pendingPromptAction, pendingPromptRow,
    importVisible, importFile, importUpdateSupport, importMessage,
    importMessageType, formMode, detail, form,
    query, resolvedFormFields, resolvedSearchFields, treeSelectProps,
    defaultIcons, visibleFormFields, visibleColumns, canCreate,
    canEdit, canDelete, canShowDetail, visibleHeaderActions,
    visibleRowActions, isHiddenColumn, resetObject, getByPath,
    getRawValue, displayValue, translateKnownValue, displayImageValue,
    resolveTagType, canAction, showMessage, hydrateDictColumns,
    resolveSearchFields, loadRows, handleSearch, resetSearch,
    resolveFormFields, applyFieldDefaults, openCreate, openEdit,
    openDetail, loadDetail, submitForm, normalizeSubmitPayload,
    handleBase64FieldChange, resolvePreviewUrl, handleFieldChange, addEditableListRow,
    removeEditableListRow, handleEditableChildChange, resolveDetailTableRows, handleSwitchChange,
    runHeaderAction, runRowAction, resolvePromptFields, submitPromptAction,
    deleteSelected, deleteOne, deleteByIds, handleUploadChange,
    submitUpload, handleImportChange, submitImport, confirmDialog,
    messageMap, commonValueMap,
  }
}
