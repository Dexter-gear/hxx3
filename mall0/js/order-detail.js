import { authorizedFetch } from "./request.js";

document.addEventListener("DOMContentLoaded", function () {
  // 从URL获取订单ID
  const urlParams = new URLSearchParams(window.location.search);
  const orderId = urlParams.get('orderId');
  
  if (orderId) {
    fetchOrderDetail(orderId);
  } else {
    document.getElementById("order-detail").innerHTML = "<p>未找到订单ID</p>";
  }
});

async function fetchOrderDetail(orderId) {
  try {
    // 先获取订单数据
    const orderResponse = await authorizedFetch(`http://localhost:8080/system/order/${orderId}`);
    if (!orderResponse || orderResponse.code !== 200) {
      console.error("获取订单数据失败：", orderResponse);
      renderOrderDetail(null);
      return;
    }
    
    const orderData = orderResponse.data;
    
    // 如果订单已付款，直接渲染订单详情
    if (orderData.paymentStatus === 1) {
      renderOrderDetail({
        ...orderData,
        details: []
      });
      return;
    }
    
    // 请求所有订单详情列表
    const response = await authorizedFetch(`http://localhost:8080/system/detail/list`);
    console.log("订单详情列表响应：", response);
    
    if (!response || !response.rows) {
      console.error("订单详情数据格式错误：", response);
      renderOrderDetail(null);
      return;
    }
    
    // 根据orderId筛选对应的订单详情
    const orderDetails = response.rows.filter(detail => detail.orderId == orderId);
    console.log("筛选后的订单详情：", orderDetails);
    
    if (!orderDetails.length) {
      console.error("未找到对应的订单详情");
      renderOrderDetail(null);
      return;
    }
    
    // 获取每个产品的详细信息
    const productPromises = orderDetails.map(detail => 
      fetchProductInfo(detail.productId)
    );
    
    const products = await Promise.all(productPromises);
    console.log("产品信息：", products);
    
    // 将产品信息添加到订单详情中
    const orderWithProducts = {
      ...orderData,
      details: orderDetails.map((detail, index) => ({
        ...detail,
        productInfo: products[index]
      }))
    };
    
    renderOrderDetail(orderWithProducts);
  } catch (error) {
    console.error("获取订单详情失败：", error);
    document.getElementById("order-detail").innerHTML = "<p>获取订单详情失败，请稍后重试</p>";
  }
}

async function fetchProductInfo(productId) {
  try {
    const response = await authorizedFetch(`http://localhost:8080/system/product/${productId}`);
    if (response && response.code === 200) {
      return response.data;
    }
    return null;
  } catch (error) {
    console.error(`获取产品${productId}信息失败：`, error);
    return null;
  }
}

function renderOrderDetail(order) {
  const orderDetailContainer = document.getElementById("order-detail");
  
  if (!orderDetailContainer) {
    console.error("找不到订单详情容器元素");
    return;
  }

  if (!order) {
    orderDetailContainer.innerHTML = "<p>未找到订单信息</p>";
    return;
  }

  const orderId = order.orderId || '未知订单号';
  const totalAmount = order.details.reduce((sum, detail) => sum + (detail.price * detail.quantity), 0);
  
  const orderDetails = Array.isArray(order.details) ? order.details : [];
  console.log("支付状态：", order.paymentStatus);
  
  const orderDetailHtml = `
    <div class="orders-container">
      <div class="order-item">
        <div class="order-header">
          <span>订单号：${orderId}</span>
          <span>总金额：¥${totalAmount.toFixed(2)}</span>
          <span>支付状态：${order.paymentStatus === "1" ? '已支付' : '待支付'}</span>
        </div>
        <div class="order-details">
          ${orderDetails.map(detail => {
            const productInfo = detail.productInfo || {};
            const productName = productInfo.name || '未知商品';
            const productDescription = productInfo.description || '';
            const productImage = productInfo.imageUrl || 'images/product-default.png';
            const quantity = detail.quantity || 0;
            const price = detail.price ? `¥${detail.price}` : '¥0.00';
            
            return `
              <div class="order-detail-item">
                <img src="${productImage}" alt="${productName}" width="80" height="80">
                <div class="detail-info">
                  <h5>${productName}</h5>
                  <p>${productDescription}</p>
                  <p>数量：${quantity}</p>
                  <p>单价：${price}</p>
                </div>
              </div>
            `;
          }).join('')}
        </div>
        ${order.paymentStatus !== "1" ? `
          <div style="text-align: center; margin-top: 20px;">
            <button onclick="window.location.href='payment.html?orderId=${orderId}&amount=${totalAmount.toFixed(2)}'" class="button button-primary button-zakaria">去支付</button>
          </div>
        ` : ''}
      </div>
    </div>
  `;

  orderDetailContainer.innerHTML = orderDetailHtml;
}
