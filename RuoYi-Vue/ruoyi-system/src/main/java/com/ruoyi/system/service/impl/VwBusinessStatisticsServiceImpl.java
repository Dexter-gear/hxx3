package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.VwBusinessStatisticsMapper;
import com.ruoyi.system.domain.VwBusinessStatistics;
import com.ruoyi.system.service.IVwBusinessStatisticsService;

/**
 * 统计数据Service业务层处理
 * 
 * @author hxx
 * @date 2025-05-05
 */
@Service
public class VwBusinessStatisticsServiceImpl implements IVwBusinessStatisticsService 
{
    @Autowired
    private VwBusinessStatisticsMapper vwBusinessStatisticsMapper;

    /**
     * 查询统计数据
     * 
     * @param module 统计数据主键
     * @return 统计数据
     */
    @Override
    public VwBusinessStatistics selectVwBusinessStatisticsByModule(String module)
    {
        return vwBusinessStatisticsMapper.selectVwBusinessStatisticsByModule(module);
    }

    /**
     * 查询统计数据列表
     * 
     * @param vwBusinessStatistics 统计数据
     * @return 统计数据
     */
    @Override
    public List<VwBusinessStatistics> selectVwBusinessStatisticsList(VwBusinessStatistics vwBusinessStatistics)
    {
        return vwBusinessStatisticsMapper.selectVwBusinessStatisticsList(vwBusinessStatistics);
    }

    /**
     * 新增统计数据
     * 
     * @param vwBusinessStatistics 统计数据
     * @return 结果
     */
    @Override
    public int insertVwBusinessStatistics(VwBusinessStatistics vwBusinessStatistics)
    {
        return vwBusinessStatisticsMapper.insertVwBusinessStatistics(vwBusinessStatistics);
    }

    /**
     * 修改统计数据
     * 
     * @param vwBusinessStatistics 统计数据
     * @return 结果
     */
    @Override
    public int updateVwBusinessStatistics(VwBusinessStatistics vwBusinessStatistics)
    {
        return vwBusinessStatisticsMapper.updateVwBusinessStatistics(vwBusinessStatistics);
    }

    /**
     * 批量删除统计数据
     * 
     * @param modules 需要删除的统计数据主键
     * @return 结果
     */
    @Override
    public int deleteVwBusinessStatisticsByModules(String[] modules)
    {
        return vwBusinessStatisticsMapper.deleteVwBusinessStatisticsByModules(modules);
    }

    /**
     * 删除统计数据信息
     * 
     * @param module 统计数据主键
     * @return 结果
     */
    @Override
    public int deleteVwBusinessStatisticsByModule(String module)
    {
        return vwBusinessStatisticsMapper.deleteVwBusinessStatisticsByModule(module);
    }
}
