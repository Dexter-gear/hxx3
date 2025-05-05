package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.VwBusinessStatistics;

/**
 * 统计数据Service接口
 * 
 * @author hxx
 * @date 2025-05-05
 */
public interface IVwBusinessStatisticsService 
{
    /**
     * 查询统计数据
     * 
     * @param module 统计数据主键
     * @return 统计数据
     */
    public VwBusinessStatistics selectVwBusinessStatisticsByModule(String module);

    /**
     * 查询统计数据列表
     * 
     * @param vwBusinessStatistics 统计数据
     * @return 统计数据集合
     */
    public List<VwBusinessStatistics> selectVwBusinessStatisticsList(VwBusinessStatistics vwBusinessStatistics);

    /**
     * 新增统计数据
     * 
     * @param vwBusinessStatistics 统计数据
     * @return 结果
     */
    public int insertVwBusinessStatistics(VwBusinessStatistics vwBusinessStatistics);

    /**
     * 修改统计数据
     * 
     * @param vwBusinessStatistics 统计数据
     * @return 结果
     */
    public int updateVwBusinessStatistics(VwBusinessStatistics vwBusinessStatistics);

    /**
     * 批量删除统计数据
     * 
     * @param modules 需要删除的统计数据主键集合
     * @return 结果
     */
    public int deleteVwBusinessStatisticsByModules(String[] modules);

    /**
     * 删除统计数据信息
     * 
     * @param module 统计数据主键
     * @return 结果
     */
    public int deleteVwBusinessStatisticsByModule(String module);
}
