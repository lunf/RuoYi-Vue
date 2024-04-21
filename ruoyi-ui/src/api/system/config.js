import request from '@/utils/request'

// List of requests.
export function listConfig(query) {
  return request({
    url: '/system/config/list',
    method: 'get',
    params: query
  })
}

// Question parameters in detail
export function getConfig(configId) {
  return request({
    url: '/system/config/' + configId,
    method: 'get'
  })
}

// According to the parameter key name, the parameter value
export function getConfigKey(configKey) {
  return request({
    url: '/system/config/configKey/' + configKey,
    method: 'get'
  })
}

// Additional parameters
export function addConfig(data) {
  return request({
    url: '/system/config',
    method: 'post',
    data: data
  })
}

// Modification of parameters
export function updateConfig(data) {
  return request({
    url: '/system/config',
    method: 'put',
    data: data
  })
}

// Remove the parameters.
export function delConfig(configId) {
  return request({
    url: '/system/config/' + configId,
    method: 'delete'
  })
}

// Updated parameter cache
export function refreshCache() {
  return request({
    url: '/system/config/refreshCache',
    method: 'delete'
  })
}
