import { request, upload } from './http'

function normalizeRows(payload) {
  if (Array.isArray(payload)) return payload
  return payload?.rows || payload?.data || []
}

function normalizeTotal(payload) {
  if (typeof payload?.total === 'number') return payload.total
  const rows = normalizeRows(payload)
  return rows.length
}

function joinIds(ids) {
  return Array.isArray(ids) ? ids.join(',') : ids
}

export function listResource(basePath, query = {}, method = 'GET') {
  return request(`${basePath}/list`, {
    method,
    query
  })
}

export function getResource(basePath, id) {
  return request(`${basePath}/${id}`)
}

export function createResource(basePath, payload, method = 'POST') {
  return request(basePath, {
    method,
    body: JSON.stringify(payload)
  })
}

export function updateResource(basePath, payload, method = 'PUT') {
  return request(basePath, {
    method,
    body: JSON.stringify(payload)
  })
}

export function deleteResource(basePath, ids, method = 'DELETE') {
  if (method === 'POST' || method === 'PUT') {
    return request(basePath, {
      method,
      body: JSON.stringify(Array.isArray(ids) ? ids : [ids])
    })
  }
  return request(`${basePath}/${joinIds(ids)}`, { method })
}

export function uploadFiles(files) {
  const formData = new FormData()
  ;[...files].forEach((file) => formData.append('files', file))
  return upload('/files', formData)
}

export { normalizeRows, normalizeTotal }
