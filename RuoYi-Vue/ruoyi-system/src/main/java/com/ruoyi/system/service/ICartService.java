package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.Cart;

/**
 * 购物车Service接口
 * 
 * @author hxx
 * @date 2025-04-13
 */
public interface ICartService 
{
    /**
     * 查询购物车
     * 
     * @param cartId 购物车主键
     * @return 购物车
     */
    public Cart selectCartByCartId(Long cartId);

    /**
     * 查询购物车列表
     * 
     * @param cart 购物车
     * @return 购物车集合
     */
    public List<Cart> selectCartList(Cart cart);

    /**
     * 新增购物车
     * 
     * @param cart 购物车
     * @return 结果
     */
    public int insertCart(Cart cart);

    /**
     * 修改购物车
     * 
     * @param cart 购物车
     * @return 结果
     */
    public int updateCart(Cart cart);

    /**
     * 批量删除购物车
     * 
     * @param cartIds 需要删除的购物车主键集合
     * @return 结果
     */
    public int deleteCartByCartIds(Long[] cartIds);

    /**
     * 删除购物车信息
     * 
     * @param cartId 购物车主键
     * @return 结果
     */
    public int deleteCartByCartId(Long cartId);
}
