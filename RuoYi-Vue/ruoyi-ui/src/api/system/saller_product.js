import request from '@/utils/request'

// 查询销售员-产品关联列表
export function listSaller_product(query) {
  return request({
    url: '/system/saller_product/list',
    method: 'get',
    params: query
  })
}

// 查询销售员-产品关联详细
export function getSaller_product(userId) {
  return request({
    url: '/system/saller_product/' + userId,
    method: 'get'
  })
}

// 新增销售员-产品关联
export function addSaller_product(data) {
  return request({
    url: '/system/saller_product',
    method: 'post',
    data: data
  })
}

// 修改销售员-产品关联
export function updateSaller_product(data) {
  return request({
    url: '/system/saller_product',
    method: 'put',
    data: data
  })
}

// 删除销售员-产品关联
export function delSaller_product(userId) {
  return request({
    url: '/system/saller_product/' + userId,
    method: 'delete'
  })
}
