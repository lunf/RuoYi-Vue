import request from '@/utils/request'

// Obtaining Service Information
export function getServer() {
  return request({
    url: '/monitor/server',
    method: 'get'
  })
}