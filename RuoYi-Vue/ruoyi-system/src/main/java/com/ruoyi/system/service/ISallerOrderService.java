package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.SallerOrder;

/**
 * 销售员-订单关联Service接口
 * 
 * @author hxx
 * @date 2025-04-27
 */
public interface ISallerOrderService 
{
    /**
     * 查询销售员-订单关联
     * 
     * @param userId 销售员-订单关联主键
     * @return 销售员-订单关联
     */
    public SallerOrder selectSallerOrderByUserId(Long userId);

    /**
     * 查询销售员-订单关联列表
     * 
     * @param sallerOrder 销售员-订单关联
     * @return 销售员-订单关联集合
     */
    public List<SallerOrder> selectSallerOrderList(SallerOrder sallerOrder);

    /**
     * 新增销售员-订单关联
     * 
     * @param sallerOrder 销售员-订单关联
     * @return 结果
     */
    public int insertSallerOrder(SallerOrder sallerOrder);

    /**
     * 修改销售员-订单关联
     * 
     * @param sallerOrder 销售员-订单关联
     * @return 结果
     */
    public int updateSallerOrder(SallerOrder sallerOrder);

    /**
     * 批量删除销售员-订单关联
     * 
     * @param userIds 需要删除的销售员-订单关联主键集合
     * @return 结果
     */
    public int deleteSallerOrderByUserIds(Long[] userIds);

    /**
     * 删除销售员-订单关联信息
     * 
     * @param userId 销售员-订单关联主键
     * @return 结果
     */
    public int deleteSallerOrderByUserId(Long userId);
}
