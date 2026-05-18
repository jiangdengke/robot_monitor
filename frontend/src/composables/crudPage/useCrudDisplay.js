import { computed } from 'vue'
import { resolveDictLabel, resolveDictTagType } from '@/utils/dict'
import { hasAnyPermission } from '@/utils/permission'
import { commonValueMap } from './constants'
import { getByPath } from './helpers'

export function useCrudDisplay(props, state) {
  const {
    rows,
    query,
    form,
    formMode,
    resolvedFormFields
  } = state

  const hasListHandler = computed(() => typeof props.list === 'function' || Boolean(props.listPath || props.basePath))
  const hasCreateHandler = computed(() => typeof props.create === 'function' || Boolean(props.createPath || props.basePath))
  const hasUpdateHandler = computed(() => typeof props.update === 'function' || Boolean(props.updatePath || props.basePath))
  const hasDeleteHandler = computed(() => typeof props.remove === 'function' || Boolean(props.deletePath || props.basePath))
  const hasDetailHandler = computed(() => typeof props.detail === 'function' || Boolean(props.detailLoader || props.detailPath || props.basePath))

  const visibleFormFields = computed(() =>
    resolvedFormFields.value.filter((field) => !field.hidden?.({ form, mode: formMode.value }))
  )
  const visibleColumns = computed(() =>
    props.columns.filter((column) => !isHiddenColumn(column))
  )
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

  return {
    hasListHandler,
    hasDetailHandler,
    visibleFormFields,
    visibleColumns,
    canCreate,
    canEdit,
    canDelete,
    canShowDetail,
    visibleHeaderActions,
    visibleRowActions,
    isHiddenColumn,
    getRawValue,
    displayValue,
    translateKnownValue,
    displayImageValue,
    resolveTagType,
    canAction,
    commonValueMap
  }
}
