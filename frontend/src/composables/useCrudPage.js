import { onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { commonValueMap } from './crudPage/constants'
import { createCrudState } from './crudPage/createCrudState'
import { useCrudActions } from './crudPage/useCrudActions'
import { useCrudData } from './crudPage/useCrudData'
import { useCrudDisplay } from './crudPage/useCrudDisplay'
import { useCrudFeedback } from './crudPage/useCrudFeedback'
import { useCrudForm } from './crudPage/useCrudForm'
import { getByPath, resetObject } from './crudPage/helpers'

export function useCrudPage(props) {
  const router = useRouter()
  const state = createCrudState()
  const feedback = useCrudFeedback()
  const display = useCrudDisplay(props, state)
  const data = useCrudData(props, state, {
    hasListHandler: display.hasListHandler,
    showMessage: feedback.showMessage
  })
  const form = useCrudForm(props, state, {
    hasDetailHandler: display.hasDetailHandler,
    loadRows: data.loadRows,
    showMessage: feedback.showMessage
  })
  const actions = useCrudActions(props, state, {
    router,
    loadRows: data.loadRows,
    showMessage: feedback.showMessage,
    confirmDialog: feedback.confirmDialog,
    applyFieldDefaults: form.applyFieldDefaults,
    getRawValue: display.getRawValue
  })

  onMounted(() => {
    resetObject(state.query, props.initialQuery)
    Promise.all([data.hydrateDictColumns(), data.resolveSearchFields()]).finally(data.loadRows)
  })

  return {
    ...state,
    ...display,
    ...data,
    ...form,
    ...actions,
    ...feedback,
    commonValueMap,
    getByPath,
    resetObject
  }
}
