package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.UserAddressMapper;
import com.ruoyi.system.domain.UserAddress;
import com.ruoyi.system.service.IUserAddressService;

/**
 * 用户地址信息Service业务层处理
 * 
 * @author hxx
 * @date 2025-04-27
 */
@Service
public class UserAddressServiceImpl implements IUserAddressService 
{
    @Autowired
    private UserAddressMapper userAddressMapper;

    /**
     * 查询用户地址信息
     * 
     * @param addressId 用户地址信息主键
     * @return 用户地址信息
     */
    @Override
    public UserAddress selectUserAddressByAddressId(Long addressId)
    {
        return userAddressMapper.selectUserAddressByAddressId(addressId);
    }

    /**
     * 查询用户地址信息列表
     * 
     * @param userAddress 用户地址信息
     * @return 用户地址信息
     */
    @Override
    public List<UserAddress> selectUserAddressList(UserAddress userAddress)
    {
        return userAddressMapper.selectUserAddressList(userAddress);
    }

    /**
     * 新增用户地址信息
     * 
     * @param userAddress 用户地址信息
     * @return 结果
     */
    @Override
    public int insertUserAddress(UserAddress userAddress)
    {
        return userAddressMapper.insertUserAddress(userAddress);
    }

    /**
     * 修改用户地址信息
     * 
     * @param userAddress 用户地址信息
     * @return 结果
     */
    @Override
    public int updateUserAddress(UserAddress userAddress)
    {
        return userAddressMapper.updateUserAddress(userAddress);
    }

    /**
     * 批量删除用户地址信息
     * 
     * @param addressIds 需要删除的用户地址信息主键
     * @return 结果
     */
    @Override
    public int deleteUserAddressByAddressIds(Long[] addressIds)
    {
        return userAddressMapper.deleteUserAddressByAddressIds(addressIds);
    }

    /**
     * 删除用户地址信息信息
     * 
     * @param addressId 用户地址信息主键
     * @return 结果
     */
    @Override
    public int deleteUserAddressByAddressId(Long addressId)
    {
        return userAddressMapper.deleteUserAddressByAddressId(addressId);
    }
}
