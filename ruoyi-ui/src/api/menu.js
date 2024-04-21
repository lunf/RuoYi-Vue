import request from '@/utils/request'

// Get the routing.
export const getRouters = () => {
  return request({
    url: '/getRouters',
    method: 'get'
  })
}