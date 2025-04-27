package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.AddressMapper;
import com.ruoyi.system.domain.Address;
import com.ruoyi.system.service.IAddressService;

/**
 * 订单地址信息Service业务层处理
 * 
 * @author hxx
 * @date 2025-04-27
 */
@Service
public class AddressServiceImpl implements IAddressService 
{
    @Autowired
    private AddressMapper addressMapper;

    /**
     * 查询订单地址信息
     * 
     * @param addressId 订单地址信息主键
     * @return 订单地址信息
     */
    @Override
    public Address selectAddressByAddressId(Long addressId)
    {
        return addressMapper.selectAddressByAddressId(addressId);
    }

    /**
     * 查询订单地址信息列表
     * 
     * @param address 订单地址信息
     * @return 订单地址信息
     */
    @Override
    public List<Address> selectAddressList(Address address)
    {
        return addressMapper.selectAddressList(address);
    }

    /**
     * 新增订单地址信息
     * 
     * @param address 订单地址信息
     * @return 结果
     */
    @Override
    public int insertAddress(Address address)
    {
        return addressMapper.insertAddress(address);
    }

    /**
     * 修改订单地址信息
     * 
     * @param address 订单地址信息
     * @return 结果
     */
    @Override
    public int updateAddress(Address address)
    {
        return addressMapper.updateAddress(address);
    }

    /**
     * 批量删除订单地址信息
     * 
     * @param addressIds 需要删除的订单地址信息主键
     * @return 结果
     */
    @Override
    public int deleteAddressByAddressIds(Long[] addressIds)
    {
        return addressMapper.deleteAddressByAddressIds(addressIds);
    }

    /**
     * 删除订单地址信息信息
     * 
     * @param addressId 订单地址信息主键
     * @return 结果
     */
    @Override
    public int deleteAddressByAddressId(Long addressId)
    {
        return addressMapper.deleteAddressByAddressId(addressId);
    }
}
