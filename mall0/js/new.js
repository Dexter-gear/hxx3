import { authorizedFetch } from './request.js';

console.log('new.js 开始加载');

document.addEventListener('DOMContentLoaded', async () => {
  console.log('DOM加载完成');
  
  const productForm = document.getElementById('product-form');
  const uploadButton = document.getElementById('uploadButton');
  const removeButton = document.getElementById('removeButton');
  const fileInput = document.getElementById('product-image');
  const previewImg = document.getElementById('previewImg');
  const imagePreview = document.getElementById('imagePreview');
  const categorySelect = document.getElementById('product-category');
  
  // 获取商品类别列表
  try {
    const response = await authorizedFetch("http://localhost:8080/system/category/list", {
      method: 'GET'
    });
    
    if (response.code === 200) {
      const categories = response.rows;
      categories.forEach(category => {
        const option = document.createElement('option');
        option.value = category.categoryId;
        option.textContent = category.name;
        categorySelect.appendChild(option);
      });
    } else {
      console.error('获取商品类别失败:', response.msg);
    }
  } catch (error) {
    console.error('获取商品类别时出错:', error);
  }
  
  // 图片上传相关变量
  let currentFile = null;
  let imageUrl = null;
  const maxFileSize = 5 * 1024 * 1024; // 5MB
  const allowedTypes = ['image/jpeg', 'image/png', 'image/jpg'];
  
  // 选择图片按钮点击事件
  if (uploadButton) {
    console.log('绑定上传按钮点击事件');
    uploadButton.addEventListener('click', (e) => {
      console.log('上传按钮被点击');
      e.preventDefault();
      if (fileInput) {
        console.log('触发文件选择框');
        fileInput.click();
      } else {
        console.error('文件输入框不存在');
      }
    });
  } else {
    console.error('上传按钮不存在');
  }
  
  // 文件选择事件
  if (fileInput) {
    console.log('绑定文件选择事件');
    fileInput.addEventListener('change', async (e) => {
      console.log('文件选择事件触发');
      const file = e.target.files[0];
      if (file) {
        console.log('选择的文件:', file);
        
        // 验证文件类型
        if (!allowedTypes.includes(file.type)) {
          alert('请上传jpg、png、jpeg格式的图片！');
          return;
        }
        
        // 验证文件大小
        if (file.size > maxFileSize) {
          alert('图片大小不能超过5MB！');
          return;
        }
        
        // 预览图片
        try {
          console.log('开始预览图片');
          const reader = new FileReader();
          
          reader.onload = function(event) {
            console.log('FileReader onload触发');
            if (previewImg) {
              console.log('设置预览图片');
              previewImg.src = event.target.result;
              previewImg.style.display = 'block';
              if (removeButton) {
                removeButton.style.display = 'inline-block';
              }
            } else {
              console.error('预览图片元素不存在');
            }
          };
          
          reader.onerror = function(error) {
            console.error('FileReader错误:', error);
            alert('图片预览失败，请重试');
          };
          
          reader.readAsDataURL(file);
          console.log('开始读取文件');
        } catch (error) {
          console.error('预览图片时出错:', error);
          alert('图片预览失败，请重试');
        }
        
        currentFile = file;
        
        // 上传图片
        console.log('开始上传图片');
        const formData = new FormData();
        formData.append('file', file);
        
        const response = await authorizedFetch("http://localhost:8080/common/upload", {
          method: 'POST',
          body: formData
        });
        
        console.log('上传响应:', response);
        
        if (response.code === 200) {
          imageUrl = response.url;
          console.log('图片上传成功，URL:', imageUrl);
        } else {
          alert('图片上传失败：' + response.msg);
          currentFile = null;
          if (previewImg) {
            previewImg.src = '';
            previewImg.style.display = 'none';
          }
          if (removeButton) {
            removeButton.style.display = 'none';
          }
        }
      }
    });
  } else {
    console.error('文件输入框不存在');
  }
  
  // 删除图片按钮点击事件
  if (removeButton) {
    console.log('绑定删除按钮点击事件');
    removeButton.addEventListener('click', () => {
      console.log('删除按钮被点击');
      if (fileInput) {
        fileInput.value = '';
      }
      if (previewImg) {
        previewImg.src = '';
        previewImg.style.display = 'none';
      }
      if (removeButton) {
        removeButton.style.display = 'none';
      }
      currentFile = null;
      imageUrl = null;
    });
  } else {
    console.error('删除按钮不存在');
  }
  
  if (productForm) {
    console.log('绑定表单提交事件');
    productForm.addEventListener('submit', async (e) => {
      console.log('表单提交事件触发');
      e.preventDefault();
      
      if (!imageUrl) {
        alert('请先上传商品图片！');
        return;
      }
      
      const productData = {
        categoryId: parseInt(document.getElementById('product-category').value),
        name: document.getElementById('product-name').value,
        price: parseFloat(document.getElementById('product-price').value),
        description: document.getElementById('product-description').value,
        qualityLevel: parseInt(document.getElementById('product-quality').value),
        stock: parseInt(document.getElementById('product-stock').value),
        pStatus: parseInt(document.getElementById('product-status').value),
        imageUrl: imageUrl
      };
      
      try {
        console.log('开始提交商品数据');
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
          if (previewImg) {
            previewImg.src = '';
            previewImg.style.display = 'none';
          }
          if (removeButton) {
            removeButton.style.display = 'none';
          }
          currentFile = null;
          imageUrl = null;
        } else {
          const error = await response.json();
          alert(`添加失败：${error.message}`);
        }
      } catch (error) {
        console.error('提交商品时出错：', error);
        alert('提交失败，请稍后重试');
      }
    });
  } else {
    console.error('表单不存在');
  }
});
