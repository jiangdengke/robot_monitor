import { listResource, normalizeRows, normalizeTotal } from '@/api/crud'
import { request } from '@/api/http'
import { loadDictOptions } from '@/utils/dict'
import { resetObject } from './helpers'

export function useCrudData(props, state, context) {
  const {
    rows,
    total,
    pageNum,
    pageSize,
    loading,
    errorMessage,
    query,
    resolvedSearchFields
  } = state
  const { hasListHandler, showMessage } = context

  async function hydrateDictColumns() {
    const dictColumns = props.columns.filter((column) => column.dictType && !column.dictOptions)
    if (!dictColumns.length) return
    await Promise.all(dictColumns.map(async (column) => {
      column.dictOptions = await loadDictOptions(column.dictType)
      column.tag = column.tag || 'info'
    }))
  }

  async function resolveSearchFields() {
    const fields = typeof props.searchFields === 'function'
      ? await props.searchFields({ query, rows: rows.value })
      : props.searchFields
    resolvedSearchFields.value = await Promise.all(
      (fields || []).map(async (field) => {
        if (typeof field.options === 'function') {
          return {
            ...field,
            options: await field.options()
          }
        }
        return field
      })
    )
  }

  async function loadRows() {
    if (!hasListHandler.value) {
      rows.value = []
      total.value = 0
      return
    }
    loading.value = true
    errorMessage.value = ''
    try {
      const params = {
        ...(props.pagination ? { pageNum: pageNum.value, pageSize: pageSize.value } : {}),
        ...query
      }
      const payload = typeof props.list === 'function'
        ? await props.list(params)
        : props.listPath
        ? await request(props.listPath, { method: props.listMethod, query: params })
        : await listResource(props.basePath, params, props.listMethod)
      const normalizedRows = normalizeRows(payload)
      rows.value = props.transformRows ? props.transformRows(normalizedRows) : normalizedRows
      total.value = props.pagination ? normalizeTotal(payload) : rows.value.length
    } catch (error) {
      const message = error?.payload?.msg || error?.message || '加载失败'
      errorMessage.value = message
      showMessage('error', message)
    } finally {
      loading.value = false
    }
  }

  function handleSearch() {
    pageNum.value = 1
    loadRows()
  }

  function resetSearch() {
    resetObject(query, props.initialQuery)
    handleSearch()
  }

  return {
    hydrateDictColumns,
    resolveSearchFields,
    loadRows,
    handleSearch,
    resetSearch
  }
}
