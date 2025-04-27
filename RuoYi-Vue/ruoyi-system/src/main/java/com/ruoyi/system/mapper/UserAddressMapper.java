package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.UserAddress;

/**
 * 用户地址信息Mapper接口
 * 
 * @author hxx
 * @date 2025-04-27
 */
public interface UserAddressMapper 
{
    /**
     * 查询用户地址信息
     * 
     * @param addressId 用户地址信息主键
     * @return 用户地址信息
     */
    public UserAddress selectUserAddressByAddressId(Long addressId);

    /**
     * 查询用户地址信息列表
     * 
     * @param userAddress 用户地址信息
     * @return 用户地址信息集合
     */
    public List<UserAddress> selectUserAddressList(UserAddress userAddress);

    /**
     * 新增用户地址信息
     * 
     * @param userAddress 用户地址信息
     * @return 结果
     */
    public int insertUserAddress(UserAddress userAddress);

    /**
     * 修改用户地址信息
     * 
     * @param userAddress 用户地址信息
     * @return 结果
     */
    public int updateUserAddress(UserAddress userAddress);

    /**
     * 删除用户地址信息
     * 
     * @param addressId 用户地址信息主键
     * @return 结果
     */
    public int deleteUserAddressByAddressId(Long addressId);

    /**
     * 批量删除用户地址信息
     * 
     * @param addressIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteUserAddressByAddressIds(Long[] addressIds);
}
