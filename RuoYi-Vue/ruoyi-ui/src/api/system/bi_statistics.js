import request from '@/utils/request'

// 查询统计数据列表
export function listBi_statistics(query) {
  return request({
    url: '/system/bi_statistics/list',
    method: 'get',
    params: query
  })
}

// 查询统计数据详细
export function getBi_statistics(module) {
  return request({
    url: '/system/bi_statistics/' + module,
    method: 'get'
  })
}

// 新增统计数据
export function addBi_statistics(data) {
  return request({
    url: '/system/bi_statistics',
    method: 'post',
    data: data
  })
}

// 修改统计数据
export function updateBi_statistics(data) {
  return request({
    url: '/system/bi_statistics',
    method: 'put',
    data: data
  })
}

// 删除统计数据
export function delBi_statistics(module) {
  return request({
    url: '/system/bi_statistics/' + module,
    method: 'delete'
  })
}
