import { uploadFiles } from '@/api/crud'
import { resolveFeedbackMessage } from '@/utils/toast'

export function useCrudFileActions(props, state, context) {
  const {
    uploadMessage,
    uploadMessageType,
    pendingFiles,
    importVisible,
    importFile,
    importUpdateSupport,
    importMessage,
    importMessageType,
    formVisible,
    form
  } = state
  const {
    loadRows,
    showMessage
  } = context

  function openImportDialog() {
    importVisible.value = true
    importMessage.value = ''
    importFile.value = null
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

  return {
    openImportDialog,
    handleUploadChange,
    submitUpload,
    handleImportChange,
    submitImport
  }
}
