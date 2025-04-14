package com.ruoyi.system.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
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
import com.ruoyi.system.domain.ViewProductReviews;
import com.ruoyi.system.service.IViewProductReviewsService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 评论详情Controller
 * 
 * @author hxx
 * @date 2025-04-13
 */
@RestController
@RequestMapping("/system/reviews")
public class ViewProductReviewsController extends BaseController
{
    @Autowired
    private IViewProductReviewsService viewProductReviewsService;

    /**
     * 查询评论详情列表
     */
    @PreAuthorize("@ss.hasPermi('system:reviews:list')")
    @GetMapping("/list")
    public TableDataInfo list(ViewProductReviews viewProductReviews)
    {
        startPage();
        List<ViewProductReviews> list = viewProductReviewsService.selectViewProductReviewsList(viewProductReviews);
        return getDataTable(list);
    }

    /**
     * 导出评论详情列表
     */
    @PreAuthorize("@ss.hasPermi('system:reviews:export')")
    @Log(title = "评论详情", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ViewProductReviews viewProductReviews)
    {
        List<ViewProductReviews> list = viewProductReviewsService.selectViewProductReviewsList(viewProductReviews);
        ExcelUtil<ViewProductReviews> util = new ExcelUtil<ViewProductReviews>(ViewProductReviews.class);
        util.exportExcel(response, list, "评论详情数据");
    }

    /**
     * 获取评论详情详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:reviews:query')")
    @GetMapping(value = "/{reviewId}")
    public AjaxResult getInfo(@PathVariable("reviewId") Long reviewId)
    {
        return success(viewProductReviewsService.selectViewProductReviewsByReviewId(reviewId));
    }

    /**
     * 新增评论详情
     */
    @PreAuthorize("@ss.hasPermi('system:reviews:add')")
    @Log(title = "评论详情", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ViewProductReviews viewProductReviews)
    {
        return toAjax(viewProductReviewsService.insertViewProductReviews(viewProductReviews));
    }

    /**
     * 修改评论详情
     */
    @PreAuthorize("@ss.hasPermi('system:reviews:edit')")
    @Log(title = "评论详情", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ViewProductReviews viewProductReviews)
    {
        return toAjax(viewProductReviewsService.updateViewProductReviews(viewProductReviews));
    }

    /**
     * 删除评论详情
     */
    @PreAuthorize("@ss.hasPermi('system:reviews:remove')")
    @Log(title = "评论详情", businessType = BusinessType.DELETE)
	@DeleteMapping("/{reviewIds}")
    public AjaxResult remove(@PathVariable Long[] reviewIds)
    {
        return toAjax(viewProductReviewsService.deleteViewProductReviewsByReviewIds(reviewIds));
    }
}
