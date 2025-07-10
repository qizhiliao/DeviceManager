import request from '@/utils/request'

export function fetchList(query) {
  return request({
    url: '/vue-element-admin/robot/list',
    method: 'get',
    params: query
  })
}

export function fetchrobot(id) {
  return request({
    url: '/vue-element-admin/robot/detail',
    method: 'get',
    params: { id }
  })
}

export function fetchPv(pv) {
  return request({
    url: '/vue-element-admin/robot/pv',
    method: 'get',
    params: { pv }
  })
}

export function createrobot(data) {
  return request({
    url: '/vue-element-admin/robot/create',
    method: 'post',
    data
  })
}

export function updaterobot(data) {
  return request({
    url: '/vue-element-admin/robot/update',
    method: 'post',
    data
  })
}
