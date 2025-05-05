package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 商家回复用户评论对象 review_reply
 * 
 * @author hxx
 * @date 2025-05-05
 */
public class ReviewReply extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 回复评论ID */
    private Long replyId;

    /** 被回复的评论ID */
    @Excel(name = "被回复的评论ID")
    private Long reviewId;

    /** 商家用户ID */
    @Excel(name = "商家用户ID")
    private Long userId;

    /** 回复内容 */
    @Excel(name = "回复内容")
    private String comment;

    /** 回复时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "回复时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdAt;

    public void setReplyId(Long replyId) 
    {
        this.replyId = replyId;
    }

    public Long getReplyId() 
    {
        return replyId;
    }

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
            .append("replyId", getReplyId())
            .append("reviewId", getReviewId())
            .append("userId", getUserId())
            .append("comment", getComment())
            .append("createdAt", getCreatedAt())
            .toString();
    }
}
