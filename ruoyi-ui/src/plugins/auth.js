import store from '@/store'

function authPermission(permission) {
  const all_permission = "*:*:*";
  const permissions = store.getters && store.getters.permissions
  if (permission && permission.length > 0) {
    return permissions.some(v => {
      return all_permission === v || v === permission
    })
  } else {
    return false
  }
}

function authRole(role) {
  const super_admin = "admin";
  const roles = store.getters && store.getters.roles
  if (role && role.length > 0) {
    return roles.some(v => {
      return super_admin === v || v === role
    })
  } else {
    return false
  }
}

export default {
  // Verify whether the user has any authorization.
  hasPermi(permission) {
    return authPermission(permission);
  },
  // Verify whether the user contains specified permissions，Include one of them.
  hasPermiOr(permissions) {
    return permissions.some(item => {
      return authPermission(item)
    })
  },
  // Verify whether the user contains specified permissions，Must have all.
  hasPermiAnd(permissions) {
    return permissions.every(item => {
      return authPermission(item)
    })
  },
  // Verify whether the user has a role.
  hasRole(role) {
    return authRole(role);
  },
  // Verify whether the user contains a specified role，Include one of them.
  hasRoleOr(roles) {
    return roles.some(item => {
      return authRole(item)
    })
  },
  // Verify whether the user contains a specified role，Must have all.
  hasRoleAnd(roles) {
    return roles.every(item => {
      return authRole(item)
    })
  }
}
