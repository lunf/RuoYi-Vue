import request from '@/utils/request'

// Inquire Job Order list
export function listJob(query) {
  return request({
    url: '/mes/job/list',
    method: 'get',
    params: query
  })
}

// Query Job Order detailed
export function getJob(jobId) {
  return request({
    url: '/mes/job/' + jobId,
    method: 'get'
  })
}

// Add Job Order
export function addJob(data) {
  return request({
    url: '/mes/job',
    method: 'post',
    data: data
  })
}

// Modify Job Order
export function updateJob(data) {
  return request({
    url: '/mes/job',
    method: 'put',
    data: data
  })
}

// Delete Job Order
export function delJob(jobId) {
  return request({
    url: '/mes/job/' + jobId,
    method: 'delete'
  })
}

// Export Job Order
export function exportJob(query) {
  return request({
    url: '/mes/job/export',
    method: 'get',
    params: query
  })
}
