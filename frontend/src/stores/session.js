import { reactive } from 'vue'
import { getToken, setToken } from '@/api/http'
import { getUserInfo, login as loginRequest } from '@/api/system'

const sessionState = reactive({
  token: getToken(),
  user: null,
  roles: [],
  permissions: [],
  roomList: []
})

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

  const infoResponse = await getUserInfo()
  const payloadUser = infoResponse?.user ? infoResponse.user : infoResponse
  sessionState.user = payloadUser || null
  sessionState.roles = infoResponse?.roles || ['admin']
  sessionState.permissions = infoResponse?.permissions || ['*:*:*']
  sessionState.roomList = infoResponse?.roomList || []
  return true
}

function clearSession() {
  setToken('')
  sessionState.token = ''
  sessionState.user = null
  sessionState.roles = []
  sessionState.permissions = []
  sessionState.roomList = []
}

export { clearSession, hydrateSession, login, sessionState }
