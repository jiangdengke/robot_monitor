import { downloadFile, request, upload } from './http'

export const login = (payload) =>
  request('/login', {
    method: 'POST',
    body: JSON.stringify(payload)
  })

export const getCaptchaImage = () => request('/captchaImage')

export const getUserInfo = () => request('/getInfo')

export const getRouters = () => request('/getRouters')

export const getProfile = () => request('/system/user/profile')

export const updateProfile = (payload) =>
  request('/system/user/profile', {
    method: 'PUT',
    body: JSON.stringify(payload)
  })

export const updatePassword = (oldPassword, newPassword) =>
  request('/system/user/profile/updatePwd', {
    method: 'PUT',
    query: {
      oldPassword,
      newPassword
    }
  })

export const updateAvatar = (avatar) =>
  request('/system/user/profile/avatar', {
    method: 'PUT',
    query: { avatar }
  })

export const getUserDetail = (userId) => request(`/system/user/${userId}`)

export const getRoleDetail = (roleId) => request(`/system/role/${roleId}`)

export const getMenuDetail = (menuId) => request(`/system/menu/${menuId}`)

export const getDeptDetail = (deptId) => request(`/system/dept/${deptId}`)

export const getPostDetail = (postId) => request(`/system/post/${postId}`)

export const getConfigDetail = (configId) => request(`/system/config/${configId}`)

export const getNoticeDetail = (noticeId) => request(`/system/notice/${noticeId}`)

export const getRobotDetail = (id) => request(`/config/robot/${id}`)

export const getAreaDetail = (id) => request(`/config/area/${id}`)

export const getDeviceDetail = (id) => request(`/config/device/${id}`)

export const getImageDetail = (id) => request(`/config/img/${id}`)

export const getAudioDetail = (id) => request(`/config/audio/${id}`)

export const getTaskDetail = (id) => request(`/config/task/${id}`)

export const getKnowledgeDetail = (id) => request(`/ai/knowledge/${id}`)

export const addKnowledge = (payload) =>
  request('/ai/knowledge', {
    method: 'POST',
    body: JSON.stringify(payload)
  })

export const editKnowledge = (payload) =>
  request('/ai/knowledge', {
    method: 'PUT',
    body: JSON.stringify(payload)
  })

export const enableKnowledge = (ids) =>
  request('/ai/knowledge/enable', {
    method: 'POST',
    body: JSON.stringify(ids)
  })

export const disableKnowledge = (ids) =>
  request('/ai/knowledge/disable', {
    method: 'POST',
    body: JSON.stringify(ids)
  })

export const embeddingKnowledge = (ids) =>
  request('/ai/knowledge/embedding', {
    method: 'POST',
    body: JSON.stringify(ids)
  })

export const deleteKnowledge = (ids) =>
  request(`/ai/knowledge/${ids.join(',')}`, {
    method: 'DELETE'
  })

export const listUsers = (query = {}) =>
  request('/system/user/list', {
    query
  })

export const listRoles = (query = {}) =>
  request('/system/role/list', {
    query
  })

export const listMenus = (query = {}) =>
  request('/system/menu/list', {
    query
  })

export const listDepts = (query = {}) =>
  request('/system/dept/list', {
    query
  })

export const listPosts = (query = {}) =>
  request('/system/post/list', {
    query
  })

export const listConfigs = (query = {}) =>
  request('/system/config/list', {
    query
  })

export const listNotices = (query = {}) =>
  request('/system/notice/list', {
    query
  })

export const listOnlineUsers = (query = {}) =>
  request('/monitor/online/list', {
    query
  })

export const listOperLogs = (query = {}) =>
  request('/monitor/operlog/list', {
    query
  })

export const listLogininfor = (query = {}) =>
  request('/monitor/logininfor/list', {
    query
  })

export const getServerInfo = () => request('/monitor/server')

export const getCacheInfo = () => request('/monitor/cache')

export const listCacheNames = () => request('/monitor/cache/getNames')

export const listCacheKeys = (cacheName) =>
  request(`/monitor/cache/getKeys/${encodeURIComponent(cacheName)}`)

