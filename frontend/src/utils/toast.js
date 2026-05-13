import { ElMessage } from 'element-plus'

export function showToast(type, message) {
  if (!message) {
    return
  }
  ElMessage({
    type,
    message,
    grouping: true
  })
}

export function resolveFeedbackMessage(result, fallback = '') {
  return result?.msg || result?.data?.msg || fallback
}

export const toastSuccess = (message) => showToast('success', message)
export const toastError = (message) => showToast('error', message)
export const toastWarning = (message) => showToast('warning', message)
export const toastInfo = (message) => showToast('info', message)
