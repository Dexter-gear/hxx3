package com.ruoyi.system.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.Review;
import com.ruoyi.system.service.IReviewService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.framework.web.service.TokenService;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.utils.StringUtils;

import com.github.pagehelper.PageHelper;
import com.ruoyi.common.utils.PageUtils;
/**
 * 评论Controller
 * 
 * @author hxx
 * @date 2025-04-13
 */
@RestController
@RequestMapping("/system/review")
public class ReviewController extends BaseController
{
    @Autowired
    private IReviewService reviewService;

    /**
     * 查询评论列表
     */
    @PreAuthorize("@ss.hasPermi('system:review:list')")
    @GetMapping("/list")
    public TableDataInfo list(Review review)
    {
        // 设置每页大小为最大值
        PageUtils.startPage();
        // 手动设置分页参数
        PageHelper.startPage(1, 10000);
        List<Review> list = reviewService.selectReviewList(review);
        return getDataTable(list);
    }

    /**
     * 导出评论列表
     */
    @PreAuthorize("@ss.hasPermi('system:review:export')")
    @Log(title = "评论", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Review review)
    {
        List<Review> list = reviewService.selectReviewList(review);
        ExcelUtil<Review> util = new ExcelUtil<Review>(Review.class);
        util.exportExcel(response, list, "评论数据");
    }

    /**
     * 获取评论详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:review:query')")
    @GetMapping(value = "/{reviewId}")
    public AjaxResult getInfo(@PathVariable("reviewId") Long reviewId)
    {
        return success(reviewService.selectReviewByReviewId(reviewId));
    }

           // 获取当前登录用户
           @Autowired
private TokenService tokenService;

@Autowired
private HttpServletRequest request;	
    /**
     * 新增评论
     */
    @PreAuthorize("@ss.hasPermi('system:review:add')")
    @Log(title = "评论", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Review review)
    {
                // 从请求头中获取 token
        String token = request.getHeader("Authorization");
        
        if (StringUtils.isEmpty(token)) {
            return AjaxResult.error("缺少 Authorization 头信息");
        }
        // 解析 token 获取登录用户信息
        LoginUser loginUser = tokenService.getLoginUser(request);
// 获取当前的用户名称
Long userId = loginUser.getUserId();
    // 设置评论的用户信息
    review.setUserId(userId);
        return toAjax(reviewService.insertReview(review));
    }

    /**
     * 修改评论
     */
    @PreAuthorize("@ss.hasPermi('system:review:edit')")
    @Log(title = "评论", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Review review)
    {
        return toAjax(reviewService.updateReview(review));
    }

    /**
     * 删除评论
     */
    @PreAuthorize("@ss.hasPermi('system:review:remove')")
    @Log(title = "评论", businessType = BusinessType.DELETE)
	@DeleteMapping("/{reviewIds}")
    public AjaxResult remove(@PathVariable Long[] reviewIds)
    {
        return toAjax(reviewService.deleteReviewByReviewIds(reviewIds));
    }
}
