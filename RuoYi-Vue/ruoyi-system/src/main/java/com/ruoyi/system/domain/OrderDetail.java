package com.ruoyi.system.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 订单详情对象 order_detail
 * 
 * @author hxx
 * @date 2025-04-13
 */
public class OrderDetail extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 订单详情ID */
    private Long detailId;

    /** 订单ID */
    @Excel(name = "订单ID")
    private Long orderId;

    /** 商品ID */
    @Excel(name = "商品ID")
    private Long productId;

    /** 商品数量 */
    @Excel(name = "商品数量")
    private Long quantity;

    /** 商品单价 */
    @Excel(name = "商品单价")
    private BigDecimal price;

    public void setDetailId(Long detailId) 
    {
        this.detailId = detailId;
    }

    public Long getDetailId() 
    {
        return detailId;
    }

    public void setOrderId(Long orderId) 
    {
        this.orderId = orderId;
    }

    public Long getOrderId() 
    {
        return orderId;
    }

    public void setProductId(Long productId) 
    {
        this.productId = productId;
    }

    public Long getProductId() 
    {
        return productId;
    }

    public void setQuantity(Long quantity) 
    {
        this.quantity = quantity;
    }

    public Long getQuantity() 
    {
        return quantity;
    }

    public void setPrice(BigDecimal price) 
    {
        this.price = price;
    }

    public BigDecimal getPrice() 
    {
        return price;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("detailId", getDetailId())
            .append("orderId", getOrderId())
            .append("productId", getProductId())
            .append("quantity", getQuantity())
            .append("price", getPrice())
            .toString();
    }
}
