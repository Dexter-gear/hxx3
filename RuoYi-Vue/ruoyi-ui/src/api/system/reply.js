import request from '@/utils/request'

// 查询商家回复用户评论列表
export function listReply(query) {
  return request({
    url: '/system/reply/list',
    method: 'get',
    params: query
  })
}

// 查询商家回复用户评论详细
export function getReply(replyId) {
  return request({
    url: '/system/reply/' + replyId,
    method: 'get'
  })
}

// 新增商家回复用户评论
export function addReply(data) {
  return request({
    url: '/system/reply',
    method: 'post',
    data: data
  })
}

// 修改商家回复用户评论
export function updateReply(data) {
  return request({
    url: '/system/reply',
    method: 'put',
    data: data
  })
}

// 删除商家回复用户评论
export function delReply(replyId) {
  return request({
    url: '/system/reply/' + replyId,
    method: 'delete'
  })
}
