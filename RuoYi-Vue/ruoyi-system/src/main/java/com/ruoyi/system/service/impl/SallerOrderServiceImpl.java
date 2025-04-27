package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.SallerOrderMapper;
import com.ruoyi.system.domain.SallerOrder;
import com.ruoyi.system.service.ISallerOrderService;

/**
 * 销售员-订单关联Service业务层处理
 * 
 * @author hxx
 * @date 2025-04-27
 */
@Service
public class SallerOrderServiceImpl implements ISallerOrderService 
{
    @Autowired
    private SallerOrderMapper sallerOrderMapper;

    /**
     * 查询销售员-订单关联
     * 
     * @param userId 销售员-订单关联主键
     * @return 销售员-订单关联
     */
    @Override
    public SallerOrder selectSallerOrderByUserId(Long userId)
    {
        return sallerOrderMapper.selectSallerOrderByUserId(userId);
    }

    /**
     * 查询销售员-订单关联列表
     * 
     * @param sallerOrder 销售员-订单关联
     * @return 销售员-订单关联
     */
    @Override
    public List<SallerOrder> selectSallerOrderList(SallerOrder sallerOrder)
    {
        return sallerOrderMapper.selectSallerOrderList(sallerOrder);
    }

    /**
     * 新增销售员-订单关联
     * 
     * @param sallerOrder 销售员-订单关联
     * @return 结果
     */
    @Override
    public int insertSallerOrder(SallerOrder sallerOrder)
    {
        return sallerOrderMapper.insertSallerOrder(sallerOrder);
    }

    /**
     * 修改销售员-订单关联
     * 
     * @param sallerOrder 销售员-订单关联
     * @return 结果
     */
    @Override
    public int updateSallerOrder(SallerOrder sallerOrder)
    {
        return sallerOrderMapper.updateSallerOrder(sallerOrder);
    }

    /**
     * 批量删除销售员-订单关联
     * 
     * @param userIds 需要删除的销售员-订单关联主键
     * @return 结果
     */
    @Override
    public int deleteSallerOrderByUserIds(Long[] userIds)
    {
        return sallerOrderMapper.deleteSallerOrderByUserIds(userIds);
    }

    /**
     * 删除销售员-订单关联信息
     * 
     * @param userId 销售员-订单关联主键
     * @return 结果
     */
    @Override
    public int deleteSallerOrderByUserId(Long userId)
    {
        return sallerOrderMapper.deleteSallerOrderByUserId(userId);
    }
}
