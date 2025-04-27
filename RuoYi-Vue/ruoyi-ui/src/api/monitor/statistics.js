import request from '@/utils/request'

// 获取统计数据
export function getStatistics() {
  return request({
    url: '/system/statistics/overview',
    method: 'get'
  })
}

// 获取商品列表
export function getProductList() {
  return request({
    url: '/system/statistics/products',
    method: 'get'
  })
}

// 获取订单列表
export function getOrderList() {
  return request({
    url: '/system/statistics/orders',
    method: 'get'
  })
}

// 获取评论列表
export function getCommentList() {
  return request({
    url: '/system/statistics/comments',
    method: 'get'
  })
} 