import request from '@/utils/request'

// 查询销售员-订单关联列表
export function listSaller_order(query) {
  return request({
    url: '/system/saller_order/list',
    method: 'get',
    params: query
  })
}

// 查询销售员-订单关联详细
export function getSaller_order(userId) {
  return request({
    url: '/system/saller_order/' + userId,
    method: 'get'
  })
}

// 新增销售员-订单关联
export function addSaller_order(data) {
  return request({
    url: '/system/saller_order',
    method: 'post',
    data: data
  })
}

// 修改销售员-订单关联
export function updateSaller_order(data) {
  return request({
    url: '/system/saller_order',
    method: 'put',
    data: data
  })
}

// 删除销售员-订单关联
export function delSaller_order(userId) {
  return request({
    url: '/system/saller_order/' + userId,
    method: 'delete'
  })
}
