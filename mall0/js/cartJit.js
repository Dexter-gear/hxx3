import { authorizedFetch } from './request.js';
import { fetchCart } from './cart.js';
// 添加或更新购物车商品的函数
export function addToCart(productId) {
    fetchCart().then(cartItems => {
      // 查找购物车中是否已有该商品
      const existingItem = cartItems.find(item => item.productId === productId);
  
      if (existingItem) {
        // 存在商品，更新数量 +1
        const updatedQuantity = existingItem.quantity + 1;
        authorizedFetch("http://localhost:8080/system/cart", {
            method: "PUT",
            headers: {
              "Content-Type": "application/json"
            },
            body: JSON.stringify({
              ...existingItem,
              quantity: updatedQuantity
            })
          }).then(response => {
              window.alert("数量已更新");
          });
      } else {
        // 不存在，新增商品
        authorizedFetch("http://localhost:8080/system/cart", {
          method: "POST",
          headers: {
            "Content-Type": "application/json"
          },
          body: JSON.stringify({
            productId: productId,
            quantity: 1
          })
        }).then(response => {
          window.alert("添加成功");
        });
      }
    });
  }
  
  