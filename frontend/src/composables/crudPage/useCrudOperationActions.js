import { resolveFeedbackMessage } from '@/utils/toast'
import { resetObject } from './helpers'

export function useCrudOperationActions(props, state, context) {
  const {
    rows,
    selectedRows,
    errorMessage,
    successMessage,
    promptVisible,
    promptTitle,
    promptFields,
    promptForm,
    pendingPromptAction,
    pendingPromptRow,
    query
  } = state
  const {
    router,
    loadRows,
    showMessage,
    confirmDialog,
    applyFieldDefaults,
    getRawValue,
    openImportDialog
  } = context

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
        openImportDialog()
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

  return {
    handleSwitchChange,
    runHeaderAction,
    runRowAction,
    resolvePromptFields,
    submitPromptAction
  }
}
