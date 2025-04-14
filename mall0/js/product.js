import { authorizedFetch } from "./request.js";

document.addEventListener("DOMContentLoaded", async function() {
  try {
    // 获取产品列表
    const response = await authorizedFetch("http://localhost:8080/system/product/list");
    
    if (response && response.code === 200) {
      renderProductList(response.rows);
    } else {
      console.error("获取产品列表失败");
      document.getElementById("product-list").innerHTML = "<p>获取产品列表失败</p>";
    }
  } catch (error) {
    console.error("获取产品列表出错：", error);
    document.getElementById("product-list").innerHTML = "<p>获取产品列表出错</p>";
  }
});

function renderProductList(products) {
  const productListContainer = document.getElementById("product-list");
  if (!productListContainer) return;

  const productListHtml = `
    <div class="products-container">
      <div class="product-header">
        <h3>产品列表</h3>
      </div>
      <div class="product-grid">
        ${products.map(product => `
          <div class="product-item">
            <div class="product-image">
              <img src="${product.imageUrl || 'images/product-default.png'}" alt="${product.name}" width="200" height="200">
            </div>
            <div class="product-info">
              <h4>${product.name}</h4>
              <p class="product-description">${product.description || '暂无描述'}</p>
              <div class="product-details">
                <p>价格: ¥${product.price}</p>
                <p>库存: ${product.stock}</p>
                <p>品质等级: ${getQualityLevelText(product.qualityLevel)}</p>
              </div>
              <div class="product-actions">
                <button class="button ${product.pStatus === 1 ? 'button-danger' : 'button-success'}" 
                        onclick="toggleProductStatus(${product.productId}, ${product.pStatus})">
                  ${product.pStatus === 1 ? '下架' : '上架'}
                </button>
              </div>
            </div>
          </div>
        `).join('')}
      </div>
    </div>
  `;

  productListContainer.innerHTML = productListHtml;
}

function getQualityLevelText(level) {
  switch(level) {
    case 1: return '优';
    case 2: return '良';
    case 3: return '中';
    default: return '未知';
  }
}

// 全局函数，用于切换产品上下架状态
window.toggleProductStatus = async function(productId, currentStatus) {
  try {
    const newStatus = currentStatus === 1 ? 0 : 1;
    const response = await authorizedFetch(`http://localhost:8080/system/product`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        productId: productId,
        pStatus: newStatus
      })
    });

    if (response && response.code === 200) {
      // 重新加载产品列表
      const listResponse = await authorizedFetch("http://localhost:8080/system/product/list");
      if (listResponse && listResponse.code === 200) {
        renderProductList(listResponse.rows);
      }
    } else {
      alert('更新产品状态失败');
    }
  } catch (error) {
    console.error('更新产品状态出错：', error);
    alert('更新产品状态出错');
  }
};
