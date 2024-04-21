import request from '@/utils/request'

// Ask for operating log list
export function list(query) {
  return request({
    url: '/monitor/operlog/list',
    method: 'get',
    params: query
  })
}

// Remove the operating log.
export function delOperlog(operId) {
  return request({
    url: '/monitor/operlog/' + operId,
    method: 'delete'
  })
}

// Clean operating logs.
export function cleanOperlog() {
  return request({
    url: '/monitor/operlog/clean',
    method: 'delete'
  })
}
