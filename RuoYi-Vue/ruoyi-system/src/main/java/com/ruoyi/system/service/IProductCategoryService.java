package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.ProductCategory;

/**
 * 商品类别Service接口
 * 
 * @author hxx
 * @date 2025-04-13
 */
public interface IProductCategoryService 
{
    /**
     * 查询商品类别
     * 
     * @param categoryId 商品类别主键
     * @return 商品类别
     */
    public ProductCategory selectProductCategoryByCategoryId(Long categoryId);

    /**
     * 查询商品类别列表
     * 
     * @param productCategory 商品类别
     * @return 商品类别集合
     */
    public List<ProductCategory> selectProductCategoryList(ProductCategory productCategory);

    /**
     * 新增商品类别
     * 
     * @param productCategory 商品类别
     * @return 结果
     */
    public int insertProductCategory(ProductCategory productCategory);

    /**
     * 修改商品类别
     * 
     * @param productCategory 商品类别
     * @return 结果
     */
    public int updateProductCategory(ProductCategory productCategory);

    /**
     * 批量删除商品类别
     * 
     * @param categoryIds 需要删除的商品类别主键集合
     * @return 结果
     */
    public int deleteProductCategoryByCategoryIds(Long[] categoryIds);

    /**
     * 删除商品类别信息
     * 
     * @param categoryId 商品类别主键
     * @return 结果
     */
    public int deleteProductCategoryByCategoryId(Long categoryId);
}
