import { fetchCart } from './cart.js';
import { fetchProduct } from './cart.js';
import { authorizedFetch } from './request.js';

document.addEventListener("DOMContentLoaded", function () {
    const submitButton = document.getElementById("submit-order");
    if (submitButton) {
      submitButton.addEventListener("click", function (event) {
        submitOrder();
      });
    }
  });
  

  
    async function submitOrder() {
    fetchCart().then(cartItems => {
      if (cartItems.length === 0) {
        window.alert("购物车为空，无法提交订单！");
        return;
      }

      const totalElement = document.getElementById("cart-total");
      const totalText = totalElement.textContent.trim();
      const totalValue = parseFloat(totalText.replace(/[^\d.]/g, ''));
      console.log('totalValue', totalValue);

      // 第一步：提交订单
      authorizedFetch("http://localhost:8080/system/order", {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify({
          totalAmount: totalValue,
        })
      })
      .then(async order => {
        const orderId = order.data.orderId;
        if (!orderId) throw new Error("订单创建失败");

        // 第二步：批量获取所有商品的详情（含 price），并合并到 cartItems 中
        const enrichedCartItems = await Promise.all(
          cartItems.map(async (item) => {
            const product = await fetchProduct(item.productId);
            return {
              ...item,
              price: product.price
            };
          })
        );

        // 第三步：逐条发送订单详情请求
        const detailPromises = enrichedCartItems.map((item) =>
          authorizedFetch("http://localhost:8080/system/detail", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({
              orderId: orderId,
              productId: item.productId,
              quantity: item.quantity,
              price: item.price
            })
          })
        );

        await Promise.all(detailPromises);
        console.log("✅ 所有订单详情已提交");
        console.log('cartItems',cartItems);

        // // 第四步：提示 & 清空购物车
        // window.alert("订单提交成功！");
        // clearCart(cartItems);
      })
      .catch(error => {
        console.error("❌ 提交订单失败：", error);
        window.alert("提交订单失败，请稍后重试。");
      });
    });
  }



//   function clearCart(cartItems) {
//     if (!Array.isArray(cartItems)) {
//       console.error("❌ 购物车数据格式错误：cartItems 不是数组");
//       return;
//     }
  
//     (async () => {
//       try {
//         for (const rows of cartItems) {
//           console.log("正在删除 item:", rows.cartId);
//           await authorizedFetch(`http://localhost:8080/system/cart/${rows.cartId}`, {
//             method: "DELETE"
//           });
//         }
  
//         window.alert("购物车已成功清空！");
//         window.location.reload();
//       } catch (error) {
//         console.error("❌ 清空购物车失败：", error);
//         window.alert("清空购物车失败，请稍后再试！");
//       }
//     })();
//   }
  