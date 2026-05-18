import { deleteResource } from '@/api/crud'
import { resolveFeedbackMessage } from '@/utils/toast'

export function useCrudDeleteActions(props, state, context) {
  const {
    selectedRows,
    errorMessage,
    successMessage
  } = state
  const {
    loadRows,
    showMessage,
    confirmDialog
  } = context

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

  return {
    deleteSelected,
    deleteOne,
    deleteByIds
  }
}
