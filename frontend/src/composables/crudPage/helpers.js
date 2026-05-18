export function resetObject(target, value = {}) {
  Object.keys(target).forEach((key) => delete target[key])
  Object.assign(target, value)
}

export function getByPath(target, path) {
  return path.split('.').reduce((current, key) => current?.[key], target)
}
