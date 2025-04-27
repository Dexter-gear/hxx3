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
import com.ruoyi.system.domain.Address;
import com.ruoyi.system.service.IAddressService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.framework.web.service.TokenService;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.utils.StringUtils;
import javax.servlet.http.HttpServletRequest;
/**
 * 订单地址信息Controller
 * 
 * @author hxx
 * @date 2025-04-27
 */
@RestController
@RequestMapping("/system/address")
public class AddressController extends BaseController
{
    @Autowired
    private IAddressService addressService;

    /**
     * 查询订单地址信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:address:list')")
    @GetMapping("/list")
    public TableDataInfo list(Address address)
    {
        startPage();
        List<Address> list = addressService.selectAddressList(address);
        return getDataTable(list);
    }

    /**
     * 导出订单地址信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:address:export')")
    @Log(title = "订单地址信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Address address)
    {
        List<Address> list = addressService.selectAddressList(address);
        ExcelUtil<Address> util = new ExcelUtil<Address>(Address.class);
        util.exportExcel(response, list, "订单地址信息数据");
    }

    /**
     * 获取订单地址信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:address:query')")
    @GetMapping(value = "/{addressId}")
    public AjaxResult getInfo(@PathVariable("addressId") Long addressId)
    {
        return success(addressService.selectAddressByAddressId(addressId));
    }

    /**
     * 新增订单地址信息
     */
    @PreAuthorize("@ss.hasPermi('system:address:add')")
    @Log(title = "订单地址信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Address address)
    {
        return toAjax(addressService.insertAddress(address));
    }

    /**
     * 修改订单地址信息
     */
    @PreAuthorize("@ss.hasPermi('system:address:edit')")
    @Log(title = "订单地址信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Address address)
    {
        return toAjax(addressService.updateAddress(address));
    }

    /**
     * 删除订单地址信息
     */
    @PreAuthorize("@ss.hasPermi('system:address:remove')")
    @Log(title = "订单地址信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{addressIds}")
    public AjaxResult remove(@PathVariable Long[] addressIds)
    {
        return toAjax(addressService.deleteAddressByAddressIds(addressIds));
    }
}
