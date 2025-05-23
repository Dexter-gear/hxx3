import request from '@/utils/request'

// 查询商品类别列表
export function listCategory(query) {
  return request({
    url: '/system/category/list',
    method: 'get',
    params: query
  })
}

// 查询商品类别详细
export function getCategory(categoryId) {
  return request({
    url: '/system/category/' + categoryId,
    method: 'get'
  })
}

// 新增商品类别
export function addCategory(data) {
  return request({
    url: '/system/category',
    method: 'post',
    data: data
  })
}

// 修改商品类别
export function updateCategory(data) {
  return request({
    url: '/system/category',
    method: 'put',
    data: data
  })
}

// 删除商品类别
export function delCategory(categoryId) {
  return request({
    url: '/system/category/' + categoryId,
    method: 'delete'
  })
}
