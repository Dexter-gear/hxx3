import { authorizedFetch } from './request.js';

new Vue({
  el: '#app',
  data() {
    const validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'));
      } else {
        if (this.pwdForm.confirmPassword !== '') {
          this.$refs.pwdForm.validateField('confirmPassword');
        }
        callback();
      }
    };
    const validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'));
      } else if (value !== this.pwdForm.newPassword) {
        callback(new Error('两次输入密码不一致!'));
      } else {
        callback();
      }
    };
    return {
      user: {},
      activeTab: 'userinfo',
      pwdForm: {
        oldPassword: '',
        newPassword: '',
        confirmPassword: ''
      },
      rules: {
        phonenumber: [
          { required: true, message: '请输入手机号码', trigger: 'blur' },
          { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号码', trigger: 'blur' }
        ],
        email: [
          { required: true, message: '请输入邮箱地址', trigger: 'blur' },
          { type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur' }
        ]
      },
      pwdRules: {
        oldPassword: [
          { required: true, message: '请输入旧密码', trigger: 'blur' }
        ],
        newPassword: [
          { required: true, validator: validatePass, trigger: 'blur' }
        ],
        confirmPassword: [
          { required: true, validator: validatePass2, trigger: 'blur' }
        ]
      }
    };
  },
  created() {
    this.getUserProfile();
  },
  methods: {
    // 获取用户信息
    async getUserProfile() {
      try {
        const response = await authorizedFetch("http://localhost:8080/system/user/profile", {
          method: 'GET'
        });
        
        if (response.code === 200) {
          this.user = response.data;
        } else {
          this.$message.error(response.msg || '获取用户信息失败');
        }
      } catch (error) {
        console.error('获取用户信息失败:', error);
        this.$message.error('获取用户信息失败');
      }
    },
    
    // 提交用户信息
    submitUserInfo() {
      this.$refs.userForm.validate(async (valid) => {
        if (valid) {
          try {
            const response = await authorizedFetch("http://localhost:8080/system/user/profile", {
              method: 'PUT',
              headers: {
                'Content-Type': 'application/json'
              },
              body: JSON.stringify(this.user)
            });
            
            if (response.code === 200) {
              this.$message.success('修改成功');
              this.getUserProfile();
            } else {
              this.$message.error(response.msg || '修改失败');
            }
          } catch (error) {
            console.error('修改用户信息失败:', error);
            this.$message.error('修改失败');
          }
        }
      });
    },
    
    // 提交密码修改
    submitPwdForm() {
      this.$refs.pwdForm.validate(async (valid) => {
        if (valid) {
          try {
            const response = await authorizedFetch("http://localhost:8080/system/user/profile/updatePwd", {
              method: 'PUT',
              headers: {
                'Content-Type': 'application/json'
              },
              body: JSON.stringify(this.pwdForm)
            });
            
            if (response.code === 200) {
              this.$message.success('密码修改成功');
              this.pwdForm = {
                oldPassword: '',
                newPassword: '',
                confirmPassword: ''
              };
            } else {
              this.$message.error(response.msg || '密码修改失败');
            }
          } catch (error) {
            console.error('修改密码失败:', error);
            this.$message.error('修改失败');
          }
        }
      });
    },
    
    // 触发头像上传
    triggerAvatarUpload() {
      document.getElementById('avatar-upload').click();
    },
    
    // 处理头像上传
    async handleAvatarUpload(event) {
      const file = event.target.files[0];
      if (!file) return;
      
      // 验证文件类型
      if (!['image/jpeg', 'image/png', 'image/jpg'].includes(file.type)) {
        this.$message.error('请上传jpg、png格式的图片！');
        return;
      }
      
      // 验证文件大小
      if (file.size > 5 * 1024 * 1024) {
        this.$message.error('图片大小不能超过5MB！');
        return;
      }
      
      const formData = new FormData();
      formData.append('file', file);
      
      try {
        const response = await authorizedFetch("http://localhost:8080/common/upload", {
          method: 'POST',
          body: formData
        });
        
        if (response.code === 200) {
          // 更新用户头像
          const updateResponse = await authorizedFetch("http://localhost:8080/system/user/profile/avatar", {
            method: 'PUT',
            headers: {
              'Content-Type': 'application/json'
            },
            body: JSON.stringify({
              avatar: response.fileName
            })
          });
          
          if (updateResponse.code === 200) {
            this.$message.success('头像更新成功');
            this.user.avatar = response.url;
          } else {
            this.$message.error(updateResponse.msg || '头像更新失败');
          }
        } else {
          this.$message.error(response.msg || '头像上传失败');
        }
      } catch (error) {
        console.error('上传头像失败:', error);
        this.$message.error('上传失败');
      }
    },
    
    // 退出登录
    handleLogout() {
      // 清除本地token
      localStorage.removeItem('token');
      // 跳转到登录页
      window.location.href = 'login.html';
    }
  },
  mounted() {
    // 监听头像上传
    document.getElementById('avatar-upload').addEventListener('change', this.handleAvatarUpload);
  }
});

  
  