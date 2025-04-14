package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.ViewProductReviews;

/**
 * 评论详情Mapper接口
 * 
 * @author hxx
 * @date 2025-04-13
 */
public interface ViewProductReviewsMapper 
{
    /**
     * 查询评论详情
     * 
     * @param reviewId 评论详情主键
     * @return 评论详情
     */
    public ViewProductReviews selectViewProductReviewsByReviewId(Long reviewId);

    /**
     * 查询评论详情列表
     * 
     * @param viewProductReviews 评论详情
     * @return 评论详情集合
     */
    public List<ViewProductReviews> selectViewProductReviewsList(ViewProductReviews viewProductReviews);

    /**
     * 新增评论详情
     * 
     * @param viewProductReviews 评论详情
     * @return 结果
     */
    public int insertViewProductReviews(ViewProductReviews viewProductReviews);

    /**
     * 修改评论详情
     * 
     * @param viewProductReviews 评论详情
     * @return 结果
     */
    public int updateViewProductReviews(ViewProductReviews viewProductReviews);

    /**
     * 删除评论详情
     * 
     * @param reviewId 评论详情主键
     * @return 结果
     */
    public int deleteViewProductReviewsByReviewId(Long reviewId);

    /**
     * 批量删除评论详情
     * 
     * @param reviewIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteViewProductReviewsByReviewIds(Long[] reviewIds);
}
