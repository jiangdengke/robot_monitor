import { useCrudDeleteActions } from './useCrudDeleteActions'
import { useCrudFileActions } from './useCrudFileActions'
import { useCrudOperationActions } from './useCrudOperationActions'

export function useCrudActions(props, state, context) {
  const {
    router,
    loadRows,
    showMessage,
    confirmDialog,
    applyFieldDefaults,
    getRawValue
  } = context

  const fileActions = useCrudFileActions(props, state, {
    loadRows,
    showMessage
  })
  const operationActions = useCrudOperationActions(props, state, {
    router,
    loadRows,
    showMessage,
    confirmDialog,
    applyFieldDefaults,
    getRawValue,
    openImportDialog: fileActions.openImportDialog
  })
  const deleteActions = useCrudDeleteActions(props, state, {
    loadRows,
    showMessage,
    confirmDialog
  })

  return {
    ...operationActions,
    ...deleteActions,
    ...fileActions
  }
}
