import request from '@/utils/request'

// Request the log list.
export function list(query) {
  return request({
    url: '/monitor/logininfor/list',
    method: 'get',
    params: query
  })
}

// Delete the log.
export function delLogininfor(infoId) {
  return request({
    url: '/monitor/logininfor/' + infoId,
    method: 'delete'
  })
}

// Unlock the user login status.
export function unlockLogininfor(userName) {
  return request({
    url: '/monitor/logininfor/unlock/' + userName,
    method: 'get'
  })
}

// Clean the log.
export function cleanLogininfor() {
  return request({
    url: '/monitor/logininfor/clean',
    method: 'delete'
  })
}
