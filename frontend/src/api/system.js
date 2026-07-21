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

export const listSites = () => request('/config/sites')
export const createSite = (payload) =>
  request('/config/sites', { method: 'POST', body: JSON.stringify(payload) })
export const updateSite = (id, payload) =>
  request(`/config/sites/${id}`, { method: 'PUT', body: JSON.stringify(payload) })
export const deleteSite = (id) => request(`/config/sites/${id}`, { method: 'DELETE' })

export const listAreas = () => request('/config/areas')
export const createArea = (payload) =>
  request('/config/areas', { method: 'POST', body: JSON.stringify(payload) })
export const updateArea = (id, payload) =>
  request(`/config/areas/${id}`, { method: 'PUT', body: JSON.stringify(payload) })
export const deleteArea = (id) => request(`/config/areas/${id}`, { method: 'DELETE' })

export const listPoints = () => request('/config/points')
export const createPoint = (payload) =>
  request('/config/points', { method: 'POST', body: JSON.stringify(payload) })
export const updatePoint = (id, payload) =>
  request(`/config/points/${id}`, { method: 'PUT', body: JSON.stringify(payload) })
export const deletePoint = (id) => request(`/config/points/${id}`, { method: 'DELETE' })

export const listDevices = () => request('/config/devices')
export const createDevice = (payload) =>
  request('/config/devices', { method: 'POST', body: JSON.stringify(payload) })
export const updateDevice = (id, payload) =>
  request(`/config/devices/${id}`, { method: 'PUT', body: JSON.stringify(payload) })
export const deleteDevice = (id) => request(`/config/devices/${id}`, { method: 'DELETE' })

export const listDevicePointBindings = (deviceId, pointId) =>
  request('/config/device-point-bindings', { query: { deviceId, pointId } })
export const getDevicePointBinding = (deviceId, pointId) =>
  listDevicePointBindings(deviceId, pointId)
export const saveDevicePointBinding = (payload) =>
  request('/config/device-point-bindings', { method: 'POST', body: JSON.stringify(payload) })
export const deleteDevicePointBinding = (deviceId, pointId) =>
  request('/config/device-point-bindings', {
    method: 'DELETE',
    query: { deviceId, pointId }
  })

export const listRobots = () => request('/config/robots')
export const createRobot = (payload) =>
  request('/config/robots', { method: 'POST', body: JSON.stringify(payload) })
export const updateRobot = (id, payload) =>
  request(`/config/robots/${id}`, { method: 'PUT', body: JSON.stringify(payload) })
export const deleteRobot = (id) => request(`/config/robots/${id}`, { method: 'DELETE' })

export const listTasks = () => request('/config/tasks')
export const createTask = (payload) =>
  request('/config/tasks', { method: 'POST', body: JSON.stringify(payload) })
export const updateTask = (id, payload) =>
  request(`/config/tasks/${id}`, { method: 'PUT', body: JSON.stringify(payload) })
export const deleteTask = (id) => request(`/config/tasks/${id}`, { method: 'DELETE' })
export const runTask = (id) => request(`/config/tasks/${id}/run`, { method: 'POST' })
export const listTaskLogs = () => request('/config/task-logs')

export const listLoginLogs = () => request('/monitor/login-logs')
export const clearLoginLogs = () => request('/monitor/login-logs', { method: 'DELETE' })
export const listOperationLogs = () => request('/monitor/operation-logs')
export const clearOperationLogs = () => request('/monitor/operation-logs', { method: 'DELETE' })

export const uploadFiles = (files) => {
  const formData = new FormData()
  ;[...files].forEach((file) => formData.append('files', file))
  return upload('/files', formData)
}
