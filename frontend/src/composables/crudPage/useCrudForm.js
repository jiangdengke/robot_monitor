import { createResource, getResource, updateResource } from '@/api/crud'
import { request } from '@/api/http'
import { resolveFeedbackMessage } from '@/utils/toast'
import { resetObject } from './helpers'

export function useCrudForm(props, state, context) {
  const {
    errorMessage,
    successMessage,
    formVisible,
    detailVisible,
    formMode,
    detail,
    form,
    resolvedFormFields
  } = state
  const {
    hasDetailHandler,
    loadRows,
    showMessage
  } = context

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

  return {
    resolveFormFields,
    applyFieldDefaults,
    openCreate,
    openEdit,
    openDetail,
    loadDetail,
    submitForm,
    normalizeSubmitPayload,
    handleBase64FieldChange,
    resolvePreviewUrl,
    handleFieldChange,
    addEditableListRow,
    removeEditableListRow,
    handleEditableChildChange,
    resolveDetailTableRows
  }
}
