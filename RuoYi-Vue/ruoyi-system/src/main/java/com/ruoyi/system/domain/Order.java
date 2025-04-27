package com.ruoyi.system.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 订单对象 order
 * 
 * @author hxx
 * @date 2025-04-26
 */
public class Order extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 订单ID */
    private Long orderId;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    /** 订单总金额 */
    @Excel(name = "订单总金额")
    private BigDecimal totalAmount;

    /** 订单状态（如待付款、已发货） */
    @Excel(name = "订单状态", readConverterExp = "如=待付款、已发货")
    private String status;

    /** 付款状态（0-未付款，1-已付款） */
    @Excel(name = "付款状态", readConverterExp = "0=-未付款，1-已付款")
    private String paymentStatus;

    /** 物流状态（如未发货、已签收） */
    @Excel(name = "物流状态", readConverterExp = "如=未发货、已签收")
    private String shippingStatus;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdAt;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updatedAt;

    /** 地址ID，外键引用 address.address_id */
    @Excel(name = "地址ID，外键引用 address.address_id")
    private Long addressId;

    public void setOrderId(Long orderId) 
    {
        this.orderId = orderId;
    }

    public Long getOrderId() 
    {
        return orderId;
    }

    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }

    public void setTotalAmount(BigDecimal totalAmount) 
    {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getTotalAmount() 
    {
        return totalAmount;
    }

    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }

    public void setPaymentStatus(String paymentStatus) 
    {
        this.paymentStatus = paymentStatus;
    }

    public String getPaymentStatus() 
    {
        return paymentStatus;
    }

    public void setShippingStatus(String shippingStatus) 
    {
        this.shippingStatus = shippingStatus;
    }

    public String getShippingStatus() 
    {
        return shippingStatus;
    }

    public void setCreatedAt(Date createdAt) 
    {
        this.createdAt = createdAt;
    }

    public Date getCreatedAt() 
    {
        return createdAt;
    }

    public void setUpdatedAt(Date updatedAt) 
    {
        this.updatedAt = updatedAt;
    }

    public Date getUpdatedAt() 
    {
        return updatedAt;
    }

    public void setAddressId(Long addressId) 
    {
        this.addressId = addressId;
    }

    public Long getAddressId() 
    {
        return addressId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("orderId", getOrderId())
            .append("userId", getUserId())
            .append("totalAmount", getTotalAmount())
            .append("status", getStatus())
            .append("paymentStatus", getPaymentStatus())
            .append("shippingStatus", getShippingStatus())
            .append("createdAt", getCreatedAt())
            .append("updatedAt", getUpdatedAt())
            .append("addressId", getAddressId())
            .toString();
    }
}
