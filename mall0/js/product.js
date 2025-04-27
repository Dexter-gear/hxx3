import { authorizedFetch } from "./request.js";

// 获取用户关联的商品ID列表
async function getUserProductIds() {
  try {
    const response = await authorizedFetch('http://localhost:8080/system/saller_product/list');
    if (response.code === 200 && response.rows) {
      return response.rows.map(item => item.productId);
    }
    return [];
  } catch (error) {
    console.error('获取用户关联商品失败：', error);
    return [];
  }
}

// 全局变量存储所有商品和用户关联的商品ID
let allProducts = [];
let userProductIds = [];

// 根据分类筛选商品
function filterProductsByCategory(categoryId) {
  // 首先过滤出用户关联的商品
  let filteredProducts = allProducts.filter(product => 
    userProductIds.includes(product.productId)
  );

  // 如果选择了分类，进一步按分类筛选
  if (categoryId) {
    filteredProducts = filteredProducts.filter(product => 
      product.categoryId === parseInt(categoryId)
    );
  }

  if (filteredProducts.length === 0) {
    const productList = document.getElementById('product-list');
    if (productList) {
      productList.innerHTML = `
        <div class="col-12 text-center">
          <div class="alert alert-info" role="alert">
            ${categoryId ? '该分类下没有您的商品' : '您还没有关联任何商品'}
          </div>
        </div>
      `;
    }
  } else {
    renderProductList(filteredProducts);
  }
}

// 渲染商品列表
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
        allProducts = listResponse.rows;
        filterProductsByCategory(null); // 重新应用筛选
      }
    } else {
      alert('更新产品状态失败');
    }
  } catch (error) {
    console.error('更新产品状态出错：', error);
    alert('更新产品状态出错');
  }
};

// 导出筛选函数供其他模块使用
export function filterByCategory(categoryId) {
  filterProductsByCategory(categoryId);
}

// 初始化加载
document.addEventListener("DOMContentLoaded", async function() {
  try {
    // 获取用户关联的商品ID列表
    userProductIds = await getUserProductIds();
    console.log('用户关联的商品ID：', userProductIds);
    
    // 获取所有商品
    const response = await authorizedFetch("http://localhost:8080/system/product/list");
    if (response && response.code === 200) {
      allProducts = response.rows;
      // 初始显示所有关联商品
      filterProductsByCategory(null);
    } else {
      throw new Error(response.msg || '获取数据失败');
    }
  } catch (error) {
    console.error('获取产品列表失败：', error);
    const productList = document.getElementById('product-list');
    if (productList) {
      productList.innerHTML = `
        <div class="col-12 text-center">
          <div class="alert alert-danger" role="alert">
            获取产品列表失败：${error.message}
          </div>
        </div>
      `;
    }
  }
});
