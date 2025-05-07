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
import com.ruoyi.system.domain.SallerOrder;
import com.ruoyi.system.service.ISallerOrderService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.framework.web.service.TokenService;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.utils.StringUtils;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import com.github.pagehelper.PageHelper;
import com.ruoyi.common.utils.PageUtils;

/**
 * 销售员-订单关联Controller
 * 
 * @author hxx
 * @date 2025-04-27
 */
@RestController
@RequestMapping("/system/saller_order")
public class SallerOrderController extends BaseController
{
    @Autowired
    private ISallerOrderService sallerOrderService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private TokenService tokenService;
    /**
     * 查询销售员-订单关联列表
     */
    @PreAuthorize("@ss.hasPermi('system:saller_order:list')")
    @GetMapping("/list")
    public TableDataInfo list(SallerOrder sallerOrder)
    {
        LoginUser loginUser = tokenService.getLoginUser(request);
        Long userId = loginUser.getUser().getUserId();
        sallerOrder.setUserId(userId);  
        // 设置每页大小为最大值
        PageUtils.startPage();
        // 手动设置分页参数
        PageHelper.startPage(1, 10000);
        List<SallerOrder> list = sallerOrderService.selectSallerOrderList(sallerOrder);
        return getDataTable(list);
    }

    /**
     * 导出销售员-订单关联列表
     */
    @PreAuthorize("@ss.hasPermi('system:saller_order:export')")
    @Log(title = "销售员-订单关联", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SallerOrder sallerOrder)
    {
        List<SallerOrder> list = sallerOrderService.selectSallerOrderList(sallerOrder);
        ExcelUtil<SallerOrder> util = new ExcelUtil<SallerOrder>(SallerOrder.class);
        util.exportExcel(response, list, "销售员-订单关联数据");
    }

    /**
     * 获取销售员-订单关联详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:saller_order:query')")
    @GetMapping(value = "/{userId}")
    public AjaxResult getInfo(@PathVariable("userId") Long userId)
    {
        return success(sallerOrderService.selectSallerOrderByUserId(userId));
    }

    /**
     * 新增销售员-订单关联
     */
    @PreAuthorize("@ss.hasPermi('system:saller_order:add')")
    @Log(title = "销售员-订单关联", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SallerOrder sallerOrder)
    {
        return toAjax(sallerOrderService.insertSallerOrder(sallerOrder));
    }

    /**
     * 修改销售员-订单关联
     */
    @PreAuthorize("@ss.hasPermi('system:saller_order:edit')")
    @Log(title = "销售员-订单关联", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SallerOrder sallerOrder)
    {
        return toAjax(sallerOrderService.updateSallerOrder(sallerOrder));
    }

    /**
     * 删除销售员-订单关联
     */
    @PreAuthorize("@ss.hasPermi('system:saller_order:remove')")
    @Log(title = "销售员-订单关联", businessType = BusinessType.DELETE)
	@DeleteMapping("/{userIds}")
    public AjaxResult remove(@PathVariable Long[] userIds)
    {
        return toAjax(sallerOrderService.deleteSallerOrderByUserIds(userIds));
    }
}
