import { message } from 'ant-design-vue'

export function showToast(type, content) {
  if (!content) {
    return
  }
  const fn = messageMap[type] || message.open
  fn({ content })
}

export function resolveFeedbackMessage(result, fallback = '') {
  return result?.msg || result?.data?.msg || fallback
}

export const toastSuccess = (message) => showToast('success', message)
export const toastError = (message) => showToast('error', message)
export const toastWarning = (message) => showToast('warning', message)
export const toastInfo = (message) => showToast('info', message)

const messageMap = {
  success: message.success,
  error: message.error,
  warning: message.warning,
  info: message.info
}
