package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 评论详情对象 view_product_reviews
 * 
 * @author hxx
 * @date 2025-04-13
 */
public class ViewProductReviews extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 评论ID */
    @Excel(name = "评论ID")
    private Long reviewId;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    /** 用户昵称 */
    @Excel(name = "用户昵称")
    private String nickName;

    /** 商品ID */
    @Excel(name = "商品ID")
    private Long productId;

    /** 评分（1-5） */
    @Excel(name = "评分", readConverterExp = "1=-5")
    private Long rating;

    /** 评论内容 */
    @Excel(name = "评论内容")
    private String comment;

    /** 评论时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "评论时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdAt;

    public void setReviewId(Long reviewId) 
    {
        this.reviewId = reviewId;
    }

    public Long getReviewId() 
    {
        return reviewId;
    }

    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }

    public void setNickName(String nickName) 
    {
        this.nickName = nickName;
    }

    public String getNickName() 
    {
        return nickName;
    }

    public void setProductId(Long productId) 
    {
        this.productId = productId;
    }

    public Long getProductId() 
    {
        return productId;
    }

    public void setRating(Long rating) 
    {
        this.rating = rating;
    }

    public Long getRating() 
    {
        return rating;
    }

    public void setComment(String comment) 
    {
        this.comment = comment;
    }

    public String getComment() 
    {
        return comment;
    }

    public void setCreatedAt(Date createdAt) 
    {
        this.createdAt = createdAt;
    }

    public Date getCreatedAt() 
    {
        return createdAt;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("reviewId", getReviewId())
            .append("userId", getUserId())
            .append("nickName", getNickName())
            .append("productId", getProductId())
            .append("rating", getRating())
            .append("comment", getComment())
            .append("createdAt", getCreatedAt())
            .toString();
    }
}
