import { authorizedFetch } from "./request.js";

document.addEventListener("DOMContentLoaded", async function() {
  try {
    // 尝试获取购物车信息
    const response = await authorizedFetch("http://localhost:8080/system/cart/list");
    
    // 如果请求成功，继续执行
    if (response && response.code === 200) {
      console.log("权限验证通过");
      return;
    }
    
    // 如果请求失败或被拒绝，跳转到index2.html
    console.log("权限验证失败，跳转到登录页面");
    window.location.href = "index2.html";
  } catch (error) {
    console.error("权限验证出错：", error);
    window.location.href = "index2.html";
  }
});