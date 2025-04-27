package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.SallerProductMapper;
import com.ruoyi.system.domain.SallerProduct;
import com.ruoyi.system.service.ISallerProductService;

/**
 * 销售员-产品关联Service业务层处理
 * 
 * @author hxx
 * @date 2025-04-27
 */
@Service
public class SallerProductServiceImpl implements ISallerProductService 
{
    @Autowired
    private SallerProductMapper sallerProductMapper;

    /**
     * 查询销售员-产品关联
     * 
     * @param userId 销售员-产品关联主键
     * @return 销售员-产品关联
     */
    @Override
    public SallerProduct selectSallerProductByUserId(Long userId)
    {
        return sallerProductMapper.selectSallerProductByUserId(userId);
    }

    /**
     * 查询销售员-产品关联列表
     * 
     * @param sallerProduct 销售员-产品关联
     * @return 销售员-产品关联
     */
    @Override
    public List<SallerProduct> selectSallerProductList(SallerProduct sallerProduct)
    {
        return sallerProductMapper.selectSallerProductList(sallerProduct);
    }

    /**
     * 新增销售员-产品关联
     * 
     * @param sallerProduct 销售员-产品关联
     * @return 结果
     */
    @Override
    public int insertSallerProduct(SallerProduct sallerProduct)
    {
        return sallerProductMapper.insertSallerProduct(sallerProduct);
    }

    /**
     * 修改销售员-产品关联
     * 
     * @param sallerProduct 销售员-产品关联
     * @return 结果
     */
    @Override
    public int updateSallerProduct(SallerProduct sallerProduct)
    {
        return sallerProductMapper.updateSallerProduct(sallerProduct);
    }

    /**
     * 批量删除销售员-产品关联
     * 
     * @param userIds 需要删除的销售员-产品关联主键
     * @return 结果
     */
    @Override
    public int deleteSallerProductByUserIds(Long[] userIds)
    {
        return sallerProductMapper.deleteSallerProductByUserIds(userIds);
    }

    /**
     * 删除销售员-产品关联信息
     * 
     * @param userId 销售员-产品关联主键
     * @return 结果
     */
    @Override
    public int deleteSallerProductByUserId(Long userId)
    {
        return sallerProductMapper.deleteSallerProductByUserId(userId);
    }
}
