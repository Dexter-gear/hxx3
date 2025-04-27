import { authorizedFetch } from './request.js';

document.addEventListener('DOMContentLoaded', () => {
  // 获取DOM元素
  const addressList = document.getElementById('address-list');
  const addAddressBtn = document.getElementById('add-address-btn');
  const addressDialog = document.getElementById('address-dialog');
  const addressForm = document.getElementById('address-form');
  const addressInput = document.getElementById('address-input');
  const submitBtn = document.getElementById('submit-address-btn');
  const cancelBtn = document.getElementById('cancel-address-btn');
  const dialogTitle = document.getElementById('dialog-title');
  
  let currentAddressId = null;
  let addressData = [];
  
  // 获取地址列表
  async function getAddressList() {
    try {
      const response = await authorizedFetch("http://localhost:8080/system/user_address/list", {
        method: 'GET'
      });
      
      if (response.code === 200) {
        addressData = response.rows;
        renderAddressList(addressData);
      } else {
        showMessage('获取地址列表失败', 'error');
      }
    } catch (error) {
      console.error('获取地址列表失败:', error);
      showMessage('获取地址列表失败', 'error');
    }
  }
  
  // 渲染地址列表
  function renderAddressList(addresses) {
    addressList.innerHTML = addresses.map(address => `
      <div class="address-item" data-id="${address.addressId}">
        <div class="address-content">
          <div class="address-info">
            <p class="address-text">${address.address}</p>
          </div>
          <div class="address-actions">
            <button class="button-text edit-btn" onclick="handleEditAddress(${address.addressId})">
              <i class="icon mdi mdi-pencil"></i> 编辑
            </button>
            <button class="button-text delete delete-btn" onclick="handleDeleteAddress(${address.addressId})">
              <i class="icon mdi mdi-delete"></i> 删除
            </button>
          </div>
        </div>
      </div>
    `).join('');
  }
  
  // 显示消息提示
  function showMessage(message, type = 'info') {
    const messageDiv = document.createElement('div');
    messageDiv.className = `message message-${type}`;
    messageDiv.textContent = message;
    document.body.appendChild(messageDiv);
    
    setTimeout(() => {
      messageDiv.remove();
    }, 3000);
  }
  
  // 显示确认对话框
  function showConfirm(message, callback) {
    if (confirm(message)) {
      callback();
    }
  }
  
  // 显示地址编辑对话框
  function showAddressDialog(title, address = null) {
    dialogTitle.textContent = title;
    if (address) {
      addressInput.value = address.address;
      currentAddressId = address.addressId;
    } else {
      addressInput.value = '';
      currentAddressId = null;
    }
    addressDialog.style.display = 'block';
  }
  
  // 隐藏地址编辑对话框
  function hideAddressDialog() {
    addressDialog.style.display = 'none';
    addressForm.reset();
    currentAddressId = null;
  }
  
  // 新增地址
  function handleAddAddress() {
    showAddressDialog('新增地址');
  }
  
  // 编辑地址
  function handleEditAddress(addressId) {
    const address = addressData.find(addr => addr.addressId === addressId);
    if (address) {
      showAddressDialog('编辑地址', address);
    }
  }
  
  // 删除地址
  async function handleDeleteAddress(addressId) {
    showConfirm('确认删除该地址吗？', async () => {
      try {
        const response = await authorizedFetch(`http://localhost:8080/system/user_address/${addressId}`, {
          method: 'DELETE'
        });
        
        if (response.code === 200) {
          showMessage('删除成功', 'success');
          getAddressList();
        } else {
          showMessage(response.msg || '删除失败', 'error');
        }
      } catch (error) {
        console.error('删除地址失败:', error);
        showMessage('删除失败', 'error');
      }
    });
  }
  
  // 提交地址表单
  async function submitAddressForm(e) {
    e.preventDefault();
    
    const address = addressInput.value.trim();
    if (!address) {
      showMessage('请输入详细地址', 'error');
      return;
    }
    
    const formData = {
      addressId: currentAddressId,
      address: address
    };
    
    try {
      const url = 'http://localhost:8080/system/user_address';
      const method = currentAddressId ? 'PUT' : 'POST';
      
      const response = await authorizedFetch(url, {
        method: method,
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(formData)
      });
      
      if (response.code === 200) {
        showMessage(currentAddressId ? '修改成功' : '新增成功', 'success');
        hideAddressDialog();
        getAddressList();
      } else {
        showMessage(response.msg || (currentAddressId ? '修改失败' : '新增失败'), 'error');
      }
    } catch (error) {
      console.error('提交地址失败:', error);
      showMessage('操作失败', 'error');
    }
  }
  
  // 绑定事件
  addAddressBtn.addEventListener('click', handleAddAddress);
  submitBtn.addEventListener('click', submitAddressForm);
  cancelBtn.addEventListener('click', hideAddressDialog);
  
  // 将函数暴露到全局作用域
  window.handleEditAddress = handleEditAddress;
  window.handleDeleteAddress = handleDeleteAddress;
  
  // 初始化
  getAddressList();
}); 