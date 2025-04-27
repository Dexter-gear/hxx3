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
import com.ruoyi.system.domain.Order;
import com.ruoyi.system.service.IOrderService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.framework.web.service.TokenService;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.utils.StringUtils;
import javax.servlet.http.HttpServletRequest;

/**
 * 订单Controller
 * 
 * @author hxx
 * @date 2025-04-26
 */
@RestController
@RequestMapping("/system/order")
public class OrderController extends BaseController
{
    @Autowired
    private IOrderService orderService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private HttpServletRequest request;

    /**
     * 查询订单列表
     */
    @PreAuthorize("@ss.hasPermi('system:order:list')")
    @GetMapping("/list")
    public TableDataInfo list(Order order)
    {
        // 从请求头中获取 token
        String token = request.getHeader("Authorization");
        // 解析 token 获取登录用户信息
        LoginUser loginUser = tokenService.getLoginUser(request);
        // 获取当前的用户名称
        Long userId = loginUser.getUserId();
        order.setUserId(userId);    
        startPage();
        List<Order> list = orderService.selectOrderList(order);
        return getDataTable(list);
    }

    /**
     * 导出订单列表
     */
    @PreAuthorize("@ss.hasPermi('system:order:export')")
    @Log(title = "订单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Order order)
    {
        List<Order> list = orderService.selectOrderList(order);
        ExcelUtil<Order> util = new ExcelUtil<Order>(Order.class);
        util.exportExcel(response, list, "订单数据");
    }

    /**
     * 获取订单详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:order:query')")
    @GetMapping(value = "/{orderId}")
    public AjaxResult getInfo(@PathVariable("orderId") Long orderId)
    {
        return success(orderService.selectOrderByOrderId(orderId));
    }

    /**
     * 新增订单
     */
    @PreAuthorize("@ss.hasPermi('system:order:add')")
    @Log(title = "订单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Order order)
    {
        // 从请求头中获取 token
        String token = request.getHeader("Authorization");
        // 解析 token 获取登录用户信息
        LoginUser loginUser = tokenService.getLoginUser(request);
        // 获取当前的用户名称
        Long userId = loginUser.getUserId();
        order.setUserId(userId);
        return toAjax(orderService.insertOrder(order));
    }

    /**
     * 修改订单
     */
    @PreAuthorize("@ss.hasPermi('system:order:edit')")
    @Log(title = "订单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Order order)
    {
        return toAjax(orderService.updateOrder(order));
    }

    /**
     * 删除订单
     */
    @PreAuthorize("@ss.hasPermi('system:order:remove')")
    @Log(title = "订单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{orderIds}")
    public AjaxResult remove(@PathVariable Long[] orderIds)
    {
        return toAjax(orderService.deleteOrderByOrderIds(orderIds));
    }
}
