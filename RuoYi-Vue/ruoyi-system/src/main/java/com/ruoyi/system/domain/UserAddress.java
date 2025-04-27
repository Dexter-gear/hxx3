package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 用户地址信息对象 user_address
 * 
 * @author hxx
 * @date 2025-04-27
 */
public class UserAddress extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 地址主键 */
    private Long addressId;

    /** 关联用户ID */
    @Excel(name = "关联用户ID")
    private Long userId;

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

    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
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
            .append("userId", getUserId())
            .append("address", getAddress())
            .toString();
    }
}
