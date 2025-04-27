import { authorizedFetch } from './request.js';
import { addToCart } from './cartJit.js';

// 获取用户关联的商品ID列表
async function getUserProductIds() {
  try {
    const response = await authorizedFetch('http://localhost:8080/system/saller_product/list');
    if (response.code === 200 && response.rows) {
      // 直接返回productId数组
      return response.rows.map(item => item.productId);
    }
    return [];
  } catch (error) {
    console.error('获取用户关联商品失败：', error);
    return [];
  }
}

// 渲染商品列表
export function renderProducts(products) {
  const productList = document.getElementById('product-list');
  if (!productList) return;

  // 清空现有内容
  productList.innerHTML = '';

  // 遍历产品数组
  products.forEach(function(product) {
    // 如果 imageUrl 为空，则设置默认图片路径
    var imgSrc = product.imageUrl ? product.imageUrl : 'images/default.png';

    // 获取上下架状态文本
    const statusText = product.pStatus === 1 ? '已上架' : '已下架';
    const statusClass = product.pStatus === 1 ? 'status-on' : 'status-off';

    // 构造产品卡片的HTML
    var html = `
      <div class="col-sm-6 col-md-4 col-lg-6 col-xl-4">
        <!-- 产品卡片 -->
        <article class="product">
          <div class="product-body">
            <div class="product-figure">
              <img src="${imgSrc}" alt="${product.name}" width="220" height="160"/>
            </div>
            <h5 class="product-title">
              <a href="single-product2.html?product_id=${product.productId}">${product.name}</a>
            </h5>
            <div class="product-price-wrap">
              <div class="product-price">¥${product.price}/kg</div>
              <div class="product-status ${statusClass}">${statusText}</div>
            </div>
          </div>
          <div class="product-button-wrap">
            <div class="product-button">
              <a class="button button-secondary" href="single-product2.html?product_id=${product.productId}" title="查看详情">🔍</a>
            </div>
            <div class="product-button">
              <a class="button button-primary" href="product_update.html?id=${product.productId}" title="编辑商品">✏️</a>
            </div>
          </div>
        </article>
      </div>
    `;
    // 将生成的HTML插入容器中
    productList.insertAdjacentHTML('beforeend', html);
  });
}

// 初始化加载所有商品
document.addEventListener('DOMContentLoaded', async () => {
  try {
    // 获取用户关联的商品ID列表
    const userProductIds = await getUserProductIds();
    console.log('用户关联的商品ID：', userProductIds); // 添加日志
    
    // 获取所有商品
    const response = await authorizedFetch('http://localhost:8080/system/product/list');
    if (response.code === 200) {
      // 过滤出用户关联的商品
      const filteredProducts = response.rows.filter(product => 
        userProductIds.includes(product.productId)
      );
      
      console.log('过滤后的商品：', filteredProducts); // 添加日志
      
      if (filteredProducts.length === 0) {
        const productList = document.getElementById('product-list');
        if (productList) {
          productList.innerHTML = `
            <div class="col-12 text-center">
              <div class="alert alert-info" role="alert">
                您还没有关联任何商品
              </div>
            </div>
          `;
        }
      } else {
        renderProducts(filteredProducts);
      }
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

function bindAddToCartEvents() {
  // 选择所有具有 data-product-id 的按钮
  const buttons = document.querySelectorAll('.add-to-cart-btn');
  
  buttons.forEach(button => {
    button.addEventListener('click', function (e) {
      const productId = this.getAttribute('data-product-id');
      if (productId) {
        addToCart(parseInt(productId));
      }
    });
  });
}