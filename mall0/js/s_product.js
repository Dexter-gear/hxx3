import { authorizedFetch } from './request.js';
import { addToCart } from './cartJit.js';
// 获取URL参数的函数
function getURLParameter(name) {
    const urlParams = new URLSearchParams(window.location.search);
    return urlParams.get(name);
  }
  
  // 获取产品信息的函数
  function fetchProductInfo(productId) {
    authorizedFetch(`http://localhost:8080/system/product/${productId}`)
      .then(data => {
    const s = data.data;
        // 构建 HTML 内容
        const productInfoHtml = `
          <h4 class="product-big-title">${s.name}</h4>
          <div class="product-big-price-wrap">
            <span class="product-big-price">¥${s.price}</span>
          </div>
          <div class="product-big-text">
            <p>${s.description}</p>
          </div>
           <div class="group-md group-middle">
             <!-- <div class="product-big-rating">
               ${renderStars(s.rating)}
             </div>
             <a class="product-big-reviews" href="#">${s.rating} 星评分</a> -->
           </div>
          <hr class="hr-gray-100">
          <ul class="list list-description">
            <li><span>库存:</span><span>${s.stock }</span></li>
            <li><span>等级:</span><span>${s.qualityLevel}</span></li>
          </ul>
          <div class="group-xs group-middle">
            <div><a class="button button-lg button-secondary button-zakaria add-to-cart-btn" data-product-id=${s.productId}>加入购物车</a></div>

          </div>
        `;
  
        // 更新商品图片
        const carouselParent = document.getElementById('carousel-parent');
        if (carouselParent) {
          carouselParent.innerHTML = `
            <div class="item">
              <div class="slick-product-figure">
                <img src="${s.imageUrl || 'images/default.png'}" alt="${s.name}" width="530" height="480"/>
              </div>
            </div>
          `;
        }
  
        // 将生成的 HTML 内容插入到 id 为 product-info 的 div 中
        document.getElementById('product-info').innerHTML = productInfoHtml;
        bindAddToCartEvents();
      })
      .catch(error => {
        console.error('请求出错:', error);
      });
  }
  
  // 渲染星级评分
  function renderStars(rating) {
    const fullStars = Math.floor(rating);
    const halfStar = rating % 1 !== 0 ? 1 : 0;
    const emptyStars = 5 - fullStars - halfStar;
  
    let starsHtml = '';
    for (let i = 0; i < fullStars; i++) {
      starsHtml += '<div class="product-big-rating-icon mdi mdi-star"></div>';
    }
    if (halfStar) {
      starsHtml += '<div class="product-big-rating-icon mdi mdi-star-half"></div>';
    }
    for (let i = 0; i < emptyStars; i++) {
      starsHtml += '<div class="product-big-rating-icon mdi mdi-star-outline"></div>';
    }
  
    return starsHtml;
  }
  
  // 获取产品 ID 并加载产品信息
  const productId = getURLParameter('product_id'); // 从 URL 获取 product_id 参数
  if (productId) {
    fetchProductInfo(productId);
  } else {
    console.error('没有找到产品 ID');
  }
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