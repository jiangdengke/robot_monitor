import { request, upload } from './http'

export const login = (payload) =>
  request('/auth/login', {
    method: 'POST',
    body: JSON.stringify(payload)
  })

export const registerAccount = (payload) =>
  request('/auth/sign-up', {
    method: 'POST',
    body: JSON.stringify(payload)
  })

export const getUserInfo = () => request('/auth/me')

export const getProfile = () => request('/me')

export const updateProfile = (payload) =>
  request('/me', {
    method: 'PUT',
    body: JSON.stringify(payload)
  })

export const updatePassword = (oldPassword, newPassword) =>
  request('/me/password', {
    method: 'PUT',
    query: { oldPassword, newPassword }
  })

export const updateAvatar = (avatarUrl) =>
  request('/me/avatar', {
    method: 'PUT',
    query: { avatarUrl }
  })

export const listUsers = () => request('/users')
export const getUserDetail = (id) => request(`/users/${id}`)
export const createUser = (payload) =>
  request('/users', { method: 'POST', body: JSON.stringify(payload) })
export const updateUser = (id, payload) =>
  request(`/users/${id}`, { method: 'PUT', body: JSON.stringify(payload) })
export const deleteUsers = (ids) =>
  request(`/users/${Array.isArray(ids) ? ids.join(',') : ids}`, { method: 'DELETE' })

export const listLounges = () => request('/config/lounges')
export const createLounge = (payload) => request('/config/lounges', { method: 'POST', body: JSON.stringify(payload) })
export const updateLounge = (id, payload) => request(`/config/lounges/${id}`, { method: 'PUT', body: JSON.stringify(payload) })
export const deleteLounge = (id) => request(`/config/lounges/${id}`, { method: 'DELETE' })
export const getDeptTree = listLounges
export const getRoomList = listLounges
export const getDeptExcludeList = () => listLounges()

export const listRegions = () => request('/config/regions')
export const createRegion = (payload) => request('/config/regions', { method: 'POST', body: JSON.stringify(payload) })
export const updateRegion = (id, payload) => request(`/config/regions/${id}`, { method: 'PUT', body: JSON.stringify(payload) })
export const deleteRegion = (id) => request(`/config/regions/${id}`, { method: 'DELETE' })
export const listConfigRegions = listRegions

export const listAreas = () => request('/config/areas')
export const createArea = (payload) => request('/config/areas', { method: 'POST', body: JSON.stringify(payload) })
export const updateArea = (id, payload) => request(`/config/areas/${id}`, { method: 'PUT', body: JSON.stringify(payload) })
export const deleteArea = (id) => request(`/config/areas/${id}`, { method: 'DELETE' })
export const listConfigAreas = listAreas

export const listImages = () => request('/config/images')
export const createImage = (payload) => request('/config/images', { method: 'POST', body: JSON.stringify(payload) })
export const updateImage = (id, payload) => request(`/config/images/${id}`, { method: 'PUT', body: JSON.stringify(payload) })
export const deleteImage = (id) => request(`/config/images/${id}`, { method: 'DELETE' })
export const listConfigImages = listImages

export const listAudios = () => request('/config/audios')
export const createAudio = (payload) => request('/config/audios', { method: 'POST', body: JSON.stringify(payload) })
export const updateAudio = (id, payload) => request(`/config/audios/${id}`, { method: 'PUT', body: JSON.stringify(payload) })
export const deleteAudio = (id) => request(`/config/audios/${id}`, { method: 'DELETE' })
export const listConfigAudios = listAudios

export const listRobotAudios = () => request('/config/robot-audios')
export const listConfigRobotAudios = listRobotAudios

export const listDevices = () => request('/config/devices')
export const createDevice = (payload) => request('/config/devices', { method: 'POST', body: JSON.stringify(payload) })
export const updateDevice = (id, payload) => request(`/config/devices/${id}`, { method: 'PUT', body: JSON.stringify(payload) })
export const deleteDevice = (id) => request(`/config/devices/${id}`, { method: 'DELETE' })
export const listConfigDevices = listDevices
export const saveDeviceRegionBinding = (payload) =>
  request('/config/device-region-bindings', { method: 'POST', body: JSON.stringify(payload) })
export const deleteDeviceRegionBinding = (deviceId, regionId) =>
  request(`/config/device-region-bindings/${deviceId}/${regionId}`, { method: 'DELETE' })
export const addDeviceRegion = saveDeviceRegionBinding
export const updateDeviceRegion = (payload) => saveDeviceRegionBinding(payload)
export const getDeviceRegion = (deviceId, regionId) =>
  request('/config/device-region-bindings', { query: { deviceId, regionId } })
export const listDeviceRegions = (deviceId) =>
  request('/config/device-region-bindings', { query: { deviceId } })

export const listTables = () => request('/config/tables')
export const createTable = (payload) => request('/config/tables', { method: 'POST', body: JSON.stringify(payload) })
export const updateTable = (id, payload) => request(`/config/tables/${id}`, { method: 'PUT', body: JSON.stringify(payload) })
export const deleteTable = (id) => request(`/config/tables/${id}`, { method: 'DELETE' })
export const listConfigTables = listTables

export const listRobots = () => request('/config/robots')
export const createRobot = (payload) => request('/config/robots', { method: 'POST', body: JSON.stringify(payload) })
export const updateRobot = (id, payload) => request(`/config/robots/${id}`, { method: 'PUT', body: JSON.stringify(payload) })
export const deleteRobot = (id) => request(`/config/robots/${id}`, { method: 'DELETE' })
export const listConfigRobots = listRobots

