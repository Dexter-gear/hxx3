import { authorizedFetch } from "./request.js";

document.addEventListener("DOMContentLoaded", function() {
  // 从 URL 获取订单信息
  const urlParams = new URLSearchParams(window.location.search);
  const orderId = urlParams.get('orderId');
  const amount = urlParams.get('amount');

  // 显示订单信息
  document.getElementById('order-number').textContent = orderId;
  document.getElementById('payment-amount').textContent = `¥${parseFloat(amount).toFixed(2)}`;

  // 添加支付按钮点击事件
  const paymentButton = document.getElementById('payment-button');
  if (paymentButton) {
    paymentButton.onclick = async () => {
      try {
        const response = await authorizedFetch(`http://localhost:8080/system/order`, {
          method: 'PUT',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify({
            orderId: orderId,
            paymentStatus: 1
          })
        });

        if (response && response.code === 200) {
          alert('支付状态更新成功！');
          // 跳转回订单详情页
          window.location.href = `order-detail.html?orderId=${orderId}`;
        } else {
          alert('支付状态更新失败，请稍后重试');
        }
      } catch (error) {
        console.error('更新支付状态失败：', error);
        alert('支付状态更新失败，请稍后重试');
      }
    };
  }
}); 