package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.VwProductWithUserMapper;
import com.ruoyi.system.domain.VwProductWithUser;
import com.ruoyi.system.service.IVwProductWithUserService;

/**
 * VIEWService业务层处理
 * 
 * @author hxx
 * @date 2025-04-27
 */
@Service
public class VwProductWithUserServiceImpl implements IVwProductWithUserService 
{
    @Autowired
    private VwProductWithUserMapper vwProductWithUserMapper;

    /**
     * 查询VIEW
     * 
     * @param productId VIEW主键
     * @return VIEW
     */
    @Override
    public VwProductWithUser selectVwProductWithUserByProductId(Long productId)
    {
        return vwProductWithUserMapper.selectVwProductWithUserByProductId(productId);
    }

    /**
     * 查询VIEW列表
     * 
     * @param vwProductWithUser VIEW
     * @return VIEW
     */
    @Override
    public List<VwProductWithUser> selectVwProductWithUserList(VwProductWithUser vwProductWithUser)
    {
        return vwProductWithUserMapper.selectVwProductWithUserList(vwProductWithUser);
    }

    /**
     * 新增VIEW
     * 
     * @param vwProductWithUser VIEW
     * @return 结果
     */
    @Override
    public int insertVwProductWithUser(VwProductWithUser vwProductWithUser)
    {
        return vwProductWithUserMapper.insertVwProductWithUser(vwProductWithUser);
    }

    /**
     * 修改VIEW
     * 
     * @param vwProductWithUser VIEW
     * @return 结果
     */
    @Override
    public int updateVwProductWithUser(VwProductWithUser vwProductWithUser)
    {
        return vwProductWithUserMapper.updateVwProductWithUser(vwProductWithUser);
    }

    /**
     * 批量删除VIEW
     * 
     * @param productIds 需要删除的VIEW主键
     * @return 结果
     */
    @Override
    public int deleteVwProductWithUserByProductIds(Long[] productIds)
    {
        return vwProductWithUserMapper.deleteVwProductWithUserByProductIds(productIds);
    }

    /**
     * 删除VIEW信息
     * 
     * @param productId VIEW主键
     * @return 结果
     */
    @Override
    public int deleteVwProductWithUserByProductId(Long productId)
    {
        return vwProductWithUserMapper.deleteVwProductWithUserByProductId(productId);
    }
}
