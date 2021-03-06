import request from '@/utils/request'

// Inquire Patient list
export function listPatient(query) {
  return request({
    url: '/med/patient/list',
    method: 'get',
    params: query
  })
}

// Query Patient detailed
export function getPatient(patientId) {
  return request({
    url: '/med/patient/' + patientId,
    method: 'get'
  })
}

// Add Patient
export function addPatient(data) {
  return request({
    url: '/med/patient',
    method: 'post',
    data: data
  })
}

// Modify Patient
export function updatePatient(data) {
  return request({
    url: '/med/patient',
    method: 'put',
    data: data
  })
}

// Delete Patient
export function delPatient(patientId) {
  return request({
    url: '/med/patient/' + patientId,
    method: 'delete'
  })
}

// Export Patient
export function exportPatient(query) {
  return request({
    url: '/med/patient/export',
    method: 'get',
    params: query
  })
}