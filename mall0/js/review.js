import { authorizedFetch } from './request.js';
// 获取 product_id
function getProductIdFromURL() {
    const params = new URLSearchParams(window.location.search);
    return params.get("product_id");
  }
  
  // 渲染评论到页面
  function renderReviews(reviews) {
    const container = document.getElementById("review");
    container.innerHTML = ""; // 清空旧内容
  
    reviews.forEach(review => {
      const html = `
        <div class="box-comment">
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
            </div>
          </div>
        </div>
      `;
      container.insertAdjacentHTML("beforeend", html);
    });
  }
  
// 获取评论数据（前端筛选）
function fetchReviews() {
    const id = getProductIdFromURL();
    if (!id) return;
  
    authorizedFetch("http://localhost:8080/system/reviews/list")
      .then(data => {
        if (data.code === 200) {
          // 前端根据 productId 过滤评论
          const filteredReviews = data.rows.filter(review => review.productId == id);
          renderReviews(filteredReviews);
        } else {
          console.error("加载评论失败:", data.msg);
        }
      })
      .catch(err => console.error("请求错误:", err));
  }
  
  document.addEventListener("DOMContentLoaded", function () {
    const form = document.querySelector(".rd-form");
    const textarea = document.getElementById("contact-message-2");
  
    form.addEventListener("submit", function (e) {
      e.preventDefault(); // 阻止默认提交行为
  
      const comment = textarea.value.trim();
      const productId = getProductIdFromURL();
  
      if (!comment) {
        alert("评论内容不能为空！");
        return;
      }
  
      if (!productId) {
        console.error("无法获取 product_id！");
        return;
      }
  
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
          if (data.code === 200) {
            textarea.value = "";
            fetchReviews(); // 重新加载评论
          } else {
            alert("提交失败：" + data.msg);
          }
        })
        .catch((error) => {
          console.error("请求错误:", error);
          alert("提交失败，请稍后再试！");
        });
    });
  });
  
  // 页面加载后执行
  document.addEventListener("DOMContentLoaded", fetchReviews);
  