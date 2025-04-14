package com.ruoyi.system.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 商品对象 product
 * 
 * @author hxx
 * @date 2025-04-13
 */
public class Product extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 商品ID */
    private Long productId;

    /** 商品类别ID */
    @Excel(name = "商品类别ID")
    private Long categoryId;

    /** 商品名称 */
    @Excel(name = "商品名称")
    private String name;

    /** 商品描述 */
    @Excel(name = "商品描述")
    private String description;

    /** 商品价格 */
    @Excel(name = "商品价格")
    private BigDecimal price;

    /** 品质等级（1-优，2-良，3-中） */
    @Excel(name = "品质等级", readConverterExp = "1=-优，2-良，3-中")
    private Long qualityLevel;

    /** 库存数量 */
    @Excel(name = "库存数量")
    private Long stock;

    /** 商品图片URL */
    @Excel(name = "商品图片URL")
    private String imageUrl;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdAt;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updatedAt;

    /** 上下架状态 */
    @Excel(name = "上下架状态")
    private Integer pStatus;

    public void setProductId(Long productId) 
    {
        this.productId = productId;
    }

    public Long getProductId() 
    {
        return productId;
    }

    public void setCategoryId(Long categoryId) 
    {
        this.categoryId = categoryId;
    }

    public Long getCategoryId() 
    {
        return categoryId;
    }

    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }

    public void setDescription(String description) 
    {
        this.description = description;
    }

    public String getDescription() 
    {
        return description;
    }

    public void setPrice(BigDecimal price) 
    {
        this.price = price;
    }

    public BigDecimal getPrice() 
    {
        return price;
    }

    public void setQualityLevel(Long qualityLevel) 
    {
        this.qualityLevel = qualityLevel;
    }

    public Long getQualityLevel() 
    {
        return qualityLevel;
    }

    public void setStock(Long stock) 
    {
        this.stock = stock;
    }

    public Long getStock() 
    {
        return stock;
    }

    public void setImageUrl(String imageUrl) 
    {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() 
    {
        return imageUrl;
    }

    public void setCreatedAt(Date createdAt) 
    {
        this.createdAt = createdAt;
    }

    public Date getCreatedAt() 
    {
        return createdAt;
    }

    public void setUpdatedAt(Date updatedAt) 
    {
        this.updatedAt = updatedAt;
    }

    public Date getUpdatedAt() 
    {
        return updatedAt;
    }

    public void setpStatus(Integer pStatus) 
    {
        this.pStatus = pStatus;
    }

    public Integer getpStatus() 
    {
        return pStatus;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("productId", getProductId())
            .append("categoryId", getCategoryId())
            .append("name", getName())
            .append("description", getDescription())
            .append("price", getPrice())
            .append("qualityLevel", getQualityLevel())
            .append("stock", getStock())
            .append("imageUrl", getImageUrl())
            .append("createdAt", getCreatedAt())
            .append("updatedAt", getUpdatedAt())
            .append("pStatus", getpStatus())
            .toString();
    }
}
