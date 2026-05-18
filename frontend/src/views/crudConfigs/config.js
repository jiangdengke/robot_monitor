import { complaintCrudPages } from './configPages/complaint'
import { deviceRobotTaskCrudPages } from './configPages/deviceRobotTask'
import { mediaCrudPages } from './configPages/media'
import { spaceCrudPages } from './configPages/space'

export const configCrudPages = {
  ...spaceCrudPages,
  ...mediaCrudPages,
  ...deviceRobotTaskCrudPages,
  ...complaintCrudPages
}
