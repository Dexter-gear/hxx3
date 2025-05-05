package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.VwAllData;

/**
 * 数据分析Mapper接口
 * 
 * @author hxx
 * @date 2025-05-05
 */
public interface VwAllDataMapper 
{
    /**
     * 查询数据分析
     * 
     * @param tableName 数据分析主键
     * @return 数据分析
     */
    public VwAllData selectVwAllDataByTableName(String tableName);

    /**
     * 查询数据分析列表
     * 
     * @param vwAllData 数据分析
     * @return 数据分析集合
     */
    public List<VwAllData> selectVwAllDataList(VwAllData vwAllData);

    /**
     * 新增数据分析
     * 
     * @param vwAllData 数据分析
     * @return 结果
     */
    public int insertVwAllData(VwAllData vwAllData);

    /**
     * 修改数据分析
     * 
     * @param vwAllData 数据分析
     * @return 结果
     */
    public int updateVwAllData(VwAllData vwAllData);

    /**
     * 删除数据分析
     * 
     * @param tableName 数据分析主键
     * @return 结果
     */
    public int deleteVwAllDataByTableName(String tableName);

    /**
     * 批量删除数据分析
     * 
     * @param tableNames 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteVwAllDataByTableNames(String[] tableNames);
}
