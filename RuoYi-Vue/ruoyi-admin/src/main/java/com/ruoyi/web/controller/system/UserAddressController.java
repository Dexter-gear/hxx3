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
import com.ruoyi.system.domain.UserAddress;
import com.ruoyi.system.service.IUserAddressService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.framework.web.service.TokenService;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.utils.StringUtils;
import javax.servlet.http.HttpServletRequest;

import com.github.pagehelper.PageHelper;
import com.ruoyi.common.utils.PageUtils;
/**
 * 用户地址信息Controller
 * 
 * @author hxx
 * @date 2025-04-27
 */
@RestController
@RequestMapping("/system/user_address")
public class UserAddressController extends BaseController
{
    @Autowired
    private IUserAddressService userAddressService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private HttpServletRequest request;
    

    /**
     * 查询用户地址信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:user_address:list')")
    @GetMapping("/list")
    public TableDataInfo list(UserAddress userAddress)
    {
        // 从请求头中获取 token
        String token = request.getHeader("Authorization");
        // 解析 token 获取登录用户信息
        LoginUser loginUser = tokenService.getLoginUser(request);
        // 获取当前的用户名称
        Long userId = loginUser.getUserId();
        
        userAddress.setUserId(userId);
        // 设置每页大小为最大值
        PageUtils.startPage();
        // 手动设置分页参数
        PageHelper.startPage(1, 10000);
        List<UserAddress> list = userAddressService.selectUserAddressList(userAddress);
        return getDataTable(list);
    }

    /**
     * 导出用户地址信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:user_address:export')")
    @Log(title = "用户地址信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, UserAddress userAddress)
    {
        List<UserAddress> list = userAddressService.selectUserAddressList(userAddress);
        ExcelUtil<UserAddress> util = new ExcelUtil<UserAddress>(UserAddress.class);
        util.exportExcel(response, list, "用户地址信息数据");
    }

    /**
     * 获取用户地址信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:user_address:query')")
    @GetMapping(value = "/{addressId}")
    public AjaxResult getInfo(@PathVariable("addressId") Long addressId)
    {
        return success(userAddressService.selectUserAddressByAddressId(addressId));
    }

    /**
     * 新增用户地址信息
     */
    @PreAuthorize("@ss.hasPermi('system:user_address:add')")
    @Log(title = "用户地址信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody UserAddress userAddress)
    {
        // 从请求头中获取 token
        String token = request.getHeader("Authorization");
        // 解析 token 获取登录用户信息
        LoginUser loginUser = tokenService.getLoginUser(request);
        // 获取当前的用户名称
        Long userId = loginUser.getUserId();
        userAddress.setUserId(userId);
        return toAjax(userAddressService.insertUserAddress(userAddress));
    }

    /**
     * 修改用户地址信息
     */
    @PreAuthorize("@ss.hasPermi('system:user_address:edit')")
    @Log(title = "用户地址信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody UserAddress userAddress)
    {
        return toAjax(userAddressService.updateUserAddress(userAddress));
    }

    /**
     * 删除用户地址信息
     */
    @PreAuthorize("@ss.hasPermi('system:user_address:remove')")
    @Log(title = "用户地址信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{addressIds}")
    public AjaxResult remove(@PathVariable Long[] addressIds)
    {
        return toAjax(userAddressService.deleteUserAddressByAddressIds(addressIds));
    }
}
