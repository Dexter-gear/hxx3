package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.CartMapper;
import com.ruoyi.system.domain.Cart;
import com.ruoyi.system.service.ICartService;

/**
 * 购物车Service业务层处理
 * 
 * @author hxx
 * @date 2025-04-13
 */
@Service
public class CartServiceImpl implements ICartService 
{
    @Autowired
    private CartMapper cartMapper;

    /**
     * 查询购物车
     * 
     * @param cartId 购物车主键
     * @return 购物车
     */
    @Override
    public Cart selectCartByCartId(Long cartId)
    {
        return cartMapper.selectCartByCartId(cartId);
    }

    /**
     * 查询购物车列表
     * 
     * @param cart 购物车
     * @return 购物车
     */
    @Override
    public List<Cart> selectCartList(Cart cart)
    {
        return cartMapper.selectCartList(cart);
    }

    /**
     * 新增购物车
     * 
     * @param cart 购物车
     * @return 结果
     */
    @Override
    public int insertCart(Cart cart)
    {
        return cartMapper.insertCart(cart);
    }

    /**
     * 修改购物车
     * 
     * @param cart 购物车
     * @return 结果
     */
    @Override
    public int updateCart(Cart cart)
    {
        return cartMapper.updateCart(cart);
    }

    /**
     * 批量删除购物车
     * 
     * @param cartIds 需要删除的购物车主键
     * @return 结果
     */
    @Override
    public int deleteCartByCartIds(Long[] cartIds)
    {
        return cartMapper.deleteCartByCartIds(cartIds);
    }

    /**
     * 删除购物车信息
     * 
     * @param cartId 购物车主键
     * @return 结果
     */
    @Override
    public int deleteCartByCartId(Long cartId)
    {
        return cartMapper.deleteCartByCartId(cartId);
    }
}
