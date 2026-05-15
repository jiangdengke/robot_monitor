const dictCache = new Map()

const staticDicts = {
  sys_normal_disable: [
    { label: '正常', value: '0', type: 'success' },
    { label: '停用', value: '1', type: 'error' }
  ]
}

async function loadDictOptions(dictType) {
  if (!dictType) return []
  if (dictCache.has(dictType)) return dictCache.get(dictType)
  const options = staticDicts[dictType] || []
  dictCache.set(dictType, options)
  return options
}

function resolveDictLabel(options, value) {
  const matched = (options || []).find((item) => String(item.value) === String(value))
  return matched?.label ?? value
}

function resolveDictTagType(options, value) {
  const matched = (options || []).find((item) => String(item.value) === String(value))
  return matched?.type || 'default'
}

function clearDictCache(dictType) {
  if (dictType) {
    dictCache.delete(dictType)
    return
  }
  dictCache.clear()
}

export { clearDictCache, loadDictOptions, resolveDictLabel, resolveDictTagType }
