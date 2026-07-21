import { configCrudPages } from './crudConfigs/config'
import { logCrudPages } from './crudConfigs/logs'
import { systemCrudPages } from './crudConfigs/system'

export const crudPages = {
  ...systemCrudPages,
  ...configCrudPages,
  ...logCrudPages
}

export function getCrudPage(key) {
  return crudPages[key]
}