export const getCacheValue = (cacheName, cacheKey) =>
  request(`/monitor/cache/getValue/${encodeURIComponent(cacheName)}/${encodeURIComponent(cacheKey)}`)

export const clearCacheName = (cacheName) =>
  request(`/monitor/cache/clearCacheName/${encodeURIComponent(cacheName)}`, {
    method: 'DELETE'
  })

export const clearCacheKey = (cacheKey) =>
  request(`/monitor/cache/clearCacheKey/${encodeURIComponent(cacheKey)}`, {
    method: 'DELETE'
  })

export const clearCacheAll = () => request('/monitor/cache/clearCacheAll', { method: 'DELETE' })

export const listConfigRobots = (query = {}) =>
  request('/config/robot/list', {
    query
  })

export const listConfigRegions = (query = {}) =>
  request('/config/region/list', {
    query
  })

export const listConfigAreas = (query = {}) =>
  request('/config/area/list', {
    query
  })

export const listConfigImages = (query = {}) =>
  request('/config/img/list', {
    query
  })

export const listConfigTables = (query = {}) =>
  request('/config/table/list', {
    query
  })

export const listConfigAudios = (query = {}) =>
  request('/config/audio/list', {
    query
  })

export const listConfigTasks = (query = {}) =>
  request('/config/task/list', {
    query
  })

export const listConfigDevices = (query = {}) =>
  request('/config/device/list', {
    query
  })

export const runConfigTask = (id) => request(`/config/task/run/${id}`)

export const listDeviceRegions = (deviceId) => request(`/config/deviceregion/list/${deviceId}`)

export const getDeviceRegion = (deviceId, regionId) => request(`/config/deviceregion/${deviceId}/${regionId}`)

export const addDeviceRegion = (payload) =>
  request('/config/deviceregion', {
    method: 'POST',
    body: JSON.stringify(payload)
  })

export const updateDeviceRegion = (payload) =>
  request('/config/deviceregion', {
    method: 'PUT',
    body: JSON.stringify(payload)
  })

export const deleteDeviceRegion = (payload) =>
  request('/config/deviceregion/delete', {
    method: 'POST',
    body: JSON.stringify(payload)
  })

export const listDictTypes = (query = {}) =>
  request('/system/dict/type/list', {
    query
  })

export const listDictData = (query = {}) =>
  request('/system/dict/data/list', {
    query
  })

export const listJobs = (query = {}) =>
  request('/monitor/job/list', {
    query
  })

export const listJobLogs = (query = {}) =>
  request('/monitor/jobLog/list', {
    query
  })

export const forceLogoutOnlineUser = (tokenId) =>
  request(`/monitor/online/${encodeURIComponent(tokenId)}`, {
    method: 'DELETE'
  })

export const cleanLogininfor = () => request('/monitor/logininfor/clean', { method: 'DELETE' })

export const unlockLogininfor = (userName) =>
  request(`/monitor/logininfor/unlock/${encodeURIComponent(userName)}`)

export const cleanOperLog = () => request('/monitor/operlog/clean', { method: 'DELETE' })

export const changeJobStatus = (jobId, status) =>
  request('/monitor/job/changeStatus', {
    method: 'PUT',
    body: JSON.stringify({ jobId, status })
  })

export const runJob = (job) =>
  request('/monitor/job/run', {
    method: 'PUT',
    body: JSON.stringify(job)
  })

export const cleanJobLog = () => request('/monitor/jobLog/clean', { method: 'DELETE' })

export const listFoodConfigs = (query = {}) =>
  request('/food/selectFoodConfigList', {
    method: 'POST',
    query
  })

export const getFoodConfig = (foodId) => request('/food/getFoodConfigById', { query: { foodId } })

export const listFoodDaily = (query = {}) =>
  request('/food/selectFoodDailyList', {
    method: 'POST',
    query
  })

export const getFoodDaily = (id) => request('/food/getFoodDailyById', { query: { id } })

export const listFoodPlans = (query = {}) =>
  request('/food/selectFoodPlanList', {
    method: 'POST',
    query
  })

export const getFoodPlan = (id) => request('/food/getFoodPlanById', { query: { id } })

