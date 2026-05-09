const API_BASE = import.meta.env.VITE_API_BASE || '/api'
const TOKEN_KEY = 'robotmonitor_admin_token'

function getToken() {
  return localStorage.getItem(TOKEN_KEY) || ''
}

function setToken(token) {
  if (token) {
    localStorage.setItem(TOKEN_KEY, token)
  } else {
    localStorage.removeItem(TOKEN_KEY)
  }
}

function buildUrl(path, query) {
  const url = new URL(`${API_BASE}${path}`, window.location.origin)
  if (query) {
    Object.entries(query).forEach(([key, value]) => {
      if (value === undefined || value === null || value === '') {
        return
      }
      if (Array.isArray(value)) {
        url.searchParams.set(key, value.join(','))
        return
      }
      url.searchParams.set(key, value)
    })
  }
  return `${url.pathname}${url.search}`
}

async function request(path, options = {}) {
  const token = getToken()
  const headers = {
    ...(options.body instanceof FormData ? {} : { 'Content-Type': 'application/json' }),
    ...(options.headers || {})
  }

  if (token) {
    headers.Authorization = `Bearer ${token}`
  }

  const response = await fetch(buildUrl(path, options.query), {
    method: options.method || 'GET',
    credentials: 'include',
    headers,
    body: options.body,
    signal: options.signal
  })

  if (!response.ok) {
    throw new Error(`HTTP ${response.status}`)
  }

  const contentType = response.headers.get('content-type') || ''
  if (!contentType.includes('application/json')) {
    return response.text()
  }

  const payload = await response.json()
  if (payload && typeof payload.code === 'number' && payload.code !== 200) {
    const error = new Error(payload.msg || 'Request failed')
    error.payload = payload
    throw error
  }

  return payload
}

async function upload(path, formData) {
  return request(path, {
    method: 'POST',
    body: formData
  })
}

async function downloadFile(path, options = {}) {
  const token = getToken()
  const headers = {
    ...(options.body instanceof FormData ? {} : { 'Content-Type': 'application/json' }),
    ...(options.headers || {})
  }

  if (token) {
    headers.Authorization = `Bearer ${token}`
  }

  const response = await fetch(buildUrl(path, options.query), {
    method: options.method || 'GET',
    credentials: 'include',
    headers,
    body: options.body,
    signal: options.signal
  })

  if (!response.ok) {
    throw new Error(`HTTP ${response.status}`)
  }

  const contentType = response.headers.get('content-type') || ''
  if (contentType.includes('application/json')) {
    const payload = await response.json()
    if (payload && typeof payload.code === 'number' && payload.code !== 200) {
      const error = new Error(payload.msg || 'Download failed')
      error.payload = payload
      throw error
    }
    return payload
  }

  const blob = await response.blob()
  const disposition = response.headers.get('content-disposition') || ''
  const matched = disposition.match(/filename\*?=(?:UTF-8'')?["']?([^;"']+)/i)
  const fileName = options.fileName || (matched ? decodeURIComponent(matched[1]) : 'download.xlsx')
  const href = URL.createObjectURL(blob)
  const anchor = document.createElement('a')
  anchor.href = href
  anchor.download = fileName
  document.body.appendChild(anchor)
  anchor.click()
  anchor.remove()
  URL.revokeObjectURL(href)
}

export { API_BASE, TOKEN_KEY, downloadFile, getToken, request, setToken, upload }
