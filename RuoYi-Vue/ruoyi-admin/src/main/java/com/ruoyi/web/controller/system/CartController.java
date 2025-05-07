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
import com.ruoyi.system.domain.Cart;
import com.ruoyi.system.service.ICartService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.framework.web.service.TokenService;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.utils.StringUtils;
import com.github.pagehelper.PageHelper;
import com.ruoyi.common.utils.PageUtils;

/**
 * 购物车Controller
 * 
 * @author hxx
 * @date 2025-04-13
 */
@RestController
@RequestMapping("/system/cart")
public class CartController extends BaseController
{
    @Autowired
    private ICartService cartService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private HttpServletRequest request;

    /**
     * 查询购物车列表
     */
    @PreAuthorize("@ss.hasPermi('system:cart:list')")
    @GetMapping("/list")
    public TableDataInfo list(Cart cart)
    {
        // 从请求头中获取 token
        String token = request.getHeader("Authorization");
        // 解析 token 获取登录用户信息
        LoginUser loginUser = tokenService.getLoginUser(request);
        // 获取当前的用户名称
        Long userId = loginUser.getUserId();
        
        cart.setUserId(userId);
        
        // 设置每页大小为最大值
        PageUtils.startPage();
        // 手动设置分页参数
        PageHelper.startPage(1, 10000);
        List<Cart> list = cartService.selectCartList(cart);
        return getDataTable(list);
    }

    /**
     * 导出购物车列表
     */
    @PreAuthorize("@ss.hasPermi('system:cart:export')")
    @Log(title = "购物车", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Cart cart)
    {
        List<Cart> list = cartService.selectCartList(cart);
        ExcelUtil<Cart> util = new ExcelUtil<Cart>(Cart.class);
        util.exportExcel(response, list, "购物车数据");
    }

    /**
     * 获取购物车详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:cart:query')")
    @GetMapping(value = "/{cartId}")
    public AjaxResult getInfo(@PathVariable("cartId") Long cartId)
    {
        return success(cartService.selectCartByCartId(cartId));
    }

    /**
     * 新增购物车
     */
    @PreAuthorize("@ss.hasPermi('system:cart:add')")
    @Log(title = "购物车", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Cart cart)
    {
        // 从请求头中获取 token
        String token = request.getHeader("Authorization");
        // 解析 token 获取登录用户信息
        LoginUser loginUser = tokenService.getLoginUser(request);
        // 获取当前的用户名称
        Long userId = loginUser.getUserId();
        cart.setUserId(userId);
        
        return toAjax(cartService.insertCart(cart));
    }

    /**
     * 修改购物车
     */
    @PreAuthorize("@ss.hasPermi('system:cart:edit')")
    @Log(title = "购物车", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Cart cart)
    {
        return toAjax(cartService.updateCart(cart));
    }

    /**
     * 删除购物车
     */
    @PreAuthorize("@ss.hasPermi('system:cart:remove')")
    @Log(title = "购物车", businessType = BusinessType.DELETE)
	@DeleteMapping("/{cartIds}")
    public AjaxResult remove(@PathVariable Long[] cartIds)
    {
        return toAjax(cartService.deleteCartByCartIds(cartIds));
    }
}
