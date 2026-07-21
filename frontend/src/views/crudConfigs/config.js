import { deviceRobotTaskCrudPages } from './configPages/deviceRobotTask'
import { spaceCrudPages } from './configPages/space'

export const configCrudPages = {
  ...spaceCrudPages,
  ...deviceRobotTaskCrudPages
}
