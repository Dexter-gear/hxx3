import request from '@/utils/request'

// 查询用户地址信息列表
export function listUser_address(query) {
  return request({
    url: '/system/user_address/list',
    method: 'get',
    params: query
  })
}

// 查询用户地址信息详细
export function getUser_address(addressId) {
  return request({
    url: '/system/user_address/' + addressId,
    method: 'get'
  })
}

// 新增用户地址信息
export function addUser_address(data) {
  return request({
    url: '/system/user_address',
    method: 'post',
    data: data
  })
}

// 修改用户地址信息
export function updateUser_address(data) {
  return request({
    url: '/system/user_address',
    method: 'put',
    data: data
  })
}

// 删除用户地址信息
export function delUser_address(addressId) {
  return request({
    url: '/system/user_address/' + addressId,
    method: 'delete'
  })
}
