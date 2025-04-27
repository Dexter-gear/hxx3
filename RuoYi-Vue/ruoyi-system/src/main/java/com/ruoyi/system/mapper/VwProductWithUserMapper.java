package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.VwProductWithUser;

/**
 * VIEWMapper接口
 * 
 * @author hxx
 * @date 2025-04-27
 */
public interface VwProductWithUserMapper 
{
    /**
     * 查询VIEW
     * 
     * @param productId VIEW主键
     * @return VIEW
     */
    public VwProductWithUser selectVwProductWithUserByProductId(Long productId);

    /**
     * 查询VIEW列表
     * 
     * @param vwProductWithUser VIEW
     * @return VIEW集合
     */
    public List<VwProductWithUser> selectVwProductWithUserList(VwProductWithUser vwProductWithUser);

    /**
     * 新增VIEW
     * 
     * @param vwProductWithUser VIEW
     * @return 结果
     */
    public int insertVwProductWithUser(VwProductWithUser vwProductWithUser);

    /**
     * 修改VIEW
     * 
     * @param vwProductWithUser VIEW
     * @return 结果
     */
    public int updateVwProductWithUser(VwProductWithUser vwProductWithUser);

    /**
     * 删除VIEW
     * 
     * @param productId VIEW主键
     * @return 结果
     */
    public int deleteVwProductWithUserByProductId(Long productId);

    /**
     * 批量删除VIEW
     * 
     * @param productIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteVwProductWithUserByProductIds(Long[] productIds);
}
