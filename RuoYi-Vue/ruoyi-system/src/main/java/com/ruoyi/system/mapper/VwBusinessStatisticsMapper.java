package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.VwBusinessStatistics;

/**
 * 统计数据Mapper接口
 * 
 * @author hxx
 * @date 2025-05-05
 */
public interface VwBusinessStatisticsMapper 
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
     * 删除统计数据
     * 
     * @param module 统计数据主键
     * @return 结果
     */
    public int deleteVwBusinessStatisticsByModule(String module);

    /**
     * 批量删除统计数据
     * 
     * @param modules 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteVwBusinessStatisticsByModules(String[] modules);
}
