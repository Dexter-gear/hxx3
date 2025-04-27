package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 订单地址信息对象 address
 * 
 * @author hxx
 * @date 2025-04-27
 */
public class Address extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 地址主键 */
    private Long addressId;

    /** 关联订单ID */
    @Excel(name = "关联订单ID")
    private Long orderId;

    /** 详细地址 */
    @Excel(name = "详细地址")
    private String address;

    public void setAddressId(Long addressId) 
    {
        this.addressId = addressId;
    }

    public Long getAddressId() 
    {
        return addressId;
    }

    public void setOrderId(Long orderId) 
    {
        this.orderId = orderId;
    }

    public Long getOrderId() 
    {
        return orderId;
    }

    public void setAddress(String address) 
    {
        this.address = address;
    }

    public String getAddress() 
    {
        return address;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("addressId", getAddressId())
            .append("orderId", getOrderId())
            .append("address", getAddress())
            .toString();
    }
}
