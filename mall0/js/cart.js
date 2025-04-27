import { authorizedFetch } from './request.js';
export function fetchCart() {
    return authorizedFetch("http://localhost:8080/system/cart/list")
      .then(data => {
        if (data.code === 200) {
            console.log(data);
            return data.rows;
        } else {
          console.error("加载购物车失败:", data.msg);
          return [];
        }
      })
      .catch(err => {
        console.error("请求错误:", err);
        return [];
      });
  }
export function fetchProduct(productId) {
    return authorizedFetch("http://localhost:8080/system/product/" + productId)
      .then(data => {
        if (data.code === 200) {
            console.log(data);
            return data.data;
        } else {
          console.error("加载产品失败:", data.msg);
          return [];
        }
      })
      .catch(err => {
        console.error("请求错误:", err);
        return [];
      });
  }

  function fetchCartWithProducts() {
    fetchCart().then(cartItems => {
      if (!Array.isArray(cartItems)) {
        console.error("购物车数据无效");
        return;
      }
  
      const productPromises = cartItems.map(cartItem => {
        return fetchProduct(cartItem.productId).then(product => {
          return {
            ...cartItem,
            product: product || null
          };
        });
      });
  
      Promise.all(productPromises)
        .then(cartWithProducts => {
          console.log("整合后的购物车与商品信息：", cartWithProducts);
          const tbody = document.getElementById("cart-body");
          const totalElement = document.getElementById("cart-total");
          let total = 0;
  
          tbody.innerHTML = ""; // 清空旧内容
  
          cartWithProducts.forEach(item => {
            const product = item.product;
            const quantity = item.quantity;
            const unitPrice = product.price;
            const subtotal = unitPrice * quantity;
            total += subtotal;
  
            const row = document.createElement("tr");
            row.innerHTML = `
              <td>
                <a class="table-cart-figure" href="single-product.html">
                  <img src="${product.imageUrl || 'images/placeholder.png'}" alt="${product.name}" width="146" height="132"/>
                </a>
                <a class="table-cart-link" href="single-product.html">${product.name}</a>
              </td>
              <td>¥${unitPrice.toFixed(2)}</td>
              <td>
                <div class="table-cart-stepper">
                  <input class="form-input quantity-input" type="number" value="${quantity}" min="1" max="1000" title="商品数量" data-cart-id="${item.cartId}">
                </div>
              </td>
              <td>¥${subtotal.toFixed(2)}</td>
            `;
            tbody.appendChild(row);
          });
  
          totalElement.textContent = `¥${total.toFixed(2)}`;
          bindQuantityChangeEvents();
        })
        .catch(err => {
          console.error("获取产品信息时发生错误:", err);
        });
    });
  }
  export function updateCartQuantity(cartId, newQuantity) {
    authorizedFetch("http://localhost:8080/system/cart", {
      method: "PUT",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify({
        cartId: cartId,
        quantity: newQuantity
      })
    })
      .then(data => {
        if (data.code === 200) {
          console.log("更新成功:", data);
          // 可选：刷新购物车数据
          fetchCartWithProducts();
        } else {
          console.error("更新失败:", data.msg);
        }
      })
      .catch(err => console.error("请求错误:", err));
  }
  function bindQuantityChangeEvents() {
    const inputs = document.querySelectorAll(".quantity-input");
    inputs.forEach(input => {
      input.addEventListener("change", (event) => {
        const cartId = input.dataset.cartId;
        const newQuantity = parseInt(input.value, 10);
        if (cartId && newQuantity > 0) {
          updateCartQuantity(cartId, newQuantity);
        }
      });
    });
  }
   
  document.addEventListener("DOMContentLoaded", fetchCartWithProducts);

export function fetchAddressList() {
  return authorizedFetch("http://localhost:8080/system/user_address/list")
    .then(data => {
      if (data.code === 200) {
        console.log("地址列表:", data);
        return data.rows;
      } else {
        console.error("加载地址列表失败:", data.msg);
        return [];
      }
    })
    .catch(err => {
      console.error("请求错误:", err);
      return [];
    });
}

export function submitOrder(addressId) {
  return authorizedFetch("http://localhost:8080/system/order", {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify({
      addressId: addressId
    })
  })
    .then(data => {
      if (data.code === 200) {
        console.log("订单提交成功:", data);
        return data;
      } else {
        console.error("订单提交失败:", data.msg);
        throw new Error(data.msg);
      }
    })
    .catch(err => {
      console.error("请求错误:", err);
      throw err;
    });
}

document.addEventListener("DOMContentLoaded", () => {
  fetchCartWithProducts();
  
  // 加载地址列表
  fetchAddressList().then(addresses => {
    const addressSelect = document.getElementById("address-select");
    if (addressSelect) {
      addressSelect.innerHTML = '<option value="">请选择收货地址</option>';
      addresses.forEach(address => {
        const option = document.createElement("option");
        option.value = address.addressId;
        option.textContent = address.address;
        addressSelect.appendChild(option);
      });
    }
  });

  // 提交订单按钮点击事件
  const submitOrderBtn = document.getElementById("submit-order");
  if (submitOrderBtn) {
    submitOrderBtn.addEventListener("click", () => {
      const addressSelect = document.getElementById("address-select");
      if (!addressSelect || !addressSelect.value) {
        alert("请选择收货地址");
        return;
      }

      submitOrder(addressSelect.value)
        .then(() => {
          alert("订单提交成功！");
          window.location.href = "checkout.html";
        })
        .catch(error => {
          alert("订单提交失败：" + error.message);
        });
    });
  }
});