export const listTasks = () => request('/config/tasks')
export const createTask = (payload) => request('/config/tasks', { method: 'POST', body: JSON.stringify(payload) })
export const updateTask = (id, payload) => request(`/config/tasks/${id}`, { method: 'PUT', body: JSON.stringify(payload) })
export const deleteTask = (id) => request(`/config/tasks/${id}`, { method: 'DELETE' })
export const runTask = (id) => request(`/config/tasks/${id}/run`, { method: 'POST' })
export const listConfigTasks = listTasks
export const runConfigTask = runTask

export const listComplaints = () => request('/config/complaints')
export const createComplaint = (payload) => request('/config/complaints', { method: 'POST', body: JSON.stringify(payload) })
export const updateComplaint = (id, payload) => request(`/config/complaints/${id}`, { method: 'PUT', body: JSON.stringify(payload) })
export const deleteComplaint = (id) => request(`/config/complaints/${id}`, { method: 'DELETE' })

export const listInLounge = () => request('/statistics/in-lounge')
export const listOutgoing = () => request('/statistics/outgoing')
export const listAccessTemp = () => request('/statistics/access-temp')
export const listInquiry = () => request('/statistics/inquiry')
export const listGuide = () => request('/statistics/guide')
export const listPassengerStatisticsByInType = listAccessTemp
export const getPassengerStatistics = listInLounge

export const listFoodItems = () => request('/foods/items')
export const createFoodItem = (payload) => request('/foods/items', { method: 'POST', body: JSON.stringify(payload) })
export const updateFoodItem = (id, payload) => request(`/foods/items/${id}`, { method: 'PUT', body: JSON.stringify(payload) })
export const deleteFoodItem = (id) => request(`/foods/items/${id}`, { method: 'DELETE' })
export const listFoodConfigs = listFoodItems
export const getFoodConfig = (id) => request(`/foods/items/${id}`)

export const listFoodDailyMenus = () => request('/foods/daily-menus')
export const createFoodDailyMenu = (payload) => request('/foods/daily-menus', { method: 'POST', body: JSON.stringify(payload) })
export const updateFoodDailyMenu = (id, payload) => request(`/foods/daily-menus/${id}`, { method: 'PUT', body: JSON.stringify(payload) })
export const deleteFoodDailyMenu = (id) => request(`/foods/daily-menus/${id}`, { method: 'DELETE' })
export const listFoodDaily = listFoodDailyMenus
export const getFoodDaily = (id) => request(`/foods/daily-menus/${id}`)

export const listFoodPlans = () => request('/foods/plans')
export const createFoodPlan = (payload) => request('/foods/plans', { method: 'POST', body: JSON.stringify(payload) })
export const updateFoodPlan = (id, payload) => request(`/foods/plans/${id}`, { method: 'PUT', body: JSON.stringify(payload) })
export const deleteFoodPlan = (id) => request(`/foods/plans/${id}`, { method: 'DELETE' })
export const getFoodPlan = (id) => request(`/foods/plans/${id}`)

export const listFoodOrders = () => request('/foods/orders')
export const createFoodOrder = (payload) => request('/foods/orders', { method: 'POST', body: JSON.stringify(payload) })
export const updateFoodOrder = (id, payload) => request(`/foods/orders/${id}`, { method: 'PUT', body: JSON.stringify(payload) })
export const deleteFoodOrder = (id) => request(`/foods/orders/${id}`, { method: 'DELETE' })
export const receiveFoodOrder = (id) => request(`/foods/orders/${id}/receive`, { method: 'POST' })
export const finishFoodOrder = (id) => request(`/foods/orders/${id}/finish`, { method: 'POST' })
export const cancelFoodOrder = (id) => request(`/foods/orders/${id}/cancel`, { method: 'POST' })
export const getFoodOrder = (id) => request(`/foods/orders/${id}`)

export const listLoginLogs = () => request('/monitor/login-logs')
export const clearLoginLogs = () => request('/monitor/login-logs', { method: 'DELETE' })
export const listOperationLogs = () => request('/monitor/operation-logs')
export const clearOperationLogs = () => request('/monitor/operation-logs', { method: 'DELETE' })
export const listLogininfor = listLoginLogs
export const listOperLogs = listOperationLogs
export const cleanLogininfor = clearLoginLogs
export const cleanOperLog = clearOperationLogs

export const listKnowledge = () => request('/knowledge')
export const getKnowledge = (id) => request(`/knowledge/${id}`)
export const createKnowledge = (payload) => request('/knowledge', { method: 'POST', body: JSON.stringify(payload) })
export const updateKnowledge = (id, payload) => request(`/knowledge/${id}`, { method: 'PUT', body: JSON.stringify(payload) })
export const deleteKnowledge = (id) => request(`/knowledge/${id}`, { method: 'DELETE' })
export const getKnowledgeDetail = getKnowledge
export const addKnowledge = createKnowledge
export const editKnowledge = (payload) => updateKnowledge(payload.id, payload)
export const enableKnowledge = () => Promise.resolve()
export const disableKnowledge = () => Promise.resolve()
export const embeddingKnowledge = () => Promise.resolve()

export const uploadFiles = (files) => {
  const formData = new FormData()
  ;[...files].forEach((file) => formData.append('files', file))
  return upload('/files', formData)
}
export const exportSystemResource = () => Promise.resolve()
