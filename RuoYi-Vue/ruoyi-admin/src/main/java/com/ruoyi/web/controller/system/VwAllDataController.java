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
import com.ruoyi.system.domain.VwAllData;
import com.ruoyi.system.service.IVwAllDataService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

import com.github.pagehelper.PageHelper;
import com.ruoyi.common.utils.PageUtils;
/**
 * 数据分析Controller
 * 
 * @author hxx
 * @date 2025-05-05
 */
@RestController
@RequestMapping("/system/an_data")
public class VwAllDataController extends BaseController
{
    @Autowired
    private IVwAllDataService vwAllDataService;

    /**
     * 查询数据分析列表
     */
    @PreAuthorize("@ss.hasPermi('system:an_data:list')")
    @GetMapping("/list")
    public TableDataInfo list(VwAllData vwAllData)
    {
        // 设置每页大小为最大值
        PageUtils.startPage();
        // 手动设置分页参数
        PageHelper.startPage(1, 10000);
        List<VwAllData> list = vwAllDataService.selectVwAllDataList(vwAllData);
        return getDataTable(list);
    }

    /**
     * 导出数据分析列表
     */
    @PreAuthorize("@ss.hasPermi('system:an_data:export')")
    @Log(title = "数据分析", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, VwAllData vwAllData)
    {
        List<VwAllData> list = vwAllDataService.selectVwAllDataList(vwAllData);
        ExcelUtil<VwAllData> util = new ExcelUtil<VwAllData>(VwAllData.class);
        util.exportExcel(response, list, "数据分析数据");
    }

    /**
     * 获取数据分析详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:an_data:query')")
    @GetMapping(value = "/{tableName}")
    public AjaxResult getInfo(@PathVariable("tableName") String tableName)
    {
        return success(vwAllDataService.selectVwAllDataByTableName(tableName));
    }

    /**
     * 新增数据分析
     */
    @PreAuthorize("@ss.hasPermi('system:an_data:add')")
    @Log(title = "数据分析", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody VwAllData vwAllData)
    {
        return toAjax(vwAllDataService.insertVwAllData(vwAllData));
    }

    /**
     * 修改数据分析
     */
    @PreAuthorize("@ss.hasPermi('system:an_data:edit')")
    @Log(title = "数据分析", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody VwAllData vwAllData)
    {
        return toAjax(vwAllDataService.updateVwAllData(vwAllData));
    }

    /**
     * 删除数据分析
     */
    @PreAuthorize("@ss.hasPermi('system:an_data:remove')")
    @Log(title = "数据分析", businessType = BusinessType.DELETE)
	@DeleteMapping("/{tableNames}")
    public AjaxResult remove(@PathVariable String[] tableNames)
    {
        return toAjax(vwAllDataService.deleteVwAllDataByTableNames(tableNames));
    }
}
