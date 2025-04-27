package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.Address;

/**
 * 订单地址信息Mapper接口
 * 
 * @author hxx
 * @date 2025-04-27
 */
public interface AddressMapper 
{
    /**
     * 查询订单地址信息
     * 
     * @param addressId 订单地址信息主键
     * @return 订单地址信息
     */
    public Address selectAddressByAddressId(Long addressId);

    /**
     * 查询订单地址信息列表
     * 
     * @param address 订单地址信息
     * @return 订单地址信息集合
     */
    public List<Address> selectAddressList(Address address);

    /**
     * 新增订单地址信息
     * 
     * @param address 订单地址信息
     * @return 结果
     */
    public int insertAddress(Address address);

    /**
     * 修改订单地址信息
     * 
     * @param address 订单地址信息
     * @return 结果
     */
    public int updateAddress(Address address);

    /**
     * 删除订单地址信息
     * 
     * @param addressId 订单地址信息主键
     * @return 结果
     */
    public int deleteAddressByAddressId(Long addressId);

    /**
     * 批量删除订单地址信息
     * 
     * @param addressIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteAddressByAddressIds(Long[] addressIds);
}
