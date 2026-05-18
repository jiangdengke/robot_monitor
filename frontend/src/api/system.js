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

export const listInLounge = (params = {}) => request('/statistics/in-lounge', { query: params })
export const listOutgoing = (params = {}) => request('/statistics/outgoing', { query: params })
export const listAccessTemp = (params = {}) => request('/statistics/access-temp', { query: params })
export const listInquiry = (params = {}) => request('/statistics/inquiry', { query: params })
export const listGuide = (params = {}) => request('/statistics/guide', { query: params })
export const listPassengerStatisticsByInType = listAccessTemp
export const getPassengerStatistics = listInLounge

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
