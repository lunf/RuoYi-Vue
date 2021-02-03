import request from '@/utils/request'

// Query Work Order by JobId
export function getWorkByJob(jobId) {
  return request({
    url: '/mes/work/job/' + jobId,
    method: 'get'
  })
}

// Inquire Work Order list
export function listWork(query) {
  return request({
    url: '/mes/work/list',
    method: 'get',
    params: query
  })
}

// Query Work Order detailed
export function getWork(workOrderId) {
  return request({
    url: '/mes/work/' + workOrderId,
    method: 'get'
  })
}

// Add Work Order
export function addWork(data) {
  return request({
    url: '/mes/work',
    method: 'post',
    data: data
  })
}

// Modify Work Order
export function updateWork(data) {
  return request({
    url: '/mes/work',
    method: 'put',
    data: data
  })
}

// Delete Work Order
export function delWork(workOrderId) {
  return request({
    url: '/mes/work/' + workOrderId,
    method: 'delete'
  })
}

// Export Work Order
export function exportWork(query) {
  return request({
    url: '/mes/work/export',
    method: 'get',
    params: query
  })
}
