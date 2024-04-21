import request from '@/utils/request'

// Registration Method
export function login(username, password, code, uuid) {
  const data = {
    username,
    password,
    code,
    uuid
  }
  return request({
    url: '/login',
    headers: {
      isToken: false,
      repeatSubmit: false
    },
    method: 'post',
    data: data
  })
}

// Registration Method
export function register(data) {
  return request({
    url: '/register',
    headers: {
      isToken: false
    },
    method: 'post',
    data: data
  })
}

// Obtaining User Details
export function getInfo() {
  return request({
    url: '/getInfo',
    method: 'get'
  })
}

// The way out.
export function logout() {
  return request({
    url: '/logout',
    method: 'post'
  })
}

// Get the verification code.
export function getCodeImg() {
  return request({
    url: '/captchaImage',
    headers: {
      isToken: false
    },
    method: 'get',
    timeout: 20000
  })
}