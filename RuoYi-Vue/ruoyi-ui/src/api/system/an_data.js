import request from '@/utils/request'

// 查询数据分析列表
export function listAn_data(query) {
  return request({
    url: '/system/an_data/list',
    method: 'get',
    params: query
  })
}

// 查询数据分析详细
export function getAn_data(tableName) {
  return request({
    url: '/system/an_data/' + tableName,
    method: 'get'
  })
}

// 新增数据分析
export function addAn_data(data) {
  return request({
    url: '/system/an_data',
    method: 'post',
    data: data
  })
}

// 修改数据分析
export function updateAn_data(data) {
  return request({
    url: '/system/an_data',
    method: 'put',
    data: data
  })
}

// 删除数据分析
export function delAn_data(tableName) {
  return request({
    url: '/system/an_data/' + tableName,
    method: 'delete'
  })
}
