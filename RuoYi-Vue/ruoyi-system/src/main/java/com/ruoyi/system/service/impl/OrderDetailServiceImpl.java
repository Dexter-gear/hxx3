package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.OrderDetailMapper;
import com.ruoyi.system.domain.OrderDetail;
import com.ruoyi.system.service.IOrderDetailService;

/**
 * 订单详情Service业务层处理
 * 
 * @author hxx
 * @date 2025-04-13
 */
@Service
public class OrderDetailServiceImpl implements IOrderDetailService 
{
    @Autowired
    private OrderDetailMapper orderDetailMapper;

    /**
     * 查询订单详情
     * 
     * @param detailId 订单详情主键
     * @return 订单详情
     */
    @Override
    public OrderDetail selectOrderDetailByDetailId(Long detailId)
    {
        return orderDetailMapper.selectOrderDetailByDetailId(detailId);
    }

    /**
     * 查询订单详情列表
     * 
     * @param orderDetail 订单详情
     * @return 订单详情
     */
    @Override
    public List<OrderDetail> selectOrderDetailList(OrderDetail orderDetail)
    {
        return orderDetailMapper.selectOrderDetailList(orderDetail);
    }

    /**
     * 新增订单详情
     * 
     * @param orderDetail 订单详情
     * @return 结果
     */
    @Override
    public int insertOrderDetail(OrderDetail orderDetail)
    {
        return orderDetailMapper.insertOrderDetail(orderDetail);
    }

    /**
     * 修改订单详情
     * 
     * @param orderDetail 订单详情
     * @return 结果
     */
    @Override
    public int updateOrderDetail(OrderDetail orderDetail)
    {
        return orderDetailMapper.updateOrderDetail(orderDetail);
    }

    /**
     * 批量删除订单详情
     * 
     * @param detailIds 需要删除的订单详情主键
     * @return 结果
     */
    @Override
    public int deleteOrderDetailByDetailIds(Long[] detailIds)
    {
        return orderDetailMapper.deleteOrderDetailByDetailIds(detailIds);
    }

    /**
     * 删除订单详情信息
     * 
     * @param detailId 订单详情主键
     * @return 结果
     */
    @Override
    public int deleteOrderDetailByDetailId(Long detailId)
    {
        return orderDetailMapper.deleteOrderDetailByDetailId(detailId);
    }
}
