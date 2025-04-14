package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.OrderDetail;

/**
 * 订单详情Service接口
 * 
 * @author hxx
 * @date 2025-04-13
 */
public interface IOrderDetailService 
{
    /**
     * 查询订单详情
     * 
     * @param detailId 订单详情主键
     * @return 订单详情
     */
    public OrderDetail selectOrderDetailByDetailId(Long detailId);

    /**
     * 查询订单详情列表
     * 
     * @param orderDetail 订单详情
     * @return 订单详情集合
     */
    public List<OrderDetail> selectOrderDetailList(OrderDetail orderDetail);

    /**
     * 新增订单详情
     * 
     * @param orderDetail 订单详情
     * @return 结果
     */
    public int insertOrderDetail(OrderDetail orderDetail);

    /**
     * 修改订单详情
     * 
     * @param orderDetail 订单详情
     * @return 结果
     */
    public int updateOrderDetail(OrderDetail orderDetail);

    /**
     * 批量删除订单详情
     * 
     * @param detailIds 需要删除的订单详情主键集合
     * @return 结果
     */
    public int deleteOrderDetailByDetailIds(Long[] detailIds);

    /**
     * 删除订单详情信息
     * 
     * @param detailId 订单详情主键
     * @return 结果
     */
    public int deleteOrderDetailByDetailId(Long detailId);
}
