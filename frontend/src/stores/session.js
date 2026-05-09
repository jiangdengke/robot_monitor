import { getToken, setToken } from '@/api/http'
import { getRouters, getUserInfo, login as loginRequest } from '@/api/system'

const sessionState = {
  token: getToken(),
  user: null,
  roles: [],
  permissions: [],
  roomList: [],
  routers: []
}

async function login(payload) {
  const response = await loginRequest(payload)
  setToken(response.token)
  sessionState.token = response.token
  return response
}

async function hydrateSession() {
  if (!sessionState.token) {
    return false
  }

  const [infoResponse, routerResponse] = await Promise.all([
    getUserInfo(),
    getRouters()
  ])

  sessionState.user = infoResponse.user || null
  sessionState.roles = infoResponse.roles || []
  sessionState.permissions = infoResponse.permissions || []
  sessionState.roomList = infoResponse.roomList || []
  sessionState.routers = routerResponse.data || []
  return true
}

function clearSession() {
  setToken('')
  sessionState.token = ''
  sessionState.user = null
  sessionState.roles = []
  sessionState.permissions = []
  sessionState.roomList = []
  sessionState.routers = []
}

export { clearSession, hydrateSession, login, sessionState }
