import request from '@/utils/request'

// 查询评论详情列表
export function listReviews(query) {
  return request({
    url: '/system/reviews/list',
    method: 'get',
    params: query
  })
}

// 查询评论详情详细
export function getReviews(reviewId) {
  return request({
    url: '/system/reviews/' + reviewId,
    method: 'get'
  })
}

// 新增评论详情
export function addReviews(data) {
  return request({
    url: '/system/reviews',
    method: 'post',
    data: data
  })
}

// 修改评论详情
export function updateReviews(data) {
  return request({
    url: '/system/reviews',
    method: 'put',
    data: data
  })
}

// 删除评论详情
export function delReviews(reviewId) {
  return request({
    url: '/system/reviews/' + reviewId,
    method: 'delete'
  })
}
