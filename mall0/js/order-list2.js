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

async function fetchOrderDetails(orderId) {
  try {
    const response = await authorizedFetch(`http://localhost:8080/system/order/${orderId}`);
    if (response.code === 200) {
      return response.data;
    }
    return null;
  } catch (error) {
    console.error(`获取订单${orderId}详情失败：`, error);
    return null;
  }
}

async function fetchOrders() {
  try {
    // 获取商家关联的订单列表
    const response = await authorizedFetch("http://localhost:8080/system/saller_order/list");
    console.log("商家订单列表响应：", response);
    
    if (!response || !response.rows) {
      console.error("订单列表数据格式错误：", response);
      renderOrders([]);
      return;
    }

    // 获取所有关联订单的详细信息
    const ordersWithDetails = await Promise.all(
      response.rows.map(async (sallerOrder) => {
        try {
          // 使用关联表中的订单ID获取订单详情
          const order = await fetchOrderDetails(sallerOrder.orderId);
          if (order) {
            const address = await fetchAddress(order.addressId);
            // 确保状态是数字类型
            const orderWithStatus = {
              ...order,
              status: parseInt(order.status),
              paymentStatus: parseInt(order.paymentStatus),
              shippingStatus: parseInt(order.shippingStatus),
              address: address ? address.address : '未知地址'
            };
            console.log("处理后的订单数据：", orderWithStatus);
            return orderWithStatus;
          }
          return null;
        } catch (error) {
          console.error(`获取订单${sallerOrder.orderId}详情失败：`, error);
          return null;
        }
      })
    );

    // 过滤掉获取失败的订单
    const validOrders = ordersWithDetails.filter(order => order !== null);
    console.log("最终渲染的订单数据：", validOrders);
    
    renderOrders(validOrders);
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

// 将函数绑定到window对象上，使其全局可访问
window.updateShippingStatus = async function(orderId) {
  try {
    const response = await authorizedFetch(`http://localhost:8080/system/order`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({ 
        orderId: orderId,
        shippingStatus: 1
      })
    });
    
    if (response.code === 200) {
      window.alert('发货状态更新成功');
      fetchOrders(); // 重新加载订单列表
    } else {
      window.alert('发货状态更新失败');
    }
  } catch (error) {
    console.error('更新发货状态失败：', error);
    window.alert('更新发货状态失败，请稍后重试');
  }
};

window.updateOrderStatus = async function(orderId) {
  try {
    const response = await authorizedFetch(`http://localhost:8080/system/order`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({ 
        orderId: orderId,
        status: 1
      })
    });
    
    if (response.code === 200) {
      window.alert('订单状态更新成功');
      fetchOrders(); // 重新加载订单列表
    } else {
      window.alert('订单状态更新失败');
    }
  } catch (error) {
    console.error('更新订单状态失败：', error);
    window.alert('更新订单状态失败，请稍后重试');
  }
};

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
    
    // 确保状态是数字类型
    const status = parseInt(order.status);
    const shippingStatus = parseInt(order.shippingStatus);
    const paymentStatus = parseInt(order.paymentStatus);
    
    console.log(`订单${orderId}的状态：`, { status, shippingStatus, paymentStatus });
    
    return `
      <div class="order-item">
        <div class="order-header">
          <div class="order-info">
            <span>订单号：${orderId}</span>
            <span>下单时间：${createTime}</span>
            <span>总金额：${totalAmount}</span>
          </div>
          <div class="order-status">
            <span class="status-item">订单状态：${getStatusText(status, 'order')}</span>
            <span class="status-item">付款状态：${getStatusText(paymentStatus, 'payment')}</span>
            <span class="status-item">物流状态：${getStatusText(shippingStatus, 'shipping')}</span>
          </div>
          <div class="order-actions">
            ${shippingStatus === 0 ? `
              <button class="button button-primary" data-order-id="${orderId}" onclick="window.updateShippingStatus('${orderId}')">
                发货
              </button>
            ` : ''}
            ${status === 0 ? `
              <button class="button button-primary" data-order-id="${orderId}" onclick="window.updateOrderStatus('${orderId}')">
                完成订单
              </button>
            ` : ''}
            <button class="button button-primary" onclick="window.location.href='order-detail.html?orderId=${orderId}'">
              查看详情
            </button>
          </div>
        </div>
        <div class="order-address">
          <strong>收货地址：</strong>${address}
        </div>
        <div class="order-details">
          ${order.details ? order.details.map(detail => {
            const productName = detail.productName || '未知商品';
            const quantity = detail.quantity || 0;
            const price = detail.price ? `¥${detail.price}/kg` : '¥0.00';
            const productImage = detail.productImage || 'images/product-default.png';
            
            return `
              <div class="order-detail-item">
                <img src="${productImage}" alt="${productName}" width="80" height="80">
                <div class="detail-info">
                  <h5>${productName}</h5>
                  <p>数量：${quantity}kg</p>
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
