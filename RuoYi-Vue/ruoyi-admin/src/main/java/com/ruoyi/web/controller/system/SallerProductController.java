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
import com.ruoyi.system.domain.SallerProduct;
import com.ruoyi.system.service.ISallerProductService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.framework.web.service.TokenService;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.utils.StringUtils;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
/**
 * 销售员-产品关联Controller
 * 
 * @author hxx
 * @date 2025-04-27
 */
@RestController
@RequestMapping("/system/saller_product")
public class SallerProductController extends BaseController
{
    @Autowired
    private ISallerProductService sallerProductService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private TokenService tokenService;

    /**
     * 查询销售员-产品关联列表
     */
    @PreAuthorize("@ss.hasPermi('system:saller_product:list')")
    @GetMapping("/list")
    public TableDataInfo list(SallerProduct sallerProduct)
    {
        LoginUser loginUser = tokenService.getLoginUser(request);
        Long userId = loginUser.getUser().getUserId();
        sallerProduct.setUserId(userId);
        startPage();
        List<SallerProduct> list = sallerProductService.selectSallerProductList(sallerProduct);
        return getDataTable(list);
    }

    /**
     * 导出销售员-产品关联列表
     */
    @PreAuthorize("@ss.hasPermi('system:saller_product:export')")
    @Log(title = "销售员-产品关联", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SallerProduct sallerProduct)
    {
        List<SallerProduct> list = sallerProductService.selectSallerProductList(sallerProduct);
        ExcelUtil<SallerProduct> util = new ExcelUtil<SallerProduct>(SallerProduct.class);
        util.exportExcel(response, list, "销售员-产品关联数据");
    }

    /**
     * 获取销售员-产品关联详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:saller_product:query')")
    @GetMapping(value = "/{userId}")
    public AjaxResult getInfo(@PathVariable("userId") Long userId)
    {
        return success(sallerProductService.selectSallerProductByUserId(userId));
    }

    /**
     * 新增销售员-产品关联
     */
    @PreAuthorize("@ss.hasPermi('system:saller_product:add')")
    @Log(title = "销售员-产品关联", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SallerProduct sallerProduct)
    {
        LoginUser loginUser = tokenService.getLoginUser(request);
        Long userId = loginUser.getUser().getUserId();
        sallerProduct.setUserId(userId);
        return toAjax(sallerProductService.insertSallerProduct(sallerProduct));
    }

    /**
     * 修改销售员-产品关联
     */
    @PreAuthorize("@ss.hasPermi('system:saller_product:edit')")
    @Log(title = "销售员-产品关联", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SallerProduct sallerProduct)
    {
        return toAjax(sallerProductService.updateSallerProduct(sallerProduct));
    }

    /**
     * 删除销售员-产品关联
     */
    @PreAuthorize("@ss.hasPermi('system:saller_product:remove')")
    @Log(title = "销售员-产品关联", businessType = BusinessType.DELETE)
	@DeleteMapping("/{userIds}")
    public AjaxResult remove(@PathVariable Long[] userIds)
    {
        return toAjax(sallerProductService.deleteSallerProductByUserIds(userIds));
    }
}
