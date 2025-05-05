package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.VwAllDataMapper;
import com.ruoyi.system.domain.VwAllData;
import com.ruoyi.system.service.IVwAllDataService;

/**
 * 数据分析Service业务层处理
 * 
 * @author hxx
 * @date 2025-05-05
 */
@Service
public class VwAllDataServiceImpl implements IVwAllDataService 
{
    @Autowired
    private VwAllDataMapper vwAllDataMapper;

    /**
     * 查询数据分析
     * 
     * @param tableName 数据分析主键
     * @return 数据分析
     */
    @Override
    public VwAllData selectVwAllDataByTableName(String tableName)
    {
        return vwAllDataMapper.selectVwAllDataByTableName(tableName);
    }

    /**
     * 查询数据分析列表
     * 
     * @param vwAllData 数据分析
     * @return 数据分析
     */
    @Override
    public List<VwAllData> selectVwAllDataList(VwAllData vwAllData)
    {
        return vwAllDataMapper.selectVwAllDataList(vwAllData);
    }

    /**
     * 新增数据分析
     * 
     * @param vwAllData 数据分析
     * @return 结果
     */
    @Override
    public int insertVwAllData(VwAllData vwAllData)
    {
        return vwAllDataMapper.insertVwAllData(vwAllData);
    }

    /**
     * 修改数据分析
     * 
     * @param vwAllData 数据分析
     * @return 结果
     */
    @Override
    public int updateVwAllData(VwAllData vwAllData)
    {
        return vwAllDataMapper.updateVwAllData(vwAllData);
    }

    /**
     * 批量删除数据分析
     * 
     * @param tableNames 需要删除的数据分析主键
     * @return 结果
     */
    @Override
    public int deleteVwAllDataByTableNames(String[] tableNames)
    {
        return vwAllDataMapper.deleteVwAllDataByTableNames(tableNames);
    }

    /**
     * 删除数据分析信息
     * 
     * @param tableName 数据分析主键
     * @return 结果
     */
    @Override
    public int deleteVwAllDataByTableName(String tableName)
    {
        return vwAllDataMapper.deleteVwAllDataByTableName(tableName);
    }
}
