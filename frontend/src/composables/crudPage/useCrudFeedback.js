import { message, Modal } from 'ant-design-vue'

const messageMap = {
  success: message.success,
  error: message.error,
  warning: message.warning,
  info: message.info
}

export function useCrudFeedback() {
  function showMessage(type, messageText) {
    if (!messageText) {
      return
    }
    const fn = messageMap[type] || messageMap.info
    fn(messageText)
  }

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

  return {
    messageMap,
    showMessage,
    confirmDialog
  }
}
