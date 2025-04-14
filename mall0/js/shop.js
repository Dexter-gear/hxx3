import { authorizedFetch } from './request.js';
import { addToCart } from './cartJit.js';

// 渲染商品列表
export function renderProducts(products) {
  const productList = document.getElementById('product-list');
  if (!productList) return;

  // 清空现有内容
  productList.innerHTML = '';

  // 遍历产品数组
  products.forEach(function(product) {
    // 如果产品已下架（pStatus === 0），则不显示
    if (product.pStatus === 0) {
      return;
    }

    // 如果 imageUrl 为空，则设置默认图片路径
    var imgSrc = product.imageUrl ? product.imageUrl : 'images/default.png';

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
              <a href="single-product.html?product_id=${product.productId}">${product.name}</a>
            </h5>
            <div class="product-price-wrap">
              <div class="product-price">¥${product.price}</div>
            </div>
          </div>
          <div class="product-button-wrap">
            <div class="product-button">
              <a class="button button-secondary" href="single-product.html?product_id=${product.productId}" title="查看详情">🔍</a>
            </div>
            <div class="product-button">
              <a class="button button-primary add-to-cart-btn" data-product-id="${product.productId}" title="加入购物车">🛒</a>
            </div>
          </div>
        </article>
      </div>
    `;
    // 将生成的HTML插入容器中
    productList.insertAdjacentHTML('beforeend', html);
  });

  // 绑定加入购物车事件
  bindAddToCartEvents();
}

// 绑定加入购物车事件
function bindAddToCartEvents() {
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

// 初始化加载所有商品
document.addEventListener('DOMContentLoaded', async () => {
  try {
    const response = await authorizedFetch('http://localhost:8080/system/product/list');
    if (response.code === 200) {
      renderProducts(response.rows);
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