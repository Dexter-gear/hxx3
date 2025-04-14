import request from '@/utils/request'

// 查询订单详情列表
export function listDetail(query) {
  return request({
    url: '/system/detail/list',
    method: 'get',
    params: query
  })
}

// 查询订单详情详细
export function getDetail(detailId) {
  return request({
    url: '/system/detail/' + detailId,
    method: 'get'
  })
}

// 新增订单详情
export function addDetail(data) {
  return request({
    url: '/system/detail',
    method: 'post',
    data: data
  })
}

// 修改订单详情
export function updateDetail(data) {
  return request({
    url: '/system/detail',
    method: 'put',
    data: data
  })
}

// 删除订单详情
export function delDetail(detailId) {
  return request({
    url: '/system/detail/' + detailId,
    method: 'delete'
  })
}
