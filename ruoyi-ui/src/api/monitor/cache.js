import request from '@/utils/request'

// Ask for detailed cache
export function getCache() {
  return request({
    url: '/monitor/cache',
    method: 'get'
  })
}

// Ask for Cache Name List
export function listCacheName() {
  return request({
    url: '/monitor/cache/getNames',
    method: 'get'
  })
}

// Ask for Cache Key Name List
export function listCacheKey(cacheName) {
  return request({
    url: '/monitor/cache/getKeys/' + cacheName,
    method: 'get'
  })
}

// Ask for cache content.
export function getCacheValue(cacheName, cacheKey) {
  return request({
    url: '/monitor/cache/getValue/' + cacheName + '/' + cacheKey,
    method: 'get'
  })
}

// Clean the specified name cache
export function clearCacheName(cacheName) {
  return request({
    url: '/monitor/cache/clearCacheName/' + cacheName,
    method: 'delete'
  })
}

// Clean the key name cache.
export function clearCacheKey(cacheKey) {
  return request({
    url: '/monitor/cache/clearCacheKey/' + cacheKey,
    method: 'delete'
  })
}

// Clean the entire cache.
export function clearCacheAll() {
  return request({
    url: '/monitor/cache/clearCacheAll',
    method: 'delete'
  })
}
