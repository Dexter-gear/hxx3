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
import com.ruoyi.system.domain.VwBusinessStatistics;
import com.ruoyi.system.service.IVwBusinessStatisticsService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

import com.github.pagehelper.PageHelper;
import com.ruoyi.common.utils.PageUtils;
/**
 * 统计数据Controller
 * 
 * @author hxx
 * @date 2025-05-05
 */
@RestController
@RequestMapping("/system/bi_statistics")
public class VwBusinessStatisticsController extends BaseController
{
    @Autowired
    private IVwBusinessStatisticsService vwBusinessStatisticsService;

    /**
     * 查询统计数据列表
     */
    @PreAuthorize("@ss.hasPermi('system:bi_statistics:list')")
    @GetMapping("/list")
    public TableDataInfo list(VwBusinessStatistics vwBusinessStatistics)
    {
        // 设置每页大小为最大值
        PageUtils.startPage();
        // 手动设置分页参数
        PageHelper.startPage(1, 10000);
        List<VwBusinessStatistics> list = vwBusinessStatisticsService.selectVwBusinessStatisticsList(vwBusinessStatistics);
        return getDataTable(list);
    }

    /**
     * 导出统计数据列表
     */
    @PreAuthorize("@ss.hasPermi('system:bi_statistics:export')")
    @Log(title = "统计数据", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, VwBusinessStatistics vwBusinessStatistics)
    {
        List<VwBusinessStatistics> list = vwBusinessStatisticsService.selectVwBusinessStatisticsList(vwBusinessStatistics);
        ExcelUtil<VwBusinessStatistics> util = new ExcelUtil<VwBusinessStatistics>(VwBusinessStatistics.class);
        util.exportExcel(response, list, "统计数据数据");
    }

    /**
     * 获取统计数据详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:bi_statistics:query')")
    @GetMapping(value = "/{module}")
    public AjaxResult getInfo(@PathVariable("module") String module)
    {
        return success(vwBusinessStatisticsService.selectVwBusinessStatisticsByModule(module));
    }

    /**
     * 新增统计数据
     */
    @PreAuthorize("@ss.hasPermi('system:bi_statistics:add')")
    @Log(title = "统计数据", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody VwBusinessStatistics vwBusinessStatistics)
    {
        return toAjax(vwBusinessStatisticsService.insertVwBusinessStatistics(vwBusinessStatistics));
    }

    /**
     * 修改统计数据
     */
    @PreAuthorize("@ss.hasPermi('system:bi_statistics:edit')")
    @Log(title = "统计数据", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody VwBusinessStatistics vwBusinessStatistics)
    {
        return toAjax(vwBusinessStatisticsService.updateVwBusinessStatistics(vwBusinessStatistics));
    }

    /**
     * 删除统计数据
     */
    @PreAuthorize("@ss.hasPermi('system:bi_statistics:remove')")
    @Log(title = "统计数据", businessType = BusinessType.DELETE)
	@DeleteMapping("/{modules}")
    public AjaxResult remove(@PathVariable String[] modules)
    {
        return toAjax(vwBusinessStatisticsService.deleteVwBusinessStatisticsByModules(modules));
    }
}
