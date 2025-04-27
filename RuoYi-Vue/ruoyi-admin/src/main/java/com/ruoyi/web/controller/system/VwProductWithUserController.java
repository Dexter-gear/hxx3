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
import com.ruoyi.system.domain.VwProductWithUser;
import com.ruoyi.system.service.IVwProductWithUserService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * VIEWController
 * 
 * @author hxx
 * @date 2025-04-27
 */
@RestController
@RequestMapping("/system/view_product_user")
public class VwProductWithUserController extends BaseController
{
    @Autowired
    private IVwProductWithUserService vwProductWithUserService;

    /**
     * 查询VIEW列表
     */
    @PreAuthorize("@ss.hasPermi('system:view_product_user:list')")
    @GetMapping("/list")
    public TableDataInfo list(VwProductWithUser vwProductWithUser)
    {
        startPage();
        List<VwProductWithUser> list = vwProductWithUserService.selectVwProductWithUserList(vwProductWithUser);
        return getDataTable(list);
    }

    /**
     * 导出VIEW列表
     */
    @PreAuthorize("@ss.hasPermi('system:view_product_user:export')")
    @Log(title = "VIEW", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, VwProductWithUser vwProductWithUser)
    {
        List<VwProductWithUser> list = vwProductWithUserService.selectVwProductWithUserList(vwProductWithUser);
        ExcelUtil<VwProductWithUser> util = new ExcelUtil<VwProductWithUser>(VwProductWithUser.class);
        util.exportExcel(response, list, "VIEW数据");
    }

    /**
     * 获取VIEW详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:view_product_user:query')")
    @GetMapping(value = "/{productId}")
    public AjaxResult getInfo(@PathVariable("productId") Long productId)
    {
        return success(vwProductWithUserService.selectVwProductWithUserByProductId(productId));
    }

    /**
     * 新增VIEW
     */
    @PreAuthorize("@ss.hasPermi('system:view_product_user:add')")
    @Log(title = "VIEW", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody VwProductWithUser vwProductWithUser)
    {
        return toAjax(vwProductWithUserService.insertVwProductWithUser(vwProductWithUser));
    }

    /**
     * 修改VIEW
     */
    @PreAuthorize("@ss.hasPermi('system:view_product_user:edit')")
    @Log(title = "VIEW", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody VwProductWithUser vwProductWithUser)
    {
        return toAjax(vwProductWithUserService.updateVwProductWithUser(vwProductWithUser));
    }

    /**
     * 删除VIEW
     */
    @PreAuthorize("@ss.hasPermi('system:view_product_user:remove')")
    @Log(title = "VIEW", businessType = BusinessType.DELETE)
	@DeleteMapping("/{productIds}")
    public AjaxResult remove(@PathVariable Long[] productIds)
    {
        return toAjax(vwProductWithUserService.deleteVwProductWithUserByProductIds(productIds));
    }
}
