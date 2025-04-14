package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.ProductCategoryMapper;
import com.ruoyi.system.domain.ProductCategory;
import com.ruoyi.system.service.IProductCategoryService;

/**
 * 商品类别Service业务层处理
 * 
 * @author hxx
 * @date 2025-04-13
 */
@Service
public class ProductCategoryServiceImpl implements IProductCategoryService 
{
    @Autowired
    private ProductCategoryMapper productCategoryMapper;

    /**
     * 查询商品类别
     * 
     * @param categoryId 商品类别主键
     * @return 商品类别
     */
    @Override
    public ProductCategory selectProductCategoryByCategoryId(Long categoryId)
    {
        return productCategoryMapper.selectProductCategoryByCategoryId(categoryId);
    }

    /**
     * 查询商品类别列表
     * 
     * @param productCategory 商品类别
     * @return 商品类别
     */
    @Override
    public List<ProductCategory> selectProductCategoryList(ProductCategory productCategory)
    {
        return productCategoryMapper.selectProductCategoryList(productCategory);
    }

    /**
     * 新增商品类别
     * 
     * @param productCategory 商品类别
     * @return 结果
     */
    @Override
    public int insertProductCategory(ProductCategory productCategory)
    {
        return productCategoryMapper.insertProductCategory(productCategory);
    }

    /**
     * 修改商品类别
     * 
     * @param productCategory 商品类别
     * @return 结果
     */
    @Override
    public int updateProductCategory(ProductCategory productCategory)
    {
        return productCategoryMapper.updateProductCategory(productCategory);
    }

    /**
     * 批量删除商品类别
     * 
     * @param categoryIds 需要删除的商品类别主键
     * @return 结果
     */
    @Override
    public int deleteProductCategoryByCategoryIds(Long[] categoryIds)
    {
        return productCategoryMapper.deleteProductCategoryByCategoryIds(categoryIds);
    }

    /**
     * 删除商品类别信息
     * 
     * @param categoryId 商品类别主键
     * @return 结果
     */
    @Override
    public int deleteProductCategoryByCategoryId(Long categoryId)
    {
        return productCategoryMapper.deleteProductCategoryByCategoryId(categoryId);
    }
}
