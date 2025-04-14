// token.js

/**
 * 从 Cookie 中获取指定名称的值
 * @param {string} name - Cookie 的名称
 * @returns {string|null} - Cookie 的值，如果未找到则返回 null
 */
function getCookieValue(name) {
    const cookieString = document.cookie;
    const cookies = cookieString ? cookieString.split('; ') : [];
    for (let i = 0; i < cookies.length; i++) {
      const [key, value] = cookies[i].split('=');
      if (key === name) {
        return decodeURIComponent(value);
      }
    }
    return null;
  }
  
  /**
   * 获取名为 'Admin-Token' 的令牌
   * @returns {string|null} - Token 的值，如果未找到则返回 null
   */
  export function getToken() {
    return getCookieValue('Admin-Token');
  }
  