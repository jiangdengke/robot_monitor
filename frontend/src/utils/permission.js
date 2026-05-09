import { sessionState } from '@/stores/session'

function hasPermission(permission) {
  if (!permission) return true
  const permissions = normalizePermissions(sessionState.permissions || [])
  return hasRole('admin') || permissions.includes('*:*:*') || permissions.includes(permission)
}

function hasAnyPermission(permissions = []) {
  if (!permissions.length) return true
  return permissions.some((permission) => hasPermission(permission))
}

function hasRole(role) {
  if (!role) return true
  const roles = sessionState.roles || []
  return roles.includes('admin') || roles.includes(role)
}

function installPermissionDirective(app) {
  app.directive('hasPermi', {
    mounted(el, binding) {
      const permissions = Array.isArray(binding.value) ? binding.value : [binding.value]
      if (!hasAnyPermission(permissions)) {
        el.parentNode?.removeChild(el)
      }
    }
  })
  app.directive('hasRole', {
    mounted(el, binding) {
      const roles = Array.isArray(binding.value) ? binding.value : [binding.value]
      if (!roles.some((role) => hasRole(role))) {
        el.parentNode?.removeChild(el)
      }
    }
  })
}

function normalizePermissions(values) {
  return values.flatMap((item) => String(item || '').split(',')).map((item) => item.trim()).filter(Boolean)
}

export { hasAnyPermission, hasPermission, hasRole, installPermissionDirective }
