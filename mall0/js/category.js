import { authorizedFetch } from './request.js';

// 获取商品列表
async function fetchProductList(categoryId = null) {
  try {
    const url = categoryId 
      ? `http://localhost:8080/system/product/list?categoryId=${categoryId}`
      : 'http://localhost:8080/system/product/list';
      
    const response = await authorizedFetch(url);
    if (response.code === 200) {
      return response.rows;
    } else {
      throw new Error(response.msg || '获取商品列表失败');
    }
  } catch (error) {
    console.error('获取商品列表失败:', error);
    throw error;
  }
}

// 获取商品分类列表
async function fetchCategoryList() {
  try {
    const response = await authorizedFetch('http://localhost:8080/system/category/list');
    if (response.code === 200) {
      return response.rows;
    } else {
      throw new Error(response.msg || '获取分类列表失败');
    }
  } catch (error) {
    console.error('获取分类列表失败:', error);
    throw error;
  }
}

// 统计每个分类的商品数量
function countProductsByCategory(products, categories) {
  const categoryCount = {};
  
  // 初始化所有分类的计数为0
  categories.forEach(category => {
    categoryCount[category.categoryId] = 0;
  });
  
  // 根据当前页面路径决定是否包含下架商品
  const currentPath = window.location.pathname;
  const includeOffShelf = currentPath.includes('grid-shop2.html');
  
  // 统计每个分类的商品数量
  products.forEach(product => {
    if (product.categoryId && 
        categoryCount[product.categoryId] !== undefined && 
        (includeOffShelf || product.pStatus === 1)) { // 如果是shop2页面或商品已上架
      categoryCount[product.categoryId]++;
    }
  });
  
  return categoryCount;
}

// 渲染商品列表
function renderProductList(products) {
  const productListContainer = document.getElementById('product-list');
  if (!productListContainer) {
    console.error('未找到商品列表容器元素');
    return;
  }

  // 清空现有内容
  productListContainer.innerHTML = '';

  // 过滤出上架状态的商品
  const availableProducts = products.filter(product => product.pStatus === 1);

  if (availableProducts.length === 0) {
    productListContainer.innerHTML = '<p class="no-products">暂无商品</p>';
    return;
  }

  // 创建商品列表HTML
  const productListHtml = `
    <div class="products-container">
      <div class="product-grid">
        ${availableProducts.map(product => `
          <div class="col-sm-6 col-md-4 col-lg-6 col-xl-4">
            <article class="product">
              <div class="product-body">
                <div class="product-figure">
                  <img src="${product.imageUrl || 'images/default.png'}" alt="${product.name}" width="220" height="160"/>
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
        `).join('')}
      </div>
    </div>
  `;

  productListContainer.innerHTML = productListHtml;
}

// 渲染分类列表
function renderCategoryList(categories, categoryCount) {
  const categoryContainer = document.getElementById('product-category');
  if (!categoryContainer) {
    console.error('未找到商品分类容器元素');
    return;
  }

  // 清空现有内容
  categoryContainer.innerHTML = '';

  // 创建分类列表
  const categoryList = document.createElement('ul');
  categoryList.className = 'list-shop-filter';

  // 计算总商品数
  const totalProducts = Object.values(categoryCount).reduce((sum, count) => sum + count, 0);

  // 添加"全部"选项
  const allItem = document.createElement('li');
  allItem.innerHTML = `
    <label class="checkbox-inline">
      <input name="category-filter" value="all" type="checkbox" checked>全部商品
    </label>
    <span class="list-shop-filter-number">(${totalProducts})</span>
  `;
  categoryList.appendChild(allItem);

  // 添加各个分类
  categories.forEach(category => {
    const categoryItem = document.createElement('li');
    categoryItem.innerHTML = `
      <label class="checkbox-inline">
        <input name="category-filter" value="${category.categoryId}" type="checkbox">
        ${category.name}
      </label>
      <span class="list-shop-filter-number">(${categoryCount[category.categoryId] || 0})</span>
    `;
    categoryList.appendChild(categoryItem);
  });

  // 添加分类列表到容器
  categoryContainer.appendChild(categoryList);

  // 添加分类筛选事件监听
  addCategoryFilterListeners();
}

// 添加分类筛选事件监听
function addCategoryFilterListeners() {
  const checkboxes = document.querySelectorAll('input[name="category-filter"]');
  checkboxes.forEach(checkbox => {
    checkbox.addEventListener('change', async function() {
      let selectedCategoryId = null;
      
      if (this.value === 'all' && this.checked) {
        // 如果选中"全部"，取消其他选项
        checkboxes.forEach(cb => {
          if (cb.value !== 'all') {
            cb.checked = false;
          }
        });
      } else if (this.checked) {
        // 如果选中具体分类，取消"全部"选项
        const allCheckbox = document.querySelector('input[name="category-filter"][value="all"]');
        if (allCheckbox) {
          allCheckbox.checked = false;
        }
        selectedCategoryId = this.value;
      }

      // 清空商品列表
      const productList = document.getElementById('product-list');
      if (productList) {
        productList.innerHTML = '';
      }

      // 根据当前页面路径选择渲染函数
      try {
        const currentPath = window.location.pathname;
        let renderModule;
        
        if (currentPath.includes('grid-shop2.html')) {
          renderModule = await import('./shop2.js');
        } else {
          renderModule = await import('./shop.js');
        }
        
        const products = await fetchProductList(selectedCategoryId);
        renderModule.renderProducts(products);
      } catch (error) {
        console.error('更新商品列表失败:', error);
        if (productList) {
          productList.innerHTML = '<p class="error-message">加载商品列表失败，请刷新页面重试</p>';
        }
      }
    });
  });
}

// 初始化
document.addEventListener('DOMContentLoaded', async () => {
  try {
    // 并行获取商品列表和分类列表
    const [products, categories] = await Promise.all([
      fetchProductList(),
      fetchCategoryList()
    ]);
    
    // 统计每个分类的商品数量
    const categoryCount = countProductsByCategory(products, categories);
    
    // 渲染分类列表
    renderCategoryList(categories, categoryCount);
  } catch (error) {
    console.error('初始化分类列表失败:', error);
    const categoryContainer = document.getElementById('product-category');
    if (categoryContainer) {
      categoryContainer.innerHTML = `
        <div class="alert alert-danger" role="alert">
          加载分类列表失败，请刷新页面重试
        </div>
      `;
    }
  }
});

document.addEventListener('categoryChanged', (event) => {
  const categoryId = event.detail.categoryId;
  console.log('分类变更:', categoryId);
  // 根据 categoryId 更新商品列表
  if (categoryId === null) {
    // 显示所有商品
  } else {
    // 显示指定分类的商品
  }
});

