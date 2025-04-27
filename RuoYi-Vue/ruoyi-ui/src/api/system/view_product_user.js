import request from '@/utils/request'

// 查询VIEW列表
export function listView_product_user(query) {
  return request({
    url: '/system/view_product_user/list',
    method: 'get',
    params: query
  })
}

// 查询VIEW详细
export function getView_product_user(productId) {
  return request({
    url: '/system/view_product_user/' + productId,
    method: 'get'
  })
}

// 新增VIEW
export function addView_product_user(data) {
  return request({
    url: '/system/view_product_user',
    method: 'post',
    data: data
  })
}

// 修改VIEW
export function updateView_product_user(data) {
  return request({
    url: '/system/view_product_user',
    method: 'put',
    data: data
  })
}

// 删除VIEW
export function delView_product_user(productId) {
  return request({
    url: '/system/view_product_user/' + productId,
    method: 'delete'
  })
}
