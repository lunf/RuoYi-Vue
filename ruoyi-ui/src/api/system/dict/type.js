import request from '@/utils/request'

// List of Dictionary Types
export function listType(query) {
  return request({
    url: '/system/dict/type/list',
    method: 'get',
    params: query
  })
}

// Find the dictionary type in detail
export function getType(dictId) {
  return request({
    url: '/system/dict/type/' + dictId,
    method: 'get'
  })
}

// New type of dictionary
export function addType(data) {
  return request({
    url: '/system/dict/type',
    method: 'post',
    data: data
  })
}

// Modify the dictionary type.
export function updateType(data) {
  return request({
    url: '/system/dict/type',
    method: 'put',
    data: data
  })
}

// Remove the dictionary type.
export function delType(dictId) {
  return request({
    url: '/system/dict/type/' + dictId,
    method: 'delete'
  })
}

// Updated dictionary cache
export function refreshCache() {
  return request({
    url: '/system/dict/type/refreshCache',
    method: 'delete'
  })
}

// Get the dictionary selection box list
export function optionselect() {
  return request({
    url: '/system/dict/type/optionselect',
    method: 'get'
  })
}