import request from '@/utils/request'

// Ask for a log list.
export function listJobLog(query) {
  return request({
    url: '/monitor/jobLog/list',
    method: 'get',
    params: query
  })
}

// Remove the log.
export function delJobLog(jobLogId) {
  return request({
    url: '/monitor/jobLog/' + jobLogId,
    method: 'delete'
  })
}

// The air conditioning log.
export function cleanJobLog() {
  return request({
    url: '/monitor/jobLog/clean',
    method: 'delete'
  })
}
