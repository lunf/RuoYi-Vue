import request from '@/utils/request'

// Find a timely task list.
export function listJob(query) {
  return request({
    url: '/monitor/job/list',
    method: 'get',
    params: query
  })
}

// Question time assignment detail.
export function getJob(jobId) {
  return request({
    url: '/monitor/job/' + jobId,
    method: 'get'
  })
}

// Additional time assignment.
export function addJob(data) {
  return request({
    url: '/monitor/job',
    method: 'post',
    data: data
  })
}

// Modification of timely tasks
export function updateJob(data) {
  return request({
    url: '/monitor/job',
    method: 'put',
    data: data
  })
}

// Remove timely tasks.
export function delJob(jobId) {
  return request({
    url: '/monitor/job/' + jobId,
    method: 'delete'
  })
}

// Modification of task status
export function changeJobStatus(jobId, status) {
  const data = {
    jobId,
    status
  }
  return request({
    url: '/monitor/job/changeStatus',
    method: 'put',
    data: data
  })
}


// Immediately perform the task once.
export function runJob(jobId, jobGroup) {
  const data = {
    jobId,
    jobGroup
  }
  return request({
    url: '/monitor/job/run',
    method: 'put',
    data: data
  })
}