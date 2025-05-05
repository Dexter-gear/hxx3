import { authorizedFetch } from './request.js';

// 获取 product_id
function getProductIdFromURL() {
  const params = new URLSearchParams(window.location.search);
  return params.get("product_id");
}

// 检查是否是商家
async function checkIsMerchant() {
  try {
    console.log('Debug: 发送商家权限检查请求...');
    const response = await authorizedFetch("http://localhost:8080/system/cart/list");
    console.log('Debug: 商家权限检查响应:', response);
    // 修改判断逻辑：如果能访问购物车接口，说明是普通用户
    return response.code !== 200;
  } catch (error) {
    console.error('Debug: 商家权限检查错误:', error);
    // 如果请求失败（可能是权限问题），认为是商家
    return true;
  }
}

// 获取评论回复
async function fetchReplies(reviewId) {
  try {
    const response = await authorizedFetch(`http://localhost:8080/system/reply/list?reviewId=${reviewId}`);
    if (response.code === 200) {
      return response.rows || [];
    }
    return [];
  } catch (error) {
    console.error("获取评论回复失败:", error);
    return [];
  }
}

// 渲染评论到页面
async function renderReviews(reviews, isMerchant) {
  const container = document.getElementById("review");
  container.innerHTML = ""; // 清空旧内容

  for (const review of reviews) {
    const replies = await fetchReplies(review.reviewId);
    const repliesHtml = replies.map(reply => `
      <div class="box-reply">
        <div class="unit flex-column flex-sm-row unit-spacing-md">
          <div class="unit-body">
            <div class="group-sm group-justify">
              <div>
                <div class="group-xs group-middle">
                  <h6 class="box-comment-author"><a href="#">商家回复</a></h6>
                  <div class="box-comment-time">${reply.createdAt || ''}</div>
                </div>
              </div>
            </div>
            <p class="box-comment-text">${reply.comment}</p>
          </div>
        </div>
      </div>
    `).join('');

    const replyButtonHtml = isMerchant ? `
      <div class="reply-actions">
        <button class="button button-primary button-sm" onclick="handleReply(${review.reviewId})">回复</button>
      </div>
    ` : '';

    const html = `
      <div class="box-comment" data-review-id="${review.reviewId}">
        <div class="unit flex-column flex-sm-row unit-spacing-md">
          <div class="unit-left">
            <a class="box-comment-figure" href="#"><img src="images/user-1-119x119.jpg" alt="" width="119" height="119"/></a>
          </div>
          <div class="unit-body">
            <div class="group-sm group-justify">
              <div>
                <div class="group-xs group-middle">
                  <h5 class="box-comment-author"><a href="#">${review.nickName}</a></h5>
                </div>
              </div>
            </div>
            <p class="box-comment-text">${review.comment}</p>
            ${replyButtonHtml}
          </div>
        </div>
        <div class="replies-container">
          ${repliesHtml}
        </div>
      </div>
    `;
    container.insertAdjacentHTML("beforeend", html);
  }
}

// 处理回复提交
async function handleReply(reviewId) {
  const replyContent = prompt("请输入回复内容：");
  if (!replyContent) return;

  try {
    const response = await authorizedFetch("http://localhost:8080/system/reply", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        reviewId: reviewId,
        comment: replyContent
      }),
    });

    if (response.code === 200) {
      fetchReviews(); // 重新加载评论和回复
    } else {
      alert("回复失败：" + response.msg);
    }
  } catch (error) {
    console.error("提交回复失败:", error);
    alert("回复失败，请稍后再试！");
  }
}

// 获取评论数据（前端筛选）
async function fetchReviews() {
  const id = getProductIdFromURL();
  if (!id) {
    console.log('Debug: 未获取到 product_id');
    return;
  }

  try {
    console.log('Debug: 开始检查商家权限...');
    const isMerchant = await checkIsMerchant();
    console.log('Debug: 商家权限检查结果:', isMerchant);
    
    console.log('Debug: 开始获取评论列表...');
    const reviewsResponse = await authorizedFetch("http://localhost:8080/system/reviews/list");
    console.log('Debug: 评论列表响应:', reviewsResponse);

    if (reviewsResponse.code === 200) {
      const filteredReviews = reviewsResponse.rows.filter(review => review.productId == id);
      console.log('Debug: 过滤后的评论数量:', filteredReviews.length);
      
      await renderReviews(filteredReviews, isMerchant);
      
      // 获取评论区容器
      console.log('Debug: 开始查找评论区容器...');
      const commentSection = document.querySelector(".comment-section");
      console.log('Debug: 评论区容器元素:', commentSection);

      if (!commentSection) {
        console.error("Debug: 错误 - 找不到评论区容器 (.comment-section)");
        console.log("Debug: 当前页面所有class包含'comment'的元素:", document.querySelectorAll("[class*='comment']"));
        return;
      }
      
      // 清空现有内容
      console.log('Debug: 清空评论区容器内容');
      commentSection.innerHTML = '';
      
      if (isMerchant) {
        console.log('Debug: 商家身份，不创建评论表单');
      } else {
        console.log('Debug: 用户身份，开始创建评论表单...');
        // 如果是用户，创建评论输入区域
        const commentFormHtml = `
          <div class="comment-form-container">
            <h4>创建新评论</h4>
            <form class="rd-form">
              <div class="row row-20 row-md-30">
                <div class="col-lg-12">
                  <div class="form-wrap">
                    <textarea class="form-input" id="contact-message-2" placeholder="请输入您的评论"></textarea>
                  </div>
                </div>
                <div class="col-12">
                  <button class="button button-primary" type="submit">提交评论</button>
                </div>
              </div>
            </form>
          </div>
        `;
        
        // 添加评论表单到容器
        console.log('Debug: 插入评论表单HTML');
        commentSection.insertAdjacentHTML('afterbegin', commentFormHtml);
        
        // 为新创建的表单添加提交事件监听器
        const newForm = commentSection.querySelector(".rd-form");
        const newTextarea = commentSection.querySelector("#contact-message-2");
        console.log('Debug: 新创建的表单元素:', newForm);
        console.log('Debug: 新创建的文本框元素:', newTextarea);
        
        if (!newForm || !newTextarea) {
          console.error('Debug: 错误 - 表单元素创建失败');
          return;
        }

        console.log('Debug: 添加表单提交事件监听器');
        newForm.addEventListener("submit", function (e) {
          console.log('Debug: 表单提交事件触发');
          e.preventDefault();
          
          const comment = newTextarea.value.trim();
          console.log('Debug: 评论内容:', comment);
          const productId = getProductIdFromURL();
          console.log('Debug: 商品ID:', productId);
          
          if (!comment) {
            console.log('Debug: 评论内容为空');
            alert("评论内容不能为空！");
            return;
          }
          
          if (!productId) {
            console.error('Debug: 无法获取商品ID');
            return;
          }
          
          console.log('Debug: 开始提交评论...');
          authorizedFetch("http://localhost:8080/system/review", {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
            },
            body: JSON.stringify({
              comment: comment,
              productId: productId
            }),
          })
            .then((data) => {
              console.log('Debug: 评论提交响应:', data);
              if (data.code === 200) {
                newTextarea.value = "";
                console.log('Debug: 评论提交成功，重新加载评论');
                fetchReviews(); // 重新加载评论
              } else {
                console.error('Debug: 评论提交失败:', data.msg);
                alert("提交失败：" + data.msg);
              }
            })
            .catch((error) => {
              console.error('Debug: 评论提交请求错误:', error);
              alert("提交失败，请稍后再试！");
            });
        });
      }
    } else {
      console.error("Debug: 加载评论失败:", reviewsResponse.msg);
    }
  } catch (err) {
    console.error("Debug: 请求错误:", err);
  }
}

// 添加全局函数以供 onclick 调用
window.handleReply = handleReply;

// 页面加载后执行
document.addEventListener("DOMContentLoaded", fetchReviews);

// 添加样式
const style = document.createElement('style');
style.textContent = `
  .box-reply {
    margin-left: 40px;
    margin-top: 10px;
    padding: 10px;
    background-color: #f8f9fa;
    border-radius: 4px;
  }
  .reply-actions {
    margin-top: 10px;
  }
  .replies-container {
    margin-top: 10px;
  }
  .box-comment-time {
    color: #999;
    font-size: 12px;
    margin-left: 10px;
  }
  .group-xs {
    display: flex;
    align-items: center;
  }
  .comment-form-container {
    margin-bottom: 30px;
  }
  .comment-form-container h4 {
    margin-bottom: 20px;
  }
`;
document.head.appendChild(style);
  