import { configCrudPages } from './crudConfigs/config'
import { logCrudPages } from './crudConfigs/logs'
import { statisticsCrudPages } from './crudConfigs/statistics'
import { systemCrudPages } from './crudConfigs/system'

export const crudPages = {
  ...systemCrudPages,
  ...configCrudPages,
  ...statisticsCrudPages,
  ...logCrudPages
}

export function getCrudPage(key) {
  return crudPages[key]
}
