import request from '@/utils/request'

// Inquire Material list
export function listMaterial(query) {
  return request({
    url: '/med/material/list',
    method: 'get',
    params: query
  })
}

// Query Material detailed
export function getMaterial(materialId) {
  return request({
    url: '/med/material/' + materialId,
    method: 'get'
  })
}

// Add Material
export function addMaterial(data) {
  return request({
    url: '/med/material',
    method: 'post',
    data: data
  })
}

// Modify Material
export function updateMaterial(data) {
  return request({
    url: '/med/material',
    method: 'put',
    data: data
  })
}

// Delete Material
export function delMaterial(materialId) {
  return request({
    url: '/med/material/' + materialId,
    method: 'delete'
  })
}

// Export Material
export function exportMaterial(query) {
  return request({
    url: '/med/material/export',
    method: 'get',
    params: query
  })
}