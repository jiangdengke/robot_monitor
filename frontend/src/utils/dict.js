import { getDictDataByType } from '@/api/system'

const dictCache = new Map()

const tagClassMap = {
  default: '',
  primary: 'primary',
  success: 'success',
  info: 'info',
  warning: 'warning',
  danger: 'danger'
}

async function loadDictOptions(dictType) {
  if (!dictType) return []
  if (dictCache.has(dictType)) {
    return dictCache.get(dictType)
  }
  try {
    const response = await getDictDataByType(dictType)
    const rows = response.data || response.rows || []
    const options = rows.map((row) => ({
      label: row.dictLabel,
      value: row.dictValue,
      type: tagClassMap[row.listClass] || row.listClass || '',
      cssClass: row.cssClass || '',
      raw: row
    }))
    dictCache.set(dictType, options)
    return options
  } catch {
    dictCache.set(dictType, [])
    return []
  }
}

function resolveDictLabel(options, value) {
  const matched = (options || []).find((item) => String(item.value) === String(value))
  return matched?.label ?? value
}

function resolveDictTagType(options, value) {
  const matched = (options || []).find((item) => String(item.value) === String(value))
  return matched?.type || 'info'
}

function clearDictCache(dictType) {
  if (dictType) {
    dictCache.delete(dictType)
    return
  }
  dictCache.clear()
}

export { clearDictCache, loadDictOptions, resolveDictLabel, resolveDictTagType }
