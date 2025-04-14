import { authorizedFetch } from "./request.js";

document.addEventListener("DOMContentLoaded", async function() {
  try {
    // 尝试获取购物车信息
    const response = await authorizedFetch("http://localhost:8080/system/cart/list");
    
    // 如果请求成功，继续执行
    if (response && response.code === 200) {
      console.log("权限验证通过");
    window.location.href = "index.html";
      return;
    }
  } catch (error) {
  }
});