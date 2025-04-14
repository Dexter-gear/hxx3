import { authorizedFetch } from './request.js';

document.addEventListener('DOMContentLoaded', () => {
  const productForm = document.getElementById('product-form');
  
  if (productForm) {
    productForm.addEventListener('submit', async (e) => {
      e.preventDefault();
      
      const formData = new FormData(productForm);
      const productData = {
        name: formData.get('name'),
        price: parseFloat(formData.get('price')),
        description: formData.get('description'),
        stock: parseInt(formData.get('stock')),
        image: formData.get('image')
      };
      
      try {
        const response = await authorizedFetch("http://localhost:8080/system/product", {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify(productData)
        });
        
        if (response.ok) {
          alert('商品添加成功！');
          productForm.reset();
        } else {
          const error = await response.json();
          alert(`添加失败：${error.message}`);
        }
      } catch (error) {
        console.error('提交商品时出错：', error);
        alert('提交失败，请稍后重试');
      }
    });
  }
});
