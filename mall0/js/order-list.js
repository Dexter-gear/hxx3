import { authorizedFetch } from "./request.js";

document.addEventListener("DOMContentLoaded", function () {
  fetchOrders();
});

async function fetchOrders() {
  try {
    const response = await authorizedFetch("http://localhost:8080/system/order/list");
    console.log("订单列表响应：", response);
    
    // 检查响应数据结构
    if (!response || !response.rows) {
      console.error("订单列表数据格式错误：", response);
      renderOrders([]);
      return;
    }
    
    renderOrders(response.rows);
  } catch (error) {
    console.error("获取订单列表失败：", error);
    window.alert("获取订单列表失败，请稍后重试");
    renderOrders([]);
  }
}

function renderOrders(orders) {
  const orderListContainer = document.getElementById("order-list");
  if (!orderListContainer) {
    console.error("找不到订单列表容器元素");
    return;
  }

  if (!Array.isArray(orders) || orders.length === 0) {
    orderListContainer.innerHTML = "<p>暂无订单记录</p>";
    return;
  }

  const ordersHtml = orders.map(order => {
    // 确保订单对象存在必要的属性
    const orderId = order.orderId || '未知订单号';
    const createTime = order.createdAt ? new Date(order.createdAt).toLocaleString() : '未知时间';
    const totalAmount = order.totalAmount ? `¥${order.totalAmount}` : '¥0.00';
    
    return `
      <div class="order-item">
        <div class="order-header">
          <span>订单号：${orderId}</span>
          <span>下单时间：${createTime}</span>
          <span>总金额：${totalAmount}</span>
          <button class="button button-primary" onclick="window.location.href='order-detail.html?orderId=${orderId}'">
            查看详情
          </button>
        </div>
        <div class="order-details">
          ${Array.isArray(order.details) ? order.details.map(detail => {
            const productName = detail.productName || '未知商品';
            const quantity = detail.quantity || 0;
            const price = detail.price ? `¥${detail.price}` : '¥0.00';
            const productImage = detail.productImage || 'images/product-default.png';
            
            return `
              <div class="order-detail-item">
                <img src="${productImage}" alt="${productName}" width="80" height="80">
                <div class="detail-info">
                  <h5>${productName}</h5>
                  <p>数量：${quantity}</p>
                  <p>单价：${price}</p>
                </div>
              </div>
            `;
          }).join('') : ''}
        </div>
      </div>
    `;
  }).join('');

  orderListContainer.innerHTML = `
    <div class="orders-container">
      ${ordersHtml}
    </div>
  `;
}
