(function() {
    // 获取 cookie 中的 Admin-Token
    var token = getCookie('Admin-Token');
    
    // 检查 token 是否存在
    if (!token) {
      // 如果没有 token，重定向到 login.html
      window.location.href = 'login.html';
    }
    
    // 如果存在 token，继续加载页面内容
    
    // 获取 cookie 中的值的函数
    function getCookie(name) {
      var match = document.cookie.match(new RegExp('(^| )' + name + '=([^;]+)'));
      if (match) {
        return match[2]; // 返回 cookie 的值
      }
      return null;
    }
  })();
  