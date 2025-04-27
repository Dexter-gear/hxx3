import { authorizedFetch } from "./request.js";

document.addEventListener("DOMContentLoaded", function () {
  fetchOrders();
});

async function fetchAddress(addressId) {
  try {
    const response = await authorizedFetch(`http://localhost:8080/system/user_address/${addressId}`);
    if (response.code === 200) {
      return response.data;
    }
    return null;
  } catch (error) {
    console.error("获取地址详情失败：", error);
    return null;
  }
}

async function fetchOrders() {
  try {
    const response = await authorizedFetch("http://localhost:8080/system/order/list");
    console.log("订单列表响应：", response);
    
    if (!response || !response.rows) {
      console.error("订单列表数据格式错误：", response);
      renderOrders([]);
      return;
    }

    // 获取所有订单的地址详情
    const ordersWithAddress = await Promise.all(
      response.rows.map(async (order) => {
        const address = await fetchAddress(order.addressId);
        return {
          ...order,
          address: address ? address.address : '未知地址'
        };
      })
    );
    
    renderOrders(ordersWithAddress);
  } catch (error) {
    console.error("获取订单列表失败：", error);
    window.alert("获取订单列表失败，请稍后重试");
    renderOrders([]);
  }
}

function getStatusText(status, type) {
  const statusMap = {
    order: {
      0: "未完成",
      1: "已完成"
    },
    payment: {
      0: "未付款",
      1: "已付款"
    },
    shipping: {
      0: "未发货",
      1: "已发货"
    }
  };
  return statusMap[type][status] || "未知状态";
}

function formatDate(dateString) {
  if (!dateString) return '未知时间';
  const date = new Date(dateString);
  if (isNaN(date.getTime())) return '未知时间';
  
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, '0');
  const day = String(date.getDate()).padStart(2, '0');
  const hours = String(date.getHours()).padStart(2, '0');
  const minutes = String(date.getMinutes()).padStart(2, '0');
  const seconds = String(date.getSeconds()).padStart(2, '0');
  
  return `${year}-${month}-${day}`;
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
    const orderId = order.orderId || '未知订单号';
    const createTime = formatDate(order.createdAt);
    const totalAmount = order.totalAmount ? `¥${order.totalAmount}` : '¥0.00';
    const address = order.address || '未知地址';
    
    return `
      <div class="order-item">
        <div class="order-header">
          <div class="order-info">
            <span>订单号：${orderId}</span>
            <span>下单时间：${createTime}</span>
            <span>总金额：${totalAmount}</span>
          </div>
          <div class="order-status">
            <span class="status-item">订单状态：${getStatusText(order.status, 'order')}</span>
            <span class="status-item">付款状态：${getStatusText(order.paymentStatus, 'payment')}</span>
            <span class="status-item">物流状态：${getStatusText(order.shippingStatus, 'shipping')}</span>
          </div>
          <button class="button button-primary" onclick="window.location.href='order-detail.html?orderId=${orderId}'">
            查看详情
          </button>
        </div>
        <div class="order-address">
          <strong>收货地址：</strong>${address}
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
