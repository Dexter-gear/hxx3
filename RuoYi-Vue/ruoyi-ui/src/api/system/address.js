import request from '@/utils/request'

// 查询订单地址信息列表
export function listAddress(query) {
  return request({
    url: '/system/address/list',
    method: 'get',
    params: query
  })
}

// 查询订单地址信息详细
export function getAddress(addressId) {
  return request({
    url: '/system/address/' + addressId,
    method: 'get'
  })
}

// 新增订单地址信息
export function addAddress(data) {
  return request({
    url: '/system/address',
    method: 'post',
    data: data
  })
}

// 修改订单地址信息
export function updateAddress(data) {
  return request({
    url: '/system/address',
    method: 'put',
    data: data
  })
}

// 删除订单地址信息
export function delAddress(addressId) {
  return request({
    url: '/system/address/' + addressId,
    method: 'delete'
  })
}
