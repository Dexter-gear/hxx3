// request.js

import { getToken } from './token.js';

/**
 * 发送带有 Authorization 头的 HTTP 请求
 * @param {string} url - 请求的 URL
 * @param {object} options - 请求的配置选项
 * @returns {Promise<Response>} - Fetch API 的响应 Promise
 */
export function authorizedFetch(url, options = {}) {
  const token = getToken();
  const headers = new Headers(options.headers || {});
  if (token) {
    headers.set('Authorization', `Bearer ${token}`);
  }
  return fetch(url, {
    ...options,
    headers,
  })
  .then((response) => response.json()) // 解析响应为 JSON
    .then((data) => {
        console.log(data);
      if (data.code === 401) {
        window.location.href = 'login.html'; // 跳转到登录页面
        return data;
      }
      return data; // 正常返回数据
    })
  .catch((error) => {
    console.error('请求出错:', error);
    throw error; // 继续抛出错误，便于上层捕获
  });
}
