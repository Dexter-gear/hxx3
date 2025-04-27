package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.SallerProduct;

/**
 * 销售员-产品关联Mapper接口
 * 
 * @author hxx
 * @date 2025-04-27
 */
public interface SallerProductMapper 
{
    /**
     * 查询销售员-产品关联
     * 
     * @param userId 销售员-产品关联主键
     * @return 销售员-产品关联
     */
    public SallerProduct selectSallerProductByUserId(Long userId);

    /**
     * 查询销售员-产品关联列表
     * 
     * @param sallerProduct 销售员-产品关联
     * @return 销售员-产品关联集合
     */
    public List<SallerProduct> selectSallerProductList(SallerProduct sallerProduct);

    /**
     * 新增销售员-产品关联
     * 
     * @param sallerProduct 销售员-产品关联
     * @return 结果
     */
    public int insertSallerProduct(SallerProduct sallerProduct);

    /**
     * 修改销售员-产品关联
     * 
     * @param sallerProduct 销售员-产品关联
     * @return 结果
     */
    public int updateSallerProduct(SallerProduct sallerProduct);

    /**
     * 删除销售员-产品关联
     * 
     * @param userId 销售员-产品关联主键
     * @return 结果
     */
    public int deleteSallerProductByUserId(Long userId);

    /**
     * 批量删除销售员-产品关联
     * 
     * @param userIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSallerProductByUserIds(Long[] userIds);
}