export const listFoodOrders = (query = {}) =>
  request('/food/queryOrderList', {
    method: 'POST',
    query
  })

export const getFoodOrder = (id) => request('/food/getOrderById', { query: { id } })

export const cancelFoodOrder = (id) =>
  request('/food/cancelOrder', {
    method: 'POST',
    body: JSON.stringify(id)
  })

export const finishFoodOrder = (id) =>
  request('/food/finishOrder', {
    method: 'POST',
    body: JSON.stringify(id)
  })

export const receiveFoodOrder = (id) =>
  request('/food/receiveOrder', {
    method: 'POST',
    body: JSON.stringify(id)
  })

export const listPassengers = (query = {}) =>
  request('/flight/passenger/list', {
    query
  })

export const listPassengerWarningLogs = (query = {}) =>
  request('/flight/passengerWarningLog/list', {
    query
  })

export const listFlightInfos = (query = {}) =>
  request('/flight/flightinfo/list', {
    query
  })

export const getDigitalTwinAll = (query = {}) =>
  request('/DigitalTwin/getAll', {
    query
  })

export const getRoomList = () => request('/system/dept/roomList')

export const getDeptTree = (query = {}) =>
  request('/system/dept/treeselect', {
    query
  })

export const getDeptExcludeList = (deptId) => request(`/system/dept/list/exclude/${deptId}`)

export const getMenuTree = (query = {}) =>
  request('/system/menu/treeselect', {
    query
  })

export const getUserCreateOptions = () => request('/system/user/')

export const getRoleMenuTree = (roleId) => request(`/system/menu/roleMenuTreeselect/${roleId}`)

export const listUnallocatedUsers = (query = {}) =>
  request('/system/role/authUser/unallocatedList', {
    query
  })

export const listAllocatedUsers = (query = {}) =>
  request('/system/role/authUser/allocatedList', {
    query
  })

export const assignRoleUsers = (roleId, userIds) =>
  request('/system/role/authUser/selectAll', {
    method: 'PUT',
    query: {
      roleId,
      userIds
    }
  })

export const cancelRoleUsers = (roleId, userIds) =>
  request('/system/role/authUser/cancelAll', {
    method: 'PUT',
    query: {
      roleId,
      userIds
    }
  })

export const getUserRoleAuth = (userId) =>
  request(`/system/user/authRole/${userId}`)

export const updateUserRoleAuth = (userId, roleIds) =>
  request('/system/user/authRole', {
    method: 'PUT',
    query: {
      userId,
      roleIds
    }
  })

export const changeUserStatus = (userId, status) =>
  request('/system/user/changeStatus', {
    method: 'PUT',
    body: JSON.stringify({ userId, status })
  })

export const resetUserPassword = (userId, password) =>
  request('/system/user/resetPwd', {
    method: 'PUT',
    body: JSON.stringify({ userId, password })
  })

export const importUsers = (file, updateSupport = false) => {
  const formData = new FormData()
  formData.append('file', file)
  formData.append('updateSupport', String(updateSupport))
  return upload('/system/user/importData', formData)
}

export const downloadUserImportTemplate = () =>
  downloadFile('/system/user/importTemplate', { method: 'POST', fileName: '用户导入模板.xlsx' })

export const changeRoleStatus = (roleId, status) =>
  request('/system/role/changeStatus', {
    method: 'PUT',
    body: JSON.stringify({ roleId, status })
  })

export const updateRoleDataScope = (payload) =>
  request('/system/role/dataScope', {
    method: 'PUT',
    body: JSON.stringify(payload)
  })

export const getRoleDeptTree = (roleId) => request(`/system/dept/roleDeptTreeselect/${roleId}`)

export const refreshConfigCache = () => request('/system/config/refreshCache', { method: 'DELETE' })

export const refreshDictCache = () => request('/system/dict/type/refreshCache', { method: 'DELETE' })

export const getDictTypeOptions = () => request('/system/dict/type/optionselect')

export const getDictDataByType = (dictType) => request(`/system/dict/data/type/${dictType}`)

export const exportSystemResource = (path, query = {}, fileName = '导出数据.xlsx') =>
  downloadFile(path, {
    method: 'POST',
    query,
    fileName
  })
