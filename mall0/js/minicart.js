import { fetchCart } from './cart.js';
import { fetchProduct } from './cart.js';

function renderCartPreview() {
    fetchCart().then(cartItems => {
      if (!Array.isArray(cartItems)) {
        console.error("购物车数据无效");
        return;
      }
  
      // 为每项购物车商品获取商品信息
      const productPromises = cartItems.map(item =>
        fetchProduct(item.productId).then(product => ({
          ...item,
          product
        }))
      );
  
      Promise.all(productPromises).then(cartWithProducts => {
        const cartBody = document.querySelector(".cart-inline-body");
        const cartCount = document.querySelectorAll(".rd-navbar-basket span, .rd-navbar-basket-mobile span");
        const cartTotalText = document.querySelector(".cart-inline-header h6 span");
  
        if (!cartBody || !cartTotalText) return;
  
        cartBody.innerHTML = "";
  
        let total = 0;
  
        cartWithProducts.forEach(({ quantity, product }) => {
          if (!product) return;
  
          const subtotal = product.price * quantity;
          total += subtotal;
  
          const itemHTML = `
            <div class="cart-inline-item">
              <div class="unit unit-spacing-sm align-items-center">
                <div class="unit-left">
                  <a class="cart-inline-figure" href="single-product.html">
                    <alt="${product.name}" width="100" height="90"/>
                  </a>
                </div>
                <div class="unit-body">
                  <h6 class="cart-inline-name"><a href="single-product.html">${product.name}</a></h6>
                    <div class="group-xs group-middle">
                      <div class="table-cart-stepper">
                        <span>${quantity}</span>
                      </div>
                      <h6 class="cart-inline-title">¥${product.price.toFixed(2)}</h6>
                  </div>
                </div>
              </div>
            </div>
          `;
          cartBody.insertAdjacentHTML("beforeend", itemHTML);
        });
  
        cartTotalText.textContent = `¥${total.toFixed(2)}`;
        cartCount.forEach(el => el.textContent = cartWithProducts.length);
        document.querySelector(".cart-inline-title span").textContent = ` ${cartWithProducts.length}`;
      });
    });
  }
  
  document.addEventListener("DOMContentLoaded", renderCartPreview